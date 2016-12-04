import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/**
 * @author Mark Gilbert and Evan Carlin
 */
public class GraphAlgorithms {


    /**
     * Computes the all pairs shortest paths according to floyd warshall algorithm
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
     * Returns back the set of parent nodes
     * @param g
     * @param source
     * @return
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
        for(int i : g.connections.keySet()){
            //Don't want to overwrite the the data for source
            if(i==source) continue;
            dist.put(i, Integer.MAX_VALUE);
            prev.put(i, null);
            pq.push(dist.get(i), i);
        }

        while(!pq.isEmpty()){
            //Get the top element then pop it
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

    /**
     * Prints the path from the start node to the end node
     * @param result
     * @param startNode
     * @param endNode
     */
    public static void printPath(HashMap<Integer,Integer> result, Integer startNode, Integer endNode){

        System.out.print(startNode + " ===> ");
        for(Integer v : result.keySet()){

        }
    }

    public static void printPath2(HashMap<Integer,Integer> result, Integer endNode){
        //Base case
        if(result.get(endNode) == null){
            //System.out.println(endNode);
            return;
        }
        //System.out.println(result.get(endNode) + " ===> " + endNode);
        System.out.println(endNode + " ===> " + result.get(endNode));

        printPath2(result, result.get(endNode));
        //System.out.println(endNode + " ===> " + result.get(endNode));
    }

    public static void main(String[] args){
       GraphAlgorithms ga = new GraphAlgorithms();
        Graph g = new Graph();
        g.addNode(1);
        g.addNode(2);
        g.addNode(3);
        g.addNode(4);
        g.addNode(5);

        g.addEdge(1,2);
        g.addEdge(1,4);
        g.addEdge(2,4);
        g.addEdge(2,3);
        g.addEdge(3,5);
        g.addEdge(4,5);
        g.addEdge(4,3);
        g.addEdge(4,2);
        g.addEdge(5,1);
        g.addEdge(5,3);

        Graph g2 = new Graph();
        g2.addNode(1);
        g2.addNode(2);
        g2.addNode(3);
        g2.addNode(4);
        g2.addNode(5);


        g2.addEdge(1,2);
        g2.addEdge(1,4);
        g2.addEdge(2,5);
        g2.addEdge(2,3);
        g2.addEdge(3,5);
        g2.addEdge(4,5);
        g2.addEdge(4,3);
        g2.addEdge(4,2);

        for(Map.Entry<Integer, List<Integer>> i : g.connections.entrySet()){
            System.out.println("These are the connections on graph1 " + i.toString());
        }
        System.out.println("\n");
        for(Map.Entry<Integer, List<Integer>> i : g2.connections.entrySet()){
            System.out.println("These are the connections on graph2 " + i.toString());
        }

        HashMap<Integer, Integer> test = ga.dijsktraAlgWithHash(g, 1);
        HashMap<Integer,Integer> test2 = ga.dijsktraAlgWithHash(g2,1);
        System.out.println("These are the values of graph1"+ test.values());
        System.out.println("These are the values of graph2"+ test2.values());
        System.out.println("These are the keys of graph1"+ test.keySet());
        System.out.println("These are the keys of graph2"+ test2.keySet());
        System.out.println("This is the path from 5");
        printPath2(test, 5);
        System.out.println("This is the result of dijkstra's on graph1 " + test.entrySet());
        System.out.println("This is the result of dijkstra's on graph2" + test2.entrySet());

    }
}
