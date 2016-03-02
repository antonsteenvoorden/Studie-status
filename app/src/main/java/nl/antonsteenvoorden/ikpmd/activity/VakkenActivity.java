package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenActivity extends AppCompatActivity {

  EditText name;
  EditText omschrijving;
  EditText toetsdatum;
  EditText mutatiedatum;
  EditText toetstype;
  EditText ects;
  EditText period;
  EditText jaar;
  EditText definitief;
  EditText grade;
  Button button;

  Module module;

  Double newGrade;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vakken);
    module = Module.find(getIntent().getLongExtra("module_id", 0));
    System.out.println("Vak opgehaald: " + getIntent().getLongExtra("module_id", 0)  + module.toString());
    getTextFields();
    fillTextFields();
    setTextFieldProperties();

//    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//    fab.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//            .setAction("Action", null).show();
//      }
//    });

    ects.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
    period.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
    grade.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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

  private void getTextFields() {
    name = (EditText) findViewById(R.id.vakken_detail_title);
    omschrijving = (EditText) findViewById(R.id.vakken_detail_omschrijving);
    toetsdatum = (EditText) findViewById(R.id.vakken_detail_toetsdatum);
    mutatiedatum = (EditText) findViewById(R.id.vakken_detail_mutatiedatum);
    toetstype = (EditText) findViewById(R.id.vakken_detail_toetstype);
    ects = (EditText) findViewById(R.id.vakken_detail_ects);
    period = (EditText) findViewById(R.id.vakken_detail_period);
    jaar = (EditText) findViewById(R.id.vakken_detail_jaar);
    definitief = (EditText) findViewById(R.id.vakken_detail_definitief);
    grade = (EditText) findViewById(R.id.vakken_detail_grade);
    button = (Button) findViewById(R.id.vakken_detail_button);

  }

  private void setTextFieldProperties() {
    name.setEnabled(false);
    omschrijving.setEnabled(false);
    toetsdatum.setEnabled(false);
    mutatiedatum.setEnabled(false);
    toetstype.setEnabled(false);
    grade.setEnabled(false);
    definitief.setEnabled(false);
  }

  private void fillTextFields() {
    try {

      if (!module.getName().isEmpty()) name.setText(module.getName());
      if (!module.getLongName().isEmpty()) omschrijving.setText(module.getLongName());
      if (module.getToetsDatum() != null) toetsdatum.setText(module.getToetsDatum().toString());
      if (module.getMutatieDatum() != null)
        mutatiedatum.setText(module.getMutatieDatum().toString());
      if (!module.getToetsType().isEmpty()) toetstype.setText(module.getToetsType());
      if (module.getGrade() != null) grade.setText(String.valueOf(module.getGrade()));
      if (module.getEcts() != null) ects.setText(String.valueOf(module.getEcts()));
      if (module.getPeriod() != null) period.setText(String.valueOf(module.getPeriod()));
      if (module.getJaar() != null) jaar.setText(String.valueOf(module.getJaar()));
      if (module.getDefinitief() != null)
        definitief.setText(String.valueOf(module.getDefinitief()));

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("VakkenActivity.fillTextFields EMPTY FIELD DETECTED");
    }
  }

  public void save() {
    module.setGrade(newGrade);
    module.save();
    Snackbar snackbar = Snackbar
        .make((CoordinatorLayout) findViewById(R.id.vakken_detail_layout), "Opgeslagen", Snackbar.LENGTH_LONG);
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
    String gradeText = grade.getText().toString();
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
      newGrade = tmpGrade;
    }
    return valid;
  }

  public void displayError() {
    Snackbar snackbar = Snackbar
        .make((CoordinatorLayout) findViewById(R.id.vakken_detail_layout),
            "Het ingevoerde cijfer is niet goed gekeurd!",
            Snackbar.LENGTH_LONG);

    snackbar.show();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }
}
