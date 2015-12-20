package perk.com.dialogueguesser.utility;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import perk.com.dialogueguesser.R;

/**
 * Created by koroy on 12/20/2015.
 */
public class CustomAlertClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity activity;
    public Dialog d;
    public Button yes, no;
    private String type,hint;
    private CustomHintDialog customHintDialog;

    public CustomAlertClass(Activity a,String type,String hint) {
        super(a);
        // TODO Auto-generated constructor stub
        this.activity = a;
        this.type=type;
        this.hint=hint;
        customHintDialog=new CustomHintDialog(activity,this.type,this.hint);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_box);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                customHintDialog.show();
                //activity.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
