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

    public static void main(String[] args){
        run();
    }

    /**
     *
     */
    private static void run(){
        System.out.println("******** Welcome to the Netflix Analyzer ********");
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the movie file you would like to analyze: ");

        while(sc.hasNext()){
//            System.out.print("Please enter your name? ");
//            String name = reader.readLine();
//            System.out.println("Your name is: " + name);
            //Get the input from the user
            System.out.println("Please specify which file you would like to analyze (movie file first, then review file)");
            String movieFileName = sc.nextLine();

            //Skip a line
            //sc.nextLine();

            System.out.println("Enter the review file: ");
            String reviewFileName = sc.nextLine();
            //Might want to put this in a try/catch block
            NetflixFileProcessor NFFileProcessor = new NetflixFileProcessor();

            //TODO: Finish this code
//            NFFileProcessor.readNetflixFiles(movieFileName, reviewFileName);

//            try{
//                NFFileProcessor.readNetflixFiles(movieFileName, reviewFileName);
//
//            }catch(IOException e){
//                System.err.println("Not a valid file, try again");
//            }
             NFFileProcessor.readNetflixFiles("movie_titles_short.txt", "movie_reviews_short.txt");

            //Proccess the files, should be in a try block

            //Construct the lists
            movies = NFFileProcessor.getMovies();
            reviewers = NFFileProcessor.getReviewers();
            //Initialize the graph
            moviesGraph = new Graph();
            // *** This MUST happen before the buildGraph method are called ***
            addMoviesToGraph();

            //System.out.println("These are the movies " + movies.toString());
            System.out.println();
            System.out.println("There are 2 options for defining adjacency");
            System.out.println("[OPTION 1] u and v are adjacent if they were made within 5 years of eachother.");
            System.out.println("[OPTION 2] u and v are adjacent if a single reviewer has seen both movies");
            System.out.println("EX: User1 watches movie 1 and movie 2, ergo movie 1 and 2 are connected, if user1 watches movies 1-10, movies 1-10 are connected");

            System.out.println("Choose an option to build the graph with");
            //Should handle bad input
            int choice = sc.nextInt();
            if(choice == 1){
                System.out.print("Creating graph...");
                BuildGraphForMoviesMadeWithin5Years();
            }else if(choice ==2){
                System.out.print("Creating graph...");
                BuildGraphOption2Optimize();

            }else{
                System.out.println("Incorrect input try again");
                //A try catch block might make more sense
            }

            //TODO: Remove for testing
            System.out.println("This is the graph that was created " + moviesGraph.toString());
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
                        System.out.println("Thank you for analyzing?..... EXITING");
                        sc.close();
                        //Just in case, can probably remove
                        flag = true;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Try again?");
                        //flag = true;
                        break;
                }
            }

        }
        sc.close();
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
     *
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
     *
     */
    private static void displayShortestPath(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a start node (1-" + moviesGraph.getNumVertices() + "): ");
        int start = sc.nextInt();
        System.out.println("This is the start number " + start);
        System.out.println("Enter a end node (1-" + moviesGraph.getNumVertices() + "): ");
        int end = sc.nextInt();
        System.out.println("This is the end number " + end);
        graphAlgorithms = new GraphAlgorithms();
    //PriorityQueue pq = graphAlgorithms.returnPQ();
        //System.out.println("This is the priority queue ");
        //pq.printHeap();
        HashMap<Integer, Integer> hm = graphAlgorithms.dijsktraAlgWithHash(moviesGraph, start);

        // graphAlgorithms.printPath2(hm, end);
        printPath2(hm, end);

        //printPath2(hm, end-1, start);

    }

    /**
     *
     * @param result
     * @param endNode
     */
    public static void printPath2(HashMap<Integer,Integer> result, Integer endNode){
        //Base case
        if(result.get(endNode) == null){
            //System.out.println(endNode);
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
     *
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
        System.out.println("Shortest paths " +Arrays.deepToString(allPairsShortestPath)); //TODO: Remove, this line is just for testing
        System.out.println("Density = " + density);
        System.out.printf("The graph contains %d nodes%n", numNodes);
        System.out.printf("The graph contains %d edges%n", numEdges);
        System.out.printf("The maximum degree of any node is %d%n",maxDegree);
        System.out.printf("The diameter of the graph is  %d%n", diameter);
        System.out.printf("The average length shortest path is  %d%n", avgLengthShortesPath);
    }
}
