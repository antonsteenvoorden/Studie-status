package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeMessage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeMessage extends Fragment implements SliderFragment.Saveable {

    public WelcomeMessage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeMessage.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeMessage newInstance() {
        WelcomeMessage fragment = new WelcomeMessage();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // TODO: Rename method, updateGrade argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
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
        System.out.println(" NOTHING ");
    }
}
