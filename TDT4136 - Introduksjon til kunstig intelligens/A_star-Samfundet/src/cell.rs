use pathfinding::num_traits::abs;

pub struct cell {
    pub parent: [i32; 2],
    pub f: i32,
    pub g: i32,
    pub h: i32,
    pub x: i32,
    pub y: i32,
    pub cost: i32,
}

impl Default for cell {
    fn default() -> Self {
        cell {
            parent: [-1, -1],
            f: -1,
            g: 0,
            h: -1,
            x: -1,
            y: -1,
            cost: 1,
        }
    }
}
