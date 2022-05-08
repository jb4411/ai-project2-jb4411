import java.util.Objects;

/**
 * An abstract class to represent an individual.
 *
 * @author Jesse Burdick-Pless jb4411@g.rit.edu
 */
public abstract class Individual implements Comparable<Individual> {
    /** this individual's number */
    private final int number;
    /** this individual's resource level */
    private int resources;
    /** the strategy this individual is using */
    private final String strategy;

    /**
     * Create a new individual. Used as a super constructor by Hawk.java and Dove.java.
     *
     * @param number this individual's number, used in the equals() method
     * @param strategy the strategy ("Hawk" or "Dove") this individual is using
     */
    public Individual(int number, String strategy) {
        this.number = number;
        this.resources = 0;
        this.strategy = strategy;
    }

    /**
     * Check if this individual is dead.
     *
     * @return whether or not this individual is dead
     */
    public boolean isDead() {
        return this.resources < 0;
    }

    /**
     * Return this individual's resource level.
     *
     * @return this individual's resource level
     */
    public int getResources() {
        return this.resources;
    }

    /**
     * Update this individual's resource level by adding the number passed in to
     * this individual's resource level.
     *
     * @param resources amount to add to (or subtract from if the amount is negative) this individual's resource level
     */
    protected void addResources(int resources) {
        this.resources += resources;
    }

    /**
     * Return the strategy ("Hawk" or "Dove") this individual is using.
     *
     * @return the strategy this individual is using
     */
    public String getStrategy() {
        return strategy;
    }

    /**
     * Check if this individual is a Hawk.
     *
     * @return whether or not this individual is a Hawk
     */
    public abstract boolean isHawk();

    /**
     * Handle an encounter with the individual passed in according to the strategy this individual is using.
     *
     * @param individual the individual this individual is encountering
     * @param resource the value of the resource these individuals are competing for
     */
    public abstract void encounter(Individual individual, int resource);

    /**
     * Handle being encountered by the individual passed in according to the strategy this individual is using.
     *
     * @param individual the individual that is encountering this individual
     * @param resource the value of the resource these individuals are competing for
     */
    public abstract void encounteredBy(Individual individual, int resource);


    /**
     * Return the string representation of this individual.
     *
     * @return the string representation of this individual
     */
    @Override
    public String toString() {
        if (this.resources >= 0) {
            return String.format("%s: %d", this.strategy, this.resources);
        } else {
            return String.format("DEAD: %d", this.resources);
        }
    }

    /**
     * Compare this individual to the individual passed in. Returns the result of comparing the two individuals' resources.
     *
     * @param other the individual to compare this individual to
     * @return the result of comparing the two individuals' resources
     */
    @Override
    public int compareTo(Individual other) {
        return other.resources - this.resources;
    }

    /**
     * Check if two individuals are equal.
     *
     * @param obj the object to compare with
     * @return whether they are equal or not
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(!(obj instanceof Individual)) return false;
        final Individual o = (Individual) obj;
        return Objects.equals(this.number, o.number) && (this.resources == o.resources) && (this.strategy.equals(o.strategy));
    }
}
