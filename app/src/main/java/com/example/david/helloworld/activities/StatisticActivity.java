package com.example.david.helloworld.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.models.game.PractiseAdapterModel;
import com.example.david.helloworld.models.game.PractiseModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticActivity extends Activity {

    private ListView listView;
    TextView textLoadingScreen;
    ImageView back;
    String authToken;
    SharedPreferences sharedPreferences = null;
    ArrayList<PractiseModel> practiseRecordsList = new ArrayList<>();
    PractiseAdapterModel adapterModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        textLoadingScreen = (TextView) findViewById(R.id.text_loading_screen);
        textLoadingScreen.setText("Loading data...");

        sharedPreferences = getSharedPreferences("MY_PREF", 0);
        authToken = sharedPreferences.getString("HelloWorldToken", "");

        APIEndpoints service = ServiceGenerator.createServiceAuthorization(APIEndpoints.class, authToken);
        Call<ArrayList<PractiseModel>> getPractiseRecords = service.getPractiseRecords();

        getPractiseRecords.enqueue(new Callback<ArrayList<PractiseModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PractiseModel>> call, Response<ArrayList<PractiseModel>> response) {
                if (response.isSuccessful()) {
                    practiseRecordsList = response.body();
                    initializeElements();
                } else {
                    ServiceGenerator.handleError(response, StatisticActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PractiseModel>> call, Throwable t) {
                Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(StatisticActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void initializeElements() {
        setContentView(R.layout.activity_statistic);
        listView = (ListView) findViewById(R.id.list_view);
        back = (ImageView) findViewById(R.id.back_statistics);

        adapterModel = new PractiseAdapterModel(this, practiseRecordsList);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView.setAdapter(adapterModel);
        registerForContextMenu(listView);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
