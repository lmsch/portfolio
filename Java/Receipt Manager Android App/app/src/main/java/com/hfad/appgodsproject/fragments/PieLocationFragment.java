package com.hfad.appgodsproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.pojos.Location;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that displays a pie chart containing data on how
 * much money the user has spent at each store in the database.
 */
public class PieLocationFragment extends Fragment {

    private DAOContainer pieLocationContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pieLocationContainer = new DAOContainer(getContext());
        pieLocationContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pieLocationContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_location, container, false);

        List<Receipt> receiptList = pieLocationContainer.getReceiptDAO().fetchAllReceipts();
        List<Location> receiptLocationList = pieLocationContainer.fetchAllLocationsForReceipts(receiptList);
        List<Location> locationList = pieLocationContainer.getLocationDAO().fetchAllLocations();

        //Loops through all the locations, adding the value of the receipts that
        //contain that location to the location's value. At the end of the loop,
        //the location description and value is added to the list of data for the
        //pie chart.
        List<DataEntry> dataList = new ArrayList<>();
        for (Location location : locationList) {
            int locationValue = 0;
            for (int i = 0; i < receiptList.size(); i++) {
                if (receiptLocationList.get(i).getDescription().equals(location.getDescription())) {
                    locationValue += receiptList.get(i).getTotalPrice();
                    receiptList.remove(i);
                    receiptLocationList.remove(i);
                }
            }
            if (locationValue > 0) {
                dataList.add(new ValueDataEntry(location.getDescription(),
                        AppGodsCurrencyFormatter.convertToDoublePrice(locationValue)));
            }
        }

        Pie locationPie = AnyChart.pie();
        locationPie.data(dataList);

        //Pie chart formatting
        locationPie.tooltip().format("Money Spent: ${%value}");
        locationPie.title(getString(R.string.pie_location_title));
        locationPie.labels("outside");

        locationPie.legend().title().enabled(true);
        locationPie.legend().title().text(getString(R.string.nav_stores));
        locationPie.legend().title().margin("10dp");
        locationPie.legend().position("center").itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE).align(Align.CENTER);
        locationPie.legend().padding("10dp", "10dp", "10dp", "10dp");

        AnyChartView chartView = view.findViewById(R.id.pie_location_view);
        chartView.setChart(locationPie);

        return view;
    }
}
