package com.hikki.masakapanih.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.hikki.masakapanih.R;
import com.hikki.masakapanih.adapter.SearchAdapter;
import com.hikki.masakapanih.databinding.FragmentSearchBinding;
import com.hikki.masakapanih.model.ResultsResep;
import com.hikki.masakapanih.model.ResultsSearch;
import com.hikki.masakapanih.model.SearchModel;
import com.hikki.masakapanih.viewmodel.SearchVIewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

    FragmentSearchBinding binding;
    SearchView sv;
    LinearLayout linParent;
    RecyclerView recycler;
    SearchAdapter adapter;
    SearchVIewModel vm;
    ShimmerFrameLayout shimmer;
    ConstraintLayout parent;
    FragmentActivity activity;
    boolean tos;
    int pembagi = 10;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search,container,false);
        sv = binding.serachView;
        linParent = binding.linSearch;
        recycler = binding.recyclerSearch;
        shimmer = binding.shimmerSearch;
        parent = binding.parentSearch;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUP();
        vm = ViewModelProviders.of(activity).get(SearchVIewModel.class);
        vm.init();
     //   sv.setQuery(vm.getKey(),false);
        vm.getSearch().observe(this, new Observer<SearchModel>() {
                @Override
                public void onChanged(SearchModel searchModel) {
               //     Toast.makeText(getActivity(), "Change", Toast.LENGTH_LONG).show();
                    if (searchModel != null && searchModel.getResults().size() > 0) {
                        shimmer.setVisibility(View.GONE);
                        linParent.setVisibility(View.GONE);
                        if(!tos) {
                            Toast.makeText(activity, "Memuat " + String.valueOf(searchModel.getResults().size()) + " data...", Toast.LENGTH_LONG).show();
                        }
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.setData(searchModel.getResults());
                                recycler.setVisibility(View.VISIBLE);
                                tos = true;
                            }
                        },2000);

                    } else{
                        linParent.setVisibility(View.VISIBLE);
                        shimmer.setVisibility(View.GONE);
                        recycler.setVisibility(View.GONE);
                        if(searchModel != null && searchModel.getResults().size() == 0){
                            Toast.makeText(activity, "Tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(activity, "Koneksi error", Toast.LENGTH_SHORT).show();
                        }
                    }
                 }
            });

        vm.getKey().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sv.setQuery(s,false);
            }
        });


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                linParent.setVisibility(View.GONE);
                recycler.setVisibility(View.GONE);
                shimmer.setVisibility(View.VISIBLE);
                tos = false;
                vm.setKey(s);
                vm.reqSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        sv.clearFocus();
        parent.requestFocus();
    }

    @Override
    public void onAttach(@NonNull @NotNull Activity activity) {
        super.onAttach(activity);
        if(this.activity == null){
            this.activity = (FragmentActivity) activity;
        }

    }


    private void setUP() {
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        TextView textView = binding.getRoot().findViewById(id);
        textView.setTextColor(Color.BLACK);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new SearchAdapter();
        recycler.setAdapter(adapter);
        adapter.setClick(new SearchAdapter.OnCLick() {
            @Override
            public void click(View v,String key,String url) {
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                bundle.putString("url", url);
                Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_detailResep,bundle);

            }
        });
    }
}