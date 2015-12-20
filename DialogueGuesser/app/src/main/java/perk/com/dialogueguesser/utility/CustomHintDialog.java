package perk.com.dialogueguesser.utility;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import perk.com.dialogueguesser.R;

/**
 * Created by koroy on 12/20/2015.
 */
public class CustomHintDialog extends Dialog implements
        android.view.View.OnClickListener {

    private Activity activity;
    private Dialog d;
    private Button ok;
    private TextView hintTextView;
    private String type, hint;


    public CustomHintDialog(Activity a,String type,String value) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.type=type;
        this.hint=value;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_hint_alert);
        hintTextView=(TextView)findViewById(R.id.txt_dia);
        hintTextView.setText(type + " of the Movie was: "+hint);
        ok = (Button) findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
