from scipy.sparse.linalg import spsolve, inv, norm
from scipy.sparse import csc_matrix
from numpy.linalg import cond
from math import inf
from oppgave2 import lagA
import math
from matplotlib.pyplot import loglog, plot, show, ylabel, xlabel, annotate, legend

def calc_y_c(n, A):
	w = 0.3  # m
	t = 0.03  # m
	g = 9.81  # N
	d = 480  # kg/m^3
	L = 2  # m
	E = 1.3 * 10**10  # N/m^2
	I = (w*t**3)/12
	f_x = -d*w*t*g
	h = L/n

	p = 1-0.15 #presentage of where we should start to calculate the extra weight
	s_x = -g * 50 / 0.3

	end = round(n*p)

	b=[(h ** 4 / (E * I)) * f_x]*n

	for i in range(end, n):
		b[i] += (h ** 4 / (E * I)) * s_x

	y = spsolve(csc_matrix(A), b)
	return y


def oppgave7():
	B = [0.0]*11
	n_list = [0.0]*11
	for i in range(1, 12):
		n = 10 * 2 ** i
		n_list[i-1]=n
		A = lagA(n)
		m = calc_y_c(n, A)
		B[i-1] = m[-1]
		print("\("+str(i)+"\)","&","\("+str(n_list[i-1])+"\)","&", "\("+str(B[i-1])+"\)","\\\\\n\hline")

	plot(list(range(1, 12)), B, 'g', label="Feil")
	legend(bbox_to_anchor=(1.05, 1), loc='upper left', borderaxespad=0.)
	ylabel("Misplacement")
	xlabel("n")
	show()


oppgave7()
