import java.util.HashSet;
import java.util.ArrayList;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen ADK.
 *
 * Använder Kattio.java för in- och utläsning. Se
 * http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
	Kattio io;
	int x, y, e, v, s, t, eFlow;
	ArrayList<int[]> edges;
	ArrayList<int[]> solutionEdges;

	void readBipartiteGraph() {
		// Läs antal hörn och kanter
		x = io.getInt();
		y = io.getInt();
		e = io.getInt();

		edges = new ArrayList<>();
		ArrayList<int[]> tmp = new ArrayList<>();

		HashSet<Integer> setA = new HashSet<>();
		HashSet<Integer> setB = new HashSet<>();

		// Läs in kanterna
		for (int i = 0; i < e; ++i) {
			int a = io.getInt();
			int b = io.getInt();
			tmp.add(new int[] { a + 1, b + 1, 1 });
			// kanter[i][0] = a + 1;
			// kanter[i][1] = b + 1;
			// kanter[i][2] = 1;

			setA.add(a+1);
			setB.add(b+1);
		}

		// adjust e
		e += 3;

		for (int node : setA) {
			edges.add(new int[] { 1, node, 1 });
		}

		for (var item : tmp) {
			edges.add(item);
		}

		for (int node : setB) {
			edges.add(new int[] { node, x + y + 2, 1 });
		}

		System.err.println("Läste klart och konverterade bipartita grafen");
	}

	void writeFlowGraph() {
		// int v = 23, e = 0, s = 1, t = 2;

		v = x + y + 2;
		
		s = 1;
		t = v;
		//t = e;

		int eWrite = edges.size();

		// Skriv ut antal hörn och kanter samt källa och sänka
		io.println(v);
		io.println(s + " " + t);
		System.err.println(s + " " + t);
		io.println(eWrite);
		for (int i = 0; i < eWrite; ++i) {
			int a = edges.get(i)[0];
			int b = edges.get(i)[1];
			int c = edges.get(i)[2];
			// int a = 1, b = 2, c = 17;
			// Kant från a till b med kapacitet c
			io.println(a + " " + b + " " + c);
			System.err.println(a + " " + b + " " + c);
		}
		// Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
		io.flush();

		// Debugutskrift
		System.err.println("Skickade iväg flödesgrafen");
	}

	void readMaxFlowSolution() {
		// Läs in antal hörn, kanter, källa, sänka, och totalt flöde
		// (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
		// skickade iväg)
		int v = io.getInt();
		int s = io.getInt();
		int t = io.getInt();
		int totflow = io.getInt();
		eFlow = io.getInt();
		edges = new ArrayList<>();
		System.err.println("v: " + v + ", s: " + s + ", t: " + t + ", totflow: " + totflow + ", e: " + eFlow);

		for (int i = 0; i < eFlow; ++i) {
			// Flöde f från a till b
			int a = io.getInt();
			int b = io.getInt();
			int f = io.getInt();
			edges.add(new int[] { a, b, f});
			System.err.println("a: " + a + ", b: " + b + ", c: " + f);
		}
		System.err.println("Läste klart och konverterade flödesgrafen");
	}

	void writeBipMatchSolution() {
		//int x = 17, y = 4711, maxMatch = 0;

		solutionEdges = new ArrayList<>();
		int maxMatch = 0;

		for(int i = 0; i < eFlow; ++i){
			int a = edges.get(i)[0];
			int b = edges.get(i)[1];
			int f = edges.get(i)[2];

			if(a != 1 && b != v && f == 1){
				solutionEdges.add(new int[] {a - 1, b - 1});
				maxMatch += 1;
			}
		}

		// Skriv ut antal hörn och storleken på matchningen
		io.println(x + " " + y);
		io.println(maxMatch);

		for (int i = 0; i < maxMatch; ++i) {
			//int a = 5, b = 2323;
			int a = solutionEdges.get(i)[0];
			int b = solutionEdges.get(i)[1];
			// Kant mellan a och b ingår i vår matchningslösning
			io.println(a + " " + b);
		}

		System.err.println("Skickade bipartita grafen");
	}

	BipRed() {
		io = new Kattio(System.in, System.out);

		readBipartiteGraph();

		writeFlowGraph();

		readMaxFlowSolution();

		writeBipMatchSolution();

		// debugutskrift
		System.err.println("Bipred avslutar\n");

		// Kom ihåg att stänga ner Kattio-klassen
		io.close();
	}

	public static void main(String args[]) {
		new BipRed();
	}
}
