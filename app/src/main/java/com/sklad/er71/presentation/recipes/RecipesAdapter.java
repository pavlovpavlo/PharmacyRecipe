package com.sklad.er71.presentation.recipes;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorSpace;
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

import net.glxn.qrgen.android.QRCode;

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

        void openQr(MTablerowrecipe item);
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

        viewHolder.title.setText("Рецепт " + item.getmSeries() + " " + item.getmNumber() + " " + item.getmData());
        viewHolder.description.setText(item.getmTrademark() + ", " + item.getmLF() + ", " + item.getmDosage() + ", " + item.getmKolV());

        viewHolder.itemView.setOnClickListener(v -> listener.serviceSelected(item));

        if (item.getmStatus() == 12 || item.getmStatus() == 13 || item.getmStatus() == 14) {
            ((View) viewHolder.qr.getParent()).setVisibility(View.INVISIBLE);
            viewHolder.title.setTextColor(Color.parseColor("#7e7e7e"));
            viewHolder.description.setTextColor(Color.parseColor("#7e7e7e"));
        } else {
            Bitmap myBitmap = QRCode.from(item.getmQRString()).bitmap();
            viewHolder.qr.setImageBitmap(myBitmap);
            viewHolder.qr.setSelected(false);
            ((View) viewHolder.qr.getParent()).setVisibility(View.VISIBLE);
            ((View) viewHolder.qr.getParent()).setOnClickListener(v -> listener.openQr(item));
            viewHolder.title.setTextColor(Color.parseColor("#277a6b"));
            viewHolder.description.setTextColor(Color.parseColor("#313030"));
        }

        if(i == list.size() -1){
            viewHolder.line.setVisibility(View.GONE);
        }else
            viewHolder.line.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        ImageView qr;
        View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            qr = itemView.findViewById(R.id.qr);
            line = itemView.findViewById(R.id.line);
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
