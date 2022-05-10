import java.util.ArrayList;

public class Reduce {
    Kattio io;

    // Representerar en kant
    public class Edge {
        // Kanten sträcker sig mellan två hörn: from and two
        int from, to;

        public Edge(int f, int t) {
            from = f;
            to = t;
        }

        @Override
        public String toString() {
            return from + " " + to;
        }
    }

    void doReduce() {
        // Antal hörn -> antal roller
        int V = io.getInt();
        int roles = 0;

        // Antal kanter -> antal scener
        int E = io.getInt();

        // Mål m (antal färger) -> antal personer
        int M = io.getInt();

        // Data av kanter och hörn
        var edges = new ArrayList<Edge>();
        var vertexes = new ArrayList<Integer>();

        // Lägg till divornas kanter
        edges.add(new Edge(1, 3));
        edges.add(new Edge(2, 3));
        roles += 2;

        // Läs in kanterna med offset 2 för divornas plats
        for (int i = 0; i < E; i++) {
            int from = io.getInt() + 2;
            int to = io.getInt() + 2;

            // Spara hörnen om de inte redan finns med
            if (!vertexes.contains(from)) {
                vertexes.add(from);
                roles++;
            }
            if (!vertexes.contains(to)) {
                vertexes.add(to);
                roles++;
            }

            edges.add(new Edge(from, to));
        }

        // Hitta isolerade hörn, och mappa upp en kant till första divan
        for (int i = 3; i <= V + 2; i++) {
            if (!vertexes.contains(i)) {
                edges.add(new Edge(i, 1));
                roles += 1;
                E += 1;
            }
        }

        // Om det finns fler färger än roller, begränsa antalet
        if (M > roles) {
            M = roles;
        }

        // Skriv ut resultatet
        io.println(roles);
        io.println(edges.size());
        io.println(M + 2);
        io.println("1 1");
        io.println("1 2");

        // Skriv ut varje roll och vilka och hur många skådespelare som ingår i rollen
        // vi behöver inte ta med divorna (roles - 2) och (j + 3)
        for (int i = 0; i < roles - 2; i++) {
            io.print(M);
            io.print(" ");
            for (int j = 0; j < M; j++) {
                io.print(j + 3);
                io.print(" ");
            }
            io.println();
        }

        // Gå igenom varje scen och skriv ut (hårdkodad 2 framför)
        for (var edge : edges) {
            if (edge.from != edge.to) {
                io.println("2 " + (edge.from) + " " + (edge.to));
            }
        }
    }

    Reduce() {
        io = new Kattio(System.in, System.out);

        doReduce();

        io.close();
    }

    public static void main(String[] args) {
        new Reduce();
    }
}
