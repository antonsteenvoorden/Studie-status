package nl.antonsteenvoorden.ikpmd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.interfaces.Callback;
import nl.antonsteenvoorden.ikpmd.service.ModuleObtainer;

public class WhoAreYou extends AppCompatActivity implements Callback{
  @Bind(R.id.usernameField) EditText usernameField;
  @Bind(R.id.passwordField) EditText passwordField;
  @Bind(R.id.whoAreYou) EditText nameField;
  @Bind(R.id.welcome_button) Button button;
  String errorMessage;
  ModuleObtainer moduleObtainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_who_are_you);
    ButterKnife.bind(this);
    errorMessage = getString(R.string.login_error);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        validate();
      }
    });
  }

  public void handleCallBack(Boolean loggedIn) {
    if (loggedIn) {
      save();
      Intent i = new Intent(WhoAreYou.this, SplashScreen.class);
      startActivity(i);
      finish();
    } else {
      displayError();
    }
  }
  public void validate() {
    System.out.println("WhoAreYou.validate + " + usernameField.getText().toString());
    moduleObtainer = new ModuleObtainer("S"+usernameField.getText().toString(),passwordField.getText().toString() );
    moduleObtainer.setCallBack(this);
    moduleObtainer.execute();
  }

  public void displayError() {
    Snackbar snackbar = Snackbar
        .make((RelativeLayout) findViewById(R.id.who_are_you_layout), errorMessage, Snackbar.LENGTH_LONG);
    snackbar.show();
  }

  public void save() {
    SharedPreferences.Editor settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0).edit();
    settings.putString("username", "s"+usernameField.getText().toString());
    settings.putString("password", passwordField.getText().toString());
    settings.putString("name", nameField.getText().toString()).commit();
    settings.putBoolean("first_run", false);
    settings.apply();
  }
}
