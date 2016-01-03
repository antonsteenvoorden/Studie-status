package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.orm.Module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeStep3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeStep3Fragment extends Fragment {

    private List<Module> modules;
    private List<EditText> textFields;

    @Bind(R.id.scrollContainer) LinearLayout scrollContainer;

    public WelcomeStep3Fragment() {
        // Required empty public constructor
        textFields = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeStep3Fragment.
     */
    public static WelcomeStep3Fragment newInstance() {
        WelcomeStep3Fragment fragment = new WelcomeStep3Fragment();
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_welcome_step3, container, false);
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

    public void saveData() {
        int i = 0;
        for (Module module: modules) {
            EditText text = textFields.get(i);
            module.setGrade(Integer.parseInt(text.getText().toString()));
            i++;
        }
    }

}
