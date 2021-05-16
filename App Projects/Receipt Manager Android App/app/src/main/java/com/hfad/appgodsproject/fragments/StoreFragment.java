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
import com.hfad.appgodsproject.pojos.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment that displays the locations stored in the database
 * and allows the user to add more stores to the database.
 */
public class StoreFragment extends Fragment {

    private DAOContainer storeContainer;
    private final static String EMPTY = "";
    private List<String> storeNames;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeContainer = new DAOContainer(getContext());
        storeContainer.open();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        storeContainer.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_store, container, false);
        final EditText storeEdit = view.findViewById(R.id.store_input);

        //Clicking the add button will take whatever the user entered in the
        //editText and add it to the database, so long as it's not an empty
        //string or already in the database.
        Button addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String storeInput = storeEdit.getText().toString();
                if(!storeInput.isEmpty()) {
                    if (!storeNames.contains(storeInput)) {
                        Location store = new Location(storeInput);
                        storeContainer.getLocationDAO().addLocation(store);
                        storeEdit.setText(EMPTY);
                        setListView(view);
                    } else {
                        Toast.makeText(getContext(), R.string.toast_store_in_db,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), R.string.toast_store_empty,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        setListView(view);

        return view;
    }

    /**
     * Sets the fragment's list by getting data on all the locations
     * currently in the database. The method gets called every time a
     * location is added from this fragment.
     * @param view
     */
    private void setListView(View view) {
        ListView storeListView = view.findViewById(R.id.store_list);
        List<Location> storeList = storeContainer.getLocationDAO().fetchAllLocations();
        storeNames = new ArrayList<>();
        for (Location store : storeList) {
            storeNames.add(store.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                view.getContext(),
                android.R.layout.simple_list_item_1,
                storeNames);
        storeListView.setAdapter(adapter);
    }
}
