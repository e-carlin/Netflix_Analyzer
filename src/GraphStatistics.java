/**
 * Created by evan on 12/3/16.
 */
public class GraphStatistics {

    /**
     * Give the diameter of a graph (longest shortest path) give the all pairs shortest path
     * @param allPairsShortestPaths the result of running the Floyd Warshall algorithm
     * @param INFINITY the number used as infinity in the Floyd Warshall algorithm
     * @return the diameter (longest shortest path)
     */
    public static int diameter(int[][] allPairsShortestPaths, int INFINITY){
        int diameter = 0;

        for(int i=1; i<allPairsShortestPaths.length; i++){
            for(int j=1; j<allPairsShortestPaths.length; j++){
                //We don't count infinity as a path
                if(allPairsShortestPaths[i][j] == INFINITY){
                    continue;
                }
                //If the path between i and j is longer than the previous known diameter
                else if(allPairsShortestPaths[i][j] > diameter){
                    diameter = allPairsShortestPaths[i][j];
                }
            }
        }
        return diameter;
    }

    public static int avgLengthShortesPath(int[][] allPairsShortestPaths, int INFINITY){
        int totalCost = 0;
        int numberOfPaths = 0;

        for(int i=1; i<allPairsShortestPaths.length; i++){
            for(int j=1; j<allPairsShortestPaths.length; j++){
                //We don't count infinity as a path
                if(allPairsShortestPaths[i][j] == INFINITY){
                    continue;
                }

                //Valid paths
                totalCost += allPairsShortestPaths[i][j];
                numberOfPaths++;
            }
        }
        return totalCost/numberOfPaths;
    }

}
