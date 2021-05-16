package com.hfad.appgodsproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.adapters.ItemEditAdapter;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.fragments.DatePickerFragment;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.pojos.Receipt;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECEIPT_ID = "receipt_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        long receiptID = (Long) getIntent().getExtras().get(EXTRA_RECEIPT_ID);
        DateFormat df = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT, AppGodsCurrencyFormatter.SUPPORTED_LOCALE_CANADA);

        DAOContainer detailContainer = new DAOContainer(this);
        detailContainer.open();
        Receipt receipt = detailContainer.getPopulatedReceipt(receiptID);
        detailContainer.close();

        TextView storeView = findViewById(R.id.store_view);
        TextView dateView = findViewById(R.id.date_view);
        ListView itemList = findViewById(R.id.item_list_view);
        TextView priceView = findViewById(R.id.price_view);
        TextView subTotalView = findViewById(R.id.sub_price_view);

        storeView.setText(receipt.getLocation().getDescription());
        Date date = receipt.getPurchaseDate();
        if (date != null)
            dateView.setText(df.format(receipt.getPurchaseDate()));
        subTotalView.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(receipt.getSubTotal()));
        priceView.setText(AppGodsCurrencyFormatter.getCanadaCurrencyFormat(receipt.getTotalPrice()));

        List<Item> receiptItemList = receipt.getItemList();
        ItemEditAdapter adapter = new ItemEditAdapter(this, receiptItemList, false);
        itemList.setAdapter(adapter);
    }
}

