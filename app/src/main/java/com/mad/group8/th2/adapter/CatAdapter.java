package com.mad.group8.th2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.group8.th2.R;
import com.mad.group8.th2.model.Cat;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {

    private List<Cat> catList;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public CatAdapter(Context context, List<Cat> catList) {
        this.context = context;
        this.catList = catList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
        void onUpdateClick(int position, Cat updatedCat);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_cat, parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Cat currentCat = catList.get(position);

        holder.catNameTextView.setText(currentCat.getName());
        holder.catPriceTextView.setText(String.valueOf(currentCat.getPrice()));
        holder.catDescriptionTextView.setText(currentCat.getDescription());
        holder.catImageView.setImageResource(currentCat.getImgId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });

        if (holder.deleteButton != null) {
            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }

        if (holder.updateButton != null) {
            holder.updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onUpdateClick(position, currentCat);
                    }
                }
            });
        }

        holder.itemView.setSelected(position == selectedPosition);
    }


    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder {
        TextView catNameTextView;
        TextView catPriceTextView;
        TextView catDescriptionTextView;
        ImageView catImageView;
        Button deleteButton;
        Button updateButton;

        public CatViewHolder(@NonNull View itemView) {
            super(itemView);

            catNameTextView = itemView.findViewById(R.id.textViewCatName);
            catPriceTextView = itemView.findViewById(R.id.textViewCatPrice);
            catDescriptionTextView = itemView.findViewById(R.id.textViewCatDescription);
            catImageView = itemView.findViewById(R.id.imageViewCat);
            deleteButton = itemView.findViewById(R.id.delete);
            updateButton = itemView.findViewById(R.id.buttonUpdateCat);
        }
    }

    public Cat getCatAtPosition(int position) {
        if (position >= 0 && position < catList.size()) {
            return catList.get(position);
        }
        return null;
    }

    public void deleteCat(int position) {
        if (position >= 0 && position < catList.size()) {
            catList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, catList.size());
        }
    }

    public void addCat(Cat cat) {
        catList.add(cat);
        notifyDataSetChanged();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public Cat getSelectedCat() {
        if (selectedPosition != RecyclerView.NO_POSITION) {
            return catList.get(selectedPosition);
        }
        return null;
    }

    public void updateCat(int position, Cat updatedCat) {
        if (position >= 0 && position < catList.size()) {
            catList.set(position, updatedCat);
            notifyDataSetChanged();
        }
    }
}
