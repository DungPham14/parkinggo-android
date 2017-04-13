/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.authentication.sign_up;

import javax.inject.Inject;

import parkinggo.com.R;
import parkinggo.com.constants.Constants;
import parkinggo.com.data.DataManager;
import parkinggo.com.data.model.User;
import parkinggo.com.data.model.UserConf;
import parkinggo.com.ui.base.BasePresenter;
import retrofit2.Retrofit;


public class SignUpPresenter extends BasePresenter<SignUpMvpView> {

    UserConf userConf;

    @Inject
    public SignUpPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SignUpMvpView mvpView) {
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

    public void loadUserType() {
        getMvpView().showUserType(mDataManager.getDatabaseManager().getUserConfs());
    }

    public void setUserType(UserConf userConf) {
        this.userConf = userConf;
        int strResourceId = R.string.action_send;
        if (userConf.getType() == Constants.USER) {
            strResourceId = R.string.action_send;
        } else if (userConf.getType().equalsIgnoreCase(Constants.DRIVER)
                || userConf.getType().equalsIgnoreCase(Constants.RENTER)) {
            strResourceId = R.string.action_next;
        }
        getMvpView().changeButtonText(strResourceId);
    }

    public void implementSend(User user){
        if (userConf.getType() == Constants.USER) {

        } else if (userConf.getType().equalsIgnoreCase(Constants.DRIVER)
                || userConf.getType().equalsIgnoreCase(Constants.RENTER)) {
        }
    }
}
