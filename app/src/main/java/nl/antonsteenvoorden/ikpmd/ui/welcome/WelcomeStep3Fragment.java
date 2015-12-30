package nl.antonsteenvoorden.ikpmd.ui.welcome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.orm.Module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeStep3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeStep3Fragment extends Fragment {

    private List<Module> modules;

    public WelcomeStep3Fragment() {
        // Required empty public constructor
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
        return inflater.inflate(R.layout.fragment_welcome_step3, container, false);
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
