package com.hikki.masakapanih.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.transition.MaterialFadeThrough;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.database.AppDatabase;
import com.hikki.masakapanih.databinding.DetailResepBinding;
import com.hikki.masakapanih.model.DbResepModel;
import com.hikki.masakapanih.viewmodel.DetailResepViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailResep#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailResep extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailResep() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailResep.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailResep newInstance(String param1, String param2) {
        DetailResep fragment = new DetailResep();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        if (getArguments() != null) {
            key = getArguments().getString("key");
            url = getArguments().getString("url");
        }
        else{
            url = "";
        }
    }

    TextView ingridient, step;
    DetailResepBinding binding;
    AppDatabase appDB;
    DbResepModel model;
    String key, url, judul;
    boolean hapus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail_resep,container,false);
        ingridient = binding.resepIngridient;
        step = binding.resepLangkah;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DetailResepViewModel vm = ViewModelProviders.of(this).get(DetailResepViewModel.class);
        vm.getData(key).observe(this, detailResepModel -> {
            if(detailResepModel != null){
                detailResepModel.getResults().setThumb(url);
                if(detailResepModel.getResults().getNeedItem().size() > 0){
                    Glide.with(getActivity()).load(detailResepModel.getResults().getNeedItem().get(0).getThumbItem())
                            .placeholder(R.drawable.ic_pending_white_24dp).into(binding.imgBahabBaku);
                    binding.bahanBakuText.setText(detailResepModel.getResults().getNeedItem().get(0).getItemName());
                }
                else{
                    binding.bahanBakuText.setVisibility(View.GONE);
                    binding.imgBahabBaku.setVisibility(View.GONE);
                }
                binding.setVm(detailResepModel);
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                for(String g : detailResepModel.getResults().getIngredient()){
                    sb.append("- "+g+"\n");
                }
                for(int i =0;i<detailResepModel.getResults().getStep().size();i++){
                    sb2.append(detailResepModel.getResults().getStep().get(i).replace(String.valueOf(i+1),String.valueOf(i+1)+". ")+"\n\n");
                }

              sb.replace(sb.length()-1,sb.length(),"");
                String steps = sb2.replace(sb2.length()-2,sb2.length(),"").toString();
                binding.chunDeskripsi.setText("Deskripsi");
                binding.chunkBahanBaku.setText("Bahan Baku");
                binding.chunkLangkah.setText("Langkah-Langkah");
                ingridient.setText(sb.toString());
                step.setText(steps);
                model = new DbResepModel();
                model.setAuthor(detailResepModel.getResults().getAuthor().getUser());
                model.setJudul(detailResepModel.getResults().getTitle());
                model.setImg(url);
                model.setKey(key);
                judul = detailResepModel.getResults().getTitle();
                if(appDB == null){
                    appDB = Room.databaseBuilder(getActivity(),AppDatabase.class,"resep").build();
                }
                new cekAda().execute();
            }
            else{
                Snackbar bar = Snackbar.make(binding.getRoot(),"Koneksi error!",Snackbar.LENGTH_INDEFINITE);
                bar.setAction("Coba Lagi", v -> {
                    vm.reqData(key);
                    bar.dismiss();
                });
                bar.show();
            }
        });

        binding.backButton.setOnClickListener(v->{
            binding.backButton.setAlpha(0f);
            getActivity().onBackPressed();
        });

        binding.simpanResep.setOnClickListener(v->{
       //     Drawable.ConstantState con = getActivity().getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp,getActivity().getTheme()).getConstantState();
            if(!hapus){
                new insertData().execute();

            }
            else{
                new deleteItem().execute();
                hapus = false;
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class insertData extends AsyncTask<Void,Void,Long>{

        @Override
        protected Long doInBackground(Void... voids) {
            try{
                long g = appDB.dao().insertAll(model);
                return g;
            }
            catch(Exception e){
                return 202L;
            }
        }

        @Override
        protected void onPostExecute(Long integer) {
            super.onPostExecute(integer);
            if(integer != 202L){
                binding.simpanResep.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
                Toast.makeText(getActivity(), "Tersimpan didaftar resep", Toast.LENGTH_SHORT).show();
                hapus = true;

            }
            else{
                Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
            }
            binding.simpanResep.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class cekAda extends AsyncTask<Void,Void, List<DbResepModel>>{
        @Override
        protected List<DbResepModel> doInBackground(Void... voids) {
            List<DbResepModel> data = appDB.dao().getAll(judul);
            return data;
        }
        @Override
        protected void onPostExecute(List<DbResepModel> dbResepModels) {
            super.onPostExecute(dbResepModels);
            if(dbResepModels.size()>0){
                hapus = true;
                binding.simpanResep.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_white_24dp));
            }
            binding.simpanResep.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class deleteItem extends AsyncTask<Void,Void,Integer>{

        @Override
        protected Integer doInBackground(Void... voids) {
       //     Log.e("MODEL",model.getAuthor());
            return appDB.dao().deleteById(model.getJudul());
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
          //  Log.e("MODEL", String.valueOf(integer));
            if(integer == 0){
                Toast.makeText(getActivity(), "Gagal menghapus", Toast.LENGTH_SHORT).show();
            }
            else if(integer > 0){
                Toast.makeText(getActivity(), "Dihapus dari daftar resep", Toast.LENGTH_SHORT).show();
                binding.simpanResep.setImageDrawable(getResources().getDrawable(R.drawable.ic_bookmark_border_white_24dp));
                hapus = false;
            }
            binding.simpanResep.setVisibility(View.VISIBLE);
        }

    }
}