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

import pizza.olin.consamables.R;
import pizza.olin.consamables.ToppingAdapter;
import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.Topping;

/**
 * TODO: Add a class header comment!
 */

public class ToppingSelectPage extends Fragment {

    private ToppingSelectListener mListener;
    private SharedPrefsHandler prefsHandler;

    public static ToppingSelectPage newInstance() {
        return new ToppingSelectPage();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_topping_select_page, container, false);

        prefsHandler = new SharedPrefsHandler(getActivity().getPreferences(Context.MODE_PRIVATE));
        final ArrayList<Topping> allToppings = prefsHandler.getToppings();

        int[] spinnerIds = { R.id.spinner1, R.id.spinner2, R.id.spinner3 };

        ArrayList<Spinner> spinners = new ArrayList<>();
        ArrayList<ToppingAdapter> adapters = new ArrayList<>();

        for (int i = 0; i < spinnerIds.length; i++) {
            spinners.add((Spinner) myView.findViewById(spinnerIds[i]));
            adapters.add(new ToppingAdapter(this.getContext(), allToppings));
            spinners.get(i).setAdapter(adapters.get(i));
            attachSelectListener(i, spinners.get(i));
        }

        return myView;
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

    private void attachSelectListener(int i, Spinner spinner) {
        final int index = i;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Topping topping = (Topping) parent.getItemAtPosition(position);
                if (topping.getName() != "None") {
                    mListener.setFirstHalfTopping(index, topping);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public interface ToppingSelectListener {
        void setFirstHalfTopping(int index, Topping topping);

        void setSecondHalfTopping(int index, Topping topping);
    }
}
