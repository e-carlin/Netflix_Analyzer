import java.util.List;
import java.util.HashMap;
/**
 * Class that contains the Floyd Warshall algorithm and Dijkstra's algorithm
 * for computing shortest paths on graphs
 * @author Mark Gilbert and Evan Carlin
 */
public class GraphAlgorithms {

    private static final int INFINITY = Integer.MAX_VALUE/2;
    /**
     * Computes the all pairs shortest paths according to Floyd Warshall algorithm
     * @param g the graph
     * @param INFINITY a number used as infinity (Must be < Integer.MAX_Value/2)
     * @return a 2D array with the path cost to every node from every node (NOTE:
     *          the first row and col is always 0 because the movies are indexed from 1)
     */
    public static int[][] floydWarshall(Graph g, int INFINITY){
        int numberOfVerts = g.getNumVertices();
        int[][] dist = new int[numberOfVerts+1][numberOfVerts+1];

        //Initialize the dist matrix
        for(int src=1; src<=numberOfVerts; src++){
            for(int dest=1; dest<=numberOfVerts; dest++){
                //Get all of the source nodes neighbors
                List<Integer> neighbors = g.getNeighbors(src);
                //if the source node is connected to the destination node
                if(neighbors.contains(dest)){

                    dist[src][dest] = 1;
                }
                //The nodes aren't connected so you can't currently get from one to the other
                else{
                    dist[src][dest] = INFINITY;
                }
            }
        }

        //Compute the all pairs shortest paths
        for(int intermediate =1; intermediate<=numberOfVerts; intermediate++){
            for(int src=1; src<=numberOfVerts; src++){
                for(int dest=1; dest<=numberOfVerts; dest++){
                    //If it is less costly to go through this intermediate node
                    if(dist[src][intermediate] + dist[intermediate][dest] < dist[src][dest]){
                        //Then update the matrix with this new lower cost path
                        dist[src][dest] = dist[src][intermediate] + dist[intermediate][dest];
                    }
                }
            }
        }
        return dist;
    }


    /**
     * Computes the shortest path from a single node to all other nodes
     *
     * @param g the graph to analyze
     * @param source the initial node to start from
     * @return hash map of nodes to their previous node
     */
    public HashMap<Integer,Integer> dijsktraAlgWithHash(Graph g, int source){
        //Hashmaps for general implementation
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> prev = new HashMap<Integer, Integer>();

        PriorityQueue pq = new PriorityQueue();
        //Distance from the source to itself is 0
        dist.put(source,0);
        //The source has no previous node
        prev.put(source, null);
        pq.push(0,source);

        //Initialization
        HashMap<Integer, List<Integer>> hm = g.returnConnections();
        for(int i : hm.keySet()){
            //Don't want to overwrite the the data for source
            if(i==source) continue;
            dist.put(i, INFINITY);
            prev.put(i, null);
            pq.push(INFINITY, i);
        }

        while(!pq.isEmpty()){
            //Get the top element and check its neighbors
            int u = pq.topElement();
            pq.pop();
            for(int v : g.getNeighbors(u)){
                //The alternative cost of this path, weight is 1
                int alt = dist.get(u) + 1;
                //If we found a better path
                if(alt < dist.get(v)){
                    dist.put(v,alt);
                    prev.put(v, u);
                    pq.changePriority(v,alt);
                }
            }
        }
        return prev;
    }
}
