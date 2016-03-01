package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenInvoerenActivity extends AppCompatActivity {
  @Bind(R.id.vakken_detail_edit_text) EditText editText;
  @Bind(R.id.vakken_detail_title) TextView title;
  @Bind(R.id.vakken_detail_ects) TextView ects;
  @Bind(R.id.vakken_detail_period) TextView period;
  @Bind(R.id.vakken_detail_button) Button button;

  double grade;
  Module module;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vakken_detail);

    module = new Module();

    title.setText(module.getName());
    period.setText("Periode: " + module.getPeriod());
    ects.setText("ECTS: " +module.getEcts());
    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(validate()) {
          save();
        } else {
          displayError();
        }
      }
    });
  }

  public void save() {
    module.setGrade(grade);
    module.update();
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

  public boolean validate() {
    boolean valid = false;
    String gradeText = editText.getText().toString();
    double tmpGrade;
    try {
      tmpGrade = Double.parseDouble(gradeText);
    } catch (Exception e){
      return false;
    }

    if (gradeText.length() < 4) {
      valid = true;
    }
    if (gradeText.equals("") || gradeText.isEmpty() || gradeText == null) {
      valid = false;
    }
    if(tmpGrade < 1 || tmpGrade > 10) {
      valid = false;
    }
    if(valid) {
      grade = tmpGrade;
    }
    return valid;
  }

  public void displayError() {
    Snackbar snackbar = Snackbar
        .make((RelativeLayout) findViewById(R.id.vakken_detail_layout),
            "Het ingevoerde cijfer is niet goed gekeurd!",
            Snackbar.LENGTH_LONG);

    snackbar.show();
  }
}

