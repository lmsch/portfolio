package com.hfad.appgodsproject.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.pojos.Location;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddReceiptFragment extends Fragment implements View.OnClickListener {

    public static interface Listener {
        void finishedAddingReceipt(Receipt r);
        void startAddingItems();
    }
    private DAOContainer addReceiptContainer;
    private List<Location> storeList;

    private View v;

    private AutoCompleteTextView storeText;
    private EditText dateText;
    private EditText priceText;
    private EditText subPriceText;

    private DateFormat df;
    private Listener listener;
    private Receipt defaultReceipt;

    public void setDefaultReceipt(Receipt defaultReceipt) {
        this.defaultReceipt = defaultReceipt;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addReceiptContainer = new DAOContainer(getContext());
        addReceiptContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addReceiptContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_add_receipt, container, false);

        storeText = v.findViewById(R.id.storeText);
        dateText = v.findViewById(R.id.dateText);
        priceText = v.findViewById(R.id.priceText);
        subPriceText = v.findViewById(R.id.subPriceText);

        storeText.setThreshold(0);
        setStoreList();

        populateFormsWithDefaultReceipt();

        Button b = v.findViewById(R.id.finishButton);
        b.setOnClickListener(this);
        b = v.findViewById(R.id.addItemsButton);
        b.setOnClickListener(this);
        dateText.setOnClickListener(this);

        return v;
    }

    private void populateFormsWithDefaultReceipt() {
        if (defaultReceipt != null) {
            storeText.setText(defaultReceipt.getLocation().getDescription());
            dateText.setText(df.format(defaultReceipt.getPurchaseDate()));
            subPriceText.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(defaultReceipt.getSubTotal()));
            priceText.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(defaultReceipt.getTotalPrice()));
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        df = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT, AppGodsCurrencyFormatter.SUPPORTED_LOCALE_CANADA);
        this.listener = (Listener)context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addItemsButton:
                listener.startAddingItems();
                break;

            case R.id.finishButton:
                Receipt r = retrieveReceiptInfo();
                if(r != null) {
                    listener.finishedAddingReceipt(r);
                    clearEditText();
                    Toast.makeText(getActivity(), R.string.toast_receipt_added,
                            Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.dateText:
                DialogFragment dateFrag = new DatePickerFragment();
                dateFrag.show(getActivity().getSupportFragmentManager(), "datePicker");
                getActivity().getSupportFragmentManager().executePendingTransactions();
                break;
        }
    }

    private Receipt retrieveReceiptInfo() {
        double total;
        double subTotal;
        Date date;
        Location store = null;
        try {
            String totalString = priceText.getText().toString().replace(",", "");
            String subTotalString = subPriceText.getText().toString().replace(",", "");
            subTotal = Double.parseDouble(subTotalString);
            if(!totalString.isEmpty())
                total = Double.parseDouble(totalString);
            else
                total = subTotal;

            String dateString = dateText.getText().toString();
            if(!dateString.isEmpty())
                date = df.parse(dateText.getText().toString());
            else
                date = null;

            String locationString = storeText.getText().toString();
            if (locationString.isEmpty())
                throw new Exception();
            else {
                boolean locationExists = false;
                for (Location location : storeList) {
                    if (locationString.equals(location.getDescription())) {
                        store = location;
                        locationExists = true;
                    }
                }
                if (!locationExists) {
                    addReceiptContainer.getLocationDAO().addLocation(new Location(storeText.getText().toString()));
                    setStoreList();
                    store = addReceiptContainer.getLocationDAO().fetchLocationById(storeList.size());
                }
            }
        }
        catch(Exception ex) {
            Toast.makeText(getActivity(), R.string.validations_add_receipt,
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return null;
        }
        Receipt r = new Receipt();
        r.setPurchaseDate(date);
        r.setSubTotal(AppGodsCurrencyFormatter.convertToIntegerPrice(subTotal));
        r.setTotalPrice(AppGodsCurrencyFormatter.convertToIntegerPrice(total));
        r.setLocationId(store.getId());
        return r;
    }

    public void setSubPriceText(String p){ subPriceText.setText(p);
}

    public void clearEditText() {
        storeText.getText().clear();
        dateText.getText().clear();
        priceText.getText().clear();
        subPriceText.getText().clear();
    }
  
    private void setStoreList() {
        storeList = addReceiptContainer.getLocationDAO().fetchAllLocations();
        List<String> storeNames = new ArrayList<>();
        for (int i = 0; i < storeList.size(); i++) {
            storeNames.add(storeList.get(i).getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                v.getContext(),
                android.R.layout.simple_list_item_1,
                storeNames);

        storeText.setAdapter(adapter);
    }
}
