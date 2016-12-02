import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
/**
 * @author Mark Gilbert and Evan Carlin
 */
public class GraphAlgorithms {


    /**
     * Returns back the set of parent nodes
     * @param g
     * @param source
     * @return
     */
    public static Integer[] dijsktraAlgWithHash(Graph g, int source){
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
        //Add the the previous nodes to the array to return
        Integer[] returnArr = new Integer[g.getNumVertices()];
        int counter = 0;
        for(Integer a : prev.values()){
            returnArr[counter] = a;
            counter++;
        }
        return returnArr;
    }


    public static void main(String[] args){
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

        Integer[] test = dijsktraAlgWithHash(g, 1);
        Integer[] test2 = dijsktraAlgWithHash(g2,1);
        System.out.println("This is the result of dijkstra's on graph1 " + Arrays.toString(test));
        System.out.println("This is the result of dijkstra's on graph2" + Arrays.toString(test2));

    }
}
