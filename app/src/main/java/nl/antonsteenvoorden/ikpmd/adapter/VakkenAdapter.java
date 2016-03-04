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
  TextView listName;
  TextView listDate;
  TextView listGrade;

  public VakkenAdapter(Context context, int resource, List objects) {
    super(context, resource, objects);
    this.context = context;
  }


  @Override
  public View getView(int position, View view, final ViewGroup parent) {
    module = (Module) getItem(position);

    if (view == null) {
      view = LayoutInflater.from(getContext()).inflate(R.layout.vakken_list_item, parent, false);
    }
    listName = (TextView) view.findViewById(R.id.vakken_list_item_name);
    listDate = (TextView) view.findViewById(R.id.vakken_list_item_date);
    listGrade = (TextView) view.findViewById(R.id.vakken_list_item_grade);
    listName.setText(module.getName());
    listDate.setText(module.getToetsDatum().toString());
    listGrade.setText(Double.toString(module.getGrade()));

//    view.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Log.d("Vak selected", module.toString());
//        Intent intent = new Intent(context, VakkenActivity.class);
//        Log.d("ID", module.getId() + "");
//        intent.putExtra("module_id", module.getId());
//        context.startActivity(intent);
//      }
//    });
    return view;
  }

  @Override
  public int getCount() {
    return super.getCount();
  }

  @Override
  public Object getItem(int position) {
    return super.getItem(position);
  }

  @Override
  public long getItemId(int position) {
    return super.getItemId(position);
  }
}
