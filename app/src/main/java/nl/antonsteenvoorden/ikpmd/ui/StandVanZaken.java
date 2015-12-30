package nl.antonsteenvoorden.ikpmd.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StandVanZaken#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StandVanZaken extends Fragment {

    public StandVanZaken() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StandVanZaken.
     */
    // TODO: Rename and change types and number of parameters
    public static StandVanZaken newInstance() {
        StandVanZaken fragment = new StandVanZaken();
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
        View rootView = inflater.inflate(R.layout.fragment_stand_van_zaken, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
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
