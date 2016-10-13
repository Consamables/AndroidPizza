package pizza.olin.consamables.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;

import pizza.olin.consamables.BeverageAdapter;
import pizza.olin.consamables.R;
import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.Beverage;

public class BeverageSelectPage extends Fragment {

    private BeverageTypeListener mListener;
    private SharedPrefsHandler prefsHandler;

    public BeverageSelectPage() {
        // Required empty public constructor
    }

    public static BeverageSelectPage newInstance() {
        BeverageSelectPage fragment = new BeverageSelectPage();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_beverage_select_page, container, false);

        prefsHandler = new SharedPrefsHandler(getActivity().getPreferences(Context.MODE_PRIVATE));
        final ArrayList<Beverage> allBeverages = prefsHandler.getBeverages();

        Spinner spinner = (Spinner) myView.findViewById(R.id.beverage_spinner);
        BeverageAdapter adapter = new BeverageAdapter(getContext(), allBeverages);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Beverage selectedBeverage = (Beverage) parent.getItemAtPosition(position);
                mListener.setBeverage(selectedBeverage);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return myView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BeverageTypeListener) {
            mListener = (BeverageTypeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BeverageTypeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface BeverageTypeListener {
        void setBeverage(Beverage beverage);
    }
}
