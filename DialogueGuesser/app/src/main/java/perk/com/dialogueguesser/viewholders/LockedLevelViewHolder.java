package perk.com.dialogueguesser.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import perk.com.dialogueguesser.R;

/**
 * Created by koroy on 12/19/2015.
 */
public class LockedLevelViewHolder extends RecyclerView.ViewHolder{

        TextView levelName;
        TextView levelDescription;

        public LockedLevelViewHolder(View v){
            super(v);
            levelName=(TextView)v.findViewById(R.id.level_name);
            levelDescription=(TextView)v.findViewById(R.id.level_description);
        }

        public TextView getLevelName() {
        return levelName;
        }

        public void setLevelName(TextView levelName) {
        this.levelName = levelName;
        }

        public TextView getLevelDescription() {
        return levelDescription;
        }

        public void setLevelDescription(TextView levelDescription) {
        this.levelDescription = levelDescription;
        }
}
