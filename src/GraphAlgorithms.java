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
    public static Integer[] dijsktraAlg(Graph g, int source){
        int dist[] = new int[g.getNumVertices()];
        Integer prev[] = new Integer[g.getNumVertices()];
        //Make those into hash maps

        PriorityQueue pq = new PriorityQueue();
        dist[source] =0;

        for(int i : g.connections.keySet()){
        //for(int i=0; i<g.getNumVertices(); i++){
            //Don't want to overwrite dist[source]
            if(i==source) continue;
            dist[i] = Integer.MAX_VALUE;
            prev[i] = null;
            pq.push(i, dist[i]);
        }
        //Identify where you move from base 0 to base 1
        //Private method that returns the node id and subtracts 1 from it
        //Should be using hash map
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

    /**
     * Returns back the set of parent nodes
     * @param g
     * @param source
     * @return
     */
    public static int[] dijsktraAlgWithHash(Graph g, int source){
        //int dist[] = new int[g.getNumVertices()];
        //Integer prev[] = new Integer[g.getNumVertices()];
        //Make those into hash maps
        HashMap<Integer, Integer> dist = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> prev = new HashMap<Integer, Integer>();

        PriorityQueue pq = new PriorityQueue();
        dist.put(source,0);
        //dist[source] =0;
        pq.push(0,source);
        System.out.println("These are the connections " + g.connections.keySet());
        for(int i : g.connections.keySet()){
            //for(int i=0; i<g.getNumVertices(); i++){
            //Don't want to overwrite dist[source]
            if(i==source) continue;
            //dist[i] = Integer.MAX_VALUE;
            dist.put(i, Integer.MAX_VALUE);
            prev.put(i, null);
            //prev[i] = null;
            pq.push(dist.get(i), i);
        }
        System.out.println("This is dist");
        System.out.println(dist.toString());

        System.out.println("This is prev");
        System.out.println(prev.toString());
        System.out.println("This is the pq");
        pq.printHeap();

        //Identify where you move from base 0 to base 1
        //Private method that returns the node id and subtracts 1 from it
        //Should be using hash map
        while(!pq.isEmpty()){
            //Get the top element then pop it
            //int u = pq.topPriority();
            int u = pq.topElement();
            pq.pop();
            System.out.println("This is u " + u);
            System.out.println("these are u's adj list "+ g.getNeighbors(u).toString());
            for(int v : g.getNeighbors(u)){
                //The alt cost, weight is 1
                int alt = dist.get(u) + 1;
                if(alt<dist.get(v)){
                    dist.put(v,alt);
                    //dist[j] = alt;
                    prev.put(v, u);
                    //prev[j] = u;
                    pq.changePriority(v,alt);
                }
            }
        }
        //Integer[] test = (Integer[])prev.values().toArray();
        //return (Integer[]) prev.values().toArray();
        int[] test = new int[g.getNumVertices()];
        int counter =0;
        for(int a : prev.values()){
            test[counter] = a;
            counter++;
        }
        return test;
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
        g.addEdge(5,2);
        g.addEdge(5,4);



        for(Map.Entry<Integer, List<Integer>> i : g.connections.entrySet()){
            System.out.println("These are the connections " + i.toString());
        }

        System.out.println("These are the neighbors of 1 " + g.getNeighbors(1).toString());
        int[] test = dijsktraAlgWithHash(g, 1);
        System.out.println("This is the result of dijkstra's " + Arrays.toString(test));
    }
}
