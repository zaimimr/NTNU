from math import inf
from numpy import ones
from numpy.linalg import norm
from scipy.sparse.linalg import inv, norm as sp_norm
from scipy.sparse import csc_matrix

# Tidligere oppgaver
from oppgave2 import lagA
from oppgave4c import oppgave4c

y4_c = oppgave4c()

E = 1.3 * 10 ** 10 
I = (0.3 * 0.03 ** 3) / 12
f = -480 * 0.3 * 0.03 * 9.81
# Regner ut vektoren med lengde 10
y4_e = (f / (E * I)) * ones((10, 1)) 

forward_error = norm(y4_e - y4_c, ord=inf)
rel_forward_error = forward_error / norm(y4_e, ord=inf)
# Antar at den relative bakoverfeilen er 2^-52
rel_backwards_error = 2**(-52)
# Feilforstørringen er relativ foroverfeil delt på relativ bakoverfeil
error_magnification = rel_forward_error / rel_backwards_error

# Lager matrisen fra oppgave 2
A = lagA(10)
# Konverterer matrisen til en 'sparse matrix' for å inverse den
A = csc_matrix(A)
A_Invers = inv(A)
# Kondisjonstallet
cond_A = sp_norm(A, ord=inf) * sp_norm(A_Invers, ord=inf)

# Printer ut svarene
print("Foroverfeil: ", forward_error)
print("Relativ foroverfeil:", rel_forward_error)
print("Feilforstørring:", error_magnification)
print("Kondisjonstall for A:", cond_A)
