/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. All rights reserved
 *  Author TrinhQuan. Create on 2016/8/24
 * ******************************************************************************
 */

package parkinggo.com.data.convert;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class WrapperDeserializer<T> implements JsonDeserializer<T> {
    private final Gson mGson;

    public WrapperDeserializer(Gson internalGson) {
        mGson = internalGson;
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return mGson.fromJson(json, typeOfT);
        } catch (Exception e) {
            //
        }
        return null;
    }
}
