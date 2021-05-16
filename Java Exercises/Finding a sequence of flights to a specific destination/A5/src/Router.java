/*  Router - modified class with route(), which takes a departure and arrival city and returns an ArrayList of a possible flight path. Route calls a helper
method that performs a Breadth First Search. For each neighbour visited, its parent is mapped to a separate hash table. When the arrival city is found,
BFS calls another helper which prints the route of flights leading to the arrival city.
CS2610 Assignment #5
Author - Luke Schipper (142186)
Date - Dec. 3, 2017
*/

import java.util.*;

//is it possible to get from start to end
public class Router {

    private Map<String, City> database;

    public Router (Map<String, City> database) {
        this.database = database;
    }

    //search to find a route from start to finish in maxSteps or less steps
    public Iterable<String> route(String start, String finish, int maxSteps) {
        if (database.isEmpty())
            throw new IllegalStateException("Database is empty.");
        // reset all visit values for every city
        for (City city : database.values())
            city.visit(false);
        // get flight route
        ArrayList<String> flights = BFS(start, finish);
        // if route was not found
        if (flights == null)
            return null;
        // if route is within the number of specified flights
        if (flights.size() <= maxSteps)
            return flights;
        else
            return null;
        //hopefully you'll return some Iterable object like an ArrayList for example with the names of cities leading from start to finish
    }

    private ArrayList<String> BFS(String start, String finish) {

        Queue<City> queue = new LinkedList<>(); // tracks what cities need to be visited
        HashMap<City, City> parentHash = new HashMap<>(); // stores parents
        City departCity = database.get(start); // start by getting the departure city from the database
        if (departCity == null) throw new IllegalArgumentException();
        departCity.visit(true); // set visited
        queue.add(departCity); // add to queue
        while (!queue.isEmpty()) { // while still cities left to be visited
            City current = queue.remove(); // get next city
            if (current.getName().equals(finish)) // if it equals arrival city
                return getRoute(parentHash, departCity, current); // get the route
            for (City neighbour : current.getNeighbours()) {
                if (!neighbour.isVisited()) { // if neighbour cities not visits
                    parentHash.put(neighbour, current); // record their parents
                    queue.add(neighbour); // add them to the queue
                    neighbour.visit(true); // mark them as visited
                }
            }
        }
        return null; // if no route found, return null
    }

    private ArrayList<String> getRoute(Map<City, City> parentHash, City departCity, City arriveCity) {
        if (arriveCity == departCity) // no possible route
            return null;
        ArrayList<String> flights = new ArrayList<>(); // stores flights
        City child = arriveCity;
        City parent = parentHash.get(child);
        flights.add(parent + " to " + child); // records flight, using a child and parent
        while (parent != departCity) { // keeps recoding flights
            /* gets parent of each consecutive city until parent equals the departure city; then we know we have the entire route */
            child = parent;
            parent = parentHash.get(child);
            flights.add(parent + " to " + child);
        }
        Collections.reverse(flights); // ArrayList ordered from arrival city to departure city; reverse to get natural route
        return flights;
    }
}
