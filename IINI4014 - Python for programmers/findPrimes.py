# An implementation of the Sieve of Eratosthenes.
# Ali Alsam
from math import sqrt
import time
start_time = time.time()

# theLimit is a range of real numbes used
theLimit = 7950

# define a list with numbers from 2 to theLimit
theNumbers = list(range(2, theLimit))


def findPrimes(thePrimes):
    # filter the list
    for i in range(2, int(sqrt(theLimit) + 1)):
        # use list comprehension
        thePrimes = [n for n in thePrimes if (n == i or n % i != 0)]
    return(thePrimes)


def main():
    primes = findPrimes(theNumbers)
    primes = primes[0: 1000]
    end_time = time.time()
    for p in primes:
        print(p)


if __name__ == "__main__":
    main()
