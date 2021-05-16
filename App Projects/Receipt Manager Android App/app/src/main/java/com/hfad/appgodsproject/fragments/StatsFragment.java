package com.hfad.appgodsproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.util.List;

/**
 * A fragment that displays general spending statistics
 * to the user.
 */
public class StatsFragment extends Fragment {

    private DAOContainer statsContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statsContainer = new DAOContainer(getContext());
        statsContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        statsContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        List<Receipt> receiptList = statsContainer.getReceiptDAO().fetchAllReceipts();
        List<Item> itemList = statsContainer.getItemDAO().fetchAllItems();

        //Get the total amount of money the user has spent
        //and find the most expensive receipt

        if(!receiptList.isEmpty()) {
            int totalSpent = 0;
            Receipt topReceipt = receiptList.get(0);
            for (Receipt receipt : receiptList) {
                totalSpent += receipt.getTotalPrice();
                if (receipt.getTotalPrice() > topReceipt.getTotalPrice()) {
                    topReceipt = receipt;
                }
            }

            //Update the textviews
            TextView totalView = view.findViewById(R.id.total_view);
            totalView.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(totalSpent));

            TextView receiptsStored = view.findViewById(R.id.receipt_view);
            receiptsStored.setText("" + receiptList.size());

            TextView expensiveReceipt = view.findViewById(R.id.receipt_total_view);
            expensiveReceipt.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(topReceipt.getTotalPrice()));
        }

        if(!itemList.isEmpty()) {
            //Find the most expensive item
            Item topItem = itemList.get(0);
            for (int i = 1; i < itemList.size(); i++) {
                if (itemList.get(i).getPrice() > topItem.getPrice()) {
                    topItem = itemList.get(i);
                }
            }
            TextView itemsPurchased = view.findViewById(R.id.item_view);
            itemsPurchased.setText("" + itemList.size());

            TextView expensiveItem = view.findViewById(R.id.item_total_view);
            expensiveItem.setText(topItem.getDescription() + " - " +
                    AppGodsCurrencyFormatter.getCanadaCurrencyFormat(topItem.getPrice()));
        }

        return view;
    }
}
