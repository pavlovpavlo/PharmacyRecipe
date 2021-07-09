package com.sklad.er71.presentation.recipes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sklad.er71.Enum.Recipe_SNILS.MTablerowrecipe;
import com.sklad.er71.R;

import java.util.ArrayList;
import java.util.List;


class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<MTablerowrecipe> list;
    private OnRecipeClickListener listener;

    public RecipesAdapter() {
        list = new ArrayList<>();
    }

    public interface OnRecipeClickListener {
        void serviceSelected(MTablerowrecipe item);
    }

    @NonNull
    @Override
    public RecipesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup vGroupText = (ViewGroup) mInflater.inflate(R.layout.item_recipe, viewGroup, false);
        return new ViewHolder(vGroupText);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesAdapter.ViewHolder viewHolder, int i) {
        MTablerowrecipe item = list.get(i);

        viewHolder.recipe.setText(item.toString());

        viewHolder.itemView.setOnClickListener(v -> {
            listener.serviceSelected(item);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView recipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipe = itemView.findViewById(R.id.recipe);
        }
    }

    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void setList(List<MTablerowrecipe> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItems(List<MTablerowrecipe> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<MTablerowrecipe> getList() {
        return list;
    }

    public void setListener(OnRecipeClickListener listener) {
        this.listener = listener;
    }
}
