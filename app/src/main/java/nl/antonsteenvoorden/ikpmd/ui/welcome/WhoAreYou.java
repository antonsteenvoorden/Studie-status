package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.activity.SplashScreen;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WhoAreYou#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WhoAreYou extends Fragment implements SliderFragment.Saveable {

    @Bind(R.id.whoAreYou) EditText input;

    public WhoAreYou() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WhoAreYou.
     */
    // TODO: Rename and change types and number of parameters
    public static WhoAreYou newInstance() {
        WhoAreYou fragment = new WhoAreYou();
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
        View view = inflater.inflate(R.layout.fragment_welcome_who_are_you, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void save() {
        SharedPreferences settings = getActivity().getSharedPreferences(SplashScreen.PREFS_NAME, 0);
        settings.edit().putString("name", input.getText().toString()).commit();
    }
}
