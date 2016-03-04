package nl.antonsteenvoorden.ikpmd.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

  EditText name;
  EditText omschrijving;
  EditText toetsdatum;
  EditText mutatiedatum;
  EditText toetstype;
  EditText ects;
  Spinner period;
  Spinner jaar;
  EditText definitief;
  EditText grade;
  Button button;

  Module module;

  Double newGrade;

  int currentPeriod;
  int currentYear;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vakken);
    getFields();
    setUpTextFieldProperties();
    ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.period_year,
        android.R.layout.simple_spinner_item);
    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    int id = (int) getIntent().getLongExtra("module_id", -1);

    if(id != -1) {
      module = Module.find(id);
      fillTextFields();
      if(module.getHandmatig() != 1) {
        disableTextFields();
      }
    } else {
      module = new Module();
      module.setHandmatig(1);
    }
    System.out.println("Vak opgehaald: " + getIntent().getLongExtra("module_id", 0)  + module.toString());


    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        save();
      }
    });
  }

  private void setUpTextFieldProperties() {
    ects.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
    grade.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    period.setOnItemSelectedListener(this);
    jaar.setOnItemSelectedListener(this);
  }

  private void getFields() {
    name = (EditText) findViewById(R.id.vakken_detail_title);
    omschrijving = (EditText) findViewById(R.id.vakken_detail_omschrijving);
    toetsdatum = (EditText) findViewById(R.id.vakken_detail_toetsdatum);
    mutatiedatum = (EditText) findViewById(R.id.vakken_detail_mutatiedatum);
    toetstype = (EditText) findViewById(R.id.vakken_detail_toetstype);
    ects = (EditText) findViewById(R.id.vakken_detail_ects);
    period = (Spinner) findViewById(R.id.vakken_detail_period);
    jaar = (Spinner) findViewById(R.id.vakken_detail_jaar);
    definitief = (EditText) findViewById(R.id.vakken_detail_definitief);
    grade = (EditText) findViewById(R.id.vakken_detail_grade);
    button = (Button) findViewById(R.id.vakken_detail_button);

  }

  private void disableTextFields() {
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
      if (module.getPeriod() != null) period.setSelection(module.getPeriod());  //period.setText(String.valueOf(module.getPeriod()));
      if (module.getJaar() != null) jaar.setSelection(module.getJaar()); //jaar.setText(String.valueOf(module.getJaar()));
      if (module.getDefinitief() != null)
        definitief.setText(String.valueOf(module.getDefinitief()));

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("VakkenActivity.fillTextFields EMPTY FIELD DETECTED");
    }
  }

  public void save() {
    if(validateGrade()) {
      validate();
      module.setName(name.getText().toString());
      module.setGrade(newGrade);
      if(ects.getText().toString().isEmpty()) {
        module.setEcts(0);
      }
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
    } else {
      displayError();
    }
  }

  public void validate() {
    if(name.getText().toString().isEmpty()) module.setName("Undefined");
    if(omschrijving.getText().toString().isEmpty()) module.setLongName("Undefined");
    if(toetstype.getText().toString().isEmpty()) module.setName("Undefined");
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
    } else {
      newGrade = 1.0;
    }

    return valid;
  }

  public void displayError() {
    Snackbar snackbar = Snackbar
        .make((CoordinatorLayout) findViewById(R.id.vakken_detail_layout),
            "Controleer de velden nog eens!",
            Snackbar.LENGTH_LONG);

    snackbar.show();
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  public void onItemSelected(AdapterView<?> parent, View view,
                             int pos, long id) {
    // An item was selected. You can retrieve the selected item using
    Object selected = parent.getItemAtPosition(pos);
    currentYear = Integer.valueOf(selected.toString());

  }

  public void onNothingSelected(AdapterView<?> parent) {
    // Another interface callback
  }


}
