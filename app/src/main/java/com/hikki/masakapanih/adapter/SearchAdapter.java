package com.hikki.masakapanih.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.databinding.SearchBinding;
import com.hikki.masakapanih.model.ResultsSearch;
import com.hikki.masakapanih.model.ResultsSearch;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<ResultsSearch> data;
    private OnCLick onCLick;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_search
                ,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchAdapter.ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<ResultsSearch> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchBinding binding;
        public ViewHolder(@NonNull @NotNull SearchBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
            binding.cardResep.setOnClickListener(v->{
                onCLick.click(v,data.get(getAdapterPosition()).getKey(),data.get(getAdapterPosition()).getThumb());
            });
        }
        public void bind(ResultsSearch model){
            model.setTitle(model.getTitle());
            binding.setVm(model);
        }
    }

    public void setClick(OnCLick onCLick){
        this.onCLick = onCLick;
    }
    public interface OnCLick {
        void click(View v,String key, String url);
    }
}
