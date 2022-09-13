use pathfinding::num_traits::abs;
use priority_queue::DoublePriorityQueue;
use priority_queue::PriorityQueue;
use std::collections::{HashMap, HashSet};
use crate::cell::cell;
use std::borrow::Borrow;
use serde::__private::de::Borrowed;
use std::cmp::{Ordering, Reverse};


pub fn astar_solve(grid: Vec<Vec<i32>>, &start: &[i32; 2], &destination: &[i32;2]) -> Vec<Vec<i32>> {
    //List with cells that have been visited and then closed
    let mut closed_list : HashSet<(i32, i32)> = HashSet::new();

    // PriorityQueue to store the cells to be visited, and ordering the one with the least f-value
    let mut open_list : PriorityQueue<(i32, i32), i32> = PriorityQueue::new();

    // Using a hashmap for efficient look up of cells
    let mut cells: HashMap<(i32, i32), cell> = HashMap::new();
    for (x, row) in grid.iter().enumerate() {
        for (y, cell) in row.iter().enumerate() {
            cells.insert((x as i32, y as i32), cell{cost: *cell, x: x as i32, y: y as i32, ..Default::default()});
        }
    }

    // Inserts the start location to the priority queue and cell list
    cells.insert((start[0], start[1]), cell{x: start[0], y: start[1], parent: [start[0], start[1]], ..Default::default()});
    open_list.push((start[0], start[1]), 0);

    while !open_list.is_empty() {
        // P is the cell with the lowest f-value
        if let Some(p) = open_list.peek() {
            //i and j are the coordinates of the cell we are currently working on
            let i : i32 = p.0.0;
            let j : i32 = p.0.1;
            open_list.pop();

            closed_list.insert((i, j));

            // Since we are only allowing vertical and horizontal moves there will only be four possible neigbours
            let potential_neighbours : [[i32; 2]; 4] = [[i, j + 1], [i, j-1], [i + 1, j], [i-1, j], ];
            for potential_neighbour in potential_neighbours {
                let x = potential_neighbour[0];
                let y = potential_neighbour[1];
                if is_valid(grid.clone(), potential_neighbour) {
                    if is_destination(potential_neighbour, destination) {
                        let mut cell = cells.get_mut(&(x, y)).unwrap();
                        cell.parent = [i,j];

                        return generate_grid_with_visited_cells(grid, cells,[i,j], start, destination);
                    }
                        // If it is a not a wall and not been checked earlier
                    else if !closed_list.contains(&(x, y)) && is_unblocked(grid.clone(), [x,y]) {
                        let new_g = cells.get(&(i, j)).unwrap().g + cells.get(&(i,j)).unwrap().cost;
                        let new_h = calculate_heuristic_value([x, y], destination);
                        let new_f = new_g + new_h;

                        if cells.get(&(x, y)).unwrap().f == -1 || cells.get(&(x, y)).unwrap().f > new_f {
                            // Multiply with -1 to avoid priority queue choosing the highest f-value
                            open_list.push((x, y), new_f * -1);
                            let mut cell = cells.get_mut(&(x, y)).unwrap();
                            cell.g = new_g;
                            cell.h = new_h;
                            cell.f = new_f;
                            cell.parent = [i,j];
                        }
                    }

                }
            }
        }
    }
    println!("Did not find anything");
    return grid;

}

pub fn is_destination(point: [i32; 2], destination: [i32; 2]) -> bool {
    point[0] == destination[0 as usize] && point[1 as usize] == destination[1 as usize]
}

pub fn is_valid(grid: Vec<Vec<i32>>, point: [i32; 2]) -> bool {
    point[0] >= 0 && point[0] < grid.len() as i32 && point[1] >= 0 && point[1] < grid[0].len() as i32
}

pub fn is_unblocked(grid: Vec<Vec<i32>>, point: [i32; 2]) -> bool {
    let cell : i32 = grid[point[0 as usize] as usize][point[1 as usize] as usize];
    is_valid(grid, point) && cell >= 1
}

pub fn calculate_heuristic_value(point: [i32; 2], destination: [i32; 2]) -> i32 {
    let dx = abs(point[0] - destination[0]);
    let dy = abs(point[1] - destination[1]);
    (dx + dy)
}

fn generate_grid_with_visited_cells(original_grid: Vec<Vec<i32>>, cells: HashMap<(i32, i32), cell>, current_cell: [i32; 2], start_pos: [i32;2], destination: [i32;2] ) -> Vec<Vec<i32>> {
    let mut final_grid = original_grid.clone();
    let mut curr = current_cell;
    while curr[0] != start_pos[0] || curr[1] != start_pos[1] {
        final_grid[curr[0] as usize][curr[1] as usize] = 0;
        curr = cells.get(&(curr[0], curr[1])).unwrap().parent;
    }

    // For illustration purpose we display the start and destination-cell differently
    final_grid[start_pos[0] as usize][start_pos[1] as usize] = -2;
    final_grid[destination[0] as usize][destination[1] as usize] = -2;

    return final_grid;
}