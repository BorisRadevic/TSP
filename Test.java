 * Test klasa koja sadrzi main() funkciju.
 */
package ptp;

import java.util.Scanner;

public class Test {
	public static int naslov() {
		String spaces = "                                        ";
		System.out.println("\n" + spaces + "PROBLEM TRGOVACKOG PUTNIKA\n");
		System.out.print("\t    " + spaces + "*");
		System.out.print("*");
		System.out.print("*\n\n");
		int vel = 114;
		StringBuilder sb = new StringBuilder("\n");
		while (vel-- > 0) {
			sb.append("=");
		}
		System.out.println(sb.toString());

		System.out.println("Pocetni grad je A.");

		System.out.print("Unesite broj gradova i kliknite enter: ");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		int n = s.nextInt();
		while (true) {
			if (n <= 1) {
				System.out.print("Unesite broj gradova veci od 1 i kliknite enter: ");
				n = s.nextInt();
			} else {
				break;
			}
		}
		return n;
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		int vel = 114;
		StringBuilder sb = new StringBuilder("\n");
		while (vel-- > 0) {
			sb.append("=");
		}
		int n = naslov();
		MatricaPuteva m = new MatricaPuteva(n);
		System.out.println(sb.toString());

		System.out.println("\nNeki od mogucih nacina za resenje ovog problema su: \n");
		System.out.println("\t1. Brute force algoritam");
		System.out.println("\t   - Najneoptimanije resenje sto se performansi tice ");
		System.out.println("\t   - Racuna duzine svih mogucih puteva i vraca najkrace moguce\n");

		System.out.println("\t2. Dinamicko odlucivanje na osnovu minimalne udaljenost do svih susednih gradova");
		System.out.println("\t   - Krece se iz grada A i ide se u grad najmanje udaljenosti od grada A");
		System.out.println("\t   - Naredni grad koji se obilazi bice onaj neobidjeni grad najmanje udaljenosti\n");

		System.out.println("\t3. Koriscenjem minimalnog razapinjujuceg stabla kao heuristicke funkcije");
		System.out.println("\t   - Koriste se i cene i heuristike");
		System.out.println("\t   - Cene predstavljaju ukupnu udaljenost na putanji od pocetnog grada do trenutnog");
		System.out.println(
				"\t   - Heuristika se racuna kao minimalno razapinjujuce stablo svih neobidjenih i krajnjeg grada");
		System.out.println(
				"\t   - Algoritam funkcionise tako sto se obrade redom gradovi koji imaju najmanji zbir cene i heuristike ");
		System.out.println(
				"\t   - Kraj obrade je kada se pocetni (krajnji) grad pojavi sa najmanjim zbirom cene i heuristike");

		System.out.print("\nUnesite broj resenja (1,2 ili 3) i kliknite enter: ");

		Scanner s = new Scanner(System.in);
		int r = s.nextInt();

		while (true) {
			if (r <= 0 || r > 3) {
				System.out.print("Unesite broj resenja (1,2 ili 3) i kliknite enter: ");
				r = s.nextInt();
			} else {
				break;
			}
		}

		System.out.println(m);

		while (true) {

			switch (r) {
			case 1:
				System.out.println(sb.toString());
				BruteForce bf = new BruteForce(m.getMat());
				bf.getputanja();

				System.out.println("\n*** RESENJE Brute Force algoritam ***\nProlazak kroz gradove: " + bf.toString());
				System.out.println("Najbolji put je duzine: " + bf.getduzina());
				break;
			case 2:
				System.out.println(sb.toString());
				MatricaPuteva mm = new MatricaPuteva(m.ukGradova);
				for (int i = 0; i < m.ukGradova; i++) {
					for (int j = 0; j < m.ukGradova; j++) {
						if (i == j)
							mm.mat[i][j] = 0;
						else {
							mm.mat[i][j] = m.mat[i][j];
						}
					}
				}
				MinPutanja mp = new MinPutanja(mm, 1);
				mp.putanja();

				System.out.println(
						"\n*** RESENJE Dinamicko odlucivanje na osnovu minimalne udaljenost do svih susednih gradova ***\nProlazak kroz gradove: "
								+ mp.toString());
				System.out.println("Najbolji put je duzine: " + mp.suma);

				break;
			case 3:
				System.out.println(sb.toString());
				System.out.print("\n*** RESENJE Minimalno razapinjujuce stablo kao heuristicka funkcije***");
				System.out.println("\nPostupak:");
				MinStablo ms = new MinStablo(m);

				System.out.println("\n\nProlazak kroz gradove: " + ms.toString());
				System.out.println("Najbolji put je duzine: " + ms.duzina);

				break;

			}

			System.out.println(sb.toString());
			System.out.println("\n * Za kraj rada kliknite 0(nulu)");
			System.out.print(" * Za ponovno pokretanje unesite broj resenja (1,2 ili 3)");
			r = s.nextInt();

			while (true) {
				if (r == 0) {
					return;
				} else if (r < 1 || r > 3) {
					System.out.print("Unesite broj resenja (1,2 ili 3) ili 0 za kraj rada: ");
					r = s.nextInt();
				} else {
					break;
				}
			}

			System.out.println(sb.toString());
		}

	}
}
