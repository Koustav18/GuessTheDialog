package perk.com.dialogueguesser.model;

/**
 * Created by koroy on 12/19/2015.
 */
public class LevelDataModel {

    private String levelName;
    private int completedDialog;
    private int totalDialog;
    private boolean isLocked;
    private int pointsToBeUnlocked;

    public int getPointsToBeUnlocked() {
        return pointsToBeUnlocked;
    }

    public void setPointsToBeUnlocked(int pointsToBeUnlocked) {
        this.pointsToBeUnlocked = pointsToBeUnlocked;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getCompletedDialog() {
        return completedDialog;
    }

    public void setCompletedDialog(int completedDialog) {
        this.completedDialog = completedDialog;
    }

    public int getTotalDialog() {
        return totalDialog;
    }

    public void setTotalDialog(int totalDialog) {
        this.totalDialog = totalDialog;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }
}
