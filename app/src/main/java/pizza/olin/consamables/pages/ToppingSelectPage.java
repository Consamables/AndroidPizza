package pizza.olin.consamables.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import pizza.olin.consamables.R;
import pizza.olin.consamables.ToppingAdapter;
import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.PizzaOrderType;
import pizza.olin.consamables.types.Topping;

import static pizza.olin.consamables.types.PizzaOrderType.HALF;

/**
 * TODO: Add a class header comment!
 */

public class ToppingSelectPage extends Fragment {

    private ToppingSelectListener mListener;
    private SharedPrefsHandler prefsHandler;

    HashMap<PizzaOrderType, Integer> viewOptions;

    public static ToppingSelectPage newInstance() {
        return new ToppingSelectPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewOptions = new HashMap<>();
        viewOptions.put(PizzaOrderType.HALF, R.layout.fragment_half_topping_select_page);
        viewOptions.put(PizzaOrderType.WHOLE, R.layout.fragment_whole_topping_select_page);

        prefsHandler = new SharedPrefsHandler(getActivity().getPreferences(Context.MODE_PRIVATE));

        View myView = inflater.inflate(R.layout.fragment_topping_container_layout, container, false);

        View toppingView;

        if (mListener.getPizzaType() == HALF) {
            toppingView = inflater.inflate(R.layout.fragment_half_topping_select_page, (ViewGroup) myView, false);
        } else {
            toppingView = inflater.inflate(R.layout.fragment_whole_topping_select_page, (ViewGroup) myView, false);
        }

        configureView(toppingView);
         ((ViewGroup) myView).addView(toppingView);

        return myView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if (isVisible) {
            ViewGroup parent = (ViewGroup) getView();
            View currentView = parent.getChildAt(0);
            int optionId = viewOptions.get(mListener.getPizzaType());

            if (currentView.getId() != optionId) {
                parent.removeView(currentView);
                currentView = getActivity().getLayoutInflater().inflate(optionId, parent, false);
                configureView(currentView);
                parent.addView(currentView, 0);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ToppingSelectListener) {
            mListener = (ToppingSelectListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ToppingSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void configureView(View myView) {
        final ArrayList<Topping> allToppings = prefsHandler.getToppings();

        if (mListener.getPizzaType() == HALF) {
            int[] spinnerIds = {R.id.spinner1, R.id.spinner2, R.id.spinner3};

            ArrayList<Spinner> spinners = new ArrayList<>();
            ArrayList<ToppingAdapter> adapters = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                spinners.add((Spinner) myView.findViewById(spinnerIds[i]));
                adapters.add(new ToppingAdapter(this.getContext(), allToppings));
                spinners.get(i).setAdapter(adapters.get(i));
                attachFirstHalfSelectListener(i, spinners.get(i));
            }
        } else {
            int[] leftSpinnerIds = { R.id.spinner1a, R.id.spinner2a, R.id.spinner3a };
            int[] rightSpinnerIds = { R.id.spinner1b, R.id.spinner2b, R.id.spinner3b };

            ArrayList<Spinner> leftSpinners = new ArrayList<>();
            ArrayList<Spinner> rightSpinners = new ArrayList<>();

            ArrayList<ToppingAdapter> leftAdapters = new ArrayList<>();
            ArrayList<ToppingAdapter> rightAdapters = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                leftSpinners.add((Spinner) myView.findViewById(leftSpinnerIds[i]));
                rightSpinners.add((Spinner) myView.findViewById(rightSpinnerIds[i]));

                leftAdapters.add(new ToppingAdapter(this.getContext(), allToppings));
                rightAdapters.add(new ToppingAdapter(this.getContext(), allToppings));

                leftSpinners.get(i).setAdapter(leftAdapters.get(i));
                rightSpinners.get(i).setAdapter(rightAdapters.get(i));

                attachFirstHalfSelectListener(i, leftSpinners.get(i));
                attachSecondHalfSelectListener(i, rightSpinners.get(i));
            }
        }
    }

    private void attachFirstHalfSelectListener(int i, Spinner spinner) {
        final int index = i;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Topping topping = (Topping) parent.getItemAtPosition(position);
                if (!topping.getName().equals("None")) {
                    mListener.setFirstHalfTopping(index, topping);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void attachSecondHalfSelectListener(int i, Spinner spinner) {
        final int index = i;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Topping topping = (Topping) parent.getItemAtPosition(position);
                if (!topping.getName().equals("None")) {
                    mListener.setSecondHalfTopping(index, topping);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    public interface ToppingSelectListener {
        void setFirstHalfTopping(int index, Topping topping);

        void setSecondHalfTopping(int index, Topping topping);

        PizzaOrderType getPizzaType();
    }
}
