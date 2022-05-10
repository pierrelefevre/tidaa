import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Heuristic {
    Kattio io;

    public class Role {
        int roleNumber;
        ArrayList<Integer> actors;

        @Override
        public String toString() {
            return "Role " + roleNumber + " " + Arrays.toString(actors.toArray());
        }
    }

    public class Scene {
        int sceneNumber;
        ArrayList<Integer> roles;
        boolean takenByDiva = false;

        @Override
        public String toString() {
            return "Scene " + sceneNumber + " " + Arrays.toString(roles.toArray());
        }
    }

    public class ActorRoles {
        int actorNumber;
        ArrayList<Integer> roles;

        public ActorRoles(int actorNumber, ArrayList<Integer> roles) {
            this.actorNumber = actorNumber;
            this.roles = roles;
        }

        public ActorRoles(int actorNumber) {
            this.actorNumber = actorNumber;
            this.roles = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "Actor " + actorNumber + " has roles " + Arrays.toString(roles.toArray());
        }
    }

    public class State {
        ArrayList<ActorRoles> actorRoles;
        ArrayList<Role> roles;
        ArrayList<Scene> scenes;
        int n;
        int s;
        int k;

        public State(ArrayList<ActorRoles> actorRoles, ArrayList<Role> roles, ArrayList<Scene> scenes, int n, int s,
                int k) {
            this.actorRoles = actorRoles;
            this.roles = roles;
            this.scenes = scenes;
            this.n = n;
            this.s = s;
            this.k = k;
        }

        int calculateCost() {
            HashSet<Integer> actors = new HashSet<>();
            for (var actor : actorRoles) {
                if (actor.roles.size() > 0)
                    actors.add(actor.actorNumber);
            }
            int sum = actors.size();
            return sum;
        }

        void randomSwapActors() {

            // Choose random roles
            int index1 = 0;
            int index2 = 0;

            while (!(index1 != 0 && index1 != 0 && index2 != 1 && index2 != 1 && index1 != index2
                    && actorRoles.get(index2).roles.size() > 0)) {
                index1 = (int) (actorRoles.size() * Math.random());
                index2 = (int) (actorRoles.size() * Math.random());
            }

            var actor1 = actorRoles.get(index1);
            var actor2 = actorRoles.get(index2);

            // Choose random actors
            int minSize = actor2.roles.size();
            int role = (int) (minSize * Math.random());

            // Swap actors
            actor1.roles.add(actor2.roles.remove(role));
        }
    }

    // Global states
    State state;
    State best;

    // Calculate probability, help method for simulated annealing
    double probability(double currentCost, double newCost, double temp) {
        if (newCost < currentCost) {
            return 1;
        } else {
            return Math.exp((currentCost - newCost) / temp);
        }
    }

    void readInput() {
        // Antal roller
        int n = io.getInt();

        // Antal scener
        int s = io.getInt();

        // Antal skådespelare
        int k = io.getInt();

        // ladda in roller
        ArrayList<Role> roles = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            var role = new Role();
            role.actors = new ArrayList<>();
            int roleCount = io.getInt();
            for (int j = 0; j < roleCount; j++) {
                role.actors.add(io.getInt());
            }

            role.roleNumber = i;
            roles.add(role);
        }

        // ladda in scener
        ArrayList<Scene> scenes = new ArrayList<>();

        for (int i = 0; i < s; i++) {
            var scene = new Scene();
            scene.roles = new ArrayList<>();
            int roleCount = io.getInt();
            for (int j = 0; j < roleCount; j++) {
                scene.roles.add(io.getInt());
            }

            scene.sceneNumber = i;
            scenes.add(scene);
        }

        var actorRoles = new ArrayList<ActorRoles>();
        for (int i = 0; i < k; i++) {
            actorRoles.add(new ActorRoles(i));
        }

        state = new State(actorRoles, roles, scenes, n, s, k);
    }

    void solve() {
        ArrayList<Integer> occupiedRoles = new ArrayList<>();

        // Actor 1 tar alla möjliga
        boolean endLoop = false;
        for (int i = 0; i < state.scenes.size(); i++) {
            if (endLoop)
                break;
            if (state.scenes.get(i).takenByDiva)
                continue;
            for (var sceneRole : state.scenes.get(i).roles) {
                if (endLoop)
                    break;
                for (var roleActor : state.roles.get(sceneRole - 1).actors) {
                    if (endLoop)
                        break;
                    if (roleActor.equals(1)) {
                        if (!state.actorRoles.get(0).roles.contains(sceneRole)) {
                            state.actorRoles.get(0).roles.add(sceneRole);
                            occupiedRoles.add(sceneRole - 1);
                            state.scenes.get(i).takenByDiva = true;
                            endLoop = true;
                        }
                    }
                }
            }
        }

        // Actor 2 tar alla möjliga, och tar bort actor 1
        for (int i = 0; i < state.scenes.size(); i++) {
            for (var sceneRole : state.scenes.get(i).roles) {
                for (var roleActor : state.roles.get(sceneRole - 1).actors) {
                    if (roleActor.equals(2) && !state.scenes.get(i).takenByDiva
                            && !state.actorRoles.get(1).roles.contains(sceneRole)) {
                        state.actorRoles.get(1).roles.add(sceneRole);
                        occupiedRoles.add(sceneRole - 1);
                        state.scenes.get(i).takenByDiva = true;
                    }
                }
            }
        }

        // for (int i = 0; i < state.scenes.size(); i++) {
        // for (var sceneRoles : state.scenes.get(i).roles) {
        // for (var roleActor : state.roles.get(sceneRoles - 1).actors) {
        // if (roleActor.equals(m)) {
        // actor.roles.add(sceneRoles);
        // actors.add(actor);
        // state.actorRoles = actors;
        // m++;
        // actor = new ActorRoles(m);
        // occupiedRoles.add(sceneRoles - 1);
        // }
        // }
        // }
        // }

        /*
         * for (int i = 0; i < state.n; i++) {
         * for (int j = 0; j < state.roles.size(); j++) {
         * // boolean flag = false;
         * for (var roleActor : state.roles.get(j).actors) {
         * // if (flag)
         * // break;
         * if (roleActor.equals(m) && !occupiedRoles.contains(j)) {
         * /*
         * if (m == 2) {
         * for (int x = 0; x < state.scenes.size(); x++) {
         * for (var sceneRoles : state.scenes.get(x).roles) {
         * if (occupiedRoles.contains(sceneRoles)) {
         * flag = true;
         * }
         * }
         * }
         * } else {
         */
        /*
         * actor.roles.add(j + 1);
         * actors.add(actor);
         * state.actorRoles = actors;
         * m++;
         * actor = new ActorRoles(m);
         * occupiedRoles.add(j);
         * break;
         * // }
         * }}}}
         */

        // actors.add(actor);
        // state.actorRoles = actors;
        int m = 1;
        int k = state.k;

        // Superskådisar för de roller som är kvar
        for (int i = 0; i < state.n; i++) {
            if (!occupiedRoles.contains(i)) {
                var actor = new ActorRoles(k + m);
                actor.roles.add(i + 1);
                occupiedRoles.add(i);
                state.actorRoles.add(actor);
            }
            m++;
        }

        best = state;
    }

    void improve() {
        double maxTemp = 1000;
        double minTemp = 1;
        double temp = maxTemp;
        double coolingFactor = 0.005;
        while (temp > minTemp) {
            // Cool temp
            temp *= (1 - coolingFactor);
            // System.out.println("Temp: " + temp + " Iterations: " + iterations);

            // Generate new state
            var newState = new State(state.actorRoles, state.roles, state.scenes, state.n, state.s, state.k);

            // Do swap
            newState.randomSwapActors();

            // Calculate delta
            int currentCost = state.calculateCost();
            int newCost = newState.calculateCost();

            if (Math.random() < probability(currentCost, newCost, temp)) {
                state = newState;
            }

            if (state.calculateCost() < best.calculateCost()) {
                best = state;
            }
        }
    }

    void print() {
        io.println(best.calculateCost());
        for (var actor : best.actorRoles) {
            if (actor.roles.size() == 0)
                continue;
            io.print(actor.actorNumber + 1 + " ");
            io.print(actor.roles.size() + " ");
            for (var role : actor.roles) {
                io.print(role + " ");
            }
            io.println();
        }
    }

    Heuristic() {
        io = new Kattio(System.in, System.out);

        // Load problem
        readInput();

        // Create trivial solution to be used as initial state
        solve();

        // Improve solution using simulated annealing
        // improve();

        // Print solution
        print();

        io.close();
    }

    public static void main(String[] args) {
        new Heuristic();
    }
}
