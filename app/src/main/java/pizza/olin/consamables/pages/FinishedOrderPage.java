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

public class FinishedOrderPage extends Fragment {

    private FinishedOrderListener mListener;

    // This isn't used
    private SharedPrefsHandler prefsHandler;

    public FinishedOrderPage() {
        // Required empty public constructor
    }

    public static FinishedOrderPage newInstance() {
        FinishedOrderPage fragment = new FinishedOrderPage();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_finished_order_page, container, false);

        return myView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);
        if (isVisible) {
            mListener.confirmOrder();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FinishedOrderListener) {
            mListener = (FinishedOrderListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FinishedOrderListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface FinishedOrderListener {
        void confirmOrder();
    }
}
