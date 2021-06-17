 * 3. resenje
 * 
 * Klasa za realizaciju 3. nacina resenja, koriscenjem minimalnog razapinjujuceg stabla kao heuristicke funkcije.
 * Algoritam krece iz pocetnog grada A, formira stablo pretrage sa korenom A, i u prvoj iteraciji ispisuje:
 * Cvor <ime_cvora_koji_je_moguce_obici>:
   -> dubina u stablu: d
   -> roditelj: <ime_cvora_roditelja>
   -> grane minimalnog razapinjujuceg stabla: [x, y][z, w]..[t, q] za cvor <ime_cvora>: heuristika(n) + cena(m) = n+m
 * 
 * Naredni grad koji se obradjuje algoritmom, predstavlja onaj list stabla pretrage koji ima najmanju vrednost zbira
 * heuristike i cene.
 * Taj cvor moze biti direktan potomak poslednjeg obradjenog cvora, ali moze biti i neki drugi list tog stabla, 
 * na bilo kojoj dubini. 
 * Na osnovu gore navedenog oblika ispisa, moguce je potpuno konstruisati izgled stabla algoritma, jer postoje svi 
 * potrebni podaci: naziv, dubina, roditelj.
 * U okviru postupka prikazuje se ispis za svaki cvor za koji je racunata heuristika.
 * Kada se kao dete cvor javi krajnji (pocetni) cvor A, i kada taj cvor ima najmanju vrednost heuristika + cena, 
 * tada se algoritam prekida, i ispisuje se putanja kojom se vratilo u pocetni grad.
 */
package ptp;

import java.util.ArrayList;
import java.util.List;

public class MinStablo {

	public static MatricaPuteva matstatic;
	protected MatricaPuteva mat;
	protected int[][] matrica;
	protected Node koren;

	protected int tr = 0;
	protected int duzina;
	protected int[] put;

	protected List<Node> obidjeni = new ArrayList<>();
	protected List<Node> listovi = new ArrayList<>();

	int id = 0;
	int size = 0;

	public MinStablo(MatricaPuteva m) {

		mat = m;
		matstatic = m;
		matrica = m.mat;
		koren = new Node(0, 0, null, 0);
		koren.setId(++id);
		obidjeni.add(koren);

		for (int i = 1; i < mat.ukGradova; i++) {
			listovi.add(new Node(i, m.dohPolje(0, i), koren, 1));
		}
		odrediPutanju();
		put = nadjiPut();

	}

	public Node dohvatiNajboljeg() {
		int min = Integer.MAX_VALUE;
		int index = 0;
		int vr = 0;
		for (int i = 0; i < listovi.size(); i++) {
			vr = listovi.get(i).vrednost;
			if (vr < min) {
				min = vr;
				index = i;
			}
		}
		return listovi.remove(index);
	}

	public void odrediPutanju() {
		Node next;
		while (true) {
			next = dohvatiNajboljeg();

			next.setId(++id);
			obidjeni.add(next);
			if (next.dubina == mat.ukGradova - 1) {
				break;
			}

			duzina += mat.dohPolje(tr, next.redniBroj);
			tr = next.redniBroj;
			azurirajListove(next);
		}
	}

	public int[] nadjiPut() {
		int[] put = new int[mat.ukGradova];
		Node rod = obidjeni.get(obidjeni.size() - 1);
		for (int i = put.length - 1; i > 0; i--) {
			put[i] = rod.redniBroj;
			rod = rod.roditelj;
		}
		put[0] = 0;
		return put;
	}

	public void azurirajListove(Node node) {

		int[] lista = new int[node.dubina];

		Node rod = node;
		for (int i = 0; i < node.dubina; i++) {
			lista[i] = rod.redniBroj;
			rod = rod.roditelj;
		}
		boolean dodaj = true;
		for (int i = 1; i < mat.ukGradova; i++) {
			dodaj = true;
			for (int j = 0; j < lista.length; j++) {
				if (lista[j] == i) {
					dodaj = false;
				}
			}

			if (dodaj) {
				listovi.add(new Node(i, node.cena + mat.dohPolje(node.redniBroj, i), node, node.dubina + 1));
			}
		}
	}

	public String toString() {

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < put.length; i++) {
			char grad = (char) ('A' + put[i]);
			sb.append(grad + " -> ");
		}

		duzina = 0;
		int i = 0;
		for (; i < put.length - 1; i++) {
			duzina += mat.dohPolje(put[i], put[i + 1]);
		}
		duzina += mat.dohPolje(put[i], 0);

		sb.append("A (pocetni grad)");
		return sb.toString();
	}
}
