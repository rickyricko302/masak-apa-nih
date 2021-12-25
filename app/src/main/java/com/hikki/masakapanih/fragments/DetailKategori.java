package com.hikki.masakapanih.fragments;

import android.os.Bundle;
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
import com.google.android.material.transition.MaterialFadeThrough;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.adapter.ResepHomeAdapter;
import com.hikki.masakapanih.databinding.FragmentDetailKategoriBinding;
import com.hikki.masakapanih.model.ResepModel;
import com.hikki.masakapanih.utils.ErrorHandling;
import com.hikki.masakapanih.viewmodel.DetailKategoriViewModel;
import org.jetbrains.annotations.NotNull;


public class DetailKategori extends Fragment implements ErrorHandling {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String key;
    public DetailKategori() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        if (getArguments() != null) {
           key = getArguments().getString("key");
        }
    }

    FragmentDetailKategoriBinding binding;
    ResepHomeAdapter adapter;
    ShimmerFrameLayout shimmer2;
    RecyclerView recycler;
    DetailKategoriViewModel vm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail_kategori,container,false);
        binding.judulKategori.setText(key);
        recycler = binding.recyclerDetailKategori;
        shimmer2 = binding.shimmerDetailkategori;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUP();
        vm = ViewModelProviders.of(this).get(DetailKategoriViewModel.class);
        vm.getResepData(key).observe(this, new Observer<ResepModel>() {
            @Override
            public void onChanged(ResepModel resepModel) {
                if(resepModel != null){
                    shimmer2.setVisibility(View.GONE);
                    adapter.setData(resepModel.getResults());
                    binding.chunk6.setVisibility(View.VISIBLE);
                    if(recycler.getVisibility() == View.GONE ){
                        recycler.setVisibility(View.VISIBLE);
                    }
                }else{
                    shimmer2.hideShimmer();
                    Toast.makeText(getActivity(), "Gagal mendapatkan data resep", Toast.LENGTH_SHORT).show();
                    onError(shimmer2, binding.buttonCobalagi);
                }
            }
        });


        binding.swipyDetail.setOnRefreshListener(direction -> {
            recycler.setVisibility(View.GONE);
            binding.chunk6.setVisibility(View.GONE);
            binding.buttonCobalagi.setVisibility(View.GONE);
            shimmer2.setVisibility(View.VISIBLE);
            shimmer2.startShimmer();
            vm.setPage(vm.getPage()+1);
            vm.reqResep(key,vm.getPage());
            binding.swipyDetail.setRefreshing(false);
        });

        binding.backDetailKategori.setOnClickListener(v->{
            binding.backDetailKategori.setVisibility(View.GONE);
            getActivity().onBackPressed();
        });
    }

    public void setUP(){
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ResepHomeAdapter();
        recycler.setAdapter(adapter);
        adapter.setOnClick(new ResepHomeAdapter.OnClick() {
            @Override
            public void click(View v, String key, String url) {
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                bundle.putString("url", url);
                Navigation.findNavController(v).navigate(R.id.action_detailKategori_to_detailResep,bundle);
            }
        });
    }

    @Override
    public void onError(View parent, View error) {
        binding.buttonCobalagi.setVisibility(View.VISIBLE);
        binding.chunk6.setVisibility(View.GONE);
        binding.buttonCobalagi.setOnClickListener(v->{
            shimmer2.showShimmer(true);
            vm.reqResep(key,vm.getPage());

            binding.buttonCobalagi.setVisibility(View.GONE);
        });
    }
}