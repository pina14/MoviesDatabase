package Model;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by hugo on 04/04/2017.
 */
public class Serie {
    private String id;
    private String type;
    private String title;
    private String released;
    private String[] genres;
    private String[] actors;
    private String plot;
    private float imdbRating;
    private int runtime;
    private String posterLink;  //Link to Image
    private ImageIcon poster;   //Image
    private int numSeasons;
    private boolean isUpToDate;

    public Serie(String id, String type, String title, String released, String[] genres, String[] actors,
                 String plot, String imdbRating, String runtime, String poster, String totalSeasons) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.released = released;
        this.genres = genres;
        this.actors = actors;
        this.plot = plot;
        this.imdbRating = Float.parseFloat(imdbRating);
        this.runtime = Integer.parseInt(runtime.split(" ")[0]);
        posterLink = poster;
        try {
            this.poster = new ImageIcon(new URL(poster));
        } catch (MalformedURLException e) {
            System.out.println(posterLink+" is not a valid URL");
            this.posterLink = "https://image.flaticon.com/icons/svg/36/36601.svg";

        }
        numSeasons = Integer.parseInt(totalSeasons);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }

    public ImageIcon getPoster() {
        return poster;
    }

    public void setPoster(ImageIcon poster) {
        this.poster = poster;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public String[] getActors() {
        return actors;
    }

    public String[] getGenres() {
        return genres;
    }

    public String getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getNumSeasons() {
        return numSeasons;
    }

    public void setNumSeasons(int numSeasons) {
        this.numSeasons = numSeasons;
    }

    public boolean isUpToDate() {
        return isUpToDate;
    }

    public void setUpToDate(boolean upToDate) {
        isUpToDate = upToDate;
    }
}
