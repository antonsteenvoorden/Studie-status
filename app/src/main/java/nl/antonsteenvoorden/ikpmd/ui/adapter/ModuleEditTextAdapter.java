package nl.antonsteenvoorden.ikpmd.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.orm.Module;

/**
 * Created by Daan on 30-Dec-15.
 */
public class ModuleEditTextAdapter extends ArrayAdapter<Module> {

    @Bind(R.id.editText) EditText input;

    public ModuleEditTextAdapter(Context context, List<Module> modules) {
        super(context, 0, modules);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Module module = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext_module, parent, false);
        }
        ButterKnife.bind(this, convertView);

        input.setHint(module.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}
