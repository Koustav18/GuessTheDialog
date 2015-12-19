package perk.com.dialogueguesser.model;

/**
 * Created by koroy on 12/19/2015.
 */
public class LockedLevelModel {

    private String levelName;
    private int dialogueToUnlock;

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getDialogueToUnlock() {
        return dialogueToUnlock;
    }

    public void setDialogueToUnlock(int dialogueToUnlock) {
        this.dialogueToUnlock = dialogueToUnlock;
    }
}
