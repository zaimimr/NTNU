extern crate glutin_window;
extern crate graphics;
extern crate opengl_graphics;
extern crate piston;

use glutin_window::GlutinWindow as Window;
use opengl_graphics::{GlGraphics, OpenGL};
use piston::event_loop::{EventSettings, Events};
use piston::input::{RenderArgs, RenderEvent, UpdateArgs, UpdateEvent};
use piston::window::WindowSettings;
use graphics::types::{Rectangle, Matrix2d};
use graphics::Context;
use std::any::Any;
use crate::graphics::Transformed;
use std::borrow::Borrow;

pub struct Draw {
    pub(crate) gl: GlGraphics, // OpenGL drawing backend.
    pub(crate) width: f64,
    pub(crate) height: f64,
    pub(crate) blocks: Vec<Vec<i32>>,
}
const BLACK: [f32; 4] = [0.0, 0.0, 0.0, 1.0];
const WHITE: [f32; 4] = [1.0, 1.0, 1.0, 1.0];
const YELLOW: [f32; 4] = [1.0, 1.0, 0.0, 1.0];
const BLUE: [f32; 4] = [0.0, 0.0, 1.0, 1.0];
const RED: [f32; 4] = [1.0, 0.0, 0.0, 1.0];
const LIGHTGRAY: [f32; 4] = [0.823, 0.823, 0.823, 1.0];
const GRAY: [f32; 4] = [0.5, 0.5, 0.5, 1.0];
const DARKGRAY: [f32; 4] = [0.18, 0.31, 0.31, 1.0];




impl Draw {
    pub(crate) fn render(&mut self, args: &RenderArgs) {
        use graphics::*;
        let blocks = &self.blocks;
        let box_size: f64 = self.width / blocks.len() as f64;



        self.gl.draw(args.viewport(), |c, gl| {
            // Clear the screen.
            clear(RED, gl);

            // Draw a box rotating around the middle of the screen.
            let mut xp:f64 = 0.0;
            let mut yp :f64 = 0.0;
            for block in blocks {
                for cell in block {
                    let x = xp*box_size;
                    let y = yp*box_size;
                    match cell {
                        -2 => START_END(x, y, c, gl, box_size),
                        -1 => Wall(x, y , c, gl, box_size),
                        0 => Visited(x, y, c, gl, box_size),
                        1 => Floor(x, y, c, gl, box_size),
                        2 => Stair(x, y, c, gl, box_size),
                        3 => Packed_Stair(x, y, c, gl, box_size),
                        4 => Packed_Room(x, y, c, gl, box_size),
                        _ => ()
                    }
                    xp+=1.0;
                }
                yp+=1.0;
                xp=0.0;
            }


        });
    }

    pub(crate) fn update(&mut self, args: &UpdateArgs) {
        // Rotate 2 radians per second.
        //self.rotation += 2.0 * args.dt;
    }
}

fn Wall(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(RED, square, c
        .transform, gl)
}

fn Floor(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(LIGHTGRAY, square, c
        .transform, gl)
}

fn Stair(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(GRAY, square, c
        .transform, gl)
}

fn Packed_Stair(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(DARKGRAY, square, c
        .transform, gl)
}
fn START_END(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(DARKGRAY, square, c
        .transform, gl)
}
fn Packed_Room(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(BLACK, square, c
        .transform, gl)
}



fn Visited(x: f64, y: f64, c: Context, gl: &mut GlGraphics, box_size: f64) {
    use graphics::ellipse;
    use graphics::rectangle;

    let square = rectangle::square(x, y, box_size);
    rectangle(YELLOW, square, c
        .transform, gl);

    let circle = ellipse::circle(x, y, box_size/4.0);

    ellipse(BLUE, circle, c.transform
        .trans(box_size/2.0, box_size/2.0), gl)
}