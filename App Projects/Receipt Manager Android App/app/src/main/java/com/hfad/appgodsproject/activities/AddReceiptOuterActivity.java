package com.hfad.appgodsproject.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.fragments.AddItemsFragment;
import com.hfad.appgodsproject.fragments.AddReceiptFragment;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.util.List;

public class AddReceiptOuterActivity extends AppCompatActivity implements AddReceiptFragment.Listener, AddItemsFragment.Listener {

    private DAOContainer daoContainer;
    private List<Item> newItemList;

    private AddItemsFragment addItemsFragment;
    private AddReceiptFragment addReceiptFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daoContainer = new DAOContainer(this);
        daoContainer.open();
        addReceiptFragment = new AddReceiptFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_frame, addReceiptFragment);
        ft.commit();
    }

    @Override
    public void startAddingItems() {
        addItemsFragment = new AddItemsFragment();
        List<Category> categoryList = daoContainer.getCategoryDAO().fetchAllCategories();
        addItemsFragment.setCategoriesSpinnerList(categoryList);
        addItemsFragment.setDefaultItemList(newItemList);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(addReceiptFragment);
        ft.add(R.id.content_frame, addItemsFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void finishedAddingItems(List<Item> newItemList) {
        this.newItemList = newItemList;
        int price = 0;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.remove(addItemsFragment);
        ft.show(addReceiptFragment);
        for (int i = 0; i < newItemList.size(); i++) {
            Item item = newItemList.get(i);
            price += item.getPrice() * item.getQuantity();
        }
        addReceiptFragment.setSubPriceText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(price).substring(1));
        manager.popBackStack();
        ft.commit();
    }

    @Override
    public void finishedAddingReceipt(Receipt newReceipt) {
        newReceipt.setItemList(newItemList);
        daoContainer.addPopulatedReceipt(newReceipt);
        newItemList = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        daoContainer.close();
    }
}
