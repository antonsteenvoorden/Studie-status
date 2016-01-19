package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhatAreYourGrades#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhatAreYourGrades extends Fragment implements SliderFragment.Saveable {

    private List<Module> modules;
    private List<EditText> textFields;

    @Bind(R.id.scrollContainer) LinearLayout scrollContainer;

    public WhatAreYourGrades() {
        // Required empty public constructor
        textFields = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WhatAreYourGrades.
     */
    public static WhatAreYourGrades newInstance() {
        WhatAreYourGrades fragment = new WhatAreYourGrades();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modules = Module.getAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_welcome_what_are_your_grades, container, false);
        ButterKnife.bind(this, view);

        for (Module module: modules)
            scrollContainer.addView(createEditTextField(module));

        return view;
    }

    private TextInputLayout createEditTextField(Module module) {
        TextInputLayout textContainer = new TextInputLayout(getContext()); // EditText container
        AppCompatEditText editText = new AppCompatEditText(getContext()); // Input field

        editText.setHint(module.getName()); // Set name of input field
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        styleEditTextField(editText);
        textFields.add(editText);
        return getTextInputLayout(textContainer, editText);
    }

    private TextInputLayout getTextInputLayout(TextInputLayout textContainer,
                                               AppCompatEditText editText) {
        textContainer.addView(editText);
        return textContainer;
    }

    private void styleEditTextField(AppCompatEditText editText) {
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void save() {
        int i = 0;
        for (Module module: modules) {
            EditText text = textFields.get(i);
            String gradeText = text.getText().toString();
            if (gradeText.equals("") || gradeText.isEmpty() || gradeText == null) {
                module.setGrade(1);
                module.save();
            } else if (gradeText.length() < 4 && Double.parseDouble(gradeText) <= 10) {
                module.setGrade(Double.parseDouble(gradeText));
                module.setGradeSet(1);
                module.update();
                module.save();
                i++;
            }
            else {
            //TODO DO NOTHING BUT GIVE SOME ERROR MESSAGE
            }
        }
    }
}
