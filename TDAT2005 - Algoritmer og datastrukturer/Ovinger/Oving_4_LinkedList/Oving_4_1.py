from itertools import cycle

list = (range(1, 41))

def Josephus(list):
  arr = cycle(list)
  counter = 0;
  index = 0;
  run = True
  print(list)
  while next(arr) and run:

    if counter==3:
      print("Fjern: " + str(list[index]))
      del list[index]
      arr = cycle(list)
      index = index-1
      print(list)

    index = (index+1) % len(list)
    counter = (counter+1) % 4

    if(len(list) == 1):
      run = False
      return next(arr)

print("Svaret er: " + str(Josephus(list)))