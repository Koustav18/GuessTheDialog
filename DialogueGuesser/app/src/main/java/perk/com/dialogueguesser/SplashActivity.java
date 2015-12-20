package perk.com.dialogueguesser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import perk.com.dialogueguesser.model.DialogDataModel;
import perk.com.dialogueguesser.model.LevelDataModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;

public class SplashActivity extends AppCompatActivity {

    private BufferedReader reader = null;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        dbHandler=new DatabaseHandler(this);


        if(dbHandler.getDialogInfo()==null ){
            new ExtractMovieData().execute();
        }else {
            callOptionActivity();

        }
    }

    private void callOptionActivity(){
        Intent i = new Intent(SplashActivity.this, OptionsActivity.class);
        startActivity(i);
        finish();
    }


    private class ExtractMovieData extends AsyncTask<String,Void,String>{
        private ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            try{
                String[] list=getAssets().list("");
                ArrayList<LevelDataModel> levelDataModels=new ArrayList<LevelDataModel>();
                ArrayList<DialogDataModel> dialogDataModels=new ArrayList<DialogDataModel>();
                DialogDataModel dialogDataModel;
                LevelDataModel levelDataModel;
                int levelcount=0;
                for(String s:list) {
                    if (s.contains(".txt")) {
                        levelcount++;
                        levelDataModel=new LevelDataModel();
                        reader = new BufferedReader(
                                new InputStreamReader(getAssets().open(s)));

                        // do reading, usually loop until end of file reading
                        String mLine;
                        int totalDialog=0;
                        while ((mLine = reader.readLine()) != null) {
                            dialogDataModel= extractDialogInformation(mLine);
                            dialogDataModel.setLevelName(s.replace(".txt", ""));
                            dialogDataModels.add(dialogDataModel);
                            totalDialog++;
                        }
                        levelDataModel.setLevelName(s.replace(".txt", ""));
                        levelDataModel.setTotalDialog(totalDialog);
                        levelDataModel.setPointsToBeUnlocked(levelcount * 2);
                        levelDataModel.setCompletedDialog(0);
                        if(levelcount==1)
                            levelDataModel.setIsLocked(false);
                        else
                            levelDataModel.setIsLocked(true);
                        levelDataModels.add(levelDataModel);
                    }

                }

                dbHandler.addDialogInfo(dialogDataModels);
                dbHandler.addLevelData(levelDataModels);

            }catch (Exception e){

            }finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        //log the exception
                    }
                }
            }

            return null;
        }

        private DialogDataModel extractDialogInformation(String mLine){
            DialogDataModel dialogDataModel=new DialogDataModel();
            if(mLine.contains("(")){
                String dialog= mLine.substring(0,mLine.indexOf("("));
                dialogDataModel.setDialog(dialog);
                Log.d("Dialog",dialog);
            }
            if(mLine.contains("(") && mLine.contains(")")){
                String movieName=mLine.substring(mLine.indexOf("(")+1,mLine.indexOf(")"));
                Log.d("Movie",movieName);
                dialogDataModel.setMovieName(movieName);
            }if(mLine.contains("[") && mLine.contains("]")){
                String actor=mLine.substring(mLine.indexOf("[")+1,mLine.indexOf("]"));
                Log.d("Actotr",actor);
                dialogDataModel.setActor(actor);
            }if(mLine.contains("<") && mLine.contains(">")){
                String actress=mLine.substring(mLine.indexOf("<")+1,mLine.indexOf(">"));
                Log.d("Actress",actress);
                dialogDataModel.setActress(actress);
            }
            if(mLine.contains("{") && mLine.contains("}")){
                String director=mLine.substring(mLine.indexOf("{")+1,mLine.indexOf("}"));
                Log.d("director",director);
                dialogDataModel.setDirector(director);
            }
            if(mLine.contains("!") && mLine.contains("*")){
                String genre=mLine.substring(mLine.indexOf("!")+1,mLine.indexOf("*"));
                Log.d("genre",genre);
                dialogDataModel.setGenre(genre);

            }
            dialogDataModel.setIsAttempted(false);
            return dialogDataModel;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(SplashActivity.this);
            progressDialog.setMessage("Preparing Data. Please wait..");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            callOptionActivity();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
