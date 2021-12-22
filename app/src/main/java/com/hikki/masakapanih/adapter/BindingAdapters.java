package com.hikki.masakapanih.adapter;

import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.hikki.masakapanih.R;

public class BindingAdapters {
    @BindingAdapter("thumb")
    public static void setImg(ImageView view, String url){
        Glide.with(view.getContext()).load(url).placeholder(R.drawable.ic_pending_white_24dp).optionalCenterCrop().into(view);
    }

}
