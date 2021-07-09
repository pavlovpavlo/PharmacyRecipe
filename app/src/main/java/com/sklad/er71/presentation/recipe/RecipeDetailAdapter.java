package com.sklad.er71.presentation.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sklad.er71.Enum.ResiduesPharm.MTablerowapp;
import com.sklad.er71.R;

import java.util.ArrayList;
import java.util.List;


class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder> {

    private List<MTablerowapp> list;

    public RecipeDetailAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup vGroupText = (ViewGroup) mInflater.inflate(R.layout.item_detail_recipe, viewGroup, false);
        return new ViewHolder(vGroupText);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailAdapter.ViewHolder viewHolder, int i) {
        MTablerowapp item = list.get(i);

        viewHolder.recipe.setText(item.toString());

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

    public void setList(List<MTablerowapp> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addItems(List<MTablerowapp> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<MTablerowapp> getList() {
        return list;
    }
    
}
