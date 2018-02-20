package com.example.david.helloworld.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.david.helloworld.R;
import com.example.david.helloworld.helpers.NetworkHelper;
import com.example.david.helloworld.helpers.TokenHelper;
import com.example.david.helloworld.models.user.TokenModel;

import io.jsonwebtoken.Jwts;

public class MainActivity extends Activity {

    TextView username;
    ImageView profileImage;
    ImageView playButton;
    ImageView practiseButton;
    ImageView statisticButton;
    ImageView settingsButton;
    ImageView shutdownButton;
    ImageView logoutButton;
    private SharedPreferences sharedPreferences = null;
    private String authToken;
    private TokenModel token = null;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.userName);
        profileImage = (ImageView) findViewById(R.id.userPhotoMainMenu);
        playButton = (ImageView) findViewById(R.id.main_menu_play);
        practiseButton = (ImageView) findViewById(R.id.main_menu_practise);
        statisticButton = (ImageView) findViewById(R.id.main_menu_statistic);
        settingsButton = (ImageView) findViewById(R.id.main_menu_about);
        shutdownButton = (ImageView) findViewById(R.id.shutdown);
        logoutButton = (ImageView) findViewById(R.id.logout);

        sharedPreferences = getSharedPreferences("MY_PREF", 0);
        authToken = sharedPreferences.getString("HelloWorldToken", "");

        if (TokenHelper.DecodeToken(authToken) != null) {
            token = TokenHelper.DecodeToken(authToken);
            username.setText("Hello, " + token.getUsername());

            switch (token.getImage()) {
                case Anonymous:
                    profileImage.setImageResource(R.drawable.anonymous_user_logo);
                    break;
                case DefaultImage:
                    profileImage.setImageResource(R.drawable.user_default_image);
                    break;
                case Detective:
                    profileImage.setImageResource(R.drawable.detective_user_logo);
                    break;
                case Ghost:
                    profileImage.setImageResource(R.drawable.ghost_user_logo);
                    break;
                case Hacker:
                    profileImage.setImageResource(R.drawable.hacker_user_logo);
                    break;
                case Ninja:
                    profileImage.setImageResource(R.drawable.ninja_user_logo);
                    break;
                default:
                    profileImage.setImageResource(R.drawable.user_default_image);
                    break;
            }

            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            practiseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PractiseActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            shutdownButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showExitDialog();
                }
            });

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLogoutDialog();
                }
            });

        } else {
            NetworkHelper.navigateToLogin(context, null);
        }
    }

    private void showLogoutDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_logout);

        ImageButton yes = (ImageButton) dialog.findViewById(R.id.yes_exit);
        ImageButton no = (ImageButton) dialog.findViewById(R.id.cancel_exit);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                NetworkHelper.navigateToLogin(context, sharedPreferences);
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

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("HelloWorldToken");
                editor.apply();

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
