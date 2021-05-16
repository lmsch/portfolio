import java.util.Set;
import java.util.TreeSet;

/**
* Class that implements an Adjacency List Graph
* getNeighbours returns the list of connections to this city
*
  This is slightly simplified from the text because there are no weights on the edges 
**/
public class City implements Comparable<City> {

    //cities I can connect to
    //use a set here because no duplicates and fast access 
    private Set<City> neighbours;
    //the name of this city (airport code)
    private String name;
    // addition: boolean for to keep track of what cities have been visited

    private boolean isVisited;
    
    public City (String airportCode) {
        neighbours = new TreeSet<>();
        name = airportCode;
        isVisited = false;
    }

    /**
    * Add a connection to City city
    *
    **/ 
    public void addNeighbour(City city) {
        neighbours.add(city);
    }

    /**
    * Return the neighbouring cities
    **/
    public Set<City> getNeighbours() {
        return neighbours;
    }   

    /* Return the name of this city */
    public String getName() { 
        return name;
    }

    /* Addition: Change visit value */
    public void visit(boolean isVisited) {
        this.isVisited = isVisited;
    }

    /* Addition: Check to see if city is visited */
    public boolean isVisited() {
        return isVisited;
    }

    /* Compare this city to another */
    public int compareTo(City c) {
        return this.name.compareTo(c.getName());
    }

    /* override equals */
    public boolean equals(City c) {
        return this.name.equals(c.getName());
    }

    /* Addition: returns city name */
    public String toString() {
        return name;
    }
}

