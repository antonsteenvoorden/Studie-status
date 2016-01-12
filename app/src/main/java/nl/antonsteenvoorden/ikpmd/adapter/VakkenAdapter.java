package nl.antonsteenvoorden.ikpmd.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.activity.VakkenDetailActivity;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 03/01/2016.
 */
public class VakkenAdapter extends ArrayAdapter {
    Context context;
    public VakkenAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        final Module module = (Module) getItem(position);

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.vakken_list_item, parent, false);
        }
        TextView listName = (TextView) view.findViewById(R.id.vakken_list_item_name);
        TextView listECTS = (TextView) view.findViewById(R.id.vakken_list_item_ects);
        TextView listGrade = (TextView) view.findViewById(R.id.vakken_list_item_grade);
        listName.setText(module.getName());
        listECTS.setText(Integer.toString(module.getEcts()));
        listGrade.setText(Double.toString(module.getGrade()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Vak selected", module.toString());
                Intent intent = new Intent(context, VakkenDetailActivity.class);
                intent.putExtra("module_id",module.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
