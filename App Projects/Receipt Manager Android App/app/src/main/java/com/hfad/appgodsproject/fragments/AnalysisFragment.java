package com.hfad.appgodsproject.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.hfad.appgodsproject.R;


/**
 * A fragment that contains another fragment to display
 * spending data to the user. What data they're seeing can
 * be chosen by selecting different options on a spinner.
 */
public class AnalysisFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);
        final Spinner choiceSpinner = view.findViewById(R.id.analysis_spinner);

        //Choosing different options on this spinner will allow the user to view
        //the different fragments that contain different graphs.
        choiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment frag = null;
                switch (choiceSpinner.getSelectedItem().toString()) {
                    case "General Stats":
                        frag = new StatsFragment();
                        break;
                    case "Category":
                        frag = new PieCategoryFragment();
                        break;
                    case "Location":
                        frag = new PieLocationFragment();
                        break;
                    case "Date":
                        frag = new BarFragment();
                        break;
                }
                if(frag != null) {
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.chart_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Required method, but this should never be called.
            }
        });

        return view;
    }
}
