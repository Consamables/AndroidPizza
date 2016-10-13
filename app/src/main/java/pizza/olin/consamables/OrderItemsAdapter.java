package pizza.olin.consamables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pizza.olin.consamables.types.OrderItem;

public class OrderItemsAdapter extends ArrayAdapter<OrderItem> {

    public OrderItemsAdapter(Context context, ArrayList<OrderItem> orderItems) {
        super(context, 0, orderItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderItem orderItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_item, parent, false);
        }

        // populate view
        TextView orderTypeText = (TextView) convertView.findViewById(R.id.order_type_text);
        TextView orderDetailsText = (TextView) convertView.findViewById(R.id.order_details_text);
        TextView orderPersonText = (TextView) convertView.findViewById(R.id.order_person_text);

        orderTypeText.setText(orderItem.getDisplayName());
        orderDetailsText.setText(orderItem.getDisplayDetails());
        orderPersonText.setText(orderItem.getPerson());


        return convertView;
    }
}
