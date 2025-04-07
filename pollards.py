import random
import math

def gcd(a, b):
    return math.gcd(a, b)

def pollards_rho(n):
    if n % 2 == 0:
        return 2
    x = random.randrange(2, n - 1)
    y = x
    c = random.randrange(1, n)
    d = 1

    while d == 1:
        x = (pow(x, 2, n) + c) % n
        y = (pow(y, 2, n) + c) % n
        y = (pow(y, 2, n) + c) % n
        d = gcd(abs(x - y), n)
        if d == n:
            return None
    return d

def is_prime_using_pollards_rho(n, attempts=5):
    if n <= 1:
        return False
    for _ in range(attempts):
        factor = pollards_rho(n)
        if factor is not None and factor != 1 and factor != n:
            return False
    return True

def factorize(n):
    if n == 1:
        return
    if is_prime_using_pollards_rho(n, 5):
        print(n, end=" ")
        return
    factor = pollards_rho(n)
    if factor is None or factor == n:
        print(n, end=" ")
        return
    factorize(factor)
    factorize(n // factor)

if __name__ == "__main__":
    test_prime = 7841
    test_composite = 26082004

    print(f"{test_prime} is prime? {is_prime_using_pollards_rho(test_prime, 5)}")
    print(f"{test_composite} is prime? {is_prime_using_pollards_rho(test_composite, 5)}")
    print(f"Factors of {test_prime}: ", end="")
    factorize(test_prime)
    print()
    print(f"Factors of {test_composite}: ", end="")
    factorize(test_composite)
    print()
