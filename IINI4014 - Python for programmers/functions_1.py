from functools import partial
import sys
print('I am running Python version',sys.version)


#read more on partial functions
def multiply(var1,var2):
	return var1*var2

multiplyBy2=partial(multiply,2)
#Partial functions allow us to create new functions with one or more of the variables defined.
#Very useful!
print(multiplyBy2(3))



#functions with an undefined number of arguments:
#Good for improving readablity and other things-like what?

def unlimitedVariables(*arg):
	#if there are no arguments:
	if not arg:
		print ('There are no arguments:')
	#if there are arguments:
	for var in arg:
		print('I found another variable which is: ',var)

unlimitedVariables(1,3,8,90,'strings are good','Hello','are you enjoying the course?')

unlimitedVariables()
# functions with default values:

def parameterNames(var1,var2=1):
	return var1+var2

# we can call the function with only one variable because the second is already define=1.
print(parameterNames(2))
# we can also call the function with two variables which changes var2=1 to any specified vaule.
# In this call var2=3.
print(parameterNames(2,3))

#In Python 3. we can use the *, argument to for the user to write the name of the arguments
def TheSpeed(*,distance,time):
	return distance*time

# try to call TheSpeed function

# This does not work.
# print(TheSpeed(30,10))

# This works and forces us to write the names of the arguments which is good to avoid confusion.
print(TheSpeed(distance=30,time=10))

# We can position ,* anywhere we need it.
def SpeedOfCar(carName,*,distance,time):
	 print('The speed of %s is '%carName, distance*time)

# Let us call it!
SpeedOfCar('volov 70',distance=100,time=30)

# Recursion: Functions that call themselves
def hanoi(n):
	if n==1:
		return n
	else:
		n=2*hanoi(n-1)
	return n

print(hanoi(10))

#In Python you can pass functions to other functions.
print(hanoi(hanoi(5)))
#Do not try a big number!





