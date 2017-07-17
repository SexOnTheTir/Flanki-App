package sexonthetir.flanki;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class RegisterActivity extends AppCompatActivity {

    private UserRegisterTask mRegisterTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button singUpButton;
    private View mProgressView;
    private View mLoginFormView;
    private TextView loginText;
    private boolean created = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initUI();
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        mLoginView = (EditText) findViewById(R.id.login);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        singUpButton = (Button) findViewById(R.id.sign_up_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        loginText = (TextView) findViewById(R.id.login_button);
    }

    private void attemptLogin() {
        if (mRegisterTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mLoginView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String login = mLoginView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_required));
            focusView = mLoginView;
            cancel = true;
        } else if (!isLoginValid(login)) {
            mLoginView.setError(getString(R.string.login_already_exists));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRegisterTask = new UserRegisterTask(login, email, password);
            mRegisterTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isLoginValid(String login) {
        //TODO: CHECK IF USER ALREADY EXISTS
        return true;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        //TODO REPAIR
    }

    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mLogin;

        UserRegisterTask(String login, String email, String password) {
            mEmail = email;
            mPassword = password;
            mLogin = login;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ParseUser newUser = new ParseUser();
            newUser.setUsername(mLogin);
            newUser.setPassword(mPassword);
            newUser.setEmail(mEmail);

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null)
                    {
                        created = true;
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        Toast.makeText(getApplicationContext(), getString(R.string.account_created), Toast.LENGTH_LONG).show();
                        finish();
                    }else
                    {
                        created = false;
                        mPasswordView.setError(e.getMessage());
                        mPasswordView.requestFocus();
                    }
                }
            });
            mRegisterTask = null;
            showProgress(false);
            return created;
        }
    /*
        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;
            showProgress(false);

            if (success) {
                // go to mainActivity with email
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            } else {
                mPasswordView.setError("Coś poszło nie tak!");
                mPasswordView.requestFocus();
            }
        }
        */
        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            showProgress(false);
        }
    }
}