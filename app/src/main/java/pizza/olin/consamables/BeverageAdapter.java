package pizza.olin.consamables;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import pizza.olin.consamables.types.Beverage;

public class BeverageAdapter extends ArrayAdapter<Beverage> {
    public BeverageAdapter(Context context, ArrayList<Beverage> beverages) {
        super(context, R.layout.support_simple_spinner_dropdown_item, beverages);
    }
}
