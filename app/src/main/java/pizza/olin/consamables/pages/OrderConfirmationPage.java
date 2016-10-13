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
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pizza.olin.consamables.BeverageAdapter;
import pizza.olin.consamables.R;
import pizza.olin.consamables.data.SharedPrefsHandler;
import pizza.olin.consamables.types.Beverage;
import pizza.olin.consamables.types.OrderItem;

public class OrderConfirmationPage extends Fragment {

    private OrderConfirmationListener mListener;
    private SharedPrefsHandler prefsHandler;

    private OrderItem thisOrder;

    private TextView orderType;
    private TextView toppings;
    private TextView price;
    private TextView beverage;

    public OrderConfirmationPage() {
        // Required empty public constructor
    }

    public static OrderConfirmationPage newInstance() {
        OrderConfirmationPage fragment = new OrderConfirmationPage();
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);
        if (isVisible) {
            thisOrder = mListener.getCurrentOrder();
            orderType.setText(thisOrder.getDisplayName());
            toppings.setText(thisOrder.getDisplayDetails());
            String dollarPrice = "$" + new DecimalFormat(".00").format(thisOrder.getPriceCents() / 100.0);
            price.setText(dollarPrice);
            if (thisOrder.getBeverage() != null) {
                beverage.setText(thisOrder.getBeverage().getName());
            } else {
                beverage.setText("None");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_order_confirmation_page, container, false);

        orderType = (TextView) myView.findViewById(R.id.pizza_type);
        toppings = (TextView) myView.findViewById(R.id.topping_list);
        price = (TextView) myView.findViewById(R.id.default_price);
        beverage = (TextView) myView.findViewById(R.id.chosen_beverage);

        return myView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderConfirmationListener) {
            mListener = (OrderConfirmationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OrderConfirmationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OrderConfirmationListener {
        OrderItem getCurrentOrder();

        void confirmOrder();
    }
}
