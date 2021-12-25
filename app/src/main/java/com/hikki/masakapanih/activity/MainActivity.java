package com.hikki.masakapanih.activity;

import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import com.hikki.masakapanih.R;
import me.ibrahimsn.lib.SmoothBottomBar;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    SmoothBottomBar smoothBottomBar;
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        smoothBottomBar = findViewById(R.id.bar);
        controller = Navigation.findNavController(this,R.id.fragment);
        PopupMenu popupMenu = new PopupMenu(this,null);
        popupMenu.inflate(R.menu.bottom_menu);
        Menu menu = popupMenu.getMenu();
        smoothBottomBar.setupWithNavController(menu,controller);
        controller.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if(destination.getId() == R.id.detailKategori || destination.getId() == R.id.detailResep){
                    smoothBottomBar.setVisibility(View.GONE);
                }
                else{
                    if(smoothBottomBar.getVisibility() == View.GONE){
                        smoothBottomBar.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return controller.navigateUp() || super.onSupportNavigateUp();
    }
}