package com.hfad.appgodsproject.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hfad.appgodsproject.R;
import com.hfad.appgodsproject.database.api.DAOContainer;
import com.hfad.appgodsproject.pojos.Category;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment that displays the categories stored in the database
 * and allows users to add new categories to the database.
 */
public class CategoryFragment extends Fragment {

    private DAOContainer categoryFragContainer;
    private final static String EMPTY = "";
    private List<String> categoryNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryFragContainer = new DAOContainer(getContext());
        categoryFragContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryFragContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category, container, false);
        final EditText categoryEdit = view.findViewById(R.id.category_input);

        //Clicking the add button will take whatever the user entered in the
        //editText and add it to the database, so long as it's not an empty
        //string or already in the database.
        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryInput = categoryEdit.getText().toString();
                if(!categoryInput.isEmpty()) {
                    if (!categoryNames.contains(categoryInput)) {
                        Category newCategory = new Category(categoryInput);
                        categoryFragContainer.getCategoryDAO().addCategory(newCategory);
                        categoryEdit.setText(EMPTY);
                        setListView(view);
                    } else {
                        Toast.makeText(getContext(), R.string.toast_category_in_db,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.toast_category_empty,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setListView(view);

        return view;
    }

    /**
     * Sets the fragment's list by getting data on all the categories
     * currently in the database. The method gets called every time a
     * category is added.
     * @param view
     */
    private void setListView(View view) {
        ListView categoryListView = view.findViewById(R.id.category_list);
        List<Category> categoryList = categoryFragContainer.getCategoryDAO().fetchAllCategories();
        categoryNames = new ArrayList<>();
        for (Category category : categoryList) {
            categoryNames.add(category.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                categoryNames);
        categoryListView.setAdapter(adapter);
    }
}
