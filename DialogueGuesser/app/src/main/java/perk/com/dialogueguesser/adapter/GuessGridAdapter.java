package perk.com.dialogueguesser.adapter;

import android.app.Activity;
import android.content.Context;
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
public class GuessGridAdapter extends BaseAdapter {

    private char[] mGuessString;
    private static LayoutInflater inflater = null;

    public GuessGridAdapter(Context context,
                            char[] mItems) {
        this.mGuessString = mItems;
        inflater = LayoutInflater.from(context);
    }

        @Override
        public int getCount() {
            if (mGuessString.length <= 0)
                return 0;
            return mGuessString.length;

        }

        @Override
        public Object getItem(int position) {
            return mGuessString[position];
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
                holder.letter = (TextView) convertView
                        .findViewById(R.id.mGuessCharacter);

                holder.letter.setText(mGuessString[position]+"");
                convertView.setTag(holder);

                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putInt("position", position);
                        msg.what = 0;
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
