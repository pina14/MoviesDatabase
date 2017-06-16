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

    public static ArrayList<Movie> moviesInformationFromDirectory(String directory) {
        ArrayList<Movie> movies = new ArrayList<>();

        ArrayList<String> movieNames = getFiles(directory);
        int countSuccess = 0, countFail = 0;

        for (String movieName : movieNames) {
            if(getMoviesInfo(movieName, movies))
                countSuccess++;
            else
                countFail++;
        }

        System.out.println("Number of series/movies added: "+countSuccess);
        System.out.println("Number of series/movies failed: "+countFail);

        return movies;
    }

    public static ArrayList<Movie> moviesInformationFromName(String movieName) {
        ArrayList<Movie> movies = new ArrayList<>();

        int countSuccess = 0, countFail = 0;

        movieName = filterMovieName(movieName);

        if(getMoviesInfo(movieName, movies))
            countSuccess++;
        else
            countFail++;

        System.out.println("Number of series/movies added: "+countSuccess);
        System.out.println("Number of series/movies failed: "+countFail);

        return movies;
    }

    private static boolean getMoviesInfo(String movieName, ArrayList<Movie> movies) {
        boolean successNFail = false;

        try {
            String urlIn = "http://www.omdbapi.com/?&apikey=7b4152d4&t="+URLEncoder.encode(movieName, "UTF-8"); //+URLEncoder.encode("Life in Pieces", "UTF-8")+"&Season=2";
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
                genres = genre.split(", ");

            //actor
            String actor = map.get("Actors");
            String[] actors = null;
            if (actor != null) {
                if(actor.contains("'"))
                    actor = actor.replace("'", "`");

                actors = actor.split(", ");
            }

            if (ID == null) {
                System.out.println(movieName + " not Found!");
            } else {

                //set insertion in data base successful
                successNFail = true;

/*                //add Serie to array
                if(type != null && type.equals("series")){
                    String totalSeasons = map.get("totalSeasons");
                    Serie serie = new Serie(ID, type, title, released, genres, actors, plot, imdbRating, runtime, poster, totalSeasons);
                    boolean isUpToDate = checkUpToDate(serie, movieName, directory);
                    serie.setUpToDate(isUpToDate);
                    series.add(serie);
                }*/
//                else //add movie to array

                if(!movieAlreadyExistst(movies, ID))
                    movies.add(new Movie(ID, type, title, released, genres, actors, plot, imdbRating, runtime, poster));
            }

        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return successNFail;
    }

    private static boolean movieAlreadyExistst(ArrayList<Movie> movies, String id) {
        for (Movie movie : movies) {
            if(movie.getId().equals(id))
                return true;
        }

        return false;
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
//        File aux = null;

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
//                            aux = file;
                            break;
                        }
                    }

//                    lastEpisodeOfSerie(aux, f.getName());

                    if (files[0].isDirectory() && !isSeason)
                        Collections.addAll(total, filterFilesNames(files));
                    else
                        total.add(filterMovieName(f.getName()));
                }
            }
        }
        return total;
    }

    /********************code about series and the last episode***********/

/*    private static boolean checkUpToDate(Serie serie, String movieName, String directory) throws IOException {
        String urlIn = "http://www.omdbapi.com/?t="+URLEncoder.encode(serie.getTitle(), "UTF-8")+"&Season="+serie.getNumSeasons();
        InputStream input = new URL(urlIn).openStream();
        InputStreamReader reader = new InputStreamReader(input, "UTF-8"); //codificado em UTF-8
        Map<String, Object> map = new Gson().fromJson(reader, new TypeToken<Map<String, Object>>(){}.getType()); //tem de ser object pois os ratings são arrays e o resto são strings

        ArrayList<LinkedTreeMap> episodes = (ArrayList<LinkedTreeMap>)map.get("Episodes");

        int lastEpisodeNumber = Integer.parseInt(String.valueOf(episodes.get(episodes.size()-1).get("Episode")));

        if(lastEpisodeNumber == filterLastEpisodeNumber(movieName, directory))
            return true;

        return false;
    }

    private static int filterLastEpisodeNumber(String movieName, String directory) {
        File temp = new File(directory+"\\"+movieName);
        return 0;
    }

    private static void lastEpisodeOfSerie(File aux, String serieName) {
        File[] subFiles = null;
        while (aux != null) {
            subFiles = aux.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    if (name.contains("Season") || name.contains("Book") || name.contains("Temporada"))
                        return true;
                    return false;
                }
            });
        }

        File lastSeason = getLastSeasonFile(subFiles);
    }

    private static File getLastSeasonFile(File[] subFiles) {
        if(subFiles[0].getName().co)
    }*/

}
