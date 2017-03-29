package parkinggo.com.ui.base;

import android.support.annotation.CallSuper;

import parkinggo.com.data.DataManager;
import parkinggo.com.data.model.ApiError;
import parkinggo.com.util.exception.HttpUtil;
import retrofit2.Retrofit;

public abstract class BasePresenter<V extends MvpView> implements Presenter<V> {

    private V mMvpView;

    protected final Retrofit mRetrofit;
    protected final DataManager mDataManager;

    public BasePresenter(Retrofit mRetrofit, DataManager mDataManager) {
        this.mRetrofit = mRetrofit;
        this.mDataManager = mDataManager;
    }

    @CallSuper
    @Override
    public void initialView(V mvpView) {
        mMvpView = mvpView;
    }

    @CallSuper
    @Override
    public void destroyView() {
        mMvpView = null;
    }

    public final boolean isInitialView() {
        return mMvpView != null;
    }

    public final V getMvpView() {
        return mMvpView;
    }

    @CallSuper
    @Override
    public void destroy() {

    }

    public void notifyNoNetwork() {
        if (isInitialView()) {
            getMvpView().showNoNetworkAlert();
        }
    }

    public boolean isConnectToInternet() {
        return !isInitialView() || getMvpView().isConnectToInternet();
    }

    public ApiError getErrorFromHttp(Throwable error){
        return HttpUtil.getError(error, mRetrofit);
    }

}
