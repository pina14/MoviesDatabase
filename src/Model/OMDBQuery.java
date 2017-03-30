package Model;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * Created by hugo on 15/03/2017.
 */
public class OMDBQuery {

    private static ArrayList<MovieSerie> movies = new ArrayList<>();

    public static ArrayList<MovieSerie> moviesInformationFromDirectory(String directory) {
        ArrayList<String> movieNames = getFiles(directory);
        int countSuccess = 0, countFail = 0;

        for (String movieName : movieNames) {
            if(getMoviesInfo(movieName))
                countSuccess++;
            else
                countFail++;
        }

        System.out.println("Number of series/movies added: "+countSuccess);
        System.out.println("Number of series/movies failed: "+countFail);

        return movies;
    }

    public static ArrayList<MovieSerie> moviesInformationFromName(String movieName) {
        int countSuccess = 0, countFail = 0;

        movieName = filterMovieName(movieName);

        if(getMoviesInfo(movieName))
            countSuccess++;
        else
            countFail++;

        System.out.println("Number of series/movies added: "+countSuccess);
        System.out.println("Number of series/movies failed: "+countFail);

        return movies;
    }

    private static boolean getMoviesInfo(String movieName) {
        boolean successNFail = false;

        try {
            String urlIn = "http://www.omdbapi.com/?t=" + URLEncoder.encode(movieName, "UTF-8");
            InputStream input = new URL(urlIn).openStream();
            InputStreamReader reader = new InputStreamReader(input, "UTF-8"); //codificado em UTF-8
            Map<String, String> map = new Gson().fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType()); //tem de ser object pois os ratings são arrays e o resto são strings

            //movie ID
            String ID = map.get("imdbID");

            //Movie Title
            String title = map.get("Title");
            if (title != null && title.equals("N/A"))
                title = "not found";
            else if(title != null && title.contains("'"))
                title = title.replace("'", "`");

            //Movie Type (Movie, Series)
            String type = map.get("Type");
            if (type != null && type.equals("N/A"))
                type = "not found";

            //Movie Plot
            String plot = map.get("Plot");
            if (plot != null && plot.equals("N/A"))
                plot = "not found";
            else if(plot != null && plot.contains("'"))
                plot = plot.replace("'", "`");

            //Movie Released Date
            String released = map.get("Released");
            released = transformDate(released);

            //Movie Rating
            String imdbRating = map.get("imdbRating");
            if (imdbRating != null && imdbRating.equals("N/A"))
                imdbRating = "0";

            //Movie Runtime
            String runtime = map.get("Runtime");
            if (runtime != null && runtime.equals("N/A"))
                runtime = "-1";

            //Movie Poster Link
            String poster = map.get("Poster");
            if (poster != null && poster.equals("N/A"))
                poster = "0";

            //genre
            String genre = map.get("Genre");
            String[] genres = null;
            if (genre != null)
                genres = genre.split(",");

            //actor
            String actor = map.get("Actors");
            String[] actors = null;
            if (actor != null) {
                if(actor.contains("'"))
                    actor = actor.replace("'", "`");

                actors = actor.split(",");
            }

            if (ID == null) {
                System.out.println(movieName + " not Found!");
            } else {

                //set insertion in data base successful
                successNFail = true;

                //add Movie/Serie to array
                movies.add(new MovieSerie(ID, type, title, released, genres, actors, plot, imdbRating, runtime, poster));

                /*//print in console
                System.out.println("ID: " + ID);
                System.out.println("Type: " + type);
                System.out.println("Title: " + title);
                System.out.println("Released: " + released);
                System.out.println("Genre: " + genre);
                System.out.println("Actors: " + actor);
                System.out.println("Plot: " + plot);
                System.out.println("IMDB Rating: " + imdbRating);
                System.out.println("Runtime: " + runtime);
                System.out.println("Poster: " + poster);
                System.out.println();*/
            }

        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return successNFail;
    }

    private static String transformDate(String s) {
        //if there's no information return a valid date
        if(s == null || s.equals("N/A"))
            return "0001-01-01";

        //getting the month from the string
        String[] date = s.split(" ");
        String month = date[1];
        String m = "";

        //converting to valid month type for SQL Server
        switch (month){
            case "Jan": m = "01"; break;
            case "Feb": m = "02"; break;
            case "Mar": m = "03"; break;
            case "Apr": m = "04"; break;
            case "May": m = "05"; break;
            case "Jun": m = "06"; break;
            case "Jul": m = "07"; break;
            case "Aug": m = "08"; break;
            case "Sep": m = "09"; break;
            case "Oct": m = "10"; break;
            case "Nov": m = "11"; break;
            case "Dec": m = "12"; break;
        }

        //returning a valid data for SQL Server input
        return date[2]+"-"+m+"-"+date[0];
    }

    private static String[] filterFilesNames(File[] files) {
        String[] movieNames = new String[files.length];

        for (int i = 0; i < files.length; i++)
            movieNames[i] = filterMovieName(files[i].getName());

        return movieNames;
    }

    private static String filterMovieName(String movieName) {
        int lastDotSpace = 0;
        char c;
        if(movieName.contains("UNRATED"))
            movieName = movieName.replace("UNRATED", "");
        if(movieName.charAt(0) == '*')
            return movieName;
        for (int j = 0; j < movieName.length(); j++) {
            c = movieName.charAt(j);
            if (c == '.' || c == ' ')
                lastDotSpace = j;
            if(c >= '0' && c <= '9'){
                if(lastDotSpace == 0 || j+1 < movieName.length() && (movieName.charAt(j+1) < '0' || movieName.charAt(j+1) > '9'))
                    continue;
                movieName = movieName.substring(0, lastDotSpace);
                break;
            }
        }
        return movieName.replace(".", " ");
    }

    //Get file names for all the folders in a folder
    private static ArrayList<String> getFiles(String directoryName) {
        File[] res = new File(directoryName).listFiles();
        ArrayList<String> total = new ArrayList<>();

        if(res != null) {
            for (File f : res) {

                if (f.isDirectory()) {
                    File[] files = new File(directoryName + "/" + f.getName()).listFiles();
                    boolean isSeason = false;

                    if (files == null || files.length == 0)
                        break;
                    for (File file : files) {
                        if (file.getName().contains("Season") || file.getName().contains("Book") || file.getName().contains("Temporada")) {
                            isSeason = true;
                            break;
                        }
                    }

                    if (files[0].isDirectory() && !isSeason)
                        Collections.addAll(total, filterFilesNames(files));
                    else
                        total.add(filterMovieName(f.getName()));
                }
            }
        }
        return total;
    }

}
