package perk.com.dialogueguesser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import perk.com.dialogueguesser.adapter.DialogueLevelAdapter;
import perk.com.dialogueguesser.interfaces.StartGameInterface;
import perk.com.dialogueguesser.model.LevelDataModel;
import perk.com.dialogueguesser.model.LockedLevelModel;
import perk.com.dialogueguesser.model.UnlockedLevelModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;

public class LevelSelectorActivity extends AppCompatActivity implements StartGameInterface{

    private RecyclerView dialoguelevels;
    private DialogueLevelAdapter dialogueLevelAdapter;
    private DatabaseHandler dbHandler;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);
        dbHandler=new DatabaseHandler(this);
        ArrayList<LevelDataModel> levelDataModels=dbHandler.getLevelInfo();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialoguelevels = (RecyclerView) findViewById(R.id.level_list);

        dialogueLevelAdapter = new DialogueLevelAdapter(createDummyData(levelDataModels), LevelSelectorActivity.this,this);
        dialoguelevels.setAdapter(dialogueLevelAdapter);
        dialoguelevels.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void startPressed(String levelName) {
        Intent i = new Intent(LevelSelectorActivity.this, GameActivity.class);
        i.putExtra(AppUtils.LEVEL_INFO, levelName);
        startActivity(i);
        finish();
    }

    private List<Object> createDummyData(ArrayList<LevelDataModel> levelDataModels){
        List<Object> dummyData=new ArrayList<>();

        for (LevelDataModel levelDataModel:levelDataModels){
            if(levelDataModel.isLocked()){
                LockedLevelModel lockedLevelModel=new LockedLevelModel();
                lockedLevelModel.setLevelName(levelDataModel.getLevelName());
                lockedLevelModel.setDialogueToUnlock(levelDataModel.getPointsToBeUnlocked());
                dummyData.add(lockedLevelModel);
            }else{
                UnlockedLevelModel unlockedLevelModel=new UnlockedLevelModel();
                unlockedLevelModel.setLevelname(levelDataModel.getLevelName());
                unlockedLevelModel.setPoints(levelDataModel.getCompletedDialog());
                unlockedLevelModel.setCompletedDialogues(levelDataModel.getCompletedDialog());
                unlockedLevelModel.setTotalDialogues(levelDataModel.getTotalDialog());
                dummyData.add(unlockedLevelModel);
            }
        }

        return dummyData;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
