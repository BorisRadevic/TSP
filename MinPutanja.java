 * 2. resenje
 * 
 * Ova klasa je formirana za drugu mogucnost resenja, koje predstavlja
 * dinamicko odlucivanje na osnovu minimalne udaljenost do svih susednih gradova.
 * Krece se iz grada A i ide se u grad najmanje udaljenosti od grada A.
 * Naredni grad koji se obilazi bice onaj neobidjeni grad najmanje udaljenosti od grada u kom se nalazi.
 * Za razliku od ostala dva resenje problema, ovo se odvija dinamicki, bez dodatnih struktura 
 * u kojima se pamte koraci algoritma, vec se pravolinijski i dinamicki u svakom koraku prelazi u najblizi
 * neobidjeni grad.
 */
package ptp;

public class MinPutanja {

	protected MatricaPuteva mat, mm;
	protected int pocetni_grad;
	protected int[] niz;
	protected int trenutnoGradova = 0;
	protected int suma = 0;

	public MinPutanja(MatricaPuteva m, int p) {
		mat = m;
		niz = new int[m.ukGradova];
		pocetni_grad = p - 1;
		niz[trenutnoGradova++] = p;
	}

	protected void putanja() {
		int t = 0;
		int i = pocetni_grad;
		int p;

		MatricaPuteva mm = new MatricaPuteva(mat.ukGradova); // mm kopija matrice
		for (int ii = 0; ii < mat.ukGradova; ii++) {
			for (int jj = 0; jj < mat.ukGradova; jj++) {
				if (ii == jj)
					mm.mat[ii][jj] = 0;
				else {
					mm.mat[ii][jj] = mat.mat[ii][jj];
				}
			}
		}

		while (t < mat.ukGradova - 1) {
			p = sled_grad(i);
			i = p;
			t++;

		}
		suma += mm.dohPolje(i, pocetni_grad);
		mat = mm;
	}

	protected int sled_grad(int i) {
		int min = Integer.MAX_VALUE;
		int mingrad = 0;

		if (trenutnoGradova < niz.length) {

			for (int j = 0; j < mat.ukGradova; j++) {
				if (j != pocetni_grad) {
					if (mat.dohPolje(i, j) < min) {
						min = mat.dohPolje(i, j);
						mingrad = j;
					}
				}
			}
		}

		else {
			for (int j = 0; j < mat.ukGradova; j++) {
				if (mat.dohPolje(i, j) < min) {
					min = mat.dohPolje(i, j);
					mingrad = j;
				}
			}
			min = mm.dohPolje(i, mingrad);
		}

		suma += min;
		niz[trenutnoGradova] = (mingrad + 1); // npr: 2 grada su u nizu, trenutnoGradova = 2
		trenutnoGradova++;
		for (int n = 0; n < mat.ukGradova; n++) {
			mat.posPolje(n, mingrad, Integer.MAX_VALUE); // da se ne bi vracao u obidjeni grad
		}

		return mingrad;

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < niz.length; i++) {
			char grad = (char) ('A' + niz[i] - 1);
			sb.append(grad + " -> ");
		}

		sb.append("A (pocetni grad)");
		return sb.toString();

	}

}
