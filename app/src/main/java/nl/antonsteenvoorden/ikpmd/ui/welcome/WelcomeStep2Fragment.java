package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.antonsteenvoorden.ikpmd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeStep2Fragment extends Fragment {

    public WelcomeStep2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WelcomeStep2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WelcomeStep2Fragment newInstance() {
        WelcomeStep2Fragment fragment = new WelcomeStep2Fragment();
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
        return inflater.inflate(R.layout.fragment_welcome_step2, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

}
