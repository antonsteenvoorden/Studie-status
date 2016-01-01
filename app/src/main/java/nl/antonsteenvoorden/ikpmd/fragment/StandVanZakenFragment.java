package nl.antonsteenvoorden.ikpmd.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;


public class StandVanZakenFragment extends Fragment {
    @Bind(R.id.stand_van_zaken_label)
    TextView textView;
    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;


    public StandVanZakenFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static StandVanZakenFragment newInstance() {
        StandVanZakenFragment fragment = new StandVanZakenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stand_van_zaken, container, false);
        ButterKnife.bind(this, rootView);

        mChart = (PieChart) rootView.findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(85);
        mChart.setCenterTextColor(Color.rgb(0,188,186));
        mChart.setCenterText("0/0 \n Studiepunten behaald");
        mChart.setCenterTextSize(20);
        mChart.getLegend().setEnabled(false);

        mChart.animateY(1500);
        setData(5);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mChart.spin(1500, 0, 360f, Easing.EasingOption.EaseInOutCirc);
    }

    private void setData(int aantal) {
        String label = (String) getString(R.string.stand_van_zaken_data);
        mChart.setCenterText(aantal + " / 60 \n"+ label );
        currentEcts = aantal;

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        yValues.add(new Entry(aantal, 0));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(60-currentEcts, 1));
        xValues.add("Resterende ECTS");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0 ,188,186)); // blue
        colors.add(Color.rgb(35 ,10,78)); // deep purple

        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);

        PieData data = new PieData(xValues, dataSet);
        data.setDrawValues(false);
        data.setValueTextSize(0.0f);
        mChart.setData(data); // bind dataset aan chart.

        mChart.invalidate();  // Aanroepen van een redraw
        Log.d("aantal =", ""+currentEcts);
    }

}
