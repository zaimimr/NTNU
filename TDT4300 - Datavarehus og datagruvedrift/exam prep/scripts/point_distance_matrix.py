from enum import Enum
import math

import numpy

DIVIDER = False
K = 3.0
DIST_ALGO_INDEX = 0
DIST_ALGOS = ["MANHATTAN", "EUCLIDEAN"]
POINTS = [
        (4, 8),
        (8, 8),
        (8, 4),
        (6, 7),
        
        (1, 10),
        (3, 6),
        (2, 4),
        (1, 7),
        
        (6, 4),
        (6, 2),
        (6, 3),
        (4, 3),
        (4, 4),
    ]
CENTROIDS = [
    (6, 6),
    (4, 6),
    (4, 5),
    (2, 6),
]

class Point:
    def __init__(self, x, y, index=""):
        self.x = x
        self.y = y
        self.index = index
    def __repr__(self):
        return f"P{self.index}({self.x}, {self.y})"

def distance(p1:Point, p2:Point):
    if DIST_ALGOS[DIST_ALGO_INDEX] == DIST_ALGOS[0]: #Manhatten
        return abs( p1.x - p2.x ) + abs( p1.y - p2.y )
    elif DIST_ALGOS[DIST_ALGO_INDEX] == DIST_ALGOS[1]: #Euchlidean
        return math.sqrt( ( p2.x - p1.x ) ** 2 + ( p2.y - p1.y ) ** 2 )

def distance_matrix(points, centroids):
    first_row = " "*8
    for point in points:
        first_row += f"({point.x},{point.y}) "
    print(first_row)
    for point1 in centroids:
        if DIVIDER:
            print("-"*len(first_row))
        output = f"({point1.x},{point1.y})"
        if len(output) == 5:
            output += " " * 5
        elif len(output) == 6:
            output += " " * 4
            
        for point2 in points:
            dist = distance(point1, point2)
            if dist <= K:
                output += f"\033[32m{dist}\033[39m"
            else:
                output += f"{dist}"
            space = len(f"({point2.x},{point2.y}) ")-1
            if len(str(dist)) == 1:
                output += " " * space
            elif len(str(dist)) == 2:
                output += " " * (space-1)
        print(output)

if __name__ == '__main__':
    points = [Point(node[0], node[1], (i+1)) for i, node in enumerate(POINTS)]
    centroids = [Point(node[0], node[1]) for node in CENTROIDS]
    distance_matrix(points, centroids)
    