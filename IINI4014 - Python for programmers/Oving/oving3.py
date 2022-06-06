from time import time

#Function prime to find n first prime numbers.
def prime(n):
  counter = 0 # Counter for how many prime found
  nr = 2 #Start at 2 because we know 0 and 1 is not prime
  arr = [] #list with all prime numbers when found
  while counter < n: #while nr of prime found is lower then wanted nr
    isPrime = True #If current nr is prime
    for i in range(2, nr): #For loop through half of nr, because you only need to check half. Lower time
      if nr%i/2==0: #  if current if possiable to divide by i
        isPrime = False #it it not a prime
        break; # stop loop, to lower runtime
    if isPrime: #if number is a prime
      arr.append(nr) # add the number to a list
      counter += 1 # increse the number of prime found
    nr += 1 # go to next number and check
  return arr # finally return the array with n number of prime


if __name__ == '__main__': #main methode
  t0 = time()#start timer
  list = prime(1000) # run function for 1000 prime
  t1 = time() # stop timer

  #Print...
  print(list)
  print(len(list))
  print('Prime finder used ' + str(round((t1-t0)*1000)) + 'ms')