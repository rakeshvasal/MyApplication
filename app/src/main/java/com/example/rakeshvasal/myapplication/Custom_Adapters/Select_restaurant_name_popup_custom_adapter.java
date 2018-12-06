package com.example.rakeshvasal.myapplication.Custom_Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.example.rakeshvasal.myapplication.GetterSetter.Select_restaurant_name_popup_RowItem;
import com.example.rakeshvasal.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by User on 9/18/2016.
 */
public class Select_restaurant_name_popup_custom_adapter extends ArrayAdapter<Select_restaurant_name_popup_RowItem> {

    Context context;
    List<Select_restaurant_name_popup_RowItem> rowItems;
    List<Select_restaurant_name_popup_RowItem> mOriginalValues;
    public List<Select_restaurant_name_popup_RowItem> objects;
    private List<Select_restaurant_name_popup_RowItem> NameIdlist = null;
    private ArrayList<Select_restaurant_name_popup_RowItem> arraylist;

    public Select_restaurant_name_popup_custom_adapter(Context context, int resourceId,
                                                       List<Select_restaurant_name_popup_RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
        this.rowItems=items;
        this.objects = items;
        this.NameIdlist = items;
        this.arraylist = new ArrayList<Select_restaurant_name_popup_RowItem>();
        this.arraylist.addAll(NameIdlist);

    }


    private class ViewHolder {

        TextView txtRestaurantName,txtRestaurantlocality;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Filter getFilter() {
        Filter myFilter= new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                ArrayList<Select_restaurant_name_popup_RowItem> tempList=new ArrayList<Select_restaurant_name_popup_RowItem>();
                if(constraint != null && objects!=null)
                {
                    int length=objects.size();
                    int i=0;
                    while(i<length){
                        Select_restaurant_name_popup_RowItem item=objects.get(i);
                        //do whatever you wanna do here
                        //adding result set output array

                        tempList.add(item);

                        i++;
                    }
                    filterResults.values = tempList;
                    filterResults.count = tempList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results)
            {
                objects = (ArrayList<Select_restaurant_name_popup_RowItem>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };
        return myFilter;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Select_restaurant_name_popup_RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.select_restauant_name_popup_item_list, null);
            holder = new ViewHolder();
            holder.txtRestaurantName = (TextView) convertView.findViewById(R.id.title);
            holder.txtRestaurantlocality = (TextView) convertView.findViewById(R.id.locality);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtRestaurantName.setText(rowItem.getRestaurant_name());
        holder.txtRestaurantlocality.setText(rowItem.getRestaurant_locality());
        //holder.txtStateId.setText(rowItem.getState_name());


        return convertView;
    }

}

