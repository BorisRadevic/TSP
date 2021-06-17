 * 1. resenje
 * 
 * Realizacija klase koja predstavlja Brute force algoritam.
 * Formiraju se sve kombinacije kojim je moguce da se obidje graf.
 * Za svaku kombinaciju se racuna putanja, i bice ispisana samo putanja
 * koja ima najkracu duzinu.
 *
 */
package ptp;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class BruteForce {

	private int N, start;
	private int[][] matrica;
	private List<Integer> putanja = new ArrayList<>();
	private int minduzina = Integer.MAX_VALUE;
	private boolean DaLiMoze = false;
	private int[] niz;

	public BruteForce(int[][] matrica) {
		this(0, matrica);
	}

	public BruteForce(int start, int[][] matrica) {
		N = matrica.length;
		this.start = start;
		this.matrica = matrica;
		niz = new int[matrica.length];
	}

	public List<Integer> getputanja() {
		if (!DaLiMoze)
			solve();

		for (int i = 0; i < niz.length; i++) {
			niz[i] = putanja.get(i);
		}

		return putanja;
	}

	public int getduzina() {
		if (!DaLiMoze)
			solve();
		return minduzina;
	}

	public void solve() {

		if (DaLiMoze)
			return;

		final int END_temp = (1 << N) - 1;
		int[][] memo = new int[N][1 << N];

		for (int end = 0; end < N; end++) {
			if (end == start)
				continue;
			memo[end][(1 << start) | (1 << end)] = matrica[start][end];
		}

		for (int r = 3; r <= N; r++) {
			for (int novigraf : kombinacija(r, N)) {
				if (notIn(start, novigraf))
					continue;
				for (int next = 0; next < N; next++) {
					if (next == start || notIn(next, novigraf))
						continue;
					int novigrafWithoutNext = novigraf ^ (1 << next);
					int minDist = Integer.MAX_VALUE;
					for (int end = 0; end < N; end++) {
						if (end == start || end == next || notIn(end, novigraf))
							continue;
						int newmatrica = memo[end][novigrafWithoutNext] + matrica[end][next];
						if (newmatrica < minDist) {
							minDist = newmatrica;
						}
					}
					memo[next][novigraf] = minDist;
				}
			}
		}

		for (int i = 0; i < N; i++) {
			if (i == start)
				continue;
			int duzina = memo[i][END_temp] + matrica[i][start];
			if (duzina < minduzina) {
				minduzina = duzina;
			}
		}

		int poslIndex = start;
		int temp = END_temp;
		putanja.add(start);

		for (int i = 1; i < N; i++) {

			int index = -1;
			for (int j = 0; j < N; j++) {
				if (j == start || notIn(j, temp))
					continue;
				if (index == -1)
					index = j;
				int pretRast = memo[index][temp] + matrica[index][poslIndex];
				int novoRast = memo[j][temp] + matrica[j][poslIndex];
				if (novoRast < pretRast) {
					index = j;
				}
			}

			putanja.add(index);
			temp = temp ^ (1 << index);
			poslIndex = index;
		}

		putanja.add(start);
		Collections.reverse(putanja);

		DaLiMoze = true;
	}

	private static boolean notIn(int elem, int novigraf) {
		return ((1 << elem) & novigraf) == 0;
	}

	public static List<Integer> kombinacija(int r, int n) {
		List<Integer> novi = new ArrayList<>();
		kombinacija(0, 0, r, n, novi);
		return novi;
	}

	private static void kombinacija(int set, int at, int r, int n, List<Integer> novi) {

		int pomLevo = n - at;
		if (pomLevo < r)
			return;

		if (r == 0) {
			novi.add(set);
		} else {
			for (int i = at; i < n; i++) {
				set |= 1 << i;

				kombinacija(set, i + 1, r - 1, n, novi);

				set &= ~(1 << i);
			}
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < niz.length; i++) {
			char grad = (char) ('A' + niz[i]);
			sb.append(grad + " -> ");
		}

		sb.append("A (pocetni grad)");
		return sb.toString();

	}

}
