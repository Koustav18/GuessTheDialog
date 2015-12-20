package perk.com.dialogueguesser.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import perk.com.dialogueguesser.GameActivity;
import perk.com.dialogueguesser.R;
import perk.com.dialogueguesser.interfaces.StartGameInterface;
import perk.com.dialogueguesser.model.DialogDataModel;
import perk.com.dialogueguesser.model.LockedLevelModel;
import perk.com.dialogueguesser.model.UnlockedLevelModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;
import perk.com.dialogueguesser.viewholders.LockedLevelViewHolder;
import perk.com.dialogueguesser.viewholders.UnlockedLevelViewHolder;

/**
 * Created by koroy on 12/19/2015.
 */
public class DialogueLevelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> levelItems;

    private final int LOCKED = 0, UNLOCKED = 1;
    private Animation popAnimation;
    private Context mContext;
    private StartGameInterface startGameInterface;


    public DialogueLevelAdapter(List<Object> levelItems,Context mContext,StartGameInterface startGameInterface) {
        this.levelItems = levelItems;
        this.mContext=mContext;
        this.startGameInterface=startGameInterface;

    }

    @Override
    public int getItemViewType(int position) {
        if (levelItems.get(position) instanceof LockedLevelModel) {
            return LOCKED;
        } else if (levelItems.get(position) instanceof UnlockedLevelModel) {
            return UNLOCKED;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        switch (viewType) {
            case LOCKED:
                View v1 = inflater.inflate(R.layout.lock_levels, viewGroup, false);
                viewHolder = new LockedLevelViewHolder(v1);
                break;
            case UNLOCKED:
                View v2 = inflater.inflate(R.layout.unlock_levels, viewGroup, false);
                viewHolder = new UnlockedLevelViewHolder(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.lock_levels, viewGroup, false);
                viewHolder = new LockedLevelViewHolder(v3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case LOCKED:
                LockedLevelViewHolder vh1 = (LockedLevelViewHolder) viewHolder;
                configureLockedViewHolder(vh1, position);
                break;
            case UNLOCKED:
                UnlockedLevelViewHolder vh2 = (UnlockedLevelViewHolder) viewHolder;
                configureUnlocedLevelViewHolder(vh2,position);
                break;
            default:
                LockedLevelViewHolder vh3 = (LockedLevelViewHolder) viewHolder;
                configureLockedViewHolder(vh3, position);
                break;
        }
    }

    private void configureLockedViewHolder(LockedLevelViewHolder lockedLevelViewHolder,int position){
        LockedLevelModel lockedLevelModel = (LockedLevelModel) levelItems.get(position);
        if (lockedLevelModel != null) {
            lockedLevelViewHolder.getLevelName().setText(lockedLevelModel.getLevelName());
            lockedLevelViewHolder.getLevelDescription().setText("You need "+lockedLevelModel.getDialogueToUnlock()+" more coins to unlock");
        }
    }

    private void configureUnlocedLevelViewHolder(final UnlockedLevelViewHolder unLockedLevelViewHolder,int position){
        final UnlockedLevelModel unlockedLevelModel=(UnlockedLevelModel)levelItems.get(position);
        if(unlockedLevelModel!=null){
            unLockedLevelViewHolder.getLevelName().setText(unlockedLevelModel.getLevelname());
            unLockedLevelViewHolder.getPoints().setText(unlockedLevelModel.getPoints() + "");
            unLockedLevelViewHolder.getDialogues().setText(unlockedLevelModel.getCompletedDialogues() + "/" + unlockedLevelModel.getTotalDialogues());
            unLockedLevelViewHolder.getProgressBar().setProgress(20*unlockedLevelModel.getCompletedDialogues());
            popAnimation = AnimationUtils.loadAnimation(mContext, R.anim.pop_up);
            if(levelItems==null || levelItems.size()<=0)
                unLockedLevelViewHolder.getStartLevel().setEnabled(false);
            unLockedLevelViewHolder.getStartLevel().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startGameInterface.startPressed(unlockedLevelModel.getLevelname());

                }
            });
        }
    }

    private int progressInfo(int complete,int total){
        Log.d("TAG","Value :"+(int)((complete/total)*100));
        return (int)((complete/total)*100);
    }

    @Override
    public int getItemCount() {
        return levelItems.size();
    }
}
