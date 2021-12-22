package com.hikki.masakapanih.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.model.ResultsKategori;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {
    private List<ResultsKategori> data;
    private onCLick click;
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull KategoriAdapter.ViewHolder holder, int position) {
        holder.button.setText(data.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<ResultsKategori> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_kategori);

            button.setOnClickListener(v->{
                click.click(v,data.get(getAdapterPosition()).getKey());
            });
        }
    }
    public void setClick(onCLick click){
        this.click = click;
    }
    public interface onCLick{
        void click (View v,String key);
    }
}
