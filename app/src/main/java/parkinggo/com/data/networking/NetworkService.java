/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 2017/03/20
 * ******************************************************************************
 */
package parkinggo.com.data.networking;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.realm.annotations.Ignore;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import parkinggo.com.BuildConfig;
import parkinggo.com.constants.Constants;
import parkinggo.com.data.model.GlobalConfig;
import parkinggo.com.data.model.LoginResponse;
import parkinggo.com.data.model.SignUpResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface NetworkService {

    @GET("_constants")
    Observable<GlobalConfig> getConfig();

    @FormUrlEncoded
    @POST("user/register")
    Observable<SignUpResponse> signUp(@FieldMap Map<String, String> signUpField, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    class Creator {
        public static Retrofit newRetrofitInstance() {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.retryOnConnectionFailure(false);
            okBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.header(Constants.HTTP_API_VERSION_HEADER_KEY, BuildConfig.VERSION_NAME)
                        .header(Constants.UER_AGENT_HEADER_KEY, Constants.UER_AGENT_HEADER_VALUE)
                        .header("Connection", "close")
                        .method(original.method(), original.body());

                return chain.proceed(builder.build());
            });
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okBuilder.addInterceptor(interceptor);
            }
            Gson internalGson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            Gson gson = new GsonBuilder()
                    .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getAnnotation(Ignore.class) != null;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuilder.build())
                    .build();
        }
    }
}
