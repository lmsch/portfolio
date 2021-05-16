package com.hfad.appgodsproject.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.hfad.appgodsproject.activities.ReceiptDetailActivity;
import com.hfad.appgodsproject.adapters.ReceiptHistoryAdapter;
import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.database.api.ReceiptDAO;
import com.hfad.appgodsproject.pojos.Location;
import com.hfad.appgodsproject.pojos.Receipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ReceiptHistoryFragment extends Fragment {

    private DAOContainer receiptFragContainer;
    private ReceiptHistoryAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiptFragContainer = new DAOContainer(getContext());
        receiptFragContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiptFragContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_receipt_history, container, false);

        Spinner spinner = view.findViewById(R.id.time_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0: recreateReceiptList(ReceiptDAO.LAST_WEEK); break;
                    case 1: recreateReceiptList(ReceiptDAO.LAST_MONTH); break;
                    case 2: recreateReceiptList(ReceiptDAO.LAST_YEAR); break;
                    case 3: recreateReceiptList(null); break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        RecyclerView receiptRecycler = view.findViewById(R.id.receipt_recycler);
        adapter = new ReceiptHistoryAdapter();

        adapter.setListener(new ReceiptHistoryAdapter.Listener() {
            @Override
            public void onClick(long id) {
                Intent intent = new Intent(getActivity(), ReceiptDetailActivity.class);
                intent.putExtra(ReceiptDetailActivity.EXTRA_RECEIPT_ID, id);
                getActivity().startActivity(intent);
            }
        });

        spinner.setSelection(0);
        recreateReceiptList(ReceiptDAO.LAST_WEEK);
        receiptRecycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        receiptRecycler.setLayoutManager(layoutManager);

        return view;
    }

    private void recreateReceiptList(String range) {

        List<Receipt> allReceipts = receiptFragContainer.getReceiptDAO().fetchReceiptsInDateRange(range);
        List<Location> stores = receiptFragContainer.fetchAllLocationsForReceipts(allReceipts);

        List<Date> dates = new ArrayList<>();
        for (int i = 0; i < allReceipts.size(); i++) {
            dates.add(allReceipts.get(i).getPurchaseDate());
        }

        List<Integer> prices = new ArrayList<>();
        for (int i = 0; i < allReceipts.size(); i++) {
            prices.add(allReceipts.get(i).getTotalPrice());
        }

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < allReceipts.size(); i++) {
            ids.add(allReceipts.get(i).getId());
        }

        adapter.setDates(dates);
        adapter.setIds(ids);
        adapter.setPrices(prices);
        adapter.setStores(stores);
        adapter.notifyDataSetChanged();
    }
}
