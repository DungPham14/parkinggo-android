package parkinggo.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    public final static String ID = "id";

    @PrimaryKey
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("username")
    @Expose
    String userName;
    @Ignore
    @SerializedName("password")
    @Expose
    String password;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("is_active")
    @Expose
    boolean isActive;
    @SerializedName("is_locked")
    @Expose
    boolean isLocked;
    @SerializedName("first_name")
    @Expose
    String firstName;
    @SerializedName("last_name")
    @Expose
    String lastName;
    @SerializedName("type")
    @Expose
    int type;
    @SerializedName("social_type")
    @Expose
    int socialType;
    @SerializedName("social_id")
    @Expose
    String socialId;
    @SerializedName("avatar")
    @Expose
    String avatar;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSocialType() {
        return socialType;
    }

    public void setSocialType(int socialType) {
        this.socialType = socialType;
    }

    public String getSocialId() {
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
