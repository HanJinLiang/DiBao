package com.hanjinliang.dibao.module.main;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
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
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.hanjinliang.dibao.R;
import com.hanjinliang.dibao.module.base.BaseListFragment;
import com.hanjinliang.dibao.module.memoire.MemoireBuildActivity;
import com.hanjinliang.dibao.module.post.ui.AddPicActivity;
import com.hanjinliang.dibao.module.post.ui.PostFragment;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.fake_status_bar)
    View fake_status_bar;
    @BindView(R.id.main_toolbar)
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
    List<String> mFragmentTitleList = Arrays.asList("圈子", "记事本");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //设置为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        initNavigationView();
        mFragmentArrayList.add(PostFragment.newInstance());
        mFragmentArrayList.add(PostFragment.newInstance());

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
        BarUtils.setStatusBarColor4Drawer(this,drawer,fake_status_bar, ContextCompat.getColor(this,R.color.colorAccent),100,false);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);//
    }

    /**
     * 初始化侧滑栏
     */
    private void initNavigationView() {
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView userName= headerLayout.findViewById(R.id.nav_userName);
        TextView userPhone=headerLayout.findViewById(R.id.nav_userPhone);
        if(BmobUser.getCurrentUser()!=null){
            userName.setText(BmobUser.getCurrentUser().getUsername());
            userPhone.setText(BmobUser.getCurrentUser().getMobilePhoneNumber());
        }
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
        contentView.findViewById(R.id.main_add_camera).setOnClickListener(addClickListener);
        contentView.findViewById(R.id.main_add_pick).setOnClickListener(addClickListener);
        contentView.findViewById(R.id.main_add_memoire).setOnClickListener(addClickListener);
    }

    class AddClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_add_camera://选择照片
                    PictureSelector.create(MainActivity.this).openCamera(PictureMimeType.ofImage()).forResult(0);
                    break;
                case R.id.main_add_pick://选择视频
                    AddPicActivity.launchActivity(MainActivity.this);
                    break;
                case R.id.main_add_memoire://新增备忘录
                    startActivity(new Intent(MainActivity.this, MemoireBuildActivity.class));
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
