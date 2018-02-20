package com.example.david.helloworld.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.models.game.Category;
import com.example.david.helloworld.models.game.PractiseModel;
import com.example.david.helloworld.models.game.PractiseRequestModel;
import com.example.david.helloworld.models.game.QuestionLevel;
import com.example.david.helloworld.models.game.QuestionModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PractiseQuestionActivity extends Activity {

    TextView scoreTxt;
    TextView countdownTimer;
    TextView questionTxt;
    ImageView questionImage;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    SharedPreferences sharedPreferences = null;
    TextView textLoadingScreen;
    TextView scoreInfoLastScreen;
    ImageButton confirmButton;
    String authToken;
    CountDownTimer timer;
    ArrayList<QuestionModel> questions = new ArrayList<QuestionModel>();
    int counterQuestion = 0;
    int points = 0;
    ProgressBar progressBarPractise;
    PractiseRequestModel requestModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);
        textLoadingScreen = (TextView) findViewById(R.id.text_loading_screen);
        textLoadingScreen.setText("We are preparing your questions");

        sharedPreferences = getSharedPreferences("MY_PREF", 0);
        authToken = sharedPreferences.getString("HelloWorldToken", "");
        requestModel = (PractiseRequestModel) getIntent().getSerializableExtra("PractiseRequestModel");

        APIEndpoints service = ServiceGenerator.createServiceAuthorization(APIEndpoints.class, authToken);
        Call<ArrayList<QuestionModel>> getQuestionsPractiseCall = service.getQuestionsForPractise(requestModel);

        getQuestionsPractiseCall.enqueue(new Callback<ArrayList<QuestionModel>>() {
            @Override
            public void onResponse(Call<ArrayList<QuestionModel>> call, Response<ArrayList<QuestionModel>> response) {
                if (response.isSuccessful()) {
                    initializeElements();
                    questions = response.body();
                    setQuestion();
                    startTimer();
                } else {
                    ServiceGenerator.handleError(response, PractiseQuestionActivity.this);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<QuestionModel>> call, Throwable t) {
                Intent intent = new Intent(PractiseQuestionActivity.this, PractiseActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(PractiseQuestionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startTimer() {
        timer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownTimer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if (counterQuestion == questions.size() - 1) {
                    showLastScreen();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            counterQuestion++;
                            setEnabledButtons();
                            returnBackground();
                            setQuestion();
                        }
                    });

                    timer.start();
                }
            }
        }.start();
    }

    private void setQuestion() {
        QuestionModel questionModel = questions.get(counterQuestion);
        List<String> answers = questionModel.shuffleAnswers();
        questionTxt.setText(questionModel.getQuestionName());
        answer1.setText(answers.get(0));
        answer2.setText(answers.get(1));
        answer3.setText(answers.get(2));
        answer4.setText(answers.get(3));
        if (questionModel.getImageThumbnail() == null) {
            questionImage.setVisibility(View.GONE);
        } else {
            byte[] decodedString = Base64.decode(questionModel.getImageThumbnail(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            questionImage.setImageBitmap(decodedByte);
        }

        checkQuestion(questionModel);
    }

    private void checkQuestion(QuestionModel questionModel) {
        final String correctAnswer = questionModel.getCorrectAnswer();

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDisabledButtons();
                timer.cancel();
                if (answer1.getText().toString().equals(correctAnswer)) {
                    answer1.setBackgroundResource(R.drawable.round_buttons_correct);
                    answer1.setTextColor(Color.BLACK);
                    points += 5;
                } else {
                    answer1.setBackgroundResource(R.drawable.round_buttons_wrong);
                    answer1.setTextColor(Color.BLACK);
                }

                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        timer.onFinish();

                    }
                }, 800);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                setDisabledButtons();
                if (answer2.getText().toString().equals(correctAnswer)) {
                    answer2.setBackgroundResource(R.drawable.round_buttons_correct);
                    answer2.setTextColor(Color.BLACK);
                    points += 5;
                } else {
                    answer2.setBackgroundResource(R.drawable.round_buttons_wrong);
                    answer2.setTextColor(Color.BLACK);
                }

                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        timer.onFinish();

                    }
                }, 800);
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                setDisabledButtons();
                if (answer3.getText().toString().equals(correctAnswer)) {
                    answer3.setBackgroundResource(R.drawable.round_buttons_correct);
                    answer3.setTextColor(Color.BLACK);
                    points += 5;
                } else {
                    answer3.setBackgroundResource(R.drawable.round_buttons_wrong);
                    answer3.setTextColor(Color.BLACK);
                }

                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        timer.onFinish();

                    }
                }, 800);
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                setDisabledButtons();
                if (answer4.getText().toString().equals(correctAnswer)) {
                    answer4.setBackgroundResource(R.drawable.round_buttons_correct);
                    answer4.setTextColor(Color.BLACK);
                    points += 5;
                } else {
                    answer4.setBackgroundResource(R.drawable.round_buttons_wrong);
                    answer4.setTextColor(Color.BLACK);
                }

                new Handler().postDelayed(new Runnable() {

                    public void run() {
                        timer.onFinish();

                    }
                }, 800);
            }
        });

        scoreTxt.setText("Score: " + String.valueOf(points));
    }

    private void setEnabledButtons() {
        answer1.setEnabled(true);
        answer2.setEnabled(true);
        answer3.setEnabled(true);
        answer4.setEnabled(true);
    }

    private void setDisabledButtons() {
        answer1.setEnabled(false);
        answer2.setEnabled(false);
        answer3.setEnabled(false);
        answer4.setEnabled(false);
    }

    private void returnBackground() {
        answer1.setBackgroundResource(R.drawable.round_buttons);
        answer2.setBackgroundResource(R.drawable.round_buttons);
        answer3.setBackgroundResource(R.drawable.round_buttons);
        answer4.setBackgroundResource(R.drawable.round_buttons);
        answer1.setTextColor(Color.parseColor("#0e6a60"));
        answer2.setTextColor(Color.parseColor("#0e6a60"));
        answer3.setTextColor(Color.parseColor("#0e6a60"));
        answer4.setTextColor(Color.parseColor("#0e6a60"));
    }

    private void initializeElements() {
        setContentView(R.layout.activity_practise_question);
        scoreTxt = (TextView) findViewById(R.id.score_question);
        countdownTimer = (TextView) findViewById(R.id.timer_practise);
        questionTxt = (TextView) findViewById(R.id.question_practise);
        questionImage = (ImageView) findViewById(R.id.image_question_practise);
        answer1 = (Button) findViewById(R.id.answer1_practise);
        answer2 = (Button) findViewById(R.id.answer2_practise);
        answer3 = (Button) findViewById(R.id.answer3_practise);
        answer4 = (Button) findViewById(R.id.answer4_practise);

        scoreTxt.setText("Score: " + String.valueOf(points));
    }

    private void showLastScreen() {
        setContentView(R.layout.practise_finish_screen);
        scoreInfoLastScreen = (TextView)findViewById(R.id.score_practise);
        confirmButton = (ImageButton) findViewById(R.id.confirm_practise);
        progressBarPractise = (ProgressBar) findViewById(R.id.progress_practise);
        progressBarPractise.setVisibility(View.GONE);

        scoreInfoLastScreen.setText(String.valueOf(points));

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmButton.setVisibility(View.GONE);
                progressBarPractise.setVisibility(View.VISIBLE);
                PractiseModel practiseModel = new PractiseModel();
                practiseModel.setCategory(requestModel.getCategory());
                practiseModel.setPoints(points);

                APIEndpoints service = ServiceGenerator.createServiceAuthorization(APIEndpoints.class, authToken);
                Call<Void> insertPractise = service.insertTrainingInfo(practiseModel);

                insertPractise.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(PractiseQuestionActivity.this, PractiseActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(PractiseQuestionActivity.this, "Your score is successfully stored in database.", Toast.LENGTH_LONG).show();
                        } else {
                            confirmButton.setVisibility(View.VISIBLE);
                            ServiceGenerator.handleError(response, PractiseQuestionActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Intent intent = new Intent(PractiseQuestionActivity.this, PractiseActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(PractiseQuestionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
