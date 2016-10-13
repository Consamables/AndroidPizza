package pizza.olin.consamables;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        View view = inflater.inflate(R.layout.fragment_half_or_whole_page, container, false);

        view.findViewById(R.id.button_half).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.setPizzaType(PizzaOrderType.HALF);
                }
            }
        });

        view.findViewById(R.id.button_whole).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.setPizzaType(PizzaOrderType.WHOLE);
                }
            }
        });

        return view;
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
