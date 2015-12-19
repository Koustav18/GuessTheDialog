package perk.com.dialogueguesser.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import perk.com.dialogueguesser.GameActivity;
import perk.com.dialogueguesser.R;

/**
 * Created by nithyanantham on 19/12/15.
 */
public class AnswerGridAdapter extends BaseAdapter {

    private char[] mAnswerString;
    private static LayoutInflater inflater = null;
    private Context mContext;

    public AnswerGridAdapter(Context context,
                             char[] mItems) {
        this.mAnswerString = mItems;
        this.mContext=context;
        inflater = LayoutInflater.from(context);
    }

        @Override
        public int getCount() {
            if (mAnswerString.length <= 0)
                return 0;
            return mAnswerString.length;

        }

        @Override
        public Object getItem(int position) {
            return mAnswerString[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            TextView letter;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.guess_grid_element, null);
                convertView.setBackgroundColor(Color.parseColor("#FFD700"));
                holder.letter = (TextView) convertView
                        .findViewById(R.id.mGuessCharacter);
                holder.letter.setTextColor(Color.parseColor("#4B0082"));

                holder.letter.setText(mAnswerString[position] + "");
                convertView.setTag(holder);

                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putInt("position", position);
                        msg.what = 1;
                        msg.setData(data);

                        if (GameActivity.mUIUpdate != null)
                            GameActivity.mUIUpdate.sendMessage(msg);


                    }
                });


            } else{
                holder = (ViewHolder) convertView.getTag();

            }



            return convertView;

        }


    }
