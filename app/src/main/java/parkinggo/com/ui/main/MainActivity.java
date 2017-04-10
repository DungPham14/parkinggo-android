package parkinggo.com.ui.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import butterknife.BindView;
import parkinggo.com.R;
import parkinggo.com.ui.base.BaseActivityWithDailog;
import parkinggo.com.ui.main.fragment.HomeFragment;
import parkinggo.com.ui.main.interfaces.OnDrawerLayoutListener;
import parkinggo.com.util.FragmentUtil;

public class MainActivity extends BaseActivityWithDailog implements MainMvpView, OnDrawerLayoutListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @Inject
    MainPresenter presenter;
    private boolean isHome = true;

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        presenter.initialView(this);
        initial();
        addHomeFragment();
    }

    public void initial() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(presenter);
    }

    @Override
    public void OnItemMenuClick() {
        drawer.closeDrawer(GravityCompat.START);
        isHome = FragmentUtil.getCurrentFragment(this, R.id.frame_content) instanceof HomeFragment;
    }

    @Override
    public void navigateHome() {
        addHomeFragment();
    }

    private void addHomeFragment() {
        toolbar.setVisibility(View.GONE);
        FragmentUtil.replaceFragmentWithoutBackStack(getSupportFragmentManager(),
                HomeFragment.getInstance(), R.id.frame_content);
    }

    @Override
    public void onMenuClickListener() {
        drawer.openDrawer(GravityCompat.START);
    }

    public void displayToolBar() {
        toolbar.setVisibility(!isHome ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        super.onBackPressed();
    }
}
