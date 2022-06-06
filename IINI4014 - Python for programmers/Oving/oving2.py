import math

def ArchimedesPI(i):
  n = 6
  s = 1
  for x in range(i):
    a = math.sqrt(1-(math.pow(s/2, 2)))
    b = 1-a
    s = math.sqrt(b*b+(math.pow(s/2, 2)))
    n=n*2
  p = n*s/2
  print("Pi er: " + str(p))

ArchimedesPI(100)