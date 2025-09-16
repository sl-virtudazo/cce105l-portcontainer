package CCE105L_LabActs;

import java.util.ArrayDeque;
import java.util.Scanner;

abstract class Container {
    public abstract String toString();
}

class Containers extends Container {
    private final String id;
    private final String description, weight;

    public Containers(String id, String description, String weight) {
        this.id = id;
        this.description = description;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return id + " | " + description + " | " + weight + "kg";
    }
}

abstract class Ship {
    public abstract String toString();
}

class Ships extends Ship {
    private final String name, captain;

    public Ships(String name, String captain) {
        this.name = name;
        this.captain = captain;
    }

    @Override
    public String toString() {
        return name + " | Capt. " + captain;
    }
}

public class PortContainer {
    private static final ArrayDeque<Container> containerStack = new ArrayDeque<>();
    private static final ArrayDeque<Ship> shipQueue = new ArrayDeque<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            Menu();
            int input = -1;
            try {
                input = Integer.parseInt(scan.nextLine());
            } catch (Exception ignored) {}

            switch (input) {
                case 1:
                    storeContainer(scan);
                    break;
                case 2:
                    viewPortContainers();
                    break;
                case 3:
                    registerArrivingShip(scan);
                    break;
                case 4:
                    viewWaitingShips();
                    break;
                case 5:
                    loadNextShip();
                    break;
                case 0:
                    System.out.println("\nExit");
                    System.exit(0);
                default:
                    System.out.println("Invalid input! Try again.");
                    break;
            }
        }
    }

    private static void Menu() {
        System.out.println("\nPort Container Management System:");
        System.out.println("[1] Store container"); // push
        System.out.println("[2] View port containers");
        System.out.println("[3] Register arriving ship"); // enqueue
        System.out.println("[4] View waiting ships");
        System.out.println("[5] Load next ship"); // pop container & poll ship
        System.out.println("[0] Exit");
        System.out.print("\nEnter choice: ");
    }

    private static void storeContainer(Scanner scan) {
        System.out.print("Enter ID: ");
        String id = scan.nextLine();
        System.out.print("Enter description: ");
        String desc = scan.nextLine();
        System.out.print("Enter weight: ");
        String weight = scan.nextLine();

        Container con = new Containers(id, desc, weight);
        containerStack.push(con);

        System.out.println("\nStored: " + con);
    }

    private static void viewPortContainers() {
        System.out.println();
        if (containerStack.isEmpty()) {
            System.out.println("No containers.");
            return;
        }
        System.out.println("TOP ->");
        for (Container c : containerStack) {
            System.out.println(c);
        }
        System.out.println("<- BOTTOM");
    }

    private static void registerArrivingShip(Scanner scan) {
        System.out.print("Enter ship name: ");
        String name = scan.nextLine();
        System.out.print("Enter captain's name: ");
        String captain = scan.nextLine();

        Ship sh = new Ships(name, captain);
        shipQueue.offer(sh);

        System.out.println("\nRegistered: " + sh);
    }

    private static void viewWaitingShips() {
        System.out.println();
        if (shipQueue.isEmpty()) {
            System.out.println("No ships are waiting.");
            return;
        }
        System.out.println("FRONT ->");
        for (Ship s : shipQueue) {
            System.out.println(s);
        }
        System.out.println("<- REAR");
    }

    private static void loadNextShip() {
        System.out.println();
        if (containerStack.isEmpty() || shipQueue.isEmpty()) {
            System.out.println("Cannot load. No containers or ships waiting.");
            return;
        }
        Container con = containerStack.pop();
        Ship sh = shipQueue.poll();

        System.out.println("Loaded: " + con + " -> " + sh);
        System.out.println("Remaining containers: " + containerStack.size());
        System.out.println("Remaining ships waiting: " + shipQueue.size());
    }
}