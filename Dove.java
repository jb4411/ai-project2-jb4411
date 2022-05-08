/**
 * A class to represent a Dove.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public class Dove extends Individual {
    public Dove(int number) {
        super(number, "Dove");
    }

    /**
     * Check if this individual is a Hawk.
     * This always returns false as this individual is a Dove.
     *
     * @return false
     */
    @Override
    public boolean isHawk() {
        return false;
    }

    /**
     * Handle an encounter with the individual passed in according to the Dove strategy.
     *
     * @param individual the individual this Dove is encountering
     * @param resource the value of the resource these individuals are competing for
     */
    @Override
    public void encounter(Individual individual, int resource) {
        int resourceChangeAmount = 0;
        if(!individual.isHawk()) {
            resourceChangeAmount = (int) Math.floor(((double) resource) / 2);
        }
        System.out.printf("Dove: %+d\t", resourceChangeAmount);
        this.addResources(resourceChangeAmount);
    }

    /**
     * Handle being encountered by the individual passed in according to the Dove strategy.
     *
     * @param individual the individual that is encountering this Dove
     * @param resource the value of the resource these individuals are competing for
     */
    @Override
    public void encounteredBy(Individual individual, int resource) {
        this.encounter(individual, resource);
    }
}
