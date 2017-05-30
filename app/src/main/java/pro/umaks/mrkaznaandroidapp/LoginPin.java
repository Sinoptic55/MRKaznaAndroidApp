package pro.umaks.mrkaznaandroidapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import pro.umaks.mrkaznaandroidapp.models.RequestModel;

public class LoginPin extends AppCompatActivity {


    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mLoginView;
    private EditText mPinView;
    private View mProgressView;
    private View mLoginFormView;
    private ArrayList<RequestModel> mRequestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);

        mLoginView = (AutoCompleteTextView)findViewById(R.id.login);
        mPinView = (EditText)findViewById(R.id.password);
        mPinView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        mLoginView.setText(getLogin());
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form_PIN);
        mProgressView = findViewById(R.id.login_progress_pin);
    }

    private void attemptLogin() {

        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mLoginView.setError(null);
        mPinView.setError(null);

        // Store values at the time of the login attempt.
        String email = mLoginView.getText().toString();
        String password = mPinView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPinView.setError(getString(R.string.error_invalid_password));
            focusView = mPinView;
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
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
            try {
                Boolean result = mAuthTask.get();
                Intent intent;
                intent = new Intent(this, RequestListActivity.class);
                startActivity(intent);

            }
            catch (Exception e)
            {

            }
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 4;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    protected String getLogin()
    {
        String result = "";
        SharedPreferences sPref = getPreferences(MODE_PRIVATE);
        result = sPref.getString("login", "");
        return result;
    }

    protected void saveLogin()
    {
        String login = mLoginView.getText().toString();



        if (!login.isEmpty())
        {
            SharedPreferences sPref = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString("login", login);
            ed.commit();
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mLogin;
        private final String mPin;

        UserLoginTask(String login, String pin) {
            mLogin = login;
            mPin = pin;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            boolean result = false;
            String loginResult = "";
            try
            {
                loginResult = TryLogin();
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            if (loginResult.contains("OK"))
            {
                result = true;
            }
            return  result;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                saveLogin();
//                MRRestService parseTask = new MRRestService();
//                parseTask.execute(mLogin, mPin);
                finish();
            } else {
                mPinView.setError(getString(R.string.error_incorrect_password));
                mPinView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private String getResponseText(InputStream inStream) {
            // very nice trick from
            // http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html
            return new Scanner(inStream).useDelimiter("\\A").next();
        }

        private String TryLogin() {
            HttpURLConnection urlConnection = null;

            String result = "";
            try {
                URL svcUrl = new URL(Constants.SVC_PATH + "reg/" + mLogin + "/" + mPin);
                urlConnection = (HttpURLConnection) svcUrl.openConnection();
                urlConnection.setDoOutput(false);
                urlConnection.setDoInput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                //urlConnection.setConnectTimeout(100000);

                //int statusCode = urlConnection.getResponseCode();
                //if (statusCode != HttpURLConnection.HTTP_OK)
                //{
                //Обработки ошибки соединения
                //  return null;
                //}
                InputStream inStream = new BufferedInputStream(urlConnection.getInputStream());
                result = getResponseText(inStream);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
            return result;
        }
    }

}
