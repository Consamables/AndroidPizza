package pizza.olin.consamables;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;

import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.Topping;

/**
 * TODO: Add a class header comment!
 */

public class ToppingSelectFragment extends Fragment {

    public static ToppingSelectFragment newInstance() {
        return new ToppingSelectFragment();
    }

    private SharedPrefsHandler prefsHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_topping_select, container, false);

        final Spinner topping1 = (Spinner) myView.findViewById(R.id.spinner1);
        final Spinner topping2 = (Spinner) myView.findViewById(R.id.spinner2);
        final Spinner topping3 = (Spinner) myView.findViewById(R.id.spinner3);

        prefsHandler = new SharedPrefsHandler(getActivity().getPreferences(Context.MODE_PRIVATE));

        final ArrayList<Topping> allToppings = prefsHandler.getToppings();

        final ToppingAdapter adapter1 = new ToppingAdapter(this.getContext(), allToppings);
        final ToppingAdapter adapter2 = new ToppingAdapter(this.getContext(), allToppings);
        final ToppingAdapter adapter3 = new ToppingAdapter(this.getContext(), allToppings);

        topping1.setAdapter(adapter1);
        topping2.setAdapter(adapter2);
        topping3.setAdapter(adapter3);

        return myView;
    }
}
