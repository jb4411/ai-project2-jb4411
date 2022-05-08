/**
 * A class to represent a Hawk.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class Hawk extends Individual {
    /** the cost of a Hawk-Hawk interaction */
    private final int hawkInteractionCost;

    /**
     * Create a new Hawk.
     *
     * @param number this individual's number
     * @param hawkInteractionCost the cost of a Hawk-Hawk interaction
     */
    public Hawk(int number, int hawkInteractionCost) {
        super(number, "Hawk");
        this.hawkInteractionCost = hawkInteractionCost;
    }

    /**
     * Check if this individual is a Hawk.
     * This always returns true as this individual is a Hawk.
     *
     * @return true
     */
    @Override
    public boolean isHawk() {
        return true;
    }

    /**
     * Handle an encounter with the individual passed in according to the Hawk strategy.
     *
     * @param individual the individual this Hawk is encountering
     * @param resource the value of the resource these individuals are competing for
     */
    @Override
    public void encounter(Individual individual, int resource) {
        int resourceChangeAmount;
        if(individual.isHawk()) {
            resourceChangeAmount = resource - this.hawkInteractionCost;
        } else {
            resourceChangeAmount = resource;
        }
        System.out.printf("Hawk: %+d\t", resourceChangeAmount);
        this.addResources(resourceChangeAmount);
    }

    /**
     * Handle being encountered by the individual passed in according to the Hawk strategy.
     *
     * @param individual the individual that is encountering this Hawk
     * @param resource the value of the resource these individuals are competing for
     */
    @Override
    public void encounteredBy(Individual individual, int resource) {
        if(individual.isHawk()) {
            this.encounter(individual, 0);
        } else {
            this.encounter(individual, resource);
        }
    }
}
