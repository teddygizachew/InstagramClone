package com.example.instagramclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
//    private Button btnSignup;
    private TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // TODO: navigate to the main activity if the user has signed in properly
        if(ParseUser.getCurrentUser() != null) {
            goMainActivity();
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
//        btnSignup = findViewById(R.id.btnSignup);
        tvSignup = findViewById(R.id.tvSignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // on some click or some loading we need to wait for...
                ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
                pb.setVisibility(ProgressBar.VISIBLE);

                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);

                // run a background job and once complete
            }
        });

//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "onClick SignUp button");
//                String username = etUsername.getText().toString();
//                String password = etPassword.getText().toString();
//                signupUser(username, password);
//            }
//        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                goSignupActivity(username, password);
            }
        });

        etUsername.addTextChangedListener(loginTextWatcher);
        etPassword.addTextChangedListener(loginTextWatcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            etUsername.setText(username);
            etPassword.setText(password);
        }
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String usernameInput = etUsername.getText().toString().trim();
            String passwordInput = etPassword.getText().toString().trim();

            btnLogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

//    private void signupUser(String username, String password) {
//        Log.i(TAG, "Attempting to SignUp" + username);
//        // Create the ParseUser
//        ParseUser user= new ParseUser();
//        // Set core properties
//        user.setUsername(username);
//        user.setPassword(password);
//        // Set custom properties
////        user.put("phone", "650-253-0000");
//        // Invoke signUpInBackground
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e != null) {
//                    // TODO: better error handling
//                    Log.e(TAG, "Issue with sign up", e);
//                    Toast.makeText(LoginActivity.this, "Issue with sign up", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                goMainActivity();
//                Toast.makeText(LoginActivity.this, "Sign up success!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "Attempting to login user" + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    // TODO: better error handling
                    Log.e(TAG, "Issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with login", Toast.LENGTH_SHORT).show();
                    return;
                }
                ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
                pb.setVisibility(ProgressBar.INVISIBLE);
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goSignupActivity(String username, String password) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra("edituser", username);
        intent.putExtra("editpass", password);
        this.startActivityForResult(intent, 100);
        finish();
    }
}