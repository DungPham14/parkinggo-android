/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import java.util.Map;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import parkinggo.com.data.model.GlobalConfig;
import parkinggo.com.data.model.LoginResponse;
import parkinggo.com.data.model.SignUpResponse;
import parkinggo.com.data.networking.NetworkService;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import rx.Observable;

public class NetworkManager {
    private final NetworkService networkService;

    @Inject
    public NetworkManager(NetworkService networkService) {
        this.networkService = networkService;
    }


    public Observable<GlobalConfig> getConfig() {
        return networkService.getConfig();
    }

    public Observable<SignUpResponse> signUp(@FieldMap Map<String, String> signUpField,
                                             @Part MultipartBody.Part avatar) {
        return networkService.signUp(signUpField, avatar);
    }

    public Observable<LoginResponse> login(String userName, String password) {
        return networkService.login(userName, password);
    }
}