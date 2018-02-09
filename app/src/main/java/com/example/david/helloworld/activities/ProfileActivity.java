package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.helloworld.R;
import com.example.david.helloworld.helpers.ImageHelper;
import com.example.david.helloworld.helpers.NetworkHelper;
import com.example.david.helloworld.helpers.TokenHelper;
import com.example.david.helloworld.models.user.TokenModel;
import com.example.david.helloworld.models.user.UserImage;
import com.example.david.helloworld.models.user.UserModel;
import com.example.david.helloworld.retrofit.APIEndpoints;
import com.example.david.helloworld.retrofit.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends Activity {

    ImageView userPhoto;
    Button uploadButton;
    EditText firstNameText;
    EditText lastNameText;
    EditText emailText;
    TextView usernameText;
    EditText passwordText;
    EditText confirmPasswordText;
    Button updateButton;

    // dialog
    ImageView ghostImage;
    ImageView ninjaImage;
    ImageView anonymousImage;
    ImageView detectiveImage;
    ImageView hackerImage;
    ImageView defaultImage;

    private SharedPreferences sharedPreferences = null;
    private String authToken;
    private TokenModel token = null;
    private Context context = this;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userPhoto = (ImageView) findViewById(R.id.userPhotoProfile);
        uploadButton = (Button) findViewById(R.id.uploadPhotoProfile);
        firstNameText = (EditText) findViewById(R.id.input_firstNameProfile);
        lastNameText = (EditText) findViewById(R.id.input_lastNameProfile);
        emailText = (EditText) findViewById(R.id.input_emailProfile);
        usernameText = (TextView) findViewById(R.id.username_text_profile);
        passwordText = (EditText) findViewById(R.id.input_passwordProfile);
        confirmPasswordText = (EditText) findViewById(R.id.input_passwordConfirmProfile);
        updateButton = (Button) findViewById(R.id.btn_update_profile);

        userModel = new UserModel();

        sharedPreferences = getSharedPreferences("MY_PREF", 0);
        authToken = sharedPreferences.getString("HelloWorldToken", "");

        if (TokenHelper.DecodeToken(authToken) != null) {
            token = TokenHelper.DecodeToken(authToken);
            usernameText.setText(token.getUsername());
            ImageHelper.setUserAvatar(userPhoto, token.getImage());
            userModel.setImage(token.getImage());

            firstNameText.setText(token.getFirstName());
            lastNameText.setText(token.getLastName());
            emailText.setText(token.getEmail());
        } else {
            NetworkHelper.navigateToLogin(context, null);
        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadButtonListener();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

    }

    public void onUploadButtonListener() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_user_image);

        ghostImage = (ImageView) dialog.findViewById(R.id.ghostLogo);
        ninjaImage = (ImageView) dialog.findViewById(R.id.ninjaLogo);
        anonymousImage = (ImageView) dialog.findViewById(R.id.anonymousLogo);
        detectiveImage = (ImageView) dialog.findViewById(R.id.detectiveLogo);
        hackerImage = (ImageView) dialog.findViewById(R.id.hackerLogo);
        defaultImage = (ImageView) dialog.findViewById(R.id.userDefaultLogo);

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

    public void updateUserProfile() {
        if (validateForm()) {
            NetworkHelper.isNetworkAvailable(context, sharedPreferences);
            updateButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Updating your account. Please wait...");
            progressDialog.show();

            userModel.setFirstName(firstNameText.getText().toString());
            userModel.setLastName(lastNameText.getText().toString());
            userModel.setUsername(usernameText.getText().toString());
            userModel.setEmail(emailText.getText().toString());
            userModel.setPassword(passwordText.getText().toString());

            APIEndpoints service = ServiceGenerator.createServiceAuthorization(APIEndpoints.class, authToken);
            Call<String> userProfileCall = service.updateUserProfile(userModel);

            userProfileCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    uploadButton.setEnabled(true);
                    progressDialog.dismiss();

                    if (response.isSuccessful()) {
                        Toast.makeText(ProfileActivity.this, "Account was updated successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("HelloWorldToken").apply();

                        String userToken = response.body();
                        editor.putString("HelloWorldToken", userToken).apply();
                    } else {
                        ServiceGenerator.handleError(response, ProfileActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    uploadButton.setEnabled(true);
                    progressDialog.dismiss();
                    Toast.makeText(ProfileActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public boolean validateForm() {
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (firstName.isEmpty() || firstName.length() < 3) {
            firstNameText.setError("First name must be at least 3 characters");
            return false;
        } else {
            firstNameText.setError(null);
        }

        if (lastName.isEmpty() || lastName.length() < 3) {
            lastNameText.setError("Last name must be at least 3 characters");
            return false;
        } else {
            lastNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Enter a valid email address");
            return false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("Password must be between 4 and 10 alphanumeric characters");
            return false;
        } else {
            passwordText.setError(null);
        }

        if (confirmPassword.isEmpty() || !confirmPassword.equals(password)) {
            confirmPasswordText.setError("Passwords mismatched");
            return false;
        } else {
            confirmPasswordText.setError(null);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
