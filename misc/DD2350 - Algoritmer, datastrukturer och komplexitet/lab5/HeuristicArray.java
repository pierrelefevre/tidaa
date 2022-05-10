import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class HeuristicArray {
    Kattio io;

    public boolean isDiva(int actor) {
        return actor < 2;
    }

    public int costOfActor(int actor) {
        return actor < numActors ? 1 : 500;
    }

    public class State {
        HashSet<Integer>[] rolesAssignedToActor;
        int[] actorAssignedToRoles;

        int cost;
        int usedActors;

        int undoActorLost;
        int undoActorGot;
        int undoRole;

        public State() {
            rolesAssignedToActor = new HashSet[numActors + numSuperActors];
            for (int i = 0; i < rolesAssignedToActor.length; i++) {
                rolesAssignedToActor[i] = new HashSet<>();
            }

            actorAssignedToRoles = new int[numRoles];
            Arrays.fill(actorAssignedToRoles, -1);
        }

        public State(State state) {
            this.cost = state.cost;
            this.usedActors = state.usedActors;

            this.rolesAssignedToActor = new HashSet[numActors + numSuperActors];
            for (int i = 0; i < rolesAssignedToActor.length; i++) {
                this.rolesAssignedToActor[i] = new HashSet<>(state.rolesAssignedToActor[i]);
            }

            this.actorAssignedToRoles = Arrays.copyOf(state.actorAssignedToRoles, state.actorAssignedToRoles.length);
        }

        boolean tryRandomMove() {
            int randomActor = (int) (numActors * Math.random());
            var roleList = new ArrayList<>(actorRoles[randomActor]);
            int randomRole = roleList.get((int) (actorRoles[randomActor].size() *
                    Math.random()));

            return trySetRole(randomActor, randomRole);
        }

        // if (actor != -1) {
        // if (!hasRoles(actor)) {
        // usedActors++;
        // }

        // actorAssignments[role] = actor;
        // roleAssignments[actor].add(role);
        // }

        // if (actor != -1) {
        // actorAssignments[role] = -1;
        // boolean removed = roleAssignments[actor].remove(role);
        // if (removed && !hasRoles(actor)) {
        // usedActors--;
        // }
        // }

        void undoTry() {

            actorAssignedToRoles[undoRole] = -1;
            boolean removed = rolesAssignedToActor[undoActorGot].remove(undoRole);

            if (removed && rolesAssignedToActor[undoActorGot].isEmpty()) {
                usedActors--;
                cost -= costOfActor(undoActorGot);
            }

            if (undoActorLost != -1) {
                if (rolesAssignedToActor[undoActorLost].isEmpty()) {
                    usedActors++;
                    cost += costOfActor(undoActorLost);
                }
                actorAssignedToRoles[undoRole] = undoActorLost;
                rolesAssignedToActor[undoActorLost].add(undoRole);
            }

            // Wipe undo cache so that you may not undo more than the one time
            undoActorGot = -1;
            undoActorLost = -1;
            undoRole = -1;
        }

        void setRole(int actor, int role) {
            int willLoseRole = actorAssignedToRoles[role];
            undoActorLost = willLoseRole;
            undoActorGot = actor;
            undoRole = role;

            // Lose role
            if (willLoseRole != -1) {
                rolesAssignedToActor[willLoseRole].remove(role);
                if (rolesAssignedToActor[willLoseRole].isEmpty()) {
                    cost -= costOfActor(actor);
                    usedActors--;
                }
            }

            // Gain role
            if (rolesAssignedToActor[actor].isEmpty()) {
                cost += costOfActor(actor);
                usedActors++;
            }

            // Save move
            actorAssignedToRoles[role] = actor;
            rolesAssignedToActor[actor].add(role);
        }

        boolean trySetRole(int actor, int role) {
            // Check if diva is assigned to role
            // Do not remove last role from diva
            if (actorAssignedToRoles[role] == 0) {
                if (rolesAssignedToActor[0].size() == 1)
                    return false;
            }
            if (actorAssignedToRoles[role] == 1) {
                if (rolesAssignedToActor[1].size() == 1)
                    return false;
            }

            // Make sure actor does not have two roles in same scene
            for (int scene : roleScenes[role]) {
                // Check if random actor already has a role in this scene
                for (int roleInScene : sceneRoles[scene]) {
                    if (rolesAssignedToActor[actor].contains(roleInScene))
                        return false;
                }

                // If random actor is a diva, check if the other diva has a role attributed in
                // the same scene
                if (isDiva(actor) && rolesAssignedToActor[(actor + 1) % 2].stream()
                        .anyMatch(r -> sceneRoles[scene].contains(r)))
                    return false;
            }

            setRole(actor, role);

            return true;
        }

        void print() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.usedActors);
            sb.append("\n");

            int[] sorted = Arrays.copyOf(this.actorAssignedToRoles, this.actorAssignedToRoles.length);
            Arrays.sort(sorted);

            for (int actor = 0; actor < rolesAssignedToActor.length; actor++) {
                if (rolesAssignedToActor[actor].isEmpty())
                    continue;

                sb.append(actor + 1);
                sb.append(" ");
                sb.append(this.rolesAssignedToActor[actor].size());
                sb.append(" ");
                for (var role : this.rolesAssignedToActor[actor]) {
                    sb.append(role + 1);
                    sb.append(" ");
                }
                sb.append("\n");
            }

            io.print(sb.toString());
        }
    }

    // Global states
    State state;
    State best;

    int numRoles;
    int numScenes;
    int numActors;
    int numSuperActors;

    HashSet<Integer>[] actorRoles;
    HashSet<Integer>[] roleActors;
    HashSet<Integer>[] sceneRoles;
    HashSet<Integer>[] roleScenes;

    void readInput() {
        numRoles = io.getInt();
        numScenes = io.getInt();
        numActors = io.getInt();

        numSuperActors = numRoles - 1;

        // Init arrays
        actorRoles = new HashSet[numActors + numSuperActors];
        roleActors = new HashSet[numRoles];
        sceneRoles = new HashSet[numScenes];
        roleScenes = new HashSet[numRoles];

        // Read actor and role data
        for (int i = 0; i < numRoles; i++) {
            int numActorsForRole = io.getInt();
            roleActors[i] = new HashSet<>(numActorsForRole + numSuperActors);

            for (int j = 0; j < numActorsForRole; j++) {
                int actorIndex = io.getInt() - 1;
                // Role -> Actors relation
                roleActors[i].add(actorIndex);

                // Actor to Roles relation
                if (actorRoles[actorIndex] == null)
                    actorRoles[actorIndex] = new HashSet<>();
                actorRoles[actorIndex].add(i);
            }
        }

        // Setup superactors and their relations
        for (int i = numActors; i < numActors + numSuperActors; i++) {
            actorRoles[i] = new HashSet<>(numRoles);

            // Add superactor to each role, and all roles to each superactor
            for (int j = 0; j < numRoles; j++) {
                actorRoles[i].add(j);
                roleActors[j].add(i);
            }
        }

        // Setup scene -> role and role -> scene
        for (int i = 0; i < numScenes; i++) {
            int numRolesInScene = io.getInt();
            sceneRoles[i] = new HashSet<>(numRolesInScene);

            for (int j = 0; j < numRolesInScene; j++) {
                int roleIndex = io.getInt() - 1;
                sceneRoles[i].add(roleIndex);

                if (roleScenes[roleIndex] == null)
                    roleScenes[roleIndex] = new HashSet<>();
                roleScenes[roleIndex].add(i);
            }
        }
    }

    void setup() {
        state = new State();

        for (int rolePlayableByDiva1 : actorRoles[0]) {

            if (!state.trySetRole(0, rolePlayableByDiva1))
                continue;
            boolean diva2Ok = false;
            for (int rolePlayableByDiva2 : actorRoles[1]) {
                if (rolePlayableByDiva1 != rolePlayableByDiva2) {
                    if (state.trySetRole(1, rolePlayableByDiva2))
                        diva2Ok = true;
                }
            }

            if (!diva2Ok)
                state.undoTry();
        }

        int tries = 0;
        for (int i = 2; i < numActors; i++) {

            boolean failed = false;
            for (int role : actorRoles[i]) {
                if (!state.trySetRole(i, role)) {
                    failed = true;
                }
            }

            if (failed)
                tries++;

            if (tries > 15)
                break;
        }

        // Superactors for the remaining roles
        int superActorCounter = 0;
        for (int roleId = 0; roleId < numRoles && superActorCounter < numSuperActors; roleId++) {
            if (state.actorAssignedToRoles[roleId] == -1) {
                if (state.trySetRole(numActors + superActorCounter, roleId))
                    superActorCounter++;
            }
        }

        best = state;
    }

    void improve() {
        double maxTemp = 100;
        double minTemp = 0.0001;
        double temp = maxTemp;
        double coolingFactor = 0.0065;

        while (temp > minTemp) {

            // Cool temp
            temp *= (1 - coolingFactor);

            // Generate new state
            int costBefore = state.cost * 100000;

            // Do swap, and anneal if it worked
            if (state.tryRandomMove()) {

                int costAfter = state.cost * 100000;

                // Maybe undo the move
                if (Math.random() >= probability(costBefore, costAfter, temp)) {
                    state.undoTry();
                }

                if (state.cost < best.cost)
                    best = state;
            }
        }
    }

    // Calculate probability, help method for simulated annealing
    double probability(double currentCost, double newCost, double temp) {
        if (newCost < currentCost)
            return 1.0;
        return Math.exp(-(newCost - currentCost) / 2 * temp);
    }

    void print() {
        best.print();
    }

    HeuristicArray() {
        io = new Kattio(System.in, System.out);
        // var path = "./testfall/sample01.in";
        // var output = "out.txt";
        // try {
        // io = new Kattio(new BufferedInputStream(new FileInputStream(path)), new
        // FileOutputStream(output));
        // } catch (FileNotFoundException e) {
        // e.printStackTrace();
        // }

        // var start = System.currentTimeMillis();

        readInput();
        setup();
        improve();
        print();

        // var stop = System.currentTimeMillis();
        // System.err.println("Time " + (stop - start) + " ms");

        io.close();
    }

    public static void main(String[] args) {
        new HeuristicArray();
    }
}
