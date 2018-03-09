package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.david.helloworld.R;
import com.example.david.helloworld.helpers.NetworkHelper;
import com.example.david.helloworld.models.game.Category;
import com.example.david.helloworld.models.game.PractiseRequestModel;
import com.example.david.helloworld.models.game.QuestionLevel;
import com.example.david.helloworld.services.BackgroundMusicService;

public class PractiseActivity extends Activity {

    ImageView webProgrammingBtn;
    ImageView databaseBtn;
    ImageView algorithmsBtn;
    ImageView oopBtn;
    ImageView dataStructuresBtn;
    ImageView historyBtn;
    ImageView backBtn;
    PractiseRequestModel practiseRequestModel;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practise);

        webProgrammingBtn = (ImageView) findViewById(R.id.web_programming_practise);
        databaseBtn = (ImageView) findViewById(R.id.database_practise);
        algorithmsBtn = (ImageView) findViewById(R.id.algorithms_practise);
        oopBtn = (ImageView) findViewById(R.id.oop_practise);
        dataStructuresBtn = (ImageView) findViewById(R.id.data_structures_practise);
        historyBtn = (ImageView) findViewById(R.id.history_practise);
        backBtn = (ImageView) findViewById(R.id.back_practise);
        practiseRequestModel = new PractiseRequestModel();

        webProgrammingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.WebProgramming);
            }
        });

        databaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.Database);
            }
        });

        algorithmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.Algorithms);
            }
        });

        oopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.ObjectOriented);
            }
        });

        dataStructuresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.DataStructures);
            }
        });

        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevel(Category.History);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showLevel(Category category) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_levels);

        TextView rookie = (TextView) dialog.findViewById(R.id.rookie_level);
        TextView pro = (TextView) dialog.findViewById(R.id.pro_level);
        TextView ghost = (TextView) dialog.findViewById(R.id.ghost_level);

        practiseRequestModel.setCategory(category);

        rookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                practiseRequestModel.setLevel(QuestionLevel.Easy);
                dialog.dismiss();
                navigateToQuestionPractise();
            }
        });

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                practiseRequestModel.setLevel(QuestionLevel.Medium);
                dialog.dismiss();
                navigateToQuestionPractise();
            }
        });

        ghost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                practiseRequestModel.setLevel(QuestionLevel.Hard);
                dialog.dismiss();
                navigateToQuestionPractise();
            }
        });

        dialog.show();
    }

    private void navigateToQuestionPractise() {
        Intent intent = new Intent(PractiseActivity.this, PractiseQuestionActivity.class);
        intent.putExtra("PractiseRequestModel", practiseRequestModel);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PractiseActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
