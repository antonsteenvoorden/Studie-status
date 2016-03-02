package nl.antonsteenvoorden.ikpmd.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.activity.VakkenActivity;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 03/01/2016.
 */
public class VakkenAdapter extends ArrayAdapter {
    Context context;
    Module module;
    public VakkenAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View view, final ViewGroup parent) {
        module = (Module) getItem(position);

        if(view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.vakken_list_item, parent, false);
        }
        TextView listName = (TextView) view.findViewById(R.id.vakken_list_item_name);
        TextView listDate = (TextView) view.findViewById(R.id.vakken_list_item_date);
        TextView listGrade = (TextView) view.findViewById(R.id.vakken_list_item_grade);
        listName.setText(module.getName());
        listDate.setText(module.getToetsDatum().toString());
        listGrade.setText(Double.toString(module.getGrade()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Vak selected", module.toString());
                Intent intent = new Intent(context, VakkenActivity.class);
                Log.d("ID", module.getId()+"");
                intent.putExtra("module_id",module.getId());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
