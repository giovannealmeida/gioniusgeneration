package com.giog.gioniusgeneration.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.giog.gioniusgeneration.R;

import java.util.ArrayList;

/**
 * Created by Giovanne on 19/04/2015.
 */
public class ScoreAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public ScoreAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.item_score_odd, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView;

        if(position%2 == 0) {
            rowView = inflater.inflate(R.layout.item_score_even, parent, false);
        } else {
            rowView = inflater.inflate(R.layout.item_score_odd, parent, false);
        }

        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvScore = (TextView) rowView.findViewById(R.id.tvScore);

        String[] nameAndScore;
        nameAndScore = values.get(position).split("\\|");

        tvName.setText(nameAndScore[0]);
        tvScore.setText(nameAndScore[1]);

        return rowView;
    }
}
