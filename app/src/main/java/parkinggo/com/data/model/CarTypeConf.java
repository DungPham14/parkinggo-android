package parkinggo.com.data.model;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class CarTypeConf extends RealmObject {
    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("value")
    @Expose
    private int value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String itSelf = gson.toJson(this);
        return itSelf;
    }
}
