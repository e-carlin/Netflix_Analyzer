import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Moork on 11/29/16.
 */
public class GraphAlgorithms {

    /**
     * Returns back the set of parent nodes
     * @param g
     * @param source
     * @return
     */
    public static Integer[] dijsktraAlg(Graph g, int source){
        int dist[] = new int[g.getNumVertices()];
        Integer prev[] = new Integer[g.getNumVertices()];
        PriorityQueue pq = new PriorityQueue();
        dist[source] =0;

        for(int i : g.connections.keySet()){
            //Don't want to overwrite dist[source]
            if(i==source) continue;
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
            pq.push(i, dist[i]);
        }

        while(!pq.isEmpty()){
            //Get the top element then pop it
            int u = pq.topPriority();
            pq.pop();
            for(int j : g.getNeighbors(u)){
                //The alt cost
                int alt = dist[u] + 1;
                if(alt<dist[j]){
                    dist[j] = alt;
                    prev[j] = u;
                    pq.changePriority(j,alt);
                }
            }
        }

        return prev;
    }

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


        for(Map.Entry<Integer, List<Integer>> i : g.connections.entrySet()){
            System.out.println("These are the connections " + i.toString());
        }

        System.out.println("These are the neighbors of 1 " + g.getNeighbors(1).toString());
        Integer[] test = dijsktraAlg(g, 1);
        System.out.println("This is the result of dijkstra's " + Arrays.toString(test));
    }
}
