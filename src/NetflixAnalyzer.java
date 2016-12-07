import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * Netflix Analyzer that allows users to explore
 * Mark Gilbert and Evan Carlin
 *
 */
public class NetflixAnalyzer {
    private static List<Movie> movies;
    private static List<Reviewer> reviewers;
    private static Graph moviesGraph;
    private static GraphAlgorithms graphAlgorithms;
    private static final int INFINITY = (Integer.MAX_VALUE/2) - 10;

    /**
     * Main method
     * @param args not used
     */
    public static void main(String[] args){
        run();
    }

    /**
     * Control of the program
     */
    private static void run(){
        System.out.println("******** Welcome to the Netflix Analyzer ********");
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the movie file you would like to analyze: ");

        while(sc.hasNext()){
            //Get the input from the user
            String movieFileName = sc.nextLine();

            System.out.println("Enter the review file: ");
            String reviewFileName = sc.nextLine();

            //Might want to put this in a try/catch block
            NetflixFileProcessor NFFileProcessor = new NetflixFileProcessor();
            NFFileProcessor.readNetflixFiles(movieFileName, reviewFileName);

            //Construct the lists
            movies = NFFileProcessor.getMovies();
            reviewers = NFFileProcessor.getReviewers();
            //Initialize the graph
            moviesGraph = new Graph();
            // *** This MUST happen before the buildGraph method are called ***
            addMoviesToGraph();

            System.out.println();
            System.out.println("There are 2 options for defining adjacency");
            System.out.println("[OPTION 1] u and v are adjacent if they were made within 5 years of eachother.");
            System.out.println("[OPTION 2] u and v are adjacent if a single reviewer has seen both movies");
            System.out.println("EX: User1 watches movie 1 and movie 2, ergo movie 1 and 2 are connected, if user1 watches movies 1-10, movies 1-10 are connected");

            System.out.println("Choose an option to build the graph with");
            int choice = sc.nextInt();
            boolean check = false;
            while(!check){
                if(choice == 1){
                    System.out.print("Creating graph...");
                    BuildGraphForMoviesMadeWithin5Years();
                    check = true;
                }else if(choice == 2){
                    System.out.print("Creating graph...");
                    BuildGraphOption2Optimize();
                    check = true;
                }else{
                    System.out.println("Incorrect input try again");
                    check = false;
                }
            }

            System.out.println("Graph has been created");
            boolean flag = false;
            while(!flag){
                System.out.println("\n\n[Option 1] Print out statistics about the graph");
                System.out.println("\n\n[Option 2] Display shortest path between 2 nodes");
                System.out.println("\n\n[Option 3] Quit");
                int choice2 = sc.nextInt();
                switch(choice2){
                    case 1:
                        displayGraphStatistics();
                        break;
                    case 2:
                        //Need to handle when there is no path
                        displayShortestPath();
                        break;
                    case 3:
                        System.out.println("Thank you for analyzing..... EXITING");
                        sc.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Try again");
                        flag = false;
                        break;
                }
            }
        }
        sc.close();
    }

    /**
     * Adds all the movies to the graph as individual nodes which will be connected when we build the graph
     */
    private static void addMoviesToGraph(){
        for(Movie m : movies){
            moviesGraph.addNode(m.getMovieId());
        }
    }

    /**
     * Builds the graph for option 1; movies are connected if they were made within 5 years of eachother
     */
    private static void BuildGraphForMoviesMadeWithin5Years(){
        int numberOfYearsApart = 5;
        for(Movie u : movies){
            for(Movie v : movies){
                //Don't add an edge to itself
                //Don't connect movies that are already connected (if u -> v , then don't add v -> u)
                if(u == v || moviesGraph.getNeighbors(u.getMovieId()).contains(v.getMovieId())) continue;
                if(Math.abs(u.getYear() - v.getYear()) <= numberOfYearsApart) {
                    moviesGraph.addEdge(u.getMovieId(), v.getMovieId());
                }
            }
        }
    }
    /**
     * Builds the graph for option 2; movies are connected if a user watches both movies.
     * This means that if user 1 watches movies 2 and 3, movies 2 and 3 are connected.
     */
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

    /**
     * Displays the shortest path from one node to another with Dijkstra's algorithm
     */
    private static void displayShortestPath(){
        Scanner sc = new Scanner(System.in);
        //Get the start and end node
        System.out.println("Enter a start node (1-" + moviesGraph.getNumVertices() + "): ");
        int start = sc.nextInt();
        System.out.println("Enter a end node (1-" + moviesGraph.getNumVertices() + "): ");
        int end = sc.nextInt();

        //Check to make sure it was valid input
        if(start > moviesGraph.getNumVertices() || start < 0 || end > moviesGraph.getNumVertices() || end < 0){
            System.err.println("That was bad input");
            return;
        }
        graphAlgorithms = new GraphAlgorithms();
        HashMap<Integer, Integer> hm = graphAlgorithms.dijsktraAlgWithHash(moviesGraph, start);
        printPath2(hm, end);
    }

    /**
     * Recursive path print that starts at the end node and prints each previous node in the back
     * @param result the resulting hashmap from Dijkstra's algorithm
     * @param endNode the endNode is the first node to look for and backtrack from there
     */
    public static void printPath2(HashMap<Integer,Integer> result, Integer endNode){
        //Base case
        if(result.get(endNode) == null){
            return;
        }
        int otherMovie = result.get(endNode);
        //The List index is off by 1 so need to subtract 1
        System.out.println(movies.get(endNode-1).getTitle() + " ===> " + movies.get(otherMovie-1).getTitle());
        printPath2(result, result.get(endNode));
    }

    /**
     * Extra credit part of it
     * @param result
     * @param startNode
     */
    public static void printRandomPath(HashMap<Integer, Integer> result, Integer startNode){
        int[][] fl = graphAlgorithms.floydWarshall(moviesGraph, INFINITY);

    }

    /**
     * Displays various statistics about the graph
     */
    private static void displayGraphStatistics(){
        //Number of nodes
        int numNodes = moviesGraph.getNumVertices();
        //Number of edges
        int numEdges = moviesGraph.getNumEdges();

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
        System.out.println("Density = " + density);
        System.out.printf("The graph contains %d nodes%n", numNodes);
        System.out.printf("The graph contains %d edges%n", numEdges);
        System.out.printf("The maximum degree of any node is %d%n",maxDegree);
        System.out.printf("The diameter of the graph is  %d%n", diameter);
        System.out.printf("The average length shortest path is  %d%n", avgLengthShortesPath);
    }
}
