package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
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

        module = Module.find(getIntent().getLongExtra("module_id",0));

        TextView title = (TextView) findViewById(R.id.vakken_detail_title);
        title.setText(module.getName());
        TextView period = (TextView) findViewById(R.id.vakken_detail_period);
        period.setText("Periode: " + module.getPeriod());
        TextView ects = (TextView) findViewById(R.id.vakken_detail_ects);
        ects.setText("ECTS: " +module.getEcts());
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
                }, 750);
            }
        });
    }

    public void save() {
//        String gradeText = editText.getText().toString();
//        if (gradeText.equals("") || gradeText.isEmpty() || gradeText == null) {
//            module.setGrade(1);
//            module.setGradeSet(1);
//            module.updateGrade();
//        } else if (gradeText.length() < 4 && Double.parseDouble(gradeText) >= 1 && Double.parseDouble(gradeText) <= 10) {
//            module.setGrade(Double.parseDouble(gradeText));
//            module.setGradeSet(1);
//            module.updateGrade();
//        } else {
//            Snackbar snackbar = Snackbar
//                    .make((RelativeLayout) findViewById(R.id.vakken_detail_layout),
//                            "Het ingevoerde cijfer is niet goed gekeurd!",
//                            Snackbar.LENGTH_LONG);
//
//            snackbar.show();
//        }
    }
}

