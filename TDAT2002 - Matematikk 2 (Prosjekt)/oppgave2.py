from scipy.sparse import spdiags, issparse
from numpy import ones

def lagA(n):
  #Oppretter en matrise med n størrelse fylt av 1'ere
  e = ones(n)
  # Oppretter båndmatrise
  A = spdiags([e, -4*e, 6*e, -4*e, e], [-2, -1, 0, 1, 2], n, n).toarray()
  # Looper gjennom 4 verdier
  for i in range(4):
    # Setter inn verdier for første rad
    A[0][i] = [16, -9, 8/3, -1/4][i]
    # Setter inn verdier for nest siste rad
    A[n-2][n-4+i] = [16, -60, 72, -28][i]/17
    # Setter inn verdier for siste rad
    A[n-1][n-4+i] = [-12, 96, -156, 72][i]/17
  return A

