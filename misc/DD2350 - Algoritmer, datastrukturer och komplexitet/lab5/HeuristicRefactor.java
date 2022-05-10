import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

public class HeuristicRefactor {
    Kattio io;

    public class Actor {
        int actorId;
        HashSet<Role> roles;
        int cost;

        boolean isDiva() {
            return actorId < 2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Actor that = (Actor) o;
            return actorId == that.actorId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(actorId);
        }
    }

    public class Role {
        int roleId;

        HashSet<Scene> scenes;
        HashSet<Actor> actors;

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Role that = (Role) o;
            return roleId == that.roleId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(roleId);
        }
    }

    public class Scene {
        int sceneId;
        HashSet<Role> roles;
    }

    public class State {

        HashSet<Role>[] rolesAssignedToActor;
        Actor[] actorAssignedToRoles;
        int cost;
        int usedActors;

        public State() {
            rolesAssignedToActor = new HashSet[numActors + numSuperActors];
            for (int i = 0; i < rolesAssignedToActor.length; i++) {
                rolesAssignedToActor[i] = new HashSet<>();
            }

            actorAssignedToRoles = new Actor[numRoles];
        }

        public State(State state) {
            this.cost = state.cost;
            this.usedActors = state.usedActors;

            // Deep copy role assignment
            this.rolesAssignedToActor = new HashSet[numActors + numSuperActors];
            for (int i = 0; i < rolesAssignedToActor.length; i++) {
                this.rolesAssignedToActor[i] = new HashSet<>(state.rolesAssignedToActor[i]);
            }

            // Shallow copy of actor assignment, becaus actor is never changed
            this.actorAssignedToRoles = Arrays.copyOf(state.actorAssignedToRoles, state.actorAssignedToRoles.length);
        }

        boolean tryRandomMove() {
            Actor randomActor = actors[(int) (numActors * Math.random())];
            var roleList = new ArrayList<>(randomActor.roles);
            Role randomRole = roleList.get((int) (randomActor.roles.size() * Math.random()));

            return trySetRole(randomActor, randomRole);
        }

        boolean trySetRole(Actor actor, Role role) {
            // Check if diva is assigned to role
            boolean diva1HasRole = actorAssignedToRoles[role.roleId] != null
                    && actorAssignedToRoles[role.roleId].actorId == 0;
            boolean diva2HasRole = actorAssignedToRoles[role.roleId] != null
                    && actorAssignedToRoles[role.roleId].actorId == 1;

            // Do not remove last role from diva
            if (diva1HasRole) {
                if (rolesAssignedToActor[0].size() == 1)
                    return false;
            } else if (diva2HasRole) {
                if (rolesAssignedToActor[1].size() == 1)
                    return false;
            }

            // Make sure actor does not have two roles in same scene
            for (var scene : role.scenes) {

                // Check if random actor already has a role in this scene
                if (scene.roles.stream().anyMatch(rolesAssignedToActor[actor.actorId]::contains))
                    return false;

                // If random actor is a diva, check if the other diva has a role attributed in
                // the same scene
                if (actor.isDiva() && rolesAssignedToActor[(actor.actorId + 1) % 2].stream()
                        .anyMatch(r -> scene.roles.contains(r)))
                    return false;

            }

            Actor willLoseRole = actorAssignedToRoles[role.roleId];

            // Lose role
            if (willLoseRole != null) {
                rolesAssignedToActor[willLoseRole.actorId].remove(role);
                if (rolesAssignedToActor[willLoseRole.actorId].isEmpty()) {
                    cost -= actor.cost;
                    usedActors--;
                }
            }

            // Gain role
            if (rolesAssignedToActor[actor.actorId].isEmpty()) {
                cost += actor.cost;
                usedActors++;
            }

            // Do the move
            actorAssignedToRoles[role.roleId] = actor;
            rolesAssignedToActor[actor.actorId].add(role);

            return true;
        }

        void print() {
            io.println(this.usedActors);

            for (Actor actor : Arrays.stream(this.actorAssignedToRoles).filter(a -> a != null)
                    .sorted((e1, e2) -> e1.actorId - e2.actorId)
                    .collect(Collectors.toSet())) {

                io.print((actor.actorId + 1) + " ");
                io.print(this.rolesAssignedToActor[actor.actorId].size() + " ");
                for (var role : this.rolesAssignedToActor[actor.actorId]) {
                    io.print((role.roleId + 1) + " ");
                }
                io.println();
            }
        }
    }

    // Global states
    State state = new State();
    State best;

    int numRoles;
    int numScenes;
    int numActors;
    int numSuperActors;

    Actor[] actors;
    Role[] roles;
    Scene[] scenes;

    // Calculate probability, help method for simulated annealing
    double probability(double currentCost, double newCost, double temp) {
        if (newCost < currentCost) {
            return 1;
        } else {
            return Math.exp((currentCost - newCost) / temp);
        }
    }

    void readInput() {
        numRoles = io.getInt();
        numScenes = io.getInt();
        numActors = io.getInt();

        numSuperActors = numRoles - 1;

        actors = new Actor[numActors + numSuperActors];
        for (int i = 0; i < actors.length; i++) {
            actors[i] = new Actor();
            actors[i].actorId = i;
        }

        // Read actor data and setup one Role -> many Actors relation
        roles = new Role[numRoles];
        for (int i = 0; i < roles.length; i++) {
            Role role = new Role();
            role.roleId = i;
            int numActorsForRole = io.getInt();
            role.actors = new HashSet<>(numActorsForRole);

            for (int j = 0; j < numActorsForRole; j++) {
                int actorIndex = io.getInt() - 1;
                role.actors.add(actors[actorIndex]);
            }

            roles[i] = role;
        }

        // Setup one Actor -> many Roles relation
        for (int i = 0; i < numActors; i++) {
            Actor actor = actors[i];
            actor.cost = 1;
            actor.roles = new HashSet<>();
            int nRoles = 0;
            for (Role role : roles) {
                if (role.actors.contains(actor)) {
                    actor.roles.add(role);
                    nRoles++;
                }
            }
        }

        // Setup superactors and their relations
        for (int i = numActors; i < numActors + numSuperActors; i++) {
            Actor actor = actors[i];
            actor.cost = 2;
            actor.actorId = i;
            actor.roles = new HashSet<>();
            for (Role role : roles) {
                actor.roles.add(role);
                role.actors.add(actor);
            }
        }

        // Read scenes and setup one Scene -> many Roles relation
        scenes = new Scene[numScenes];

        for (int i = 0; i < scenes.length; i++) {
            Scene scene = new Scene();
            scene.sceneId = i;
            int numRolesInScene = io.getInt();
            scene.roles = new HashSet<>(numRolesInScene);

            for (int j = 0; j < numRolesInScene; j++) {
                int roleIndex = io.getInt() - 1;
                scene.roles.add(roles[roleIndex]);
            }

            scenes[i] = scene;
        }

        // Setup one Role -> many Scenes ralation
        for (Role role : roles) {
            role.scenes = new HashSet<>();
            for (Scene scene : scenes) {
                if (scene.roles.contains(role)) {
                    role.scenes.add(scene);
                }
            }
        }
    }

    void setup() {
        state = new State();

        for (Role rolePlayableByDiva1 : actors[0].roles) {
            boolean diva2Ok = false;
            for (Role rolePlayableByDiva2 : actors[1].roles) {
                if (!rolePlayableByDiva1.equals(rolePlayableByDiva2) && !Arrays.stream(scenes).anyMatch(
                        s -> s.roles.contains(rolePlayableByDiva1) && s.roles.contains(rolePlayableByDiva2))) {
                    if (state.trySetRole(actors[1], rolePlayableByDiva2))
                        diva2Ok = true;
                }
            }

            if (diva2Ok) {
                state.trySetRole(actors[0], rolePlayableByDiva1);
                break;
            }
        }

        for (int i = 2; i < numActors; i++) {
            Actor actor = actors[i];
            for (var role : actor.roles) {
                state.trySetRole(actor, role);
            }
        }

        // Superactors for the remaining roles
        int superActorCounter = 0;
        for (int roleId = 0; roleId < numRoles && superActorCounter < numSuperActors; roleId++) {
            if (state.actorAssignedToRoles[roleId] == null) {
                if (state.trySetRole(actors[numActors + superActorCounter], roles[roleId]))
                    superActorCounter++;
            }
        }

        best = state;
    }

    void improve() {
        double maxTemp = 1000;
        double minTemp = 1;
        double temp = maxTemp;
        double coolingFactor = 0.015;

        while (temp > minTemp) {

            // Cool temp
            temp *= (1 - coolingFactor);

            // Generate new state
            State newState = new State(state);

            // Do swap, and anneal if it worked
            if (newState.tryRandomMove()) {
                if (Math.random() < probability(state.cost, newState.cost, temp))
                    state = newState;

                if (state.cost < best.cost)
                    best = state;
            }
        }
    }

    void print() {
        best.print();
    }

    HeuristicRefactor() {
        io = new Kattio(System.in, System.out);

        readInput();
        setup();
        improve();
        print();

        io.close();
    }

    public static void main(String[] args) {
        new HeuristicRefactor();
    }
}
