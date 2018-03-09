package com.example.david.helloworld.models.game;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.david.helloworld.R;

import java.util.ArrayList;

/**
 * Created by david on 6.3.2018..
 */

public class PractiseAdapterModel extends ArrayAdapter<PractiseModel> {


    public PractiseAdapterModel(Context context, ArrayList<PractiseModel> practiseRecordsList) {
        super(context, 0, practiseRecordsList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PractiseModel practiseModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.practise_record_item, parent, false);
        }

        TextView category = (TextView) convertView.findViewById(R.id.category_item);
        TextView level = (TextView) convertView.findViewById(R.id.level_item);
        TextView date = (TextView) convertView.findViewById(R.id.date_item);
        TextView points = (TextView) convertView.findViewById(R.id.points_item);

        category.setText(String.valueOf(practiseModel.getCategory()));
        level.setText(String.valueOf(practiseModel.getLevel()));
        date.setText(String.valueOf(practiseModel.getDatePlayed()));
        points.setText(String.valueOf(practiseModel.getPoints()));

        return convertView;
    }
}
