package nl.antonsteenvoorden.ikpmd.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;

import java.util.ArrayList;

import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 19/01/2016.
 */
public class BarChartFragment extends Fragment {
  private CombinedChart barChart;
  View rootView;
  Context context;


  ArrayList<BarEntry> barTotaalEntries;
  ArrayList<BarEntry> barObtainedEntries;
  BarDataSet lineDataSet;
  BarDataSet barDataSet;

  BarData barObtained;
  BarData barTotaal;

  public static BarChartFragment newInstance() {
    BarChartFragment fragment = new BarChartFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_bar_chart, container, false);
    super.onCreate(savedInstanceState);

    context = rootView.getContext();
    barTotaalEntries = new ArrayList<BarEntry>();
    barObtainedEntries = new ArrayList<BarEntry>();

    barChart = (CombinedChart) rootView.findViewById(R.id.chart);
    initBarChart();
    getData();
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onHiddenChanged(boolean changed) {
    super.onResume();
    getData();
    barChart.invalidate();
  }

  @Override
  public void onResume() {
    super.onResume();
    getData();
  }

  private void initBarChart() {
    barChart.setClickable(false);
    barChart.getLegend().setEnabled(false);
    barChart.getXAxis().setEnabled(false);
    barChart.getAxisLeft().setDrawAxisLine(false);
    barChart.getAxisLeft().setDrawGridLines(false);
    barChart.getAxisLeft().setDrawLabels(true);
    barChart.getAxisLeft().setTextColor(Color.WHITE);
    barChart.getAxisRight().setDrawAxisLine(false);
    barChart.getAxisRight().setDrawGridLines(false);
    barChart.getAxisRight().setDrawLabels(false);
    barChart.setDrawHighlightArrow(false);
    barChart.setDrawBorders(false);
    barChart.setDrawValueAboveBar(true);
    barChart.setDescriptionColor(Color.WHITE);
    barChart.setDrawGridBackground(false);
    barChart.setDescription("");

  }

  private void getData() {
    barTotaalEntries.clear();
    barObtainedEntries.clear();
    for (int i = 0; i < 4; i++) {
      int ectsTmp = 0;
      int ectsReceivedTmp = 0;
      for (Module module : Module.getPeriod(i + 1)) {
        ectsTmp += module.getEcts();
        if (module.getGrade() >= 5.5) {
          ectsReceivedTmp += module.getEcts();
        }
      }
      barTotaalEntries.add(new BarEntry(ectsTmp, i));
      barObtainedEntries.add(new BarEntry(ectsReceivedTmp, i));
      if (ectsReceivedTmp == ectsTmp) {

      }
    }
    setData();
  }

  private void setData() {

    // KUNNEN HALEN
    barTotaal = new BarData();
    lineDataSet = new BarDataSet(barTotaalEntries, "Bar DataSet");
    lineDataSet.setColor(Color.rgb(255, 255, 255));
    lineDataSet.setValueTextColor(Color.WHITE);
    lineDataSet.setValueTextSize(15f);
    barTotaal.addDataSet(lineDataSet);


    // BAR DATA GEHAALD
    barObtained = new BarData();
    barDataSet = new BarDataSet(barObtainedEntries, "Bar DataSet");
    barDataSet.setColor(Color.rgb(0, 188, 186));
    barDataSet.setValueTextColor(Color.WHITE);
    barDataSet.setValueTextSize(15f);
    barObtained.addDataSet(barDataSet);

    // ADD data to the chart
    String[] xValues = {"1", "2", "3", "4"};
    CombinedData data = new CombinedData(xValues);
    data.setData(barTotaal);
    data.setData(barObtained);

    barChart.setData(data);
    barChart.invalidate();
  }
}
