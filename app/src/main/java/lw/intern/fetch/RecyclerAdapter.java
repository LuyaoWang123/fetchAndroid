package lw.intern.fetch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * The RecyclerAdapter class is responsible for managing the data and views in a RecyclerView.
 * It binds data from a list of items to the corresponding views in the RecyclerView.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private List<ItemInterface> items;

    /**
     * The RecyclerViewHolder class represents a single item view in the RecyclerView.
     * It holds references to the views within each item.
     */
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textViewId;
        TextView ViewListId;

        /**
         * Constructs a new RecyclerViewHolder.
         *
         * @param itemView The root view of the item layout.
         */
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.textViewTitle);
            this.textViewId = itemView.findViewById(R.id.textViewId);
            this.ViewListId = itemView.findViewById(R.id.textViewListId);
        }
    }

    /**
     * Constructs a new RecyclerAdapter with a list of items.
     *
     * @param items The list of items to be displayed in the RecyclerView.
     */
    public RecyclerAdapter(List<ItemInterface> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        ItemInterface item = items.get(position);
        holder.textView.setText(item.getName());
        String id = "id: " + item.getId();
        String listId = "list Id: " + item.getListId();
        holder.textViewId.setText(id);
        holder.ViewListId.setText(listId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Update the items displayed in the RecyclerView with a new list of items.
     *
     * @param newItems The new list of items to be displayed.
     */
    public void updateItems(List<ItemInterface> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
    }
}
