package perk.com.dialogueguesser.model;

/**
 * Created by koroy on 12/19/2015.
 */
public class DialogDataModel {

    private String levelName;
    private String dialog;
    private String actor;
    private String actress;
    private String director;
    private String movieName;
    private String genre;
    private boolean isAttempted;

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setIsAttempted(boolean isAttempted) {
        this.isAttempted = isAttempted;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getActress() {
        return actress;
    }

    public void setActress(String actress) {
        this.actress = actress;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
