package nl.antonsteenvoorden.ikpmd.fragment;

/**
 * Created by Anton on 29/12/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.adapter.VakkenCursorAdapter;
import nl.antonsteenvoorden.ikpmd.database.DatabaseHelper;
import nl.antonsteenvoorden.ikpmd.database.DatabaseInfo;

public class VakkenFragment extends Fragment {
    @Bind(R.id.vakken_label)
    TextView textView;

    Cursor content;
    DatabaseHelper dbHelper;
    ListView listViewItems;
    VakkenCursorAdapter lcAdapter;

    Context context;

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

        context = rootView.getContext();
        listViewItems = (ListView) rootView.findViewById(R.id.vakken_list);

        return rootView;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = DatabaseHelper.getInstance(context);

        content = dbHelper.query(DatabaseInfo.tableName, new String[]{"*"});

        lcAdapter = new VakkenCursorAdapter(context, content, 0);

        listViewItems.setAdapter(lcAdapter);

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ITEM CLICKED", String.valueOf(parent.getItemAtPosition(position)));

            }
        });


    }
}

