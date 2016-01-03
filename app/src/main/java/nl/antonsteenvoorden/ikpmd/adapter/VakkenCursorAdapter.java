package nl.antonsteenvoorden.ikpmd.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.database.DatabaseInfo;

/**
 * Created by Anton on 03/01/2016.
 */
public class VakkenCursorAdapter extends CursorAdapter {

    public VakkenCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.vakken_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView listName = (TextView) view.findViewById(R.id.vakken_list_item_name);
        TextView listECTS = (TextView) view.findViewById(R.id.vakken_list_item_ects);
        TextView listGrade = (TextView) view.findViewById(R.id.vakken_list_item_grade);

        listName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseInfo.columnName)));
        listECTS.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseInfo.columnECTS)));
        listGrade.setText(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseInfo.columnGrade)));
    }
}
