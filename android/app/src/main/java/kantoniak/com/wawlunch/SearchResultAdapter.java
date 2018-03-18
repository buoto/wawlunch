package kantoniak.com.wawlunch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

import kantoniak.com.wawlunch.data.Place;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private static final String TAG = SearchResultAdapter.class.getSimpleName();

    private final List<Place> places = new LinkedList<>();
    private final View.OnClickListener clickListener;

    public SearchResultAdapter(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mItemView;
        public TextView mNameText;
        public TextView mStreetText;
        public ImageView mThumbView;
        public TextView mOpenTextView;
        public TextView mCommonPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mNameText = itemView.findViewById(R.id.search_result_name);
            mStreetText = itemView.findViewById(R.id.search_result_street);
            mThumbView = itemView.findViewById(R.id.search_result_thumb);
            mOpenTextView = itemView.findViewById(R.id.result_open);
            mCommonPriceTextView = itemView.findViewById(R.id.result_common_price);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.search_result_item_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Place place = places.get(position);
        holder.mItemView.setTag(R.id.list_item_place, place);
        holder.mItemView.setOnClickListener(clickListener);
        holder.mNameText.setText(place.getName());
        holder.mStreetText.setText(place.getStreet());

        int zlote = place.getCommonPrice() / 100;
        int grosze = place.getCommonPrice() % 100;

        holder.mCommonPriceTextView.setText(String.format("%d,%02d zł", zlote, grosze));
        holder.mOpenTextView.setText(place.getOpenFrom() + " - " + place.getOpenTo());

        Picasso.get().load(place.getPicture()).into(holder.mThumbView);
    }

    @Override
    public void onViewRecycled(@NonNull ViewHolder holder) {
        holder.mItemView.setOnClickListener(null);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void updatePlaces(List<Place> places) {
        this.places.clear();
        this.places.addAll(places);
        this.notifyDataSetChanged();
    }
}
