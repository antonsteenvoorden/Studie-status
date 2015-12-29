package nl.antonsteenvoorden.ikpmd.fragment;

/**
 * Created by Anton on 29/12/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class VakkenFragment extends Fragment {
    @Bind(R.id.vakken_label)
    TextView textView;

    public VakkenFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VakkenFragment newInstance() {
        VakkenFragment fragment = new VakkenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vakken, container, false);
        ButterKnife.bind(this, rootView);
        textView.setText("Vakken");
        return rootView;
    }
}

