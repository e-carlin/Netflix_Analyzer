import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Netflix Analyzer that allows users to explore
 * Mark Gilbert and Evan Carlin
 *
 */
public class NetflixAnalyzer {

    public static void main(String[] args){
        System.out.println("******** Welcom to the Netflix Analyzer ********");
        //Get the input from the user
        System.out.println("Please specify which file you would like to analyze (movie file first, then review file)");
        Scanner sc = new Scanner(System.in);
        String movieFileName = sc.nextLine();
        String reviewFileName = sc.nextLine();

        //Proccess the files
        NetflixFileProcessor NFFileProcessor = new NetflixFileProcessor();
        //TODO: Uncomment the code to actually get data from the user
//        NFFileProcessor.readNetflixFiles(movieFileName, reviewFileName);
        NFFileProcessor.readNetflixFiles("movie_titles_short.txt", "movie_reviews_short.txt");


    }
}
