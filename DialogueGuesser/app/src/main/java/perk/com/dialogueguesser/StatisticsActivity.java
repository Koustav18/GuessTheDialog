package perk.com.dialogueguesser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

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

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FacebookCallback<LoginResult> mCallback=new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Profile profile = Profile.getCurrentProfile();
            if(profile!=null){
                Toast.makeText(StatisticsActivity.this, "Profile Name:" + profile.getFirstName(), Toast.LENGTH_SHORT).show();
                ShareDialog shareDialog = new ShareDialog(StatisticsActivity.this);
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("http://perk.com/perkstatic/home"))
                        .build();
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

            }
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_statistics);

        callbackManager=CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        // Callback registration
        loginButton.registerCallback(callbackManager, mCallback);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
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
