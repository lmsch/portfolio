package com.hfad.appgodsproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.pojos.Category;
import com.hfad.appgodsproject.pojos.Item;
import com.hfad.appgodsproject.util.AppGodsCurrencyFormatter;

import java.util.List;

public class ItemEditAdapter extends ArrayAdapter<Item> {

    private LayoutInflater inflater;
    private List<Item> itemList;
    private boolean showDeleteButton;

    public static final String QUANTITY_PRICE_LIST_FORMAT = "%d x %s";

    public ItemEditAdapter(Context context, List<Item> itemList, boolean showDeleteButton) {
        super(context, 0, itemList);
        inflater = LayoutInflater.from(context);
        this.itemList = itemList;
        this.showDeleteButton = showDeleteButton;

    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.item_list_entry, parent, false);
        Item item = itemList.get(position);
        TextView itemNameText = convertView.findViewById(R.id.item_list_description);
        TextView itemCategoriesText = convertView.findViewById(R.id.item_list_category);
        TextView itemQuantityPriceText = convertView.findViewById(R.id.item_list_quantity_price);
        Button removeButton = convertView.findViewById(R.id.remove_item_button);
        itemNameText.setText(item.getDescription());
        itemCategoriesText.setText(makeCategoryNamesString(item.getCategoryList()));
        int quantity = item.getQuantity();
        String currency = AppGodsCurrencyFormatter.getCanadaCurrencyFormat(item.getPrice());
        itemQuantityPriceText.setText(String.format(QUANTITY_PRICE_LIST_FORMAT, quantity, currency));
        if (showDeleteButton) {
            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemList.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else
            removeButton.setVisibility(View.GONE);
        return convertView;
    }

    private String makeCategoryNamesString(List<Category> categoryList) {
        if (categoryList.size() < 1)
            return "";
        StringBuilder sb = new StringBuilder(categoryList.get(0).getDescription());
        for (int i = 1; i < categoryList.size(); i++) {
            sb.append(", ");
            sb.append(categoryList.get(i).getDescription());
        }
        return sb.toString();
    }
}
