from oppgave2 import lagA
from numpy import dot

def y_e_vektor(L):
    w = 0.3 # m
    t = 0.03 # m
    g = 9.81 # N
    d = 480 # kg/m^3
    E = 1.3 * 10**10 # N/m^2
    I = (w*t**3)/12 # Tverrsnitt m^2
    f_x = -d*w*t*g # egen vekt

    y_e = []
    for i in range(0, 10):
        x = (i+1)*0.2
        y_e.append(f_x / 24 / E / I * (x**2) * (x**2 - 4*x*L + 6*L**2))

    return y_e

def oppgave4c():
    L = 2 #m
    n = 10
    # lengden på hver seksjon
    h = L/n
    # Strukturmatrise
    A = lagA(10)
    # Vektor med eksakte y-verdier
    y_e = y_e_vektor(L)
    # vektoren y'''' med numeriske tilnærminger
    return (1 / h**4) * dot(A, y_e)

print(oppgave4c())