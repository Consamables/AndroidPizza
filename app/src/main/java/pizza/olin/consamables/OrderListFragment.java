package pizza.olin.consamables;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderListFragment extends Fragment {
    private static final String ARG_ORDER_ID = "order_id";

    private String orderId;

    private OrderListListener mListener;

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(String orderId) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ORDER_ID, orderId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderId = getArguments().getString(ARG_ORDER_ID);
        }

        // What is this?
        if (orderId != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        // What is this?
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderListListener) {
            mListener = (OrderListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OrderListListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OrderListListener {
    }
}
