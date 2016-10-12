package pizza.olin.consamables;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pizza.olin.consamables.types.Topping;

/**
 * TODO: Add a class header comment!
 */

public class ToppingAdapter extends ArrayAdapter<Topping> {

    public ToppingAdapter(Context context, ArrayList<Topping> toppings) {
        super(context, 0, toppings);
        // This isn't finished
    }
}
