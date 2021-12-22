package com.hikki.masakapanih.fragments;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.adapter.KategoriAdapter;
import com.hikki.masakapanih.adapter.ResepHomeAdapter;
import com.hikki.masakapanih.databinding.FragmentHomeBinding;
import com.hikki.masakapanih.model.KategoriModel;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.utils.ErrorHandling;
import com.hikki.masakapanih.viewmodel.HomeViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import org.jetbrains.annotations.NotNull;
import retrofit2.http.GET;


public class HomeFragment extends Fragment implements ErrorHandling {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    ShimmerFrameLayout shimmer,shimmer2;
    RecyclerView recyclerKategori,recyclerResep;
    FragmentHomeBinding binding;
    View cobaLagi;
    HomeViewModel vm;
    KategoriAdapter kategoriAdapter;
    ResepHomeAdapter resepHomeAdapter;
    SwipyRefreshLayout swipy;
    TextView chunk;
    boolean kategori,resep;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false);
        shimmer = binding.shimmer;
        cobaLagi = binding.buttonCobalagi;
        recyclerKategori = binding.recyclerKategori;
        recyclerResep = binding.recyclerHomeResep;
        shimmer2 = binding.shimmerHome2;
        chunk = binding.chunk4;
        swipy = binding.swipyHome;
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUP();
        vm = ViewModelProviders.of(getActivity()).get(HomeViewModel.class);
        vm.getKategoriData().observe(this, new Observer<KategoriModel>() {
            @Override
            public void onChanged(KategoriModel kategoriModel) {
                if(kategoriModel != null){
                    shimmer.setVisibility(View.GONE);
                    kategori = false;
                    kategoriAdapter.setData(kategoriModel.getResults());
                    if(recyclerKategori.getVisibility() == View.GONE){
                        recyclerKategori.setVisibility(View.VISIBLE);
                    }
                }else{
                    kategori = true;
                    shimmer.hideShimmer();
                    Toast.makeText(getActivity(), "Gagal mendapatkan data kategori", Toast.LENGTH_SHORT).show();
                    onError(shimmer,cobaLagi);
                }
            }
        });
        vm.getResepData().observe(this, new Observer<ResepModel>() {
            @Override
            public void onChanged(ResepModel resepModel) {
                if(resepModel != null){
                    resep = false;
                    shimmer2.setVisibility(View.GONE);
                    resepHomeAdapter.setData(resepModel.getResults());

                    if(recyclerResep.getVisibility() == View.GONE ){
                        recyclerResep.setVisibility(View.VISIBLE);
                        chunk.setVisibility(View.VISIBLE);
                    }
                }else{
                    resep = true;
                    shimmer2.hideShimmer();
                    Toast.makeText(getActivity(), "Gagal mendapatkan data resep", Toast.LENGTH_SHORT).show();
                    onError(shimmer2,cobaLagi);
                }
            }
        });

        swipy.setOnRefreshListener(direction -> {
            recyclerResep.setVisibility(View.GONE);
            chunk.setVisibility(View.GONE);
            cobaLagi.setVisibility(View.GONE);
            shimmer2.setVisibility(View.VISIBLE);
            shimmer2.startShimmer();
            vm.setPage(vm.getPage()+1);
            vm.reqResep();
            swipy.setRefreshing(false);
        });
    }

    private void setUP() {
        recyclerKategori.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerResep.setLayoutManager(new LinearLayoutManager(getActivity()));
        kategoriAdapter = new KategoriAdapter();
        resepHomeAdapter = new ResepHomeAdapter();
        recyclerKategori.setAdapter(kategoriAdapter);
        recyclerResep.setAdapter(resepHomeAdapter);

        kategoriAdapter.setClick(new KategoriAdapter.onCLick() {
            @Override
            public void click(View v, String key) {
                Bundle bundel = new Bundle();
                bundel.putString("key",key);
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailKategori, bundel);
            }
        });
        resepHomeAdapter.setOnClick(new ResepHomeAdapter.OnClick() {
            @Override
            public void click(View v,String key, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                bundle.putString("url", url);
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_detailResep,bundle);
//                Toast.makeText(getActivity(),key,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onError(View parent, View error) {

        cobaLagi.setVisibility(View.VISIBLE);
        chunk.setVisibility(View.GONE);
        cobaLagi.setOnClickListener(v->{
            if(kategori){
                shimmer.showShimmer(true);
                vm.reqkategori();
            }
            if(resep){
                shimmer2.showShimmer(true);
                vm.reqResep();
            }
            cobaLagi.setVisibility(View.GONE);
        });
    }
}