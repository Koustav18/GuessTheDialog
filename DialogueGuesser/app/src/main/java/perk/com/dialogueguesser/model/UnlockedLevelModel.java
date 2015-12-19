package perk.com.dialogueguesser.model;

/**
 * Created by koroy on 12/19/2015.
 */
public class UnlockedLevelModel {

    private String levelname;
    private int points;
    private int completedDialogues;
    private int totalDialogues;

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCompletedDialogues() {
        return completedDialogues;
    }

    public void setCompletedDialogues(int completedDialogues) {
        this.completedDialogues = completedDialogues;
    }

    public int getTotalDialogues() {
        return totalDialogues;
    }

    public void setTotalDialogues(int totalDialogues) {
        this.totalDialogues = totalDialogues;
    }
}
