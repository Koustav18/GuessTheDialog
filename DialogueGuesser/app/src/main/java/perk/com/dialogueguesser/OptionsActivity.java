package perk.com.dialogueguesser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.perk.perksdk.PerkManager;

import java.util.ArrayList;

import perk.com.dialogueguesser.model.LevelDataModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;

public class OptionsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView playGame,statistics,settings;
    private  Animation popAnimation;
    private DatabaseHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        dbHandler=new DatabaseHandler(this);
        PerkManager.startSession(OptionsActivity.this, AppUtils.PERK_API_KEY);
        initializeActivity();

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame.startAnimation(popAnimation);
                popAnimation = AnimationUtils.loadAnimation(OptionsActivity.this, R.anim.pop_up);
                Intent i = new Intent(OptionsActivity.this,LevelSelectorActivity.class);
                startActivity(i);
            }
        });

        statistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics.startAnimation(popAnimation);
                popAnimation = AnimationUtils.loadAnimation(OptionsActivity.this, R.anim.pop_up);
                Intent i = new Intent(OptionsActivity.this,StatisticsActivity.class);
                startActivity(i);

            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.startAnimation(popAnimation);
                popAnimation = AnimationUtils.loadAnimation(OptionsActivity.this, R.anim.pop_up);
                PerkManager.showPortal(OptionsActivity.this, AppUtils.PERK_API_KEY);
            }
        });

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

    private void initializeActivity(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        playGame=(ImageView)findViewById(R.id.play_game);
        statistics=(ImageView)findViewById(R.id.stats);
        settings=(ImageView)findViewById(R.id.settings);

        popAnimation = AnimationUtils.loadAnimation(this, R.anim.pop_up);

    }



}
