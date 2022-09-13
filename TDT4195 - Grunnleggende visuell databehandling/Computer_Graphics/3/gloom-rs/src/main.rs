extern crate nalgebra_glm as glm;
use gl;
use std::sync::{Arc, Mutex, RwLock};
use std::thread;
use std::{mem, os::raw::c_void, ptr};
mod shader;
mod util;

mod mesh;
mod scene_graph;
use scene_graph::SceneNode;
mod toolbox;

use glutin::event::{
    DeviceEvent,
    ElementState::{Pressed, Released},
    Event, KeyboardInput,
    VirtualKeyCode::{self, *},
    WindowEvent,
};
use glutin::event_loop::ControlFlow;

const SCREEN_W: u32 = 800;
const SCREEN_H: u32 = 600;

// == // Helper functions to make interacting with OpenGL a little bit prettier. You *WILL* need these! // == //
// The names should be pretty self explanatory
fn byte_size_of_array<T>(val: &[T]) -> isize {
    std::mem::size_of_val(&val[..]) as isize
}

// Get the OpenGL-compatible pointer to an arbitrary array of numbers
fn pointer_to_array<T>(val: &[T]) -> *const c_void {
    &val[0] as *const T as *const c_void
}

// Get the size of the given type in bytes
fn size_of<T>() -> i32 {
    mem::size_of::<T>() as i32
}

// Get an offset in bytes for n units of type T
fn offset<T>(n: u32) -> *const c_void {
    (n * mem::size_of::<T>() as u32) as *const T as *const c_void
}

// Get a null pointer (equivalent to an offset of 0)
// ptr::null()

// == // Modify and complete the function below for the first task
unsafe fn vao(vertex_cords: &Vec<f32>, indices: &Vec<u32>, colors: &Vec<f32>, normals: &Vec<f32>) -> u32 {
    let mut array_id: gl::types::GLuint = 0;
    gl::GenVertexArrays(1, &mut array_id);
    gl::BindVertexArray(array_id);

    let mut buffer_id: gl::types::GLuint = 0;
    gl::GenBuffers(1, &mut buffer_id);
    gl::BindBuffer(gl::ARRAY_BUFFER, buffer_id);
    gl::BufferData(
        gl::ARRAY_BUFFER,
        byte_size_of_array(vertex_cords),
        pointer_to_array(vertex_cords),
        gl::STATIC_DRAW,
    );
    gl::VertexAttribPointer(0, 3, gl::FLOAT, gl::FALSE, 0, ptr::null());
    gl::EnableVertexAttribArray(0);

    let mut colors_buffer_id: gl::types::GLuint = 0;
    gl::GenBuffers(1, &mut colors_buffer_id);
    gl::BindBuffer(gl::ARRAY_BUFFER, colors_buffer_id);
    gl::BufferData(
        gl::ARRAY_BUFFER,
        byte_size_of_array(colors),
        pointer_to_array(colors),
        gl::STATIC_DRAW,
    );
    gl::VertexAttribPointer(1, 4, gl::FLOAT, gl::FALSE, 0, ptr::null());
    gl::EnableVertexAttribArray(1);

    let mut normal_buffer_id: gl::types::GLuint = 0;
    gl::GenBuffers(1, &mut normal_buffer_id);
    gl::BindBuffer(gl::ARRAY_BUFFER, normal_buffer_id);
    gl::BufferData(
        gl::ARRAY_BUFFER,
        byte_size_of_array(normals),
        pointer_to_array(normals),
        gl::STATIC_DRAW,
    );
    gl::VertexAttribPointer(3, 3, gl::FLOAT, gl::FALSE, 0, ptr::null());
    gl::EnableVertexAttribArray(3);

    let mut index_buffer_id: gl::types::GLuint = 0;
    gl::GenBuffers(1, &mut index_buffer_id);
    gl::BindBuffer(gl::ELEMENT_ARRAY_BUFFER, index_buffer_id);
    gl::BufferData(
        gl::ELEMENT_ARRAY_BUFFER,
        byte_size_of_array(indices),
        pointer_to_array(indices),
        gl::STATIC_DRAW,
    );

    return array_id;
}

struct CameraPosition {
    position: glm::Vec3,
    rotation: glm::Vec2,
}

unsafe fn draw_scene(node: &scene_graph::SceneNode, view_projection_matrix: &glm::Mat4) {
    // Check if node is drawable, set uniforms, draw
    if node.index_count > 0 {
        gl::UniformMatrix4fv(2, 1, 0, (view_projection_matrix * node.current_transformation_matrix).as_ptr());
        gl::UniformMatrix4fv(4, 1, 0, (node.current_transformation_matrix).as_ptr());
        gl::BindVertexArray(node.vao_id);
        gl::DrawElements(gl::TRIANGLES, node.index_count, gl::UNSIGNED_INT, ptr::null());
    }
    // Recurse
    for &child in &node.children {
        draw_scene(&*child, view_projection_matrix);
    }
}    

unsafe fn update_node_transformations(node: &mut scene_graph::SceneNode, transformation_so_far: &glm::Mat4) {
    // Construct the correct transformation matrix
    let origin: glm::Mat4 = glm::translation(&node.reference_point);
    let inverse_origin: glm::Mat4 = glm::inverse(&origin);
    let position: glm::Mat4 = glm::translation(&node.position);

    let x_rotation: glm::Mat4 = glm::rotation(node.rotation[0], &glm::vec3(1.0, 0.0, 0.0));
    let y_rotation: glm::Mat4 = glm::rotation(node.rotation[1], &glm::vec3(0.0, 1.0, 0.0));
    let z_rotation: glm::Mat4 = glm::rotation(node.rotation[2], &glm::vec3(0.0, 0.0, 1.0));
    // Update the node's transformation matrix
    node.current_transformation_matrix = transformation_so_far * position * origin * z_rotation * y_rotation * x_rotation * inverse_origin;
    // Recurse
    for &child in &node.children {
        update_node_transformations(&mut *child,
        &node.current_transformation_matrix);
    }
}

fn main() {
    // Set up the necessary objects to deal with windows and event handling
    let el = glutin::event_loop::EventLoop::new();
    let wb = glutin::window::WindowBuilder::new()
        .with_title("Gloom-rs")
        .with_resizable(false)
        .with_inner_size(glutin::dpi::LogicalSize::new(SCREEN_W, SCREEN_H));
    let cb = glutin::ContextBuilder::new().with_vsync(true);
    let windowed_context = cb.build_windowed(wb, &el).unwrap();
    // Uncomment these if you want to use the mouse for controls, but want it to be confined to the screen and/or invisible.
    // windowed_context.window().set_cursor_grab(true).expect("failed to grab cursor");
    // windowed_context.window().set_cursor_visible(false);

    // Set up a shared vector for keeping track of currently pressed keys
    let arc_pressed_keys = Arc::new(Mutex::new(Vec::<VirtualKeyCode>::with_capacity(10)));
    // Make a reference of this vector to send to the render thread
    let pressed_keys = Arc::clone(&arc_pressed_keys);

    // Set up shared tuple for tracking mouse movement between frames
    let arc_mouse_delta = Arc::new(Mutex::new((0f32, 0f32)));
    // Make a reference of this tuple to send to the render thread
    let mouse_delta = Arc::clone(&arc_mouse_delta);

    // Spawn a separate thread for rendering, so event handling doesn't block rendering
    let render_thread = thread::spawn(move || {
        // Acquire the OpenGL Context and load the function pointers. This has to be done inside of the rendering thread, because
        // an active OpenGL context cannot safely traverse a thread boundary
        let context = unsafe {
            let c = windowed_context.make_current().unwrap();
            gl::load_with(|symbol| c.get_proc_address(symbol) as *const _);
            c
        };

        // Set up openGL
        unsafe {
            gl::Enable(gl::DEPTH_TEST);
            gl::DepthFunc(gl::LESS);
            gl::Enable(gl::CULL_FACE);
            gl::Disable(gl::MULTISAMPLE);
            gl::Enable(gl::BLEND);
            gl::BlendFunc(gl::SRC_ALPHA, gl::ONE_MINUS_SRC_ALPHA);
            gl::Enable(gl::DEBUG_OUTPUT_SYNCHRONOUS);
            gl::DebugMessageCallback(Some(util::debug_callback), ptr::null());

            // Print some diagnostics
            println!(
                "{}: {}",
                util::get_gl_string(gl::VENDOR),
                util::get_gl_string(gl::RENDERER)
            );
            println!("OpenGL/t: {}", util::get_gl_string(gl::VERSION));
            println!(
                "GLSL/t: {}",
                util::get_gl_string(gl::SHADING_LANGUAGE_VERSION)
            );
        }

        // == // Set up your VAO here
        let terrain: mesh::Mesh = mesh::Terrain::load("./resources/lunarsurface.obj");
        let terrain_vao = unsafe { vao(&terrain.vertices, &terrain.indices, &terrain.colors, &terrain.normals) };
            
        let helicopter: mesh::Helicopter = mesh::Helicopter::load("./resources/helicopter.obj") ;
        let helicopter_body_vao = unsafe { vao(&helicopter.body.vertices, &helicopter.body.indices, &helicopter.body.colors, &helicopter.body.normals) };
        let helicopter_door_vao = unsafe { vao(&helicopter.door.vertices, &helicopter.door.indices, &helicopter.door.colors, &helicopter.door.normals) };
        let helicopter_main_vao = unsafe { vao(&helicopter.main_rotor.vertices, &helicopter.main_rotor.indices, &helicopter.main_rotor.colors, &helicopter.main_rotor.normals) };
        let helicopter_tail_vao = unsafe { vao(&helicopter.tail_rotor.vertices, &helicopter.tail_rotor.indices, &helicopter.tail_rotor.colors, &helicopter.tail_rotor.normals) };

        let mut root_node = scene_graph::SceneNode::new();
        let mut terrain_node = scene_graph::SceneNode::from_vao(terrain_vao, terrain.index_count);
        
        let number_of_helicopters = 5;
        for _x in 0..number_of_helicopters {
            let mut helicopter_body_node = scene_graph::SceneNode::from_vao(helicopter_body_vao, helicopter.body.index_count);
            helicopter_body_node.add_child(&scene_graph::SceneNode::from_vao(helicopter_door_vao, helicopter.door.index_count));
            helicopter_body_node.add_child(&scene_graph::SceneNode::from_vao(helicopter_main_vao, helicopter.main_rotor.index_count));
            helicopter_body_node.add_child(&scene_graph::SceneNode::from_vao(helicopter_tail_vao, helicopter.tail_rotor.index_count));
            helicopter_body_node[2].reference_point = glm::vec3(0.35, 2.3, 10.4);
            terrain_node.add_child(&helicopter_body_node);
        }
        

        root_node.add_child(&terrain_node);
        
        
        let shader = unsafe {
            shader::ShaderBuilder::new()
                .attach_file("./shaders/simple.frag")
                .attach_file("./shaders/simple.vert")
                .link()
        };

        unsafe {
            shader.activate();
        }

        // Used to demonstrate keyboard handling -- feel free to remove
        let mut _arbitrary_number = 0.0;

        let first_frame_time = std::time::Instant::now();
        let mut last_frame_time = first_frame_time;

        // angle, x, y, z
        let mut camera_pos = CameraPosition{
            position: glm::vec3(0.0, -0.5, 10.0),
            rotation: glm::vec2(0.0, 3.14),
        };

        let speed = 10.0;
        let rotate_speed = 5.0;

        let mut door_open = false;

        // The main rendering loop
        loop {
            // println!("x: {}, y: {}, z: {}, xa: {}, ya: {}", camera_pos.x, camera_pos.y, camera_pos.z, camera_pos.x_angle, camera_pos.y_angle);
            let now = std::time::Instant::now();
            let elapsed = now.duration_since(first_frame_time).as_secs_f32();
            let delta_time = now.duration_since(last_frame_time).as_secs_f32();
            last_frame_time = now;

            // Handle keyboard input
            if let Ok(keys) = pressed_keys.lock() {
                for key in keys.iter() {
                    match key {
                        VirtualKeyCode::W => {
                            camera_pos.position.z +=  delta_time*speed;
                        }
                        VirtualKeyCode::A => {
                            camera_pos.position.x +=  delta_time*speed;
                        }
                        VirtualKeyCode::S => {
                            camera_pos.position.z -=  delta_time*speed;
                        }
                        VirtualKeyCode::D => {
                            camera_pos.position.x -=  delta_time*speed;
                        }
                        VirtualKeyCode::Q => {
                            camera_pos.position.y += delta_time*speed;
                        }
                        VirtualKeyCode::E => {
                            camera_pos.position.y -= delta_time*speed;
                        }
                        VirtualKeyCode::Up => {
                            camera_pos.rotation.x -= delta_time*rotate_speed;
                        }
                        VirtualKeyCode::Down => {
                            camera_pos.rotation.x += delta_time*rotate_speed;
                        }
                        VirtualKeyCode::Left => {
                            camera_pos.rotation.y -= delta_time*rotate_speed;
                        }
                        VirtualKeyCode::Right => {
                            camera_pos.rotation.y += delta_time*rotate_speed;
                        }
                        VirtualKeyCode::Z => {
                            door_open = true;
                        }
                        VirtualKeyCode::X => {
                            door_open = false;
                        }
                       _ => {}
                    }
                }
            }
            // Handle mouse movement. delta contains the x and y movement of the mouse since last frame in pixels
            if let Ok(mut delta) = mouse_delta.lock() {
                *delta = (0.0, 0.0);
            }

            unsafe {
                gl::ClearColor(0.76862745, 0.71372549, 0.94901961, 1.0); // moon raker, full opacity
                gl::Clear(gl::COLOR_BUFFER_BIT | gl::DEPTH_BUFFER_BIT);

                let perspective_transform: glm::Mat4 = glm::perspective(1.0, 1.0, 1.0, 1000.0);
                let translation: glm::Mat4 = glm::translation(&camera_pos.position);

                let x_rotation: glm::Mat4 = glm::rotation(camera_pos.rotation.x, &glm::vec3(1.0, 0.0, 0.0));
                let y_rotation: glm::Mat4 = glm::rotation(camera_pos.rotation.y, &glm::vec3(0.0, 1.0, 0.0));
                
                let matrix: glm::Mat4 =  perspective_transform * x_rotation * y_rotation * translation;

                for i in 0..terrain_node.get_n_children() {
                    let heading = toolbox::simple_heading_animation(elapsed+i as f32);
                    terrain_node.get_child(i).get_child(1).rotation.y += elapsed;
                    terrain_node.get_child(i).get_child(2).rotation.x += elapsed;
                    // Animation for the door
                    if(door_open) {
                        if (terrain_node.get_child(i).get_child(0).position.z <= 2.0){
                            terrain_node.get_child(i).get_child(0).position.z += 0.1;
                        }
                    } else {
                        if (terrain_node.get_child(i).get_child(0).position.z >= 0.0){
                            terrain_node.get_child(i).get_child(0).position.z -= 0.1;
                        }
                    }
                    terrain_node.get_child(i).position = glm::vec3(heading.x, terrain_node[i].position.y, heading.z);;
                    terrain_node.get_child(i).rotation = glm::vec3(heading.pitch, heading.yaw, heading.roll);
                }
                
                update_node_transformations(&mut root_node, &glm::identity());          
                draw_scene(&root_node, &matrix);
            }

            context.swap_buffers().unwrap();
        }
    });

    // Keep track of the health of the rendering thread
    let render_thread_healthy = Arc::new(RwLock::new(true));
    let render_thread_watchdog = Arc::clone(&render_thread_healthy);
    thread::spawn(move || {
        if !render_thread.join().is_ok() {
            if let Ok(mut health) = render_thread_watchdog.write() {
                println!("Render thread panicked!");
                *health = false;
            }
        }
    });

    // Start the event loop -- This is where window events get handled
    el.run(move |event, _, control_flow| {
        *control_flow = ControlFlow::Wait;

        // Terminate program if render thread panics
        if let Ok(health) = render_thread_healthy.read() {
            if *health == false {
                *control_flow = ControlFlow::Exit;
            }
        }

        match event {
            Event::WindowEvent {
                event: WindowEvent::CloseRequested,
                ..
            } => {
                *control_flow = ControlFlow::Exit;
            }
            // Keep track of currently pressed keys to send to the rendering thread
            Event::WindowEvent {
                event:
                    WindowEvent::KeyboardInput {
                        input:
                            KeyboardInput {
                                state: key_state,
                                virtual_keycode: Some(keycode),
                                ..
                            },
                        ..
                    },
                ..
            } => {
                if let Ok(mut keys) = arc_pressed_keys.lock() {
                    match key_state {
                        Released => {
                            if keys.contains(&keycode) {
                                let i = keys.iter().position(|&k| k == keycode).unwrap();
                                keys.remove(i);
                            }
                        }
                        Pressed => {
                            if !keys.contains(&keycode) {
                                keys.push(keycode);
                            }
                        }
                    }
                }

                // Handle escape separately
                match keycode {
                    Escape => {
                        *control_flow = ControlFlow::Exit;
                    }
                    _ => {}
                }
            }
            Event::DeviceEvent {
                event: DeviceEvent::MouseMotion { delta },
                ..
            } => {
                // Accumulate mouse movement
                if let Ok(mut position) = arc_mouse_delta.lock() {
                    *position = (position.0 + delta.0 as f32, position.1 + delta.1 as f32);
                }
            }
            _ => {}
        }
    });
}
