
import java.util.List;
import java.util.Scanner;

/**
 * Netflix Analyzer that allows users to explore
 * Mark Gilbert and Evan Carlin
 *
 */
public class NetflixAnalyzer {
    private static List<Movie> movies;
    private static List<Reviewer> reviewers;
    private static Graph moviesGraph;

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
        //TODO: Define the other options and actually get the selected option from the user
        BuildGraphForMoviesMadeWithin5Years();
        System.out.println(moviesGraph);


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
}
