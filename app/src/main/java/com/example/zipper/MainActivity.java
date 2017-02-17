package com.example.zipper;

import com.example.zipper.animations.AnimationsFragment;
import com.example.zipper.customview.CustomViewFragment;
import com.example.zipper.mod.ModsFragment;
import com.example.zipper.recyclerview.RecyclerViewFragment;
import com.example.zipper.textview.TextViewFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class MainActivity extends AppCompatActivity {

    private Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Adapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(), this));

        // Pages in main view
        List<MyViewPagerAdapter.Page> pages = new ArrayList<>();
        pages.add(newPage(RecyclerViewFragment.class.getName(), "RecyclerView"));
        pages.add(newPage(TextViewFragment.class.getName(), "TextView"));
        pages.add(newPage(CustomViewFragment.class.getName(), "CustomView"));
        pages.add(newPage(AnimationsFragment.class.getName(), "Animations"));
        pages.add(newPage(ModsFragment.class.getName(), "Mods"));
        MainViewModel model = new MainViewModel(pages);

        binding = Zipper.bind(model, this);
    }

    private MyViewPagerAdapter.Page newPage(String name, String title) {
        return new MyViewPagerAdapter.Page(name, title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unBind();
    }
}
