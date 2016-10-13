package pizza.olin.consamables.pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Beverage beverage) {
        if (mListener != null) {
            mListener.setBeverage(beverage);
        }
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
