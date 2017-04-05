package parkinggo.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("data")
    @Expose
    AuthenData data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AuthenData getData() {
        return data;
    }

    public void setData(AuthenData data) {
        this.data = data;
    }
}
