package pizza.olin.consamables;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import pizza.olin.consamables.types.Topping;

/**
 * TODO: Add a class header comment!
 */

public class ToppingSelectFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_topping_select, container, false);

        final Spinner topping1 = (Spinner) myView.findViewById(R.id.spinner1);
        final Spinner topping2 = (Spinner) myView.findViewById(R.id.spinner2);
        final Spinner topping3 = (Spinner) myView.findViewById(R.id.spinner3);

        final ArrayList allToppings = new ArrayList<Topping>();

        final ArrayAdapter<Topping> adapter1 = new ToppingAdapter(this.getContext(), allToppings);
        final ArrayAdapter<Topping> adapter2 = new ToppingAdapter(this.getContext(), allToppings);
        final ArrayAdapter<Topping> adapter3 = new ToppingAdapter(this.getContext(), allToppings);

        // This isn't finished

        return myView;
    }
}
