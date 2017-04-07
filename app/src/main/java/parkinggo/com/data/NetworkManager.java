/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import parkinggo.com.data.model.GlobalConf;
import parkinggo.com.data.model.LoginResponse;
import parkinggo.com.data.model.SignUpResponse;
import parkinggo.com.data.model.User;
import parkinggo.com.data.networking.NetworkService;
import retrofit2.http.FieldMap;
import retrofit2.http.Part;
import rx.Observable;

public class NetworkManager {

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private final NetworkService networkService;

    @Inject
    public NetworkManager(NetworkService networkService) {
        this.networkService = networkService;
    }

    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }

    public Observable<GlobalConf> getConfig() {
        return networkService.getConfig();
    }

    public Observable<SignUpResponse> signUp(User user, File file) {
        // Basic info
        HashMap<String, RequestBody> map = new HashMap<>();
        RequestBody socialId = createRequestBody(user.getSocialId());
        RequestBody type = createRequestBody(user.getId());
        RequestBody socialType = createRequestBody(user.getId());
        RequestBody firstName = createRequestBody(user.getId());
        RequestBody lastName = createRequestBody(user.getId());
        RequestBody email = createRequestBody(user.getId());
        RequestBody username = createRequestBody(user.getId());
        RequestBody password = createRequestBody(user.getId());
        map.put("socialId", socialId);
        map.put("type", type);
        map.put("socialType", socialType);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("email", email);
        map.put("username", username);
        map.put("password", password);

        // Avatar
        HashMap<String, RequestBody> avatar = new HashMap<>();
        RequestBody avatarRequest = createFileRequestBody(file);
        avatar.put("avatar", avatarRequest);
        return networkService.signUp(map, avatar);
    }

    public Observable<LoginResponse> signIn(RequestBody userName, RequestBody password) {
        return networkService.signIn(userName, password);
    }

    public Observable<SignUpResponse> signUpBySocial(User user) {
        HashMap<String, RequestBody> map = new HashMap<>();
        RequestBody socialId = createRequestBody(user.getSocialId());
        RequestBody type = createRequestBody(user.getId());
        RequestBody socialType = createRequestBody(user.getId());
        RequestBody firstName = createRequestBody(user.getId());
        RequestBody lastName = createRequestBody(user.getId());
        RequestBody email = createRequestBody(user.getId());

        map.put("socialId", socialId);
        map.put("type", type);
        map.put("socialType", socialType);
        map.put("firstName", firstName);
        map.put("lastName", lastName);
        map.put("email", email);

        return networkService.signUpBySocial(map);
    }

    private RequestBody createRequestBody(@NonNull Object object) {
        String value = String.valueOf(object);
        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), value);
    }

    public static RequestBody createFileRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), file);
    }
}
