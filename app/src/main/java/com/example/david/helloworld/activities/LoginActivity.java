package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.models.user.UserLoginModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.usernameLoginId);
        passwordText = (EditText) findViewById(R.id.passwordLoginId);
        loginButton = (Button) findViewById(R.id.loginButtonId);
        signupLink = (TextView) findViewById(R.id.signUpLink);

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        if (validateForm()) {
            if (!isNetworkAvailable()) {
                Toast.makeText(getBaseContext(), "No internet access. \nPlease connect to internet to continue.", Toast.LENGTH_LONG).show();
                return;
            } else {
                loginButton.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Authenticating ...");
                progressDialog.show();

                UserLoginModel loginModel = new UserLoginModel();
                loginModel.setUsername(usernameText.getText().toString());
                loginModel.setPassword(passwordText.getText().toString());

                APIEndpoints service = ServiceGenerator.createService(APIEndpoints.class);
                Call<String> userLoginCall = service.login(loginModel);

                userLoginCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        loginButton.setEnabled(true);
                        progressDialog.dismiss();

                        if (response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                            String userToken = response.body();
                            SharedPreferences settings = getSharedPreferences("MY_PREF", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("HelloWorldToken", userToken).apply();
                        } else {
                            ServiceGenerator.handleError(response, LoginActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        loginButton.setEnabled(true);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        }
    }

    public boolean validateForm() {
        boolean valid;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty()) {
            usernameText.setError("Enter a valid username");
            valid = false;
        } else {
            usernameText.setError(null);
            valid = true;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
            valid = true;
        }

        return valid;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void showExitDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_exit);

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.yes_exit);
        ImageButton no = (ImageButton) dialog.findViewById(R.id.cancel_exit);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(startMain);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}
