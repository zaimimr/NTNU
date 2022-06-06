from enum import Enum
import math
from dbscan import MINPTS, EPS, POINTS, Point, distance
DIVIDER = False

def distance_matrix(points):
    density = [0]*len(points)
    first_row = " "*8
    for point in points:
        first_row += f"({point.x},{point.y}) "
    print(first_row)
    for point1 in points:
        if DIVIDER:
            print("-"*len(first_row))
        output = f"({point1.x},{point1.y})"
        if len(output) == 5:
            output += " " * 5
        elif len(output) == 6:
            output += " " * 4
            
        for i, point2 in enumerate(points):
            dist = distance(point1, point2)
            if dist <= EPS:
                density[i] += 1
                output += f"\033[32m{dist}\033[39m"
            else:
                output += f"{dist}"
            space = len(f"({point2.x},{point2.y}) ")-1
            if len(str(dist)) == 1:
                output += " " * space
            elif len(str(dist)) == 2:
                output += " " * (space-1)
            
            
        print(output)
    print("-"*len(first_row))
    output = "DENSITY:"
    for d in density:
        if d >= MINPTS:
            output += f"  \033[32m{d}\033[39m  "
        else:
            output += f"  {d}  "
        if len(str(d)) == 1:
            output += " "
    print(output)
            


if __name__ == '__main__':
    points = [Point(node[0], node[1],(i+1)) for i, node in enumerate(POINTS)]
    distance_matrix(points)

    