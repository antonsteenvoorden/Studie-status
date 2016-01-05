package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenDetailActivity extends AppCompatActivity {
    AppCompatEditText editText;
    Module module;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vakken_detail);
        TextView title = (TextView) findViewById(R.id.vakken_detail_title);
        module = (Module) getIntent().getSerializableExtra("module");
        title.setText(module.getName());


        editText = (AppCompatEditText) findViewById(R.id.vakken_detail_edit_text);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        Button button = (Button) findViewById(R.id.vakken_detail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                Snackbar snackbar = Snackbar
                        .make((RelativeLayout)findViewById(R.id.vakken_detail_layout), "Opgeslagen", Snackbar.LENGTH_LONG);
                snackbar.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }
        });
    }

    public void save() {
        String grade = editText.getText().toString().trim();
        if (grade.equals(""))
            grade = "0";
        module.setGrade(Integer.parseInt(grade));
    }
}

