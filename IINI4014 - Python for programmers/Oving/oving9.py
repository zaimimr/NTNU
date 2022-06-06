from graphics import *
import math
from random import randint

class Dice:
  sides = []
  def __init__(self, win):
    for i in range(6):
      self.sides.append(Side(i+1))
    self.win = win

  def showSide(self, x, y, dx, dy, dice, dot):
    side = randint(0, 5)
    self.sides[side].draw(self.win, x, y, dx, dy, dice, dot)
    return side
class Side:
  def __init__(self, nr):
    self.nr = nr

  def draw(self, win, x, y, dx, dy, dice, dot):
    p1 = Point(x, y)
    p2 = Point(x+dx, y+dy)
    rect = Rectangle(p1, p2)
    rect.setFill(dice)
    rect.draw(win)
    if(self.nr == 1):
      circle = Circle(Point(x+(dx/2), y+(dy/2)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
    if(self.nr == 2):
      circle = Circle(Point(x+(dx/4), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
    if(self.nr == 3):
      circle = Circle(Point(x+(dx/4), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/2), y+(dy/2)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
    if(self.nr == 4):
      circle = Circle(Point(x+(dx/4), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
    if(self.nr == 5):
      circle = Circle(Point(x+(dx/4), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/2), y+(dy/2)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
    if(self.nr == 6):
      circle = Circle(Point(x+(dx/4*3), y+(dy/2)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4), y+(dy/2)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4*3), y+(dy/4)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)
      circle = Circle(Point(x+(dx/4), y+(dy/4*3)), dy*0.07)
      circle.setFill(dot)
      circle.draw(win)

def initializeGraphics():
  wid = 500
  lc = 'darkgray'
  win = GraphWin(width = wid, height = wid)
  win.setBackground(lc)
  return win

def main():
  win = initializeGraphics()
  dice = Dice(win)
  x = int(input("X "))
  y = int(input("Y "))
  dx = int(input("Size x "))
  dy = int(input("Size y "))
  dc = input("dice ")
  dot = input("dot ")
  side = dice.showSide(x, y, dx, dy, dc, dot)
  print("Pos: " + str(x) + ", " + str(y) +". Size: " + str(dx) + ", " + str(dy) + ". Dice color: " + dc + ", dot color: " + dot + ". Value: " + str((side+1)))
  win.getMouse()

main()