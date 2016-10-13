package pizza.olin.consamables;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pizza.olin.consamables.types.HalfPizza;
import pizza.olin.consamables.types.OrderItem;
import pizza.olin.consamables.types.PizzaOrderType;

public class HalfOrWholePage extends Fragment {
    private PizzaTypeListener mListener;

    public HalfOrWholePage() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HalfOrWholePage newInstance() {
        HalfOrWholePage fragment = new HalfOrWholePage();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_half_or_whole_page, container, false);
    }

    public void onHalfPizzaButtonPressed() {
        if (mListener != null) {
            mListener.setPizzaType(PizzaOrderType.HALF);
        }
    }

    public void onWholePizzaButtonPressed() {
        if (mListener != null) {
            mListener.setPizzaType(PizzaOrderType.WHOLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PizzaTypeListener) {
            mListener = (PizzaTypeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PizzaTypeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PizzaTypeListener {
        void setPizzaType(PizzaOrderType type);
    }
}
