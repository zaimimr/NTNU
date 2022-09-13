mod draw;
mod cell;
mod astar;

extern crate glutin_window;
extern crate graphics;
extern crate opengl_graphics;
extern crate piston;
extern crate csv;
extern crate ndarray;
extern crate ndarray_csv;

use csv::{ReaderBuilder, WriterBuilder};
use ndarray::{Array, Array2};
use ndarray_csv::{Array2Reader, Array2Writer};
use std::error::Error;
use std::fs::File;
use std::borrow::Borrow;
use std::{env, process};

use glutin_window::GlutinWindow as Window;
use opengl_graphics::{GlGraphics, OpenGL};
use piston::event_loop::{EventSettings, Events};
use piston::input::{RenderArgs, RenderEvent, UpdateArgs, UpdateEvent};
use piston::window::WindowSettings;
use crate::draw::Draw;
use std::collections::HashMap;
use piston::keyboard::Key::Hash;
use crate::astar::astar_solve;


fn main() {
    let args: Vec<String> = env::args().collect();
    let task_number = if Some(&args[1]).is_some() {
        args[1].parse::<i32>().unwrap()
    } else {
        1
    };
    let path: String;
    let start_pos: [i32; 2];
    let goal_pos: [i32; 2];
    match task_number {
        1 => {
            path = "src/Samfundet_map_1.csv".parse().unwrap();
            start_pos = [27, 18];
            goal_pos = [40,32];
        }
        2 => {
            path = "src/Samfundet_map_1.csv".parse().unwrap();
            start_pos = [40, 32];
            goal_pos = [8,5];
        }
        3 => {
            path = "src/Samfundet_map_2.csv".parse().unwrap();
            start_pos = [28, 32];
            goal_pos = [6,32];
        }
        4 => {
            path = "src/Samfundet_map_Edgar_full.csv".parse().unwrap();
            start_pos = [28,32];
            goal_pos = [6,32];
        }
        _ => {
            println!("You must enter a task between 1 and 4");
            process::exit(0x0100);

        }
    }
    let blocks = read_from_file(&path).unwrap();

    // Change this to OpenGL::V2_1 if not working.
    let opengl = OpenGL::V3_2;
    let (width, height) = (800.0, 800.0);

    let new_blocks= astar_solve(blocks.clone(), &start_pos, &goal_pos);

    // Create an Glutin window.
    let mut window: Window = WindowSettings::new("spinning-square", [width, height])
        .graphics_api(opengl)
        .exit_on_esc(true)
        .build()
        .unwrap();

    // Create a new game and run it.
    let mut draw = Draw {
        gl: GlGraphics::new(opengl),
        width,
        height,
        blocks : new_blocks,
    };

    let mut events = Events::new(EventSettings::new());
    while let Some(e) = events.next(&mut window) {
        if let Some(args) = e.render_args() {
            draw.render(&args);
        }

        if let Some(args) = e.update_args() {
            draw.update(&args);
        }
    }
}




fn read_from_file(path: &str) -> Result<Vec<Vec<i32>>, Box<dyn Error>> {
    // Build the CSV reader and iterate over each record.
    let mut blocks : Vec<Vec<i32>>= Vec::new();
    let mut reader = ReaderBuilder::new().has_headers(false).from_path(path)?;
    for result in reader.records() {
        let mut temp_vec: Vec<i32> = Vec::new();

        for cell in  result.unwrap().iter() {
            let value : i32 = cell.parse().unwrap();
            temp_vec.push(value);
        }
        blocks.push(temp_vec);
    }
    Ok(blocks)
}