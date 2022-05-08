import java.util.*;

/**
 * Used to manage the state of the ESS Simulation.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class Simulation {
    /** the string used to display the menu to the user */
    private final String menuString = "===============MENU============= \n" +
            "1 ) Starting Stats \n" +
            "2 ) Display Individuals and Points \n" +
            "3 ) Display Sorted \n" +
            "4 ) Have 1000 interactions \n" +
            "5 ) Have 10000 interactions \n" +
            "6 ) Have N interactions \n" +
            "7 ) Step through interactions \"Stop\" to return to menu \n" +
            "8 ) Quit \n" +
            "================================ \n";
    /** the total number of individuals in the population */
    private final int populationSize;
    /** the percentage of the population employing the Hawk strategy */
    private final int percentHawks;
    /** the total number of Hawks in the population */
    private final int numHawks;
    /** the total number of Doves in the population */
    private final int numDoves;
    /** the value of each resource */
    private final int resourceValue;
    /** the cost of a Hawk-Hawk interaction */
    private final int hawkInteractionCost;
    /** an array of all individuals in the population */
    private ArrayList<Individual> allIndividuals;
    /** an array of all living individuals */
    private ArrayList<Individual> livingIndividuals;
    /** the Random object used when randomly picking individuals for encounters */
    private final Random random;
    /** the total number of interactions that have been performed */
    private int encounterNum;

    /**
     * Create a new Simulation.
     *
     * @param populationSize the total number of individuals in the population
     * @param percentHawks the percentage of the population employing the Hawk strategy
     * @param resourceValue the value of each resource
     * @param hawkInteractionCost the cost of a Hawk-Hawk interaction
     */
    public Simulation(int populationSize, int percentHawks, int resourceValue, int hawkInteractionCost) {
        this.populationSize = populationSize;
        this.percentHawks = percentHawks;
        this.numHawks = (int) Math.floor((((double) percentHawks) / 100) * ((double) populationSize));
        this.numDoves = this.populationSize - this.numHawks;
        this.resourceValue = resourceValue;
        this.hawkInteractionCost = hawkInteractionCost;
        this.initializeIndividuals();
        this.random = new Random();
        this.encounterNum = 0;
    }

    /**
     * Initialize the arrays of individuals in the population.
     */
    private void initializeIndividuals() {
        this.allIndividuals = new ArrayList<>();
        this.livingIndividuals = new ArrayList<>();

        Individual individual;
        for (int i = 0; i < this.numHawks; i++) {
            individual = new Hawk(i + 1, this.hawkInteractionCost);
            this.allIndividuals.add(individual);
            this.livingIndividuals.add(individual);
        }

        for (int i = 0; i < this.numDoves; i++) {
            individual = new Dove(i + this.numHawks + 1);
            this.allIndividuals.add(individual);
            this.livingIndividuals.add(individual);
        }
    }

    /**
     * A helper method used to get a valid integer from the user.
     * This method prompts the user to enter an integer. If the input
     * received is either not an integer, or not within the specified range,
     * the user will be re-prompted until they enter a valid integer.
     *
     * @param message the message used to prompt the user for an integer
     * @param min the smallest valid integer
     * @param max the largest valid integer
     * @param in the Scanner to read input from
     * @return the valid integer entered by the user
     */
    private int getIntFromUser(String message, int min, int max, Scanner in) {
        String option;
        int optionNum = 0;
        boolean needOption = true;
        while (needOption) {
            System.out.print("> ");
            option = in.nextLine().strip();
            try {
                optionNum = Integer.parseInt(option);
                if (optionNum >= min && optionNum <= max) {
                    needOption = false;
                } else {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println(message);
            }
        }
        return optionNum;
    }

    /**
     * The main method used to run the simulation.
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        boolean running = true;
        boolean needOption = false;
        int optionNum;
        while (running) {
            if (!needOption) {
                System.out.println(menuString);
            }
            optionNum = getIntFromUser("Please enter a valid option.", 1, 8, in);
            needOption = false;
            switch (optionNum) {
                case 1 -> this.displayStartingStatistics();
                case 2 -> this.displayIndividuals();
                case 3 -> this.displayResourceAmounts();
                case 4 -> this.doInteractions(1000);
                case 5 -> this.doInteractions(10000);
                case 6 -> {
                    // check if the simulation cannot continue
                    if (this.livingIndividuals.size() <= 1) {
                        this.simulationComplete();
                        break;
                    }
                    System.out.println("Please enter the number of interactions to perform.");
                    int numInteractions = getIntFromUser("Please enter a valid number of interactions.", 0, Integer.MAX_VALUE, in);
                    this.doInteractions(numInteractions);
                }
                case 7 -> this.stepThroughInteractions(in);
                case 8 -> running = false;
                default -> needOption = true;
            }
        }
        in.close();
    }

    /**
     * Display the starting statistics.
     *
     * Called when the user enters "1" from the menu.
     */
    private void displayStartingStatistics() {
        System.out.printf("Population size: %d%n", this.populationSize);
        System.out.printf("Percentage of Hawks: %d%%%n", this.percentHawks);
        System.out.printf("Number of Hawks: %d%n%n", this.numHawks);

        System.out.printf("Percentage of Doves: %d%%%n", 100 - this.percentHawks);
        System.out.printf("Number of Doves: %d%n%n", this.numDoves);

        System.out.printf("Each resource is worth: %d%n", this.resourceValue);
        System.out.printf("Cost of Hawk-Hawk interaction: %d%n%n", this.hawkInteractionCost);
    }

    /**
     * Display all individuals in the population, the strategy each is using if they are alive or
     * "DEAD" if they have died, the accumulated resources for each individual, and the number
     * of living individuals remaining.
     *
     * Called when the user enters "2" from the menu.
     */
    private void displayIndividuals() {
        for (int i = 0; i < this.allIndividuals.size(); i++) {
            System.out.printf("Individual[%d] = %s%n", i, this.allIndividuals.get(i));
        }
        System.out.printf("Living: %d%n%n", this.livingIndividuals.size());
    }

    /**
     * Display all individuals in the population, the strategy each is using if they are alive or
     * "DEAD" if they have died, and the accumulated resources for each individual. The output is
     * sorted from most to least resources.
     *
     * Called when the user enters "3" from the menu.
     */
    private void displayResourceAmounts() {
        ArrayList<Individual> sortedIndividuals = new ArrayList<>(this.allIndividuals);
        Collections.sort(sortedIndividuals);
        for (Individual sortedIndividual : sortedIndividuals) {
            System.out.println(sortedIndividual);
        }
    }

    /**
     * A helper method to display a message explaining why the simulation cannot continue.
     *
     * This is called when all members of the population are dead, or there is only one living individual left.
     */
    private void simulationComplete() {
        if (this.livingIndividuals.size() == 1) {
            System.out.println("There is only one living individual left, this simulation is complete.");
        } else if (this.livingIndividuals.size() == 0) {
            System.out.println("There are no living individuals left, this simulation is complete.");
        }
    }

    /**
     * Run interactions until the simulation reaches a state where it cannot continue, or the
     * number of interactions passed in have been completed.
     *
     * @param numInteractions the target number of interactions to run
     */
    private void doInteractions(int numInteractions) {
        for (int i = 0; i < numInteractions; i++) {
            // check if the simulation cannot continue
            if (this.livingIndividuals.size() <= 1) {
                this.simulationComplete();
                return;
            }
            // perform an encounter
            this.encounterNum++;
            System.out.printf("Encounter: %d%n", this.encounterNum);
            this.doEncounter();
            System.out.println("");
        }
    }

    /**
     * Run the simulation one interaction at a time. Each time the user hits <Enter>, display another interaction.
     * This continues until the simulation reaches a state where it cannot continue, or the user enters "Stop".
     *
     * @param in the Scanner to read input from
     */
    private void stepThroughInteractions(Scanner in) {
        String option;
        while (true) {
            // check if the simulation cannot continue
            if (this.livingIndividuals.size() <= 1) {
                this.simulationComplete();
                return;
            }
            // perform an encounter
            this.encounterNum++;
            System.out.printf("Encounter: %d%n", this.encounterNum);
            this.doEncounter();

            // get user input
            option = in.nextLine().strip().toLowerCase(Locale.ROOT);
            if (option.equals("stop")) {
                return;
            }
        }
    }

    /**
     * A helper method to handle randomly selecting two living individuals, and having them encounter each other.
     */
    private void doEncounter() {
        Individual firstIndividual;
        Individual secondIndividual;
        int firstInt;
        int secondInt;

        // choose two different random living individuals
        firstInt = this.random.nextInt(this.livingIndividuals.size());
        secondInt = firstInt;
        while (secondInt == firstInt) {
            secondInt = this.random.nextInt(this.livingIndividuals.size());
        }
        firstIndividual = this.livingIndividuals.get(firstInt);
        secondIndividual = this.livingIndividuals.get(secondInt);

        // print encounter info
        System.out.printf("Individual %d: %s%n", firstInt, firstIndividual.getStrategy());
        System.out.printf("Individual %d: %s%n", secondInt, secondIndividual.getStrategy());
        System.out.printf("%s/%s ", firstIndividual.getStrategy(), secondIndividual.getStrategy());

        // perform encounter
        firstIndividual.encounter(secondIndividual, this.resourceValue);
        secondIndividual.encounteredBy(firstIndividual, this.resourceValue);

        // print encounter results
        if (firstIndividual.isDead()) {
            System.out.printf("%n%s one has died!", firstIndividual.getStrategy());
            this.livingIndividuals.remove(firstIndividual);
        }
        if (secondIndividual.isDead()) {
            System.out.printf("%n%s two have died!", secondIndividual.getStrategy());
            this.livingIndividuals.remove(secondIndividual);
        }
        System.out.printf("%nIndividual %d = %d\t Individual %d = %d%n", firstInt, firstIndividual.getResources(), secondInt, secondIndividual.getResources());
    }
}
