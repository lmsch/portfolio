package com.hfad.appgodsproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;
import com.thomashaertel.widget.MultiSpinner;
import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.adapters.ItemEditAdapter;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;

import java.util.ArrayList;
import java.util.List;

public class AddItemsFragment extends Fragment implements View.OnClickListener {

    private List<Item> itemList;
    private List<Item> defaultItemList;
    private List<Category> categoryList;
    private List<Category> selectedCategoryList;

    private EditText itemDescriptionEdit;
    private EditText itemQuantityEdit;
    private EditText itemPriceEdit;
    private MultiSpinner categoriesSpinner;

    private  ItemEditAdapter itemListAdapter;

    private Listener listener;

    public static interface Listener {
        void finishedAddingItems(List<Item> itemList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (Listener)context;
        selectedCategoryList = new ArrayList<>();
        if(defaultItemList == null)
            itemList = new ArrayList<>();
        else
            itemList = new ArrayList<>(defaultItemList);
        if(categoryList == null)
            categoryList = new ArrayList<>();
    }

    public void setDefaultItemList(List<Item> defaultItemList) {
        this.defaultItemList = defaultItemList;
    }

    public void setCategoriesSpinnerList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_items, container, false);

        itemDescriptionEdit =  v.findViewById(R.id.item_description_edit);
        itemQuantityEdit = v.findViewById(R.id.item_quantity_edit);
        itemPriceEdit = v.findViewById(R.id.item_price_edit);

        categoriesSpinner = v.findViewById(R.id.categories_spinner);
        categoriesSpinner.setDefaultText(getString(R.string.categories_spinner_default));

        ArrayAdapter<String> spinnerCategoriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, createCategoryNamesList());
        categoriesSpinner.setAdapter(spinnerCategoriesAdapter, false, new MultiSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                selectedCategoryList.clear();
                for(int i = 0; i < selected.length; i++) {
                    if(selected[i])
                        selectedCategoryList.add(categoryList.get(i));
                }
            }
        });

        itemListAdapter = new ItemEditAdapter(getContext(), itemList, true);
        ListView editItemsListView = v.findViewById(R.id.edit_items_list);
        editItemsListView.setAdapter(itemListAdapter);

        Button addItemButton = v.findViewById(R.id.add_item_button);
        addItemButton.setOnClickListener(this);
        Button finishAddItemButton = v.findViewById(R.id.finish_add_item_button);
        finishAddItemButton.setOnClickListener(this);

        resetForms();

        return v;
    }

    private List<String> createCategoryNamesList() {
        List<String> names = new ArrayList<>();
        for(Category category : categoryList) {
            names.add(category.getDescription());
        }
        return names;
    }

    private void getItemFromEditTexts() {
        String description = itemDescriptionEdit.getText().toString();
        String quantity = itemQuantityEdit.getText().toString();
        String price = itemPriceEdit.getText().toString();

        Item item = new Item();
        if(!description.isEmpty() && !quantity.isEmpty() && !price.isEmpty()) {
            item.setDescription(description);
            item.setPrice(AppGodsCurrencyFormatter.convertToIntegerPrice(Double.parseDouble(price)));
            item.setQuantity(Integer.parseInt(quantity));
            item.setCategoryList(new ArrayList<>(selectedCategoryList));
            itemList.add(item);

            resetForms();
            itemListAdapter.notifyDataSetChanged();
            selectedCategoryList.clear();
        }
        else{
            Toast.makeText(getActivity(), R.string.validations_add_items,
                    Toast.LENGTH_LONG).show();
        }
    }

    private void resetForms() {
        itemDescriptionEdit.getText().clear();
        itemQuantityEdit.getText().clear();
        itemPriceEdit.getText().clear();
        categoriesSpinner.setSelected(new boolean[categoryList.size()]);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_item_button:
                getItemFromEditTexts();
                break;
            case R.id.finish_add_item_button:
                listener.finishedAddingItems(itemList);
                break;
        }
    }
}
