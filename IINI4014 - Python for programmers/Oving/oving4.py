#import statemenets
from turtle import *
import math


#main methode that holds all variables
def main():
  r = 200 #circle radius
  times = 77 #times to get different shapes
  num = 100 #number of point

  #go to position 0
  pu()
  goto(r, 0)

  drawCircle(r) #draw circle (optional)
  list = drawDots(r, num) #get list of all points alpha value
  drawNum(r, num) #draw number to each point (optional)
  drawLines(r, times, list) #draw lines between points

  done() #turtle if finished

#draw circle based om alpha value
def drawCircle(r):
  step = 0.1 #steps
  a = 0
  while a-step <= 2*math.pi:
    x =  r*math.cos(a)
    y =  r*math.sin(a)
    pd()
    goto(x, y)
    pu()
    a += step

#draw and return list of dots
def drawDots(r, ant):
  a = 0
  arr = []
  pu()
  while a <= 2*math.pi:
    x =  r*math.cos(a)
    y =  r*math.sin(a)
    goto(x, y)
    dot()
    arr.append(a)
    a += 2*math.pi/ant
  return arr

#draw number to each dot
def drawNum(r, ant):
  pu()
  a = 0
  r += 15 #increse r to draw outside dot
  i = 1
  while i <= ant:
    x =  r*math.cos(a)
    y =  r*math.sin(a)
    goto(x, y)
    write(str(i)) #write number
    i += 1
    a += 2*math.pi/ant

#draw lines between points based on multiplier
def drawLines(r, times, arr):
  pu()
  u = generator(arr, times) #uses generoator to calcualte what points to connect
  for i in range(len(arr)-1):
    x =  r*math.cos(arr[i])
    y =  r*math.sin(arr[i])
    goto(x, y) #goto point
    j = next(u) #find next point
    x =  r*math.cos(arr[j])
    y =  r*math.sin(arr[j])
    pd()
    goto(x, y) #draw line
    pu()

def generator(arr, times):
  x = len(arr)
  for i in range(x-1):
    yield ((i*times) % (x-1)) #fined point to go to using mod

#run main methode
main()