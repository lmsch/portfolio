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
import com.anychart.charts.Cartesian;
import com.anychart.core.ui.table.Column;
import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.database.api.ReceiptDAO;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A fragment that displays a bar graph containing data
 * on what dates the user has spent their money on.
 */
public class BarFragment extends Fragment {

    private DAOContainer barContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        barContainer = new DAOContainer(getContext());
        barContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        barContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bar, container, false);

        List<Receipt> receiptList = barContainer.getReceiptDAO().fetchReceiptsInDateRange(ReceiptDAO.LAST_TWO_WEEKS);

        List<Date> dateList = new ArrayList<>();
        Map<Date, Integer> valuePerDate = new HashMap<>();
        for (Receipt receipt : receiptList) {
            if (!valuePerDate.containsKey(receipt.getPurchaseDate())) {
                valuePerDate.put(receipt.getPurchaseDate(), receipt.getTotalPrice());
                dateList.add(receipt.getPurchaseDate());
            } else {
                int newValue = valuePerDate.get(receipt.getPurchaseDate()) + receipt.getTotalPrice();
                valuePerDate.put(receipt.getPurchaseDate(), newValue);
            }
        }

        List<DataEntry> dataList = new ArrayList<>();

        Collections.sort(dateList);
        DateFormat df = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT, AppGodsCurrencyFormatter.SUPPORTED_LOCALE_CANADA);
        for (Date date : dateList) {
            dataList.add(new ValueDataEntry(df.format(date),
                    AppGodsCurrencyFormatter.convertToDoublePrice(valuePerDate.get(date))));
        }

        Cartesian cartesian = AnyChart.column();
        cartesian.column(dataList);

        //Bar graph formatting
        cartesian.tooltip().format("Money Spent: ${%value}");
        cartesian.title(getString(R.string.bar_title));

        AnyChartView chartView = view.findViewById(R.id.bar_view);
        chartView.setChart(cartesian);

        return view;
    }
}
