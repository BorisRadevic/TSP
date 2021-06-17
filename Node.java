 * 3. resenje
 * 
 * Klasa Node predstavlja jedan cvor stabla pretrage, kod realizacije 3. nacina resenja, 
 * odnosno koriscenja minimalnog razapinjujuceg stabla kao heuristicke funkcije, s tim da 
 * ne predstavlja cvor minimalnog razapinjujuceg stabla, vec stabla samog algoritma.
 * Svaki cvor ima podatke o: dubini u stablu na kojoj se nalazi, svom roditelju, ceni 
 * (tj duzini putanje od pocetnog grada do njega), heuristiku (koja se racuna u ovoj klasi).
 * Metoda heuristika(Node n) vraca vrednost heuristike za trenutni cvor.
 * Za racunanje te heuristike, koraci su sledeci:
 * 1) Formira se niz int[] minStablo u koji se smestaju redni brojevi gradova koji nisu na trenutnoj putanji 
 * ukljucujuci krajnji grad, odnosno oni gradovi koji ce formirati minimalno razapinjujuce stablo
 * 2) Poziva se funkcija h() koja ce izracunati duzinu minimalnog razapinjujuceg stabla, 
 * i ispisati parove koji predstavljaju grane tog stabla 
 *
 */
package ptp;

public class Node {
	protected int id = 0;

	protected int cena;
	protected int heuristika;
	protected Node roditelj;

	public int redniBroj;
	public int vrednost;
	public int dubina;

	public Node(int rb, int c, Node rod, int d) {
		redniBroj = rb;
		cena = c;
		dubina = d;
		roditelj = rod;
		heuristika = heuristika(rod);
		vrednost = cena + heuristika;

	}

	public void setId(int i) {
		id = i;
	}

	public int heuristika(Node n) {

		if (n == null)
			return 0;

		StringBuilder sb = new StringBuilder();

		int[] lista = new int[n.dubina];
		int[] minStablo = new int[MinStablo.matstatic.ukGradova - n.dubina];
		int iter = 0;

		if (n.dubina >= 1) {
			lista[0] = n.redniBroj;
			Node rod = n.roditelj;
			for (int i = 1; i < n.dubina; i++) {
				lista[i] = rod.redniBroj;
				rod = rod.roditelj;
			}

		}

		boolean dodaj = true;
		for (int i = 1; i < MinStablo.matstatic.ukGradova; i++) {
			dodaj = true;
			for (int j = 0; j < lista.length; j++) {
				if (lista[j] == i) {
					dodaj = false;
				}
			}
			if (dodaj) {
				minStablo[iter++] = i;
			}
		}
		int size = 0;

		if (minStablo.length == 1) {
			size = 0;
			char grad = (char) ('A' + this.redniBroj);
			sb.append("Cvor " + grad + ": heuristika = " + size);
			System.out.println(sb.toString());
			return 0;
		}
		if (minStablo.length == 2) {
			size = MinStablo.matstatic.dohPolje(minStablo[0], minStablo[1]);
			char gradOd = (char) ('A' + minStablo[0]);
			char gradDo = (char) ('A' + minStablo[1]);
			char grad = (char) ('A' + this.redniBroj);
			char gradR = (char) ('A' + n.redniBroj);

			sb.append("\nCvor " + grad + ":");
			sb.append("\n   -> dubina u stablu: " + this.dubina);
			sb.append("\n   -> roditelj: " + gradR);
			sb.append("\n   -> grane minimalnog razapinjujuceg stabla: [" + gradOd + ", " + gradDo + "]");

			sb.append(" za cvor " + grad + ": heuristika(" + size + ") + cena(" + this.cena + ") = "
					+ (this.cena + size));
			System.out.print(sb.toString());
			return size;
		}

		/// heuristika za minstablo >= 3 elemenata

		char grad = (char) ('A' + this.redniBroj);
		char gradR = (char) ('A' + n.redniBroj);

		System.out.print("\nCvor " + grad + ":");
		System.out.print("\n   -> dubina u stablu: " + this.dubina);
		System.out.print("\n   -> roditelj: " + gradR);
		System.out.print("\n   -> grane minimalnog razapinjujuceg stabla: ");

		/// heuristika za minstablo >= 3 elemenata

		size = h(minStablo);

		sb.append(" za cvor " + grad + ": heuristika(" + size + ") + cena(" + this.cena + ") = " + (this.cena + size));
		System.out.print(sb.toString());
		return size;
	}

	public int h(int[] minStablo) {
		int num = minStablo.length;
		int[][] graph = new int[num][num];
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < num; j++) {
				graph[i][j] = MinStablo.matstatic.dohPolje(minStablo[i], minStablo[j]);
			}
		}

		int parent[] = new int[num];

		int key[] = new int[num];

		Boolean mstSet[] = new Boolean[num];
		for (int i = 0; i < num; i++) {
			key[i] = Integer.MAX_VALUE;
			mstSet[i] = false;
		}

		key[0] = 0;
		parent[0] = -1;
		for (int count = 0; count < num - 1; count++) {
			int u = minKey(key, mstSet);
			mstSet[u] = true;

			for (int v = 0; v < num; v++)
				if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
					parent[v] = u;
					key[v] = graph[u][v];
				}
		}
		return printMST(parent, graph, minStablo);

	}

	int printMST(int parent[], int graph[][], int minStablo[]) {

		int suma = 0;
		for (int i = 1; i < parent.length; i++) {

			char gradOd = (char) ('A' + minStablo[parent[i]]);
			char gradDo = (char) ('A' + minStablo[i]);
			System.out.print("[" + gradOd + ", " + gradDo + "]");
			suma += graph[i][parent[i]];
		}

		return suma;
	}

	int minKey(int key[], Boolean mstSet[]) {
		int min = Integer.MAX_VALUE, min_index = -1;
		for (int v = 0; v < key.length; v++)
			if (mstSet[v] == false && key[v] < min) {
				min = key[v];
				min_index = v;
			}

		return min_index;
	}

}
