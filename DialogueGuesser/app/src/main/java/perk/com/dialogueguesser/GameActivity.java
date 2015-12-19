package perk.com.dialogueguesser;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.perk.perksdk.PerkManager;

import java.util.ArrayList;
import java.util.Random;

import perk.com.dialogueguesser.adapter.AnswerGridAdapter;
import perk.com.dialogueguesser.adapter.GuessGridAdapter;
import perk.com.dialogueguesser.model.DialogDataModel;
import perk.com.dialogueguesser.utility.AppUtils;
import perk.com.dialogueguesser.utility.DatabaseHandler;

public class GameActivity extends AppCompatActivity {

    private DatabaseHandler dbHandler;
    GridView answerGrid;
    GridView guessGrid;
    public static Handler mUIUpdate;
    private char[] guessCharArray;
    private char[] ansCharArray;
    private int ansLength;
    private char[] movieName;
    private char[] backupGuessCharArray;
    private TextView dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        dbHandler=new DatabaseHandler(this);
        PerkManager.startSession(GameActivity.this, AppUtils.PERK_API_KEY);
        dialog=(TextView)findViewById(R.id.dialog_value);

        String currentName=getIntent().getStringExtra(AppUtils.LEVEL_INFO);
        if(!TextUtils.isEmpty(currentName)){
            ArrayList<DialogDataModel> dialogDataModels=dbHandler.getIncompletedDialogFilteredByLevel(currentName);
            Log.d("TAG","Incompleted Dialog Size is :"+dialogDataModels.size());
            movieName=dialogDataModels.get(0).getMovieName().toCharArray();
            dialog.setText(dialogDataModels.get(0).getDialog());

        }

        mUIUpdate = UIUpdate;

        answerGrid = (GridView) findViewById(R.id.mAnswer);
        guessGrid = (GridView) findViewById(R.id.mGuess);

        guessCharArray=generateGuessCharArray(movieName);
        backupGuessCharArray=guessCharArray.clone();
        ansCharArray=new char[movieName.length];

        for(int i=0;i<movieName.length;i++)
            ansCharArray[i]=' ';

        refreshGrid();


    }

    private void refreshGrid(){
        guessGrid.setAdapter(new GuessGridAdapter(this, guessCharArray));
        answerGrid.setAdapter(new AnswerGridAdapter(this, ansCharArray));
    }

    public static char[] shuffle(StringBuilder sb) {
        Random rand = new Random();
        for (int i = sb.length() - 1; i > 1; i--) {
            int swapWith = rand.nextInt(i);
            char tmp = sb.charAt(swapWith);
            sb.setCharAt(swapWith, sb.charAt(i));
            sb.setCharAt(i, tmp);
        }
        String s = new String(sb);
        return s.toCharArray();
    }

    private char[] generateGuessCharArray(char[] movieArray){
        Random r = new Random();

        String alphabet = "abcdefghijklmnopqrstwxyz";
        String tempString="";
        for (int i = 0; i < 3; i++) {
            tempString+=alphabet.charAt(r.nextInt(alphabet.length()));
        } // prints 50 random characters from alphabet

        StringBuilder sb = new StringBuilder(new String(movieArray)+tempString);
        return shuffle(sb);

    }



    Handler UIUpdate = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            int topicID = msg.what;
            Bundle data = msg.getData();
            Integer position = data.getInt("position");

            switch(topicID)
            {
                case 0://Guess Clicked
                    boolean insert = false;
                    boolean over = false;
                    if(guessCharArray[position]==' '){
                        return;
                    }
                    for(int i=0; i< ansCharArray.length; i++){
                        if(ansCharArray[i]==' '){
                            ansCharArray[i]=guessCharArray[position];
                            guessCharArray[position]=' ';
                            insert = true;
                            if(i+1 == ansCharArray.length){
                                over = true;
                            }
                            break;
                        }
                    }
                    if(insert){
                        refreshGrid();
                    }
                    if(over){
                        gameOver();
                    }
                    break;
                case 1://Answer Clicked
                    if(ansCharArray[position]==' '){

                    }else{
                        // mSampleAnswer.set(i, mSampleGuess.get(position));

                        char letter = ansCharArray[position];
                        for(int i=0; i< backupGuessCharArray.length; i++){
                            if(letter==backupGuessCharArray[i]){
                                if(guessCharArray[i]==' '){
                                    guessCharArray[i]=letter;
                                    break;
                                }
                            }
                        }
                        ansCharArray[position]=' ';
                        refreshGrid();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    };


    private void gameOver(){
        String actualMovieName=new String(movieName);
        String ansGiven = new String(ansCharArray);
        if(actualMovieName.equalsIgnoreCase(ansGiven)){
            Toast.makeText(GameActivity.this, "You have Won", Toast.LENGTH_SHORT).show();

            //PerkManager.showPortal(GameActivity.this, AppUtils.PERK_API_KEY);
           PerkManager.trackEvent(GameActivity.this, AppUtils.PERK_API_KEY, AppUtils.PERK_EVENT_ID, false, null, true);
        }else{
            Toast.makeText(GameActivity.this, "You have Lost", Toast.LENGTH_SHORT).show();
        }
    }

}
