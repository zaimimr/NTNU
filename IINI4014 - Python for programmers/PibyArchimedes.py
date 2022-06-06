# Code to find Pi by Archimedes method.
# A good description is at:
# https://www.youtube.com/watch?v=_rJdkhlWZVQ&pbjreload=10

import math


def calculatePI(radius=1):
    # radius=1
    # try differen values if you want... same results.
    # The method starts with a six sided polygon
    # The length of the side lengthOfSide=radius.
    lengthOfSide = radius
    numberOfSides = 6
    # define desired accuracy
    desAccu = 0.000000001
    TempAccuracy = math.inf
    # Start with guess PI =1
    PI_Guess = 1
    while TempAccuracy > desAccu:
        numberOfSides *= 2
        # The radius is the hypotenuse of a triangle.
        # We find the part of the leg that starts at the center
        LegA = (radius**2 - (lengthOfSide / 2)**2)**(1 / 2)
        # The length from the tip of triangle to the edge of cirle
        tipToCircle = radius - LegA
        # The length of the side along the circumference
        SideLength = (tipToCircle**2 + (lengthOfSide / 2)**2)**(1 / 2)
        PI = SideLength * numberOfSides / (2 * radius)
        lengthOfSide = SideLength
        DiffAcc = abs(PI - PI_Guess)
        PI_Guess = PI
        if DiffAcc < desAccu:
            print("The number of sides needed for the desired accuracy of %s  was %s" % (desAccu, numberOfSides))
            break
    return PI


if __name__ == "__main__":
    pi = calculatePI()
    print("The estimated value of PI is % .5f" % pi)
