import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;


/**
 * This class contains methods for reading and parsing both the movie_reviews.txt file
 * as well as the movie_titles.txt file.
 *
 * @author alchambers
 *
 */
public class NetflixFileProcessor {
    private List<Movie> movies;
    private List<Reviewer> reviewers;

    /**
     * Constructor
     */
    public NetflixFileProcessor(){
        reviewers = new ArrayList<Reviewer>();
        movies = new ArrayList<Movie>();
    }

    /**
     * Reads and parses Netflix data files
     * @param movieFilename The filename for the movie titles file
     * @param reviewFilename The filename for the reviewer file
     */
    public void readNetflixFiles(String movieFilename, String reviewFilename){
        // These files *must* be read in this order
        readMovieTitlesFile(movieFilename);
        readNetflixFile(reviewFilename);
    }


    /**
     * Prints the list of reviewers
     */
    public void printReviewerList(){
        for(Reviewer r : reviewers){
            System.out.println(r);
        }
    }

    /**
     * Prints the list of movies
     */
    public void printMovieList(){
        System.out.println("Total size: " + movies.size());
        for(Movie m : movies) {
            System.out.println(m);
        }
    }

    /**
     * Returns the list of reviewers
     */
    public List<Reviewer> getReviewers(){
        return reviewers;
    }

    /**
     * Returns the list of movies with at least 1 rating
     */
    public List<Movie> getMovies(){
        ListIterator<Movie> itr = movies.listIterator();
        while(itr.hasNext()){
            Movie m = itr.next();
            if(m.numRatings() == 0){
                throw new AssertionError();
            }
        }
        return movies;
    }



    /**************************************************************
     * 				Private Helper Methods
     **************************************************************/


    /**
     * Reads and parses the data from movie_reviews.txt
     */
    private void readNetflixFile(String filename){
        try{
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String line = input.readLine();
            while(line != null) {
                addReviewer(line);
                line = input.readLine();
            }
            input.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * Reads and parses the data from movie_titles.txt
     */
    private void readMovieTitlesFile(String filename){
        try{
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String line = input.readLine();
            while(line != null){
                addMovie(line);
                line = input.readLine();
            }
            input.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }



    /**
     * Takes in a single line from the movies file. It parses the line
     * and adds it to the list of movies.
     * @param line
     */
    private void addMovie(String line){
        String[] info = line.split(",");
        assert(info.length >= 3);

        try{
            int movieId = Integer.parseInt(info[0]);
            int year = info[1].equals("NULL") ? 0 : Integer.parseInt(info[1]);
            String title = "";
            //TODO: Make this more efficient
            for(int i = 2; i < info.length; i++){
                title += info[i];
            }

            Movie m = new Movie(movieId, year, title);
            int index = movies.indexOf(m);
            assert(index == -1); // this should be the first time we've seen this movie
            movies.add(m);

        }
        catch(NumberFormatException e){
            System.out.println(line);
            System.out.println(e);
        }
    }

    /**
     * Takes in a single line from the reviews file. It parses the line
     * and adds it to the list of reviewers. Each line is formated as follows:
     *
     *  		userId:movie,rating;movie,rating;movie,rating;
     *
     */
    private void addReviewer(String line){
        int userId = getUserId(line);
        assert(userId != -1);

        Reviewer r = new Reviewer(userId);
        line = line.substring(line.indexOf(":")+1);

        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while(tokenizer.hasMoreTokens()){
            String movieRating = tokenizer.nextToken();
            int comma = movieRating.indexOf(",");
            try{
                int movieId = Integer.parseInt(movieRating.substring(0,comma));
                int rating = Integer.parseInt(movieRating.substring(comma+1));

                Movie m = new Movie(movieId);
                int index = movies.indexOf(m);
                assert(index != -1); // we should have already processed all movies
                    movies.get(index).addRating(userId, rating);
                r.addMovie(movieId, rating);
            }
            catch(NumberFormatException e){
                System.out.println(e);
            }
        }
        reviewers.add(r);
    }



    /**
     * Extract the user id. The format of the line is
     *
     * 			userId:movie,rating;movie,rating;movie,rating;
     *
     * This method extracts the userID which comes before the colon
     */
    private int getUserId(String line){
        String userId = line.substring(0, line.indexOf(":"));
        try{
            int id = Integer.parseInt(userId);
            return id;
        }
        catch(NumberFormatException e){
            System.out.println(e);
            return(-1);
        }
    }


    public static void main(String[] args){
        NetflixFileProcessor p = new NetflixFileProcessor();
        p.readNetflixFiles("./src/movie_titles.txt", "./src/movie_reviews.txt");
        //p.readMovieTitlesFile("./src/movie_titles.txt");
        List<Movie> list = p.getMovies();

        for(Movie m : list){
            System.out.println(m.getMovieId() + " " + m.getTitle());
        }
    }

}
