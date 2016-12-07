import java.util.*;

/**
 * Directed graph that uses an adjacency list representation
 * Keys are Integers and Values are Lists of Integers
 * @author Mark Gilbert and Evan Carlin
 *
 */
public class Graph {
    private HashMap<Integer, List<Integer>> connections = new HashMap<Integer, List<Integer>>();
    private int numVertices;
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

    public int getNumVertices(){
        return this.numVertices;
    }

    /**
     * The number of edges in the graph
     * @return the number of edges
     */
    public int getNumEdges(){
        int numEdges = 0;
        for(Map.Entry<Integer, List<Integer>> node : connections.entrySet()){
            numEdges += node.getValue().size();
        }
        return numEdges;
    }

    public int getMaxDegree(){
        int maxDegree = 0;
        for(Map.Entry<Integer, List<Integer>> node : connections.entrySet()){
            if(node.getValue().size() > maxDegree){
                maxDegree = node.getValue().size();
            }
        }
        return maxDegree;
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

    /**
     * Returns the hash map associated with this graph
     * @return Hash map of the nodes to its connections
     */
    public HashMap<Integer, List<Integer>> returnConnections(){
        return connections;
    }

    /**
     * Adds a node to the graph
     * @param u the node to add
     */
    public void addNode(int u){
        List<Integer> edges = new ArrayList<Integer>();
        numVertices++;
        connections.put(u,edges);
    }

    /**
     * Returns the String representation of the hashmap
     * @return connections as a String
     */
    public String toString() {
        return connections.toString();
    }

}

