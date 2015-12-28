package nl.antonsteenvoorden.ikpmd.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nl.antonsteenvoorden.ikpmd.R;

public class WhoAreYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_are_you);
        Button save = (Button) findViewById(R.id.whoAreYouButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavePressed();
                Intent i = new Intent(WhoAreYouActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

        });
    }
    public void onSavePressed() {
        SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
        EditText input = (EditText)findViewById(R.id.whoAreYouInput);
        settings.edit().putString("name", String.valueOf(input.getText())).commit();
        settings.edit().putBoolean("first_run", false).commit();
    }
}
