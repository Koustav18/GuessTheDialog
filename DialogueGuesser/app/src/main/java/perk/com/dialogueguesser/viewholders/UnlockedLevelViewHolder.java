package perk.com.dialogueguesser.viewholders;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import perk.com.dialogueguesser.R;

/**
 * Created by koroy on 12/19/2015.
 */
public class UnlockedLevelViewHolder extends RecyclerView.ViewHolder{

    TextView levelName;
    TextView points;
    TextView dialogues;
    ProgressBar progressBar;
    ImageView startLevel;

    public UnlockedLevelViewHolder(View v){
        super(v);
        levelName=(TextView)v.findViewById(R.id.level_name);
        points=(TextView)v.findViewById(R.id.points);
        dialogues=(TextView)v.findViewById(R.id.dialogues);
        progressBar=(ProgressBar)v.findViewById(R.id.level_progress_info);
        startLevel=(ImageView)v.findViewById(R.id.start);

    }

    public TextView getLevelName() {
        return levelName;
    }

    public void setLevelName(TextView levelName) {
        this.levelName = levelName;
    }

    public TextView getPoints() {
        return points;
    }

    public void setPoints(TextView points) {
        this.points = points;
    }

    public TextView getDialogues() {
        return dialogues;
    }

    public void setDialogues(TextView dialogues) {
        this.dialogues = dialogues;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public ImageView getStartLevel() {
        return startLevel;
    }

    public void setStartLevel(ImageView startLevel) {
        this.startLevel = startLevel;
    }
}
