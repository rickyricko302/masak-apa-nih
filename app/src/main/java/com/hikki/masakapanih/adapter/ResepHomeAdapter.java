package com.hikki.masakapanih.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.databinding.ResepHomeBinding;
import com.hikki.masakapanih.model.ResultsResep;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResepHomeAdapter extends RecyclerView.Adapter<ResepHomeAdapter.ViewHolder> {

    private List<ResultsResep> data;
    private OnClick onClick;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ResepHomeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_resep
        ,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ResepHomeAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<ResultsResep> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ResepHomeBinding binding;

        public ViewHolder(@NonNull @NotNull ResepHomeBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.cardResep.setOnClickListener(v->{
                onClick.click(v,data.get(getAdapterPosition()).getKey(),data.get(getAdapterPosition()).getThumb());
            });
        }
        public void bind(ResultsResep model){
            model.setTitle(model.getTitle());
            binding.setVm(model);
        }
    }
    public void setOnClick(OnClick onClick){
        this.onClick = onClick;
    }
    public interface OnClick{
        void click(View v,String key, String url);
    }
}
