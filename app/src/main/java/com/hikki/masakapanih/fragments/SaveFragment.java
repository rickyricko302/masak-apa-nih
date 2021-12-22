package com.hikki.masakapanih.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.adapter.SimpanAdapter;
import com.hikki.masakapanih.database.AppDatabase;
import com.hikki.masakapanih.databinding.FragmentSaveBinding;
import com.hikki.masakapanih.model.DbResepModel;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SaveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SaveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SaveFragment newInstance(String param1, String param2) {
        SaveFragment fragment = new SaveFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentSaveBinding binding;
    RecyclerView recycler;
    TextView tidakAda;
    SimpanAdapter adapter;
    AppDatabase appDb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_save,container,false);
        recycler = binding.recycler;
        tidakAda = binding.tidakAdaResep;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appDb = Room.databaseBuilder(getActivity(),AppDatabase.class,"resep").build();
        new getData().execute();
    }


    private class getData extends AsyncTask<Void, Void, List<DbResepModel>>{

        @Override
        protected List<DbResepModel> doInBackground(Void... voids) {
            return appDb.dao().getData();
        }

        @Override
        protected void onPostExecute(List<DbResepModel> dbResepModels) {
            super.onPostExecute(dbResepModels);
            if(dbResepModels.size() > 0){
                recycler.setVisibility(View.VISIBLE);
                recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new SimpanAdapter();
                adapter.setData(dbResepModels);
                recycler.setAdapter(adapter);
                adapter.setClick(new SimpanAdapter.OnCLick() {
                    @Override
                    public void clickLihat(View v, String key, String url) {
                        Bundle bundle = new Bundle();
                        bundle.putString("key", key);
                        bundle.putString("url", url);
                        Navigation.findNavController(v).navigate(R.id.action_saveFragment_to_detailResep,bundle);

                    }

                    @Override
                    public void clickHapus(View v, String judul) {
                        //Log.e("JUDUL",judul);
                        new deleteItem().execute(judul);
                    }
                });
            }
            else{
                recycler.setVisibility(View.GONE);
                tidakAda.setVisibility(View.VISIBLE);
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class deleteItem extends AsyncTask<String,Void,Integer>{

        @Override
        protected Integer doInBackground(String... voids) {
            //     Log.e("MODEL",model.getAuthor());
            return appDb.dao().deleteById(voids[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            //  Log.e("MODEL", String.valueOf(integer));
            if(integer == 0){
                Toast.makeText(getActivity(), "gagal dihapus dari daftar resep", Toast.LENGTH_SHORT).show();
            }
            else if(integer > 0){
                Toast.makeText(getActivity(), "Dihapus dari daftar resep", Toast.LENGTH_SHORT).show();
                new getData().execute();
            }

        }

    }
}