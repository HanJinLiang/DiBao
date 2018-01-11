package com.hanjinliang.dibao.module.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseListFragment;
import com.hanjinliang.dibao.module.post.ui.AddPicActivity;
import com.hanjinliang.dibao.module.post.ui.PostFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.mainViewPager)
    ViewPager mainViewPager;

    ArrayList<Fragment> mFragmentArrayList = new ArrayList<>();
    List<String> mFragmentTitleList = Arrays.asList("照片", "视频", "备忘录", "账本");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        for (int i = 0; i < mFragmentTitleList.size(); i++) {
            if(i==0){
                mFragmentArrayList.add(PostFragment.newInstance(AddPicActivity.TYPE_PIC));
            }else if(i==1){
                mFragmentArrayList.add(PostFragment.newInstance(AddPicActivity.TYPE_VIDEO));
            }else{
                mFragmentArrayList.add(BaseListFragment.newInstance(i + 1));
            }

        }

        mainViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentArrayList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentArrayList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        });

        tabLayout.setupWithViewPager(mainViewPager);
    }

    @OnClick({R.id.fab})
    public void onClick(View view) {
        openBottom();
    }
    BottomSheetDialog mBottomSheetDialog;
    /**
     * 弹出添加对话框
     */
    private void openBottom() {
        LinearLayout contentView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_main_add, null);

        mBottomSheetDialog= new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(contentView, new ViewGroup.LayoutParams(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight()));
        View parent = (View) contentView.getParent();
        parent.setBackgroundColor(Color.parseColor("#55000000"));
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        behavior.setPeekHeight(ScreenUtils.getScreenHeight());
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        parent.setLayoutParams(params);
        mBottomSheetDialog.show();

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mBottomSheetDialog.dismiss();
                return false;
            }
        });
        AddClickListener addClickListener = new AddClickListener();
        contentView.findViewById(R.id.main_add_pic).setOnClickListener(addClickListener);
        contentView.findViewById(R.id.main_add_video).setOnClickListener(addClickListener);
    }

    class AddClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_add_pic://选择照片
                    AddPicActivity.launchActivity(MainActivity.this,AddPicActivity.TYPE_PIC);
                    break;
                case R.id.main_add_video://选择视频
                    AddPicActivity.launchActivity(MainActivity.this,AddPicActivity.TYPE_VIDEO);
                    break;
            }
            mBottomSheetDialog.dismiss();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.onRequestPermissionsResult(this,requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
