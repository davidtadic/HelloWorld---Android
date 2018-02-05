package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.models.user.UserImage;
import com.example.david.helloworld.models.user.UserModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {

    ImageView userPhoto;
    Button uploadButton;
    Button useDefaultButton;
    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    EditText usernameText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;
    ProgressDialog progressDialog;
    private Context context = this;

    // dialog
    ImageView ghostImage;
    ImageView ninjaImage;
    ImageView anonymousImage;
    ImageView detectiveImage;
    ImageView hackerImage;
    ImageView defaultImage;

    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userPhoto = (ImageView) findViewById(R.id.userPhoto);
        uploadButton = (Button) findViewById(R.id.uploadPhoto);
        useDefaultButton = (Button) findViewById(R.id.btn_useDefault);
        firstNameText = (EditText) findViewById(R.id.input_firstName);
        lastNameText = (EditText) findViewById(R.id.input_lastName);
        emailText = (EditText) findViewById(R.id.input_email);
        usernameText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        signupButton = (Button) findViewById(R.id.btn_signup);
        loginLink = (TextView) findViewById(R.id.link_login);

        userModel = new UserModel();
        userPhoto.setImageResource(R.drawable.user_default_image);
        userModel.setImage(UserImage.DefaultImage);

        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Building your account. Please wait...");

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();

            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadButtonListener();
            }
        });
    }

    public void onUploadButtonListener() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_user_image);

        ghostImage = (ImageView)dialog.findViewById(R.id.ghostLogo);
        ninjaImage = (ImageView)dialog.findViewById(R.id.ninjaLogo);
        anonymousImage = (ImageView)dialog.findViewById(R.id.anonymousLogo);
        detectiveImage = (ImageView)dialog.findViewById(R.id.detectiveLogo);
        hackerImage = (ImageView)dialog.findViewById(R.id.hackerLogo);
        defaultImage = (ImageView)dialog.findViewById(R.id.userDefaultLogo);

        ghostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.ghost_user_logo);
                userModel.setImage(UserImage.Ghost);
                dialog.dismiss();
            }
        });

        ninjaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.ninja_user_logo);
                userModel.setImage(UserImage.Ninja);
                dialog.dismiss();
            }
        });

        anonymousImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.anonymous_user_logo);
                userModel.setImage(UserImage.Anonymous);
                dialog.dismiss();
            }
        });

        detectiveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.detective_user_logo);
                userModel.setImage(UserImage.Detective);
                dialog.dismiss();
            }
        });

        hackerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.hacker_user_logo);
                userModel.setImage(UserImage.Hacker);
                dialog.dismiss();
            }
        });

        defaultImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPhoto.setImageResource(R.drawable.user_default_image);
                userModel.setImage(UserImage.DefaultImage);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void onDefaultPhotoListener(View v) {
        userPhoto.setImageResource(R.drawable.user_default_image);
        userModel.setImage(UserImage.DefaultImage);
    }

    public void signup() {
        if (validateForm()) {
            if (!isNetworkAvailable()) {
                Toast.makeText(getBaseContext(), "No internet access.\nPlease connect to internet to continue.", Toast.LENGTH_LONG).show();
                return;
            } else {
                signupButton.setEnabled(false);
                progressDialog.show();

                userModel.setFirstName(firstNameText.getText().toString());
                userModel.setLastName(lastNameText.getText().toString());
                userModel.setUsername(usernameText.getText().toString());
                userModel.setEmail(emailText.getText().toString());
                userModel.setPassword(passwordText.getText().toString());

                APIEndpoints service = ServiceGenerator.createService(APIEndpoints.class);
                Call<UserModel> userRegisterCall = service.register(userModel);

                userRegisterCall.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        signupButton.setEnabled(true);
                        progressDialog.hide();

                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Account was created successfully\n Please login to continue", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ServiceGenerator.handleError(response, RegisterActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        signupButton.setEnabled(true);
                        progressDialog.hide();
                        Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public boolean validateForm() {
        boolean valid;

        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameText.setError("First name must be at least 3 characters");
            valid = false;
        } else {
            firstNameText.setError(null);
            valid = true;
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameText.setError("Last name must be at least 3 characters");
            valid = false;
        } else {
            lastNameText.setError(null);
            valid = true;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
            valid = true;
        }

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
}
