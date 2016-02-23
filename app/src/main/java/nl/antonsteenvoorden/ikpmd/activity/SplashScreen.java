package nl.antonsteenvoorden.ikpmd.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.List;

import butterknife.Bind;
import nl.antonsteenvoorden.ikpmd.App;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;
import nl.antonsteenvoorden.ikpmd.service.ModuleObtainer;

public class SplashScreen extends AppCompatActivity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    public static final String PREFS_NAME = "LaunchPreferences";
    public String error;
    SharedPreferences settings;

    @Bind(R.id.splashScreenWelcome) TextView welkom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        settings = getSharedPreferences(PREFS_NAME, 0);
        welkom = (TextView) findViewById(R.id.splashScreenWelcome);

        if (settings.getBoolean("first_run", true)) {
            welkom.setText("");
        } else {
            welkom.setText("Welkom terug " + String.valueOf(settings.getString("name", ""))+ " !");
        }

        handleAfterSplash();
    }

    private void handleAfterSplash() {

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (!settings.getBoolean("first_run", true) && isNetworkConnected()) {

                    waitForModules();
                } else if (!settings.getBoolean("first_run", true) && !isNetworkConnected()) {
                    error = "Er is geen internetverbinding";
                    showError();
                } else {
                    Log.d("Comments", "Opening main activity");
                    Intent i = new Intent(SplashScreen.this, WelcomeActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }

        }, SPLASH_TIME_OUT);
    }

    private void waitForModules() {
        try {
        ActiveAndroid.beginTransaction();
        ((App) getApplication()).getModuleObtainer(
            settings.getString("username", "None"),
            settings.getString("password", "None")
        ).execute();
            //hier moet de call worden gemaakt om de nieuwe cijfers op te slaan ofzo
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void showError() {
        Snackbar.make(findViewById(R.id.splashScreenLayout), error,
                Snackbar.LENGTH_INDEFINITE).setAction("Opnieuw", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAfterSplash();
            }
        }).show();
        Log.e("Error", error);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
