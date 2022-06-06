from scipy.sparse.linalg import spsolve
from oppgave2 import lagA

def calc_y_c(n):
    w = 0.3           # bredde (m)
    d = 0.03          # tykkelse (m)
    g = 9.81          # gravitasjonskonstant (m/s^2)
    density = 480     # massetetthet (kg/m^3)
    L = 2             # Lengde (m)
    E = 1.3 * 10**10  # Materialkonstant (N/m^2)
    I = (w*d**3)/12   # Tverrsnitt (m^2)
    f = -density*w*d*g
    h = L/n
    f_x = [(h ** 4 / (E * I)) * f] * n
    A = lagA(n)
    y = spsolve(A, f_x)
    return y

print(calc_y_c(10))
