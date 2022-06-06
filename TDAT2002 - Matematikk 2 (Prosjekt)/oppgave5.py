from scipy.sparse.linalg import spsolve, inv, norm
from scipy.sparse import csc_matrix
from numpy.linalg import cond
from math import inf
from oppgave2 import lagA
from oppgave3 import calc_y_c
from matplotlib.pyplot import loglog, plot, show, ylabel, xlabel, legend



def oppgave5():
	w = 0.3  # m
	t = 0.03  # m
	g = 9.81  # N
	d = 480  # kg/m^3
	L = 2  # m
	x = L
	E = 1.3 * 10**10  # N/m^2
	I = (w*t**3)/12
	f_x = -d*w*t*g
	y = f_x / (24 * E * I) * (x ** 2) * (x ** 2 - 4 * L * x + 6 * L ** 2)

	cond_list = [0.0]*11
	err_list = [0.0]*11
	for i in range(1, 10):
		n = 10 * 2 ** i
		m = calc_y_c(n)
		condi = cond(lagA(n), p=inf)
		err = abs(y-m[-1])
		print("\("+str(i)+"\)", "&", "\("+str(n)+"\)", "&", "\("+str(condi)+"\)", "&","\("+str(err)+"\)", "\\\\\n\hline")
		cond_list[i-1]=condi
		err_list[i-1]=err

	loglog(list(range(1, 12)), cond_list, 'r', label='Kondisjonstall')
	loglog(list(range(1, 12)), err_list, 'b', label='Feil')
	legend(bbox_to_anchor=(1.05, 1), loc='upper left', borderaxespad=0.)
	ylabel("Verdi")
	xlabel("10*2^x")
	show()
oppgave5()
