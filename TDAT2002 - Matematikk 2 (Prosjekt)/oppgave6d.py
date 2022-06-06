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
	f = -d*w*t*g
	p = 100 #km/m
	h = L/n
	b = [0.0]*n
	for k in range(n):
		s = -p*g*math.sin(math.pi/L*h*(k+1))
		b[k]=([(h ** 4 / (E * I)) * (f+s)])
	y = spsolve(csc_matrix(A), b)
	return y


def oppgave6d():
	w = 0.3  # m
	t = 0.03  # m
	g = 9.81  # N
	p = 100 #kg/m
	d = 480  # kg/m^3
	L = 2  # m
	E = 1.3 * 10**10  # N/m^2
	I = (w*t**3)/12
	f_x = -d*w*t*g
	x = L

	y1 = f_x / (24 * E * I) * (x ** 2) * ((x ** 2) - 4 * L * x + 6 * L ** 2)
	y2 = ((g*p*L/(E*I*math.pi))*((L**3)/(math.pi**3)*math.sin(math.pi/L*x)-((x**3)/6)+(L*(x**2)/2)-(x*(L**2)/(math.pi**2))))
	y = y1-y2

	B = [0.0]*11
	n_list = [0.0]*11
	tf = [0.0]*11
	condition = [0.0]*11
	emach = 2**(-52)
	for i in range(1, 12):
		n = 10 * 2 ** i
		n_list[i-1]=n
		A = lagA(n)
		m = calc_y_c(n, A)
		B[i-1] = abs(abs(y) - abs(m[-1]))
		tf[i-1] = (abs((L**2)/(n**2)))
		condition[i-1] = cond(A, p=inf)*emach
		print(condition[i-1])

	loglog(n_list, B, 'g', label="Feil")
	loglog(n_list, tf, 'r', label="h^2")
	loglog(n_list, condition, 'b', label="Kondisjonstall*e_mach")
	legend(bbox_to_anchor=(1.05, 1), loc='upper left', borderaxespad=0.)
	ylabel("Feil")
	xlabel("x=10*2^i")
	annotate(str(n_list[6]) + ", " + str(B[6]), (n_list[6], B[6]))
	show()



oppgave6d()
