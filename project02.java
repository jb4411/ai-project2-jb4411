/**
 * The main project02 class is run as:
 *  $java project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]
 *       popSize: The total number of individuals in your population (required)
 *       [percentHawks]: The percentage of the population employing the Hawk strategy (optional, defaults to 20%)
 *       [resourceAmt]: The value of each resource (optional, defaults to 50)
 *       [costHawk-Hawk]: The cost of a Hawk-Hawk interaction (optional, defaults to 100)
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class project02 {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 4) {
            System.err.println("Usage: java project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]");
            System.exit(1);
        }
        int populationSize = 0;
        int percentHawks = 20;
        int resourceValue = 50;
        int hawkInteractionCost = 100;
        int[] intArgs = {populationSize, percentHawks, resourceValue, hawkInteractionCost};
        try {
            for (int i = 0; i < args.length; i++) {
                intArgs[i] = Integer.parseInt(args[i]);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid command line option.");
            System.exit(1);
        }
        final Simulation simulation = new Simulation(intArgs[0], intArgs[1], intArgs[2], intArgs[3]);
        simulation.run();
    }
}
