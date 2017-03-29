package parkinggo.com.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GlobalConfig extends RealmObject {
    @SerializedName("userConf")
    @Expose
    private RealmList<UserConf> userConfs;
    @SerializedName("socialConf")
    @Expose
    private RealmList<SocialConf> socialConfs;
    @SerializedName("currencyConf")
    @Expose
    private RealmList<CurrencyConf> currencyConfs;
    @SerializedName("carTypeConf")
    @Expose
    private RealmList<CarTypeConf> carTypeConfs;
    @SerializedName("feeTypeConf")
    @Expose
    private RealmList<FeeTypeConf> feeTypeConfs;
    @SerializedName("parkingTypeConf")
    @Expose
    private RealmList<ParkingTypeConf> parkingTypeConfs;
    @SerializedName("slotConf")
    @Expose
    private RealmList<SlotConf> slotConfs;

    public RealmList<UserConf> getUserConfs() {
        return userConfs;
    }

    public void setUserConfs(RealmList<UserConf> userConfs) {
        this.userConfs = userConfs;
    }

    public RealmList<SocialConf> getSocialConfs() {
        return socialConfs;
    }

    public void setSocialConfs(RealmList<SocialConf> socialConfs) {
        this.socialConfs = socialConfs;
    }

    public RealmList<CurrencyConf> getCurrencyConfs() {
        return currencyConfs;
    }

    public void setCurrencyConfs(RealmList<CurrencyConf> currencyConfs) {
        this.currencyConfs = currencyConfs;
    }

    public RealmList<CarTypeConf> getCarTypeConfs() {
        return carTypeConfs;
    }

    public void setCarTypeConfs(RealmList<CarTypeConf> carTypeConfs) {
        this.carTypeConfs = carTypeConfs;
    }

    public RealmList<FeeTypeConf> getFeeTypeConfs() {
        return feeTypeConfs;
    }

    public void setFeeTypeConfs(RealmList<FeeTypeConf> feeTypeConfs) {
        this.feeTypeConfs = feeTypeConfs;
    }

    public RealmList<ParkingTypeConf> getParkingTypeConfs() {
        return parkingTypeConfs;
    }

    public void setParkingTypeConfs(RealmList<ParkingTypeConf> parkingTypeConfs) {
        this.parkingTypeConfs = parkingTypeConfs;
    }

    public RealmList<SlotConf> getSlotConfs() {
        return slotConfs;
    }

    public void setSlotConfs(RealmList<SlotConf> slotConfs) {
        this.slotConfs = slotConfs;
    }
}
