from graphics import *
import math

wid = 500
nr = 10
px = wid/nr
lineWidth = px/(nr)
bc = 'black'
lc = 'gray'
cc = 'white'

#initialiserer vindu
def initializeGraphics():
  win = GraphWin(width = wid, height = wid)
  win.setBackground(bc)
  return win

#tegner vertikale linjene
def drawVLine(win):
  for x in range(1, nr):
      mySquare = Line(Point(x*px, 0), Point(x*px, wid))
      mySquare.setWidth(lineWidth)
      mySquare.setFill(lc)
      mySquare.draw(win)

#tegner horisontale linjene
def drawHLine(win):
  for y in range(1, nr):
      mySquare = Line(Point(0, y*px), Point(wid, y*px))
      mySquare.setWidth(lineWidth)
      mySquare.setFill(lc)
      mySquare.draw(win)

def drawCircles(win):
  for x in range(1, nr):
    for y in range(1, nr):
      mySquare = Circle(Point(x*px, y*px), lineWidth/2)
      mySquare.setFill(cc)
      mySquare.setOutline(cc)
      mySquare.draw(win)

def main():
  win = initializeGraphics()
  drawVLine(win)
  drawHLine(win)
  drawCircles(win)
  win.getMouse()

main()