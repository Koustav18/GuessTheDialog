package perk.com.dialogueguesser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;

import perk.com.dialogueguesser.model.LevelDataModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;

public class StatisticsActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;
    private DatabaseHandler dbHandler;
    private TextView attemptedDialog,totalDialog,hintsTaken,levelAchieved,highestLevel;
    private ArrayList<LevelDataModel> levelDataModels;
    private int attemptedDialogValue,totalDialogValue;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        sharedpreferences = getSharedPreferences(AppUtils.MyPREFERENCES, Context.MODE_PRIVATE);

        dbHandler=new DatabaseHandler(this);
        initializeView();
        levelDataModels=dbHandler.getLevelInfo();
        attemptedDialogValue=0;
        totalDialogValue=0;

        int highestLevelValue=0;

        for(LevelDataModel levelDataModel:levelDataModels){
            attemptedDialogValue+=levelDataModel.getCompletedDialog();
            totalDialogValue+=levelDataModel.getTotalDialog();
            if(!levelDataModel.isLocked())
                highestLevelValue+=1;
        }

        attemptedDialog.setText(attemptedDialogValue+"");
        totalDialog.setText(totalDialogValue+"");
        hintsTaken.setText(sharedpreferences.getInt(AppUtils.KEY_HINTS,0)+"");
        levelAchieved.setText(highestLevelValue+"");
        highestLevel.setText(levelDataModels.size()+"");


    }

    private void initializeView(){
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        attemptedDialog=(TextView)findViewById(R.id.attempted_dialog);
        totalDialog=(TextView)findViewById(R.id.total_dialog);
        hintsTaken=(TextView)findViewById(R.id.total_hints);
        levelAchieved=(TextView)findViewById(R.id.level_reached);
        highestLevel=(TextView)findViewById(R.id.highest_level);

    }

}
