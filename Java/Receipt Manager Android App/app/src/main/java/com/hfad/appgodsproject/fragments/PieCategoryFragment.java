package com.hfad.appgodsproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that displays a pie chart containing data on what
 * categories the user has spent their money on.
 */
public class PieCategoryFragment extends Fragment {

    private DAOContainer pieCategoryContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pieCategoryContainer = new DAOContainer(getContext());
        pieCategoryContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pieCategoryContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_category, container, false);

        List<Category> categoryList = pieCategoryContainer.getCategoryDAO().fetchAllCategories();
        List<Item> itemList = pieCategoryContainer.getAllItems();

        //Loops through all the categories, adding the value of all items that
        //contain that category to the category's value. At the end of the loop,
        //the category description and value is added to the list of data for the
        //pie chart.
        List<DataEntry> dataList = new ArrayList<>();
        for (Category category : categoryList) {
            int categoryValue = 0;
            for (Item item : itemList) {
                List<Category> itemCategories = item.getCategoryList();
                for (Category itemCategory : itemCategories) {
                    if (itemCategory.getDescription().equals(category.getDescription())) {
                        categoryValue += (item.getPrice() * item.getQuantity());
                    }
                }
            }
            if (categoryValue > 0) {
                dataList.add(new ValueDataEntry(category.getDescription(),
                        AppGodsCurrencyFormatter.convertToDoublePrice(categoryValue)));
            }
        }

        Pie categoryPie = AnyChart.pie();
        categoryPie.data(dataList);

        //Pie chart formatting
        categoryPie.tooltip().format("Money Spent: ${%value}");
        categoryPie.title(getString(R.string.pie_category_title));
        categoryPie.labels("outside");

        categoryPie.legend().title().enabled(true);
        categoryPie.legend().title().text(getString(R.string.nav_categories));
        categoryPie.legend().title().margin("10dp");
        categoryPie.legend().position("center").itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE).align(Align.CENTER);
        categoryPie.legend().padding("10dp", "10dp", "10dp", "10dp");

        AnyChartView chartView = view.findViewById(R.id.pie_category_view);
        chartView.setChart(categoryPie);

        return view;
    }
}
