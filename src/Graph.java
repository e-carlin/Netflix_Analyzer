import java.util.*;

/**
 *
 * @author Mark Gilbert and Evan Carlin
 *
 */
public class Graph {

    static HashMap<Integer, List<Integer>> connections = new HashMap<Integer, List<Integer>>();

    /**
     * Constructs a graph
     */
    public Graph(){
        connections = new HashMap<Integer, List<Integer>>();
    }

    /**
     * Adds information to the graph about a new connection (edge) between nodes.
     *
     * @param from  Starting node
     * @param to  The node that's connected to from
     */
    public void addEdge(Integer from, Integer to) {
        // Look in map for nodes reachable from the starting node
        List<Integer> endpoints = connections.get(from);

        // If it's null then create a new list and make "to" the first node
        // in the list.  Otherwise just add "to" to the list that's already there.
        if (endpoints == null) {

            endpoints = new LinkedList<Integer>();
            endpoints.add(to);
            connections.put(from, endpoints);
        }else if(connections.containsKey(from)){

            if(connections.get(from).equals(to)){
                //If the exact location is there, update
                endpoints.remove(0); //There should only be 1 element so remove it and update the List
                endpoints.add(to); // Add the new edge
                connections.put(from, endpoints);
                return;
            }  if(to == null){
                connections.put(from, endpoints);
                return;
            }
            //If we need to get more than 1 variable, make a new
            endpoints.add(to);
            connections.put(from, endpoints);
        }
    }

    /**
     * See if there's an edge between a pair of nodes.
     *
     * @param from  Start node
     * @param to  The destination node
     * @return  Returns true if there's an edge from "from" to "to"
     */
    public boolean adjacent(Integer from, Integer to) {
        List<Integer> endpoints = connections.get(from);
        return (endpoints != null) && endpoints.contains(to);
    }

    /**
     * Return a list of all nodes adjacent to "from".
     *
     * @param from  The node whose neighbors we want
     * @return  List of all adjacent nodes
     */
    public List<Integer> getNeighbors(Integer from) {
        return connections.get(from);
    }

    public void addNode(int u){
        List<Integer> edges = new ArrayList<Integer>();
        connections.put(u,edges);
    }

    /**
     * Just turn the table into a Integer and return that.
     */
    public String toString() {
        return connections.toString();
    }

    /**
     * Tester just for the Graph
     * @param args
     */
    public static void main(String[] args){
        Graph g = new Graph();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);

        g.addEdge(1,3);
        g.addEdge(3,5);
        g.addEdge(4,5);
        g.addEdge(1,2);
        g.addEdge(2,3);


        for(Map.Entry<Integer, List<Integer>> i : connections.entrySet()){
            System.out.println("These are the connections " + i.toString());
        }

        System.out.println("These are the neighbors of 1 " + g.getNeighbors(1).toString());

    }
}

