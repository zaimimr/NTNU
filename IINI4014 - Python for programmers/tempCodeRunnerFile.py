name = input().split(' ') #Input String from User and split on ' '(space)
fb = name[0][0].upper() #Select the first letter og the first word and uppercase it
lb = name[1][0].upper() #Select the first letter og the first word and uppercase it
welcome = fb + ' ' + lb + ', Welcome to Python 101' #Create string with first and second name
print(welcome) #Print out the welcome string