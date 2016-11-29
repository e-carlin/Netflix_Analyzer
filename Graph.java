import java.util.List;
        import java.util.LinkedList;
        import java.util.HashMap;
/**
 *
 * @author Mark Gilbert and Evan Carlin
 *
 */
public class Graph {

    HashMap<String, List<String>> connections = new HashMap<String, List<String>>();
    /**
     * Adds information to the graph about a new connection between nodes.
     *
     * @param from  Starting node
     * @param to  The node that's connected to from
     */
    public void addEdge(String from, String to) {
        // Look in map for nodes reachable from the starting node
        List<String> endpoints = connections.get(from);

        // If it's null then create a new list and make "to" the first node
        // in the list.  Otherwise just add "to" to the list that's already there.
        if (endpoints == null) {

            endpoints = new LinkedList<String>();
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
    public boolean adjacent(String from, String to) {
        List<String> endpoints = connections.get(from);
        return (endpoints != null) && endpoints.contains(to);
    }

    /**
     * Return a list of all nodes adjacent to "from".
     *
     * @param from  The node whose neighbors we want
     * @return  List of all adjacent nodes
     */
    public List<String> getNeighbors(String from) {
        return connections.get(from);
    }

    /**
     * Just turn the table into a string and return that.
     */
    public String toString() {
        return connections.toString();
    }
}

