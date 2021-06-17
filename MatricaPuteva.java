 * Klasa MatricaPuteva od polja ima kvadratnu simetricnu matricu, 
 * koja predstavlja matricu udaljenosti izmedju gradova. 
 * Dimenzija matrice je takodje polje klase ukGradova.
 * Prilikom generisanja objekta ove klase u konstruktoru ce biti pozvana 
 * metoda popuniMatricu(int n), koja ce izgenerisati random vrednosti za rastojanja
 * izmedju gradova. 
 * Radi lakse preglednosti resenja, uvedene su dve pretpostavke:
 * 1) rastojanje izmedju gradova A i B je isto kao rastojanje izmedju B i A  
 * 2) rastojanja su u opsegu [3, 24] (zbog lakse provere tacnosti) 
 *  
 */
package ptp;

public class MatricaPuteva {

	protected int[][] mat;
	protected int ukGradova;

	public MatricaPuteva(int n) {
		ukGradova = n;
		mat = new int[n][n];
		popuniMatricu(n);
	}

	public int[][] getMat() {
		return mat;
	}

	public int getUkGradova() {
		return ukGradova;
	}

	public void popuniMatricu(int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j == i) {
					mat[i][j] = 0;
				} else {
					// udaljenosti izmedju 3 i 24
					mat[i][j] = (int) (Math.random() * 21 + 3);
					// pretpostavka da je udaljenost izmedju A i B ista kao izmmedju B i A
					mat[j][i] = mat[i][j];
				}
			}
		}
	}

	public int dohPolje(int i, int j) {
		return mat[i][j];
	}

	public void posPolje(int i, int j, int vr) {
		mat[i][j] = vr;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nMatrica rastojanja izmedju gradova: \n");
		sb.append("*graf gradova je potpun povezan graf* \n\n");
		int w;

		for (int i = 0; i <= ukGradova; i++) {

			if (i == 0 || i == 1) {
				w = (ukGradova + 1) * 8 - 4;
				while (w-- > 0) {
					sb.append("-");
				}
			}
			sb.append("\n");

			for (int j = 0; j <= ukGradova; j++) {

				// i == 0 za ispis prvog reda
				if (i == 0) {
					if (j != 0) {
						char grad = (char) ('A' + j - 1);
						sb.append(grad + ":\t");
					} else {
						sb.append("grad\t");
					}
				}
				// i != 0 za ispis ostalih redova
				else
				// j == 0 prva kolona
				if (j == 0) {
					char grad = (char) ('A' + i - 1);
					sb.append(grad + ":\t");
				} else {
					sb.append(dohPolje(i - 1, j - 1) + "\t");
				}

			}
			sb.append("\n");
		}

		w = (ukGradova + 1) * 8 - 4;
		while (w-- > 0) {
			sb.append("-");
		}
		sb.append("\n");

		return sb.toString();
	}

}
