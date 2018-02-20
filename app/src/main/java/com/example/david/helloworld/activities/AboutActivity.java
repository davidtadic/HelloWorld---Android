package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.helpers.NetworkHelper;
import com.example.david.helloworld.models.user.MailModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends Activity {

    RatingBar ratingBar;
    EditText title;
    EditText message;
    Button sendBtn;

    private SharedPreferences sharedPreferences = null;
    private String authToken;
    MailModel mailModel = null;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        title = (EditText) findViewById(R.id.title_about);
        message = (EditText) findViewById(R.id.message_about);
        sendBtn = (Button) findViewById(R.id.send_feedback_btn);
        mailModel = new MailModel();

        sharedPreferences = getSharedPreferences("MY_PREF", 0);
        authToken = sharedPreferences.getString("HelloWorldToken", "");

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mailModel.setRating((double)rating);
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    private void sendFeedback() {
        if (validateForm()) {
            NetworkHelper.isNetworkAvailable(context, sharedPreferences);
            sendBtn.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(AboutActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("We're sending your feedback. Please wait...");
            progressDialog.show();

            mailModel.setSubject(title.getText().toString());
            mailModel.setMessage(message.getText().toString());

            APIEndpoints service = ServiceGenerator.createServiceAuthorization(APIEndpoints.class, authToken);
            Call<Boolean> sendFeedbackCall = service.sendFeedback(mailModel);

            sendFeedbackCall.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    sendBtn.setEnabled(true);
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        Toast.makeText(AboutActivity.this, "Your message was sent successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AboutActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        ServiceGenerator.handleError(response, AboutActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    sendBtn.setEnabled(true);
                    progressDialog.dismiss();
                    Toast.makeText(AboutActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean validateForm() {

        String subject = title.getText().toString();
        String messageString = message.getText().toString();

        if (subject.isEmpty()) {
            title.setError("Title is required");
            return false;
        } else {
            title.setError(null);
        }

        if (messageString.isEmpty()) {
            message.setError("Message is required");
            return false;
        } else {
            message.setError(null);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AboutActivity.this, SettingsActivity.class);
        startActivity(i);
        finish();
    }
}
