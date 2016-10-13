package pizza.olin.consamables;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import pizza.olin.consamables.types.GroupOrder;
import pizza.olin.consamables.types.Topping;

public class WizardBasicPage extends Fragment {

    private TextView pageText;

    public static WizardBasicPage newInstance(String pageText) {
        Bundle args = new Bundle();
        args.putString("text", pageText);

        final WizardBasicPage fragment = new WizardBasicPage();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_wizard_basic_page, container, false);
        pageText = (TextView) view.findViewById(R.id.page_text);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final String text = getArguments().getString("text", "Oops.");

        pageText.setText(text);
    }
}
