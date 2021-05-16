package com.hfad.appgodsproject.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.fragments.DatePickerFragment;
import com.hfad.appgodsproject.pojos.Location;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptHistoryAdapter extends RecyclerView.Adapter<ReceiptHistoryAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    private List<Location> stores;
    private List<Date> dates;
    private List<Integer> prices;
    private List<Long> ids;
    private DateFormat df;
    private Listener listener;

    public interface Listener {
        void onClick(long id);
    }

    public void setStores(List<Location> stores) {
        this.stores = stores;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public ReceiptHistoryAdapter(List<Location> stores, List<Date> dates, List<Integer> prices, List<Long> ids) {
        this.stores = stores;
        this.dates = dates;
        this.prices = prices;
        this.ids = ids;
        this.listener = null;
        df = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT, AppGodsCurrencyFormatter.SUPPORTED_LOCALE_CANADA);
    }

    public ReceiptHistoryAdapter() {
        df = new SimpleDateFormat(DatePickerFragment.DATE_FORMAT, AppGodsCurrencyFormatter.SUPPORTED_LOCALE_CANADA);
    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    @NonNull
    @Override
    public ReceiptHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cv = (CardView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.receipt_card, viewGroup, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final int i = viewHolder.getAdapterPosition();

        CardView cardView = viewHolder.cardView;
        TextView storeView = cardView.findViewById(R.id.store_view);
        TextView dateView = cardView.findViewById(R.id.date_view);
        TextView priceView = cardView.findViewById(R.id.price_view);

        Location store = stores.get(i);
        storeView.setText(store.getDescription());
        Date date = dates.get(i);
        if (date == null)
            dateView.setText(null);
        else
            dateView.setText(df.format(dates.get(i)));
        String currency = AppGodsCurrencyFormatter.getCanadaCurrencyFormat(prices.get(i));
        priceView.setText(currency);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(ids.get(i));
                }
            }
        });
    }
}
