/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.data.DataManager;
import parkinggo.com.ui.base.BasePresenter;
import retrofit2.Retrofit;


public class MainPresenter extends BasePresenter<MainMvpView> implements
        NavigationView.OnNavigationItemSelectedListener {

    @Inject
    public MainPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        getMvpView().OnItemMenuClick();
        switch (id) {
            case R.id.nav_home:
                getMvpView().navigateHome();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void initialView(MainMvpView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
