package nl.antonsteenvoorden.ikpmd.fragment;

/**
 * Created by Anton on 29/12/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.adapter.VakkenCursorAdapter;
import nl.antonsteenvoorden.ikpmd.database.DatabaseHelper;
import nl.antonsteenvoorden.ikpmd.database.DatabaseInfo;

public class VakkenFragment extends ListFragment {
    @Bind(R.id.vakken_label)
    TextView textView;

    Cursor content;
    DatabaseHelper dbHelper;
    ListView listViewItems;
    VakkenCursorAdapter lcAdapter;
    View rootView;

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
        rootView = inflater.inflate(R.layout.fragment_vakken, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    void test() {
        ContentValues test = new ContentValues();
        test.put(DatabaseInfo.columnName, "Test");
        test.put(DatabaseInfo.columnECTS, 1);
        test.put(DatabaseInfo.columnGrade, 1);
        test.put(DatabaseInfo.columnPeriod, 1);

        dbHelper.insert(DatabaseInfo.tableName,test);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = DatabaseHelper.getInstance(getActivity());
        content = dbHelper.query(DatabaseInfo.tableName, new String[]{"*"});

        listViewItems = (ListView) rootView.findViewById(R.id.vakken_list);
        lcAdapter = new VakkenCursorAdapter(getActivity(), content, 0);
        listViewItems.setAdapter(lcAdapter);

        test();

    }
}

