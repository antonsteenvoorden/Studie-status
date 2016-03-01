package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenDetailActivity extends AppCompatActivity {
  @Bind(R.id.vakken_detail_edit_ects)
  EditText editEcts;
  @Bind(R.id.vakken_detail_edit_period)
  EditText editPeriod;
  @Bind(R.id.vakken_detail_edit_grade)
  EditText editGrade;
  @Bind(R.id.vakken_detail_title)
  TextView title;
  @Bind(R.id.vakken_detail_ects)
  TextView ects;
  @Bind(R.id.vakken_detail_period)
  TextView period;
  @Bind(R.id.vakken_detail_button)
  Button button;
  @Bind(R.id.vakken_detail_grade)
  TextView cijfer;

  double grade;
  Module module;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vakken_detail);

    module = Module.find(getIntent().getLongExtra("module_id", 0));
    title.setText(module.getName());
    period.setText("Periode: " + module.getPeriod());
    ects.setText("ECTS: " + module.getEcts());
    cijfer.setText("Cijfer: " + module.getGrade());

    editEcts.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
    editPeriod.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
    editGrade.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validateGrade() && validateEcts() && validatePeriod()) {
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
        .make((RelativeLayout) findViewById(R.id.vakken_detail_layout), "Opgeslagen", Snackbar.LENGTH_LONG);
    snackbar.show();
    final Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        finish();
      }
    }, 750);
  }

  public boolean validateEcts() {
    return true;
  }
  public boolean validatePeriod() {
    return true;
  }

  public boolean validateGrade() {
    boolean valid = false;
    String gradeText = editGrade.getText().toString();
    double tmpGrade;
    try {
      tmpGrade = Double.parseDouble(gradeText);
    } catch (Exception e) {
      return false;
    }

    if (gradeText.length() < 4) {
      valid = true;
    }
    if (gradeText.equals("") || gradeText.isEmpty() || gradeText == null) {
      valid = false;
    }
    if (tmpGrade < 1 || tmpGrade > 10) {
      valid = false;
    }
    if (valid) {
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

