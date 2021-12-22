package com.hikki.masakapanih.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.databinding.SimpanBinding;
import com.hikki.masakapanih.model.DbResepModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpanAdapter extends RecyclerView.Adapter<SimpanAdapter.ViewHolder> {

    private List<DbResepModel> data;
    private OnCLick onClick;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        SimpanBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_simpan,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SimpanAdapter.ViewHolder holder, int position) {
        DbResepModel model = data.get(position);
      //  model.setJudul(String.valueOf(position+1)+". "+model.getJudul());
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<DbResepModel> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpanBinding binding;
        Button lihat, delete;
        public ViewHolder(@NonNull @NotNull SimpanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            lihat = binding.simpanLihat;
            delete = binding.simpanHapus;

            lihat.setOnClickListener(v->{
                onClick.clickLihat(v, data.get(getAdapterPosition()).getKey(), data.get(getAdapterPosition()).getImg());
            });

            delete.setOnClickListener(v->{
                onClick.clickHapus(v,data.get(getAdapterPosition()).getJudul());
            });
        }

        public void bind(DbResepModel model){
            binding.setVm(model);
        }

    }
    public void setClick(OnCLick click){
        onClick = click;
    }
    public interface OnCLick {
        void clickLihat(View v, String key, String url);
        void clickHapus(View v, String judul);
    }
}
