package nl.antonsteenvoorden.ikpmd.fragments;

/**
 * Created by Anton on 29/12/2015.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.adapter.VakkenAdapter;
import nl.antonsteenvoorden.ikpmd.model.Module;

public class VakkenFragment extends Fragment {
//    @Bind(R.id.vakken_label)
    TextView textView;

    List<Module> content;
    ListView listViewItems;
    VakkenAdapter lcAdapter;

    Context context;

    public VakkenFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static VakkenFragment newInstance() {
        Log.d("VAKKENFRAG", "New instance created");
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

        context = rootView.getContext();
        listViewItems = (ListView) rootView.findViewById(R.id.vakken_list);
        content = Module.getAll();
        lcAdapter = new VakkenAdapter(context, R.layout.vakken_list_item, content);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        listViewItems.invalidateViews();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewItems.setAdapter(lcAdapter);

    }
}

