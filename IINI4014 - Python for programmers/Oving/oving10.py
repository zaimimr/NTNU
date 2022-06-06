''' Circuituos, LLC -
    An Advanced Circle Analytics Company
'''
from random import random, seed
import math


class Circle(object):
    'An advance circle toolkit'

    version = '0.6'  # class version
    __slots__ = ['diameter']

    def __init__(self, radius):
        self.radius = radius

    def area(self):
        'Perform quadrature on a shape of uniform radius'
        p = self.__perimeter()
        r = p / math.pi / 2.0
        return math.pi * r ** 2.0

    def perimeter(self):
        return 2.0 * math.pi * self.radius

    @classmethod
    def from_bbd(cls, bbd):
        'Construct a circle from a bounding box diagonal'
        radius = bbd / 2.0 / math.sqrt(2.0)
        return cls(radius)

    @staticmethod
    def angle_to_grade(angle):
        'Convert angle in degree to a percentage grade'
        return math.tan(math.radians(angle)) * 100.0

    @property
    def radius(self):
        'Radius of a circle'
        return self.diameter / 2.0

    @radius.setter
    def radius(self, radius):
        self.diameter = radius * 2.0


seed(8675309)
print('Circuituous version', Circle.version)
n = 10
circles = [Circle(random()) for i in range(n)]
print('The average area of ', n, 'random circles')
avg = sum([c.area() for c in circles]) / n
print('is %.1f' % avg)
print('')

cuts = [0.1, 0.7, 0.8]
circles = [Circle(r) for r in cuts]
for c in circles:
    print('A circlet with a radius of', c.radius)
    print('has a perimeter of', c.perimeter())
    print('and a cold area of', c.area())
    c.radius *= 1.1
    print('and a warm area of', c.area())
    print('')


class Tire(Circle):
    'Tires are circles with a corrected perimeter'

    def perimeter(self):
        'Circumference corrected for the rubber'
        return Circle.perimeter(self) * 1.25


t = Tire(22)
print('A tire of radius', t.radius)
print('har an inner area of', t.area())
print('and an odometer corrected perometer of',)
print(t.perimeter())
print('')

