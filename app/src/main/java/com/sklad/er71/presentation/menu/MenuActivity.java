package com.sklad.er71.presentation.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.sklad.er71.Enum.TabBarItem;
import com.sklad.er71.R;
import com.sklad.er71.busines.BaseActivity;
import com.sklad.er71.util.Util;

import java.util.List;

public class MenuActivity extends BaseActivity {

    public NavController navController;
    private final List<TabBarItem> TAB_BAR_LIST = Util.generateTabBar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initViews();
    }

    private void initViews() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    public void clickToTab(View v){
        switch (v.getId()) {
            case R.id.goToHome:
                setActiveMenu(0);
                NavOptions.Builder navBuilder = new NavOptions.Builder();
                NavOptions navOptions = navBuilder.setPopUpTo(R.id.fragmentHome, true).build();
                navController.navigate(R.id.fragmentHome, null, navOptions);
                break;
            case R.id.goToProfile:
                setActiveMenu(2);
                navController.navigate(R.id.fragmentProfile, null);
                break;
            case R.id.goToRecipe:
                setActiveMenu(1);
                navController.navigate(R.id.fragmentRecipes, null);
                break;
        }
    }

    public void setActiveMenu(int index) {
        TabBarItem item;
        if (index != -1)
            item = TAB_BAR_LIST.get(index);
        else
            item = null;

        for (int i = 0; i < TAB_BAR_LIST.size(); i++) {
            TabBarItem itemNotActive = TAB_BAR_LIST.get(i);
            if (i != index) {
                ((TextView) findViewById(itemNotActive.getTextViewID())).setTextColor(getResources().getColor(R.color.tab_active_no_active));
                ((ImageView) findViewById(itemNotActive.getImageViewID())).setImageDrawable(getResources().getDrawable(itemNotActive.getImageNotActiveCode()));
            }
        }

        if (index != -1) {
            ((TextView) findViewById(item.getTextViewID())).setTextColor(getResources().getColor(R.color.tab_active));
            ((ImageView) findViewById(item.getImageViewID())).setImageDrawable(getResources().getDrawable(item.getImageActiveCode()));
        }
    }
}