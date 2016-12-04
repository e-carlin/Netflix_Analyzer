
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

/**
 * Netflix Analyzer that allows users to explore
 * Mark Gilbert and Evan Carlin
 *
 */
public class NetflixAnalyzer {
    private static List<Movie> movies;
    private static List<Reviewer> reviewers;
    private static Graph moviesGraph;
    private static final int INFINITY = (Integer.MAX_VALUE/2) - 10;

    public static void main(String[] args){
        System.out.println("******** Welcom to the Netflix Analyzer ********");
        //Get the input from the user
        System.out.println("Please specify which file you would like to analyze (movie file first, then review file)");
        Scanner sc = new Scanner(System.in);
        //TODO: Uncomment the code to actually prompt the user for the file names
//        String movieFileName = sc.nextLine();
//        String reviewFileName = sc.nextLine();

        //Proccess the files
        NetflixFileProcessor NFFileProcessor = new NetflixFileProcessor();
        //TODO: Uncomment the code to actually get data from the user
//        NFFileProcessor.readNetflixFiles(movieFileName, reviewFileName);
        NFFileProcessor.readNetflixFiles("movie_titles_short.txt", "movie_reviews_short.txt");

        //Construct the lists
        movies = NFFileProcessor.getMovies();
        reviewers = NFFileProcessor.getReviewers();
        //Initialize the graph
        moviesGraph = new Graph();
        // *** This MUST happen before the buildGraph method are called ***
        addMoviesToGraph();


        System.out.println();
        System.out.println("There are 2 options for definig adjacency");
        System.out.println("[OPTION 1] u and v are adjacent if they were made within 5 years of eachother.");
        //TODO: Is this right for option 2????
        System.out.println("[OPTION 2] u and v are adjacent if they have atleast one person who has seen both of them");
        //TODO: Actually get the selected option from the user
        System.out.print("Creating graph...");
        BuildGraphForMoviesMadeWithin5Years();
//        BuildGraphOption2Optimize();

        System.out.println("Graph has been created");
        System.out.println(moviesGraph); //TODO: Remove, thsi is just for testing purposes

        System.out.println("\n\n[Option 1] Print out statistics about the graph");
        displayGraphStatistics();
    }


    private static void addMoviesToGraph(){
        for(Movie m : movies){
            moviesGraph.addNode(m.getMovieId());
        }
    }

    /*
     * Builds the graph for option 1; movies are connected if they were made within 5 years of eachother
     */
    private static void BuildGraphForMoviesMadeWithin5Years(){
        int numberOfyearsApart = 5;
        for(Movie u : movies){
            for(Movie v : movies){
                if(u == v )continue;
                if(Math.abs(u.getYear() - v.getYear()) < numberOfyearsApart) {
                    moviesGraph.addEdge(u.getMovieId(), v.getMovieId());
                }
                }
            }
        }

    /**
     * Builds the graph based on the idea that movies are connected if a reviewer has seen both movies
     * We can probably optimize this a bit because we really only want to check all the movies that a reviewer
     * has seen not all the movies each time
     */
    private static void BuildGraphOption2(){
        for(Movie u : movies){
            for(Movie v : movies){
                if(u==v) continue;
                for(Reviewer r : reviewers){
                    Map<Integer,Integer> rMap = r.getRatings();
                    if(rMap.keySet().contains(u.getMovieId()) && rMap.keySet().contains(v.getMovieId())){
                        moviesGraph.addEdge(u.getMovieId(), v.getMovieId());
                        //Might not want to break because we have to go through all the
                        //break;
                    }
                }
            }
        }
        }

        private static void BuildGraphOption2Optimize(){
            //For each reviewer check their list of reviewed movies
            for(Reviewer r : reviewers){
                Map<Integer,Integer> rMap = r.getRatings();
                //For each movieID that the reviewer has viewed
                for(int u : rMap.keySet()){
                    for(int v : rMap.keySet()){
                    if(u==v || moviesGraph.getNeighbors(u).contains(v)){
                    //Don't add an edge because the same movie can't be
                        // connected and we don't want to add the same connection twice
                        continue;
                    }
                    //Add if they are not the same movie because this reviewer has seen both movies
                    moviesGraph.addEdge(u,v);
                    }
                }
            }
        }

        private static void displayGraphStatistics(){
            //Number of nodes
            int numNodes = moviesGraph.getNumVertices();
            //Number of edges
            int numEdges = moviesGraph.getNumEdges();
            //TODO: density
            //Density for a directed graph

            double density = (double)numEdges/((double)numNodes * (double)(numNodes-1));
            //Maximum degree
            int maxDegree = moviesGraph.getMaxDegree();

            //Need to run Floyd Warshall for these
            int[][]  allPairsShortestPath = GraphAlgorithms.floydWarshall(moviesGraph, INFINITY);
            //Diameter
            int diameter = GraphStatistics.diameter(allPairsShortestPath, INFINITY);
            //avg length of the shortest path
            int avgLengthShortesPath = GraphStatistics.avgLengthShortesPath(allPairsShortestPath, INFINITY);


            System.out.println("Printing statisctics about the graph...");
            System.out.println("Shortest paths " +Arrays.deepToString(allPairsShortestPath)); //TODO: Remove, this line is just for testing
            System.out.println("Density = " + density);
            System.out.printf("The graph contains %d nodes%n", numNodes);
            System.out.printf("The graph contains %d edges%n", numEdges);
            System.out.printf("The maximum degree of any node is %d%n",maxDegree);
            System.out.printf("The diameter of the graph is  %d%n", diameter);
            System.out.printf("The average length shortest path is  %d%n", avgLengthShortesPath);
        }
}
