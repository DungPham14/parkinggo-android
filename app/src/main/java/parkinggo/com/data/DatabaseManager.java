/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 29/03/2017.
 * ******************************************************************************
 */
package parkinggo.com.data;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import parkinggo.com.data.model.CarTypeConf;
import parkinggo.com.data.model.CurrencyConf;
import parkinggo.com.data.model.FeeTypeConf;
import parkinggo.com.data.model.GlobalConf;
import parkinggo.com.data.model.ParkingTypeConf;
import parkinggo.com.data.model.SlotConf;
import parkinggo.com.data.model.SocialConf;
import parkinggo.com.data.model.Token;
import parkinggo.com.data.model.User;
import parkinggo.com.data.model.UserConf;
import parkinggo.com.data.realm.CarTypeConfRepository;
import parkinggo.com.data.realm.CurrencyConfRepository;
import parkinggo.com.data.realm.FeeTypeConfigRepository;
import parkinggo.com.data.realm.GlobalConfRepository;
import parkinggo.com.data.realm.ParkingTypeConfRepository;
import parkinggo.com.data.realm.SlotConfRepository;
import parkinggo.com.data.realm.SocialConfRepository;
import parkinggo.com.data.realm.TokenRepository;
import parkinggo.com.data.realm.UserConfRepository;
import parkinggo.com.data.realm.UserRepository;

public class DatabaseManager {
    private final Realm mRealm;

    private CarTypeConfRepository carTypeConfRepository;
    private CurrencyConfRepository currencyConfRepository;
    private FeeTypeConfigRepository feeTypeConfigRepository;
    private GlobalConfRepository globalConfRepository;
    private ParkingTypeConfRepository parkingTypeConfRepository;
    private SlotConfRepository slotConfRepository;
    private SocialConfRepository socialConfRepository;
    private UserConfRepository userConfRepository;
    private TokenRepository tokenRepository;
    private UserRepository userRepository;

    @Inject
    public DatabaseManager(Realm realm) {
        this.mRealm = realm;
    }

    public void saveConfig(GlobalConf globalConf) {
        saveUserConf(globalConf.getUserConfs());
        saveCarTypeConf(globalConf.getCarTypeConfs());
        saveCurrencyConf(globalConf.getCurrencyConfs());
        saveFeeTypeConf(globalConf.getFeeTypeConfs());
        saveParkingTypeConf(globalConf.getParkingTypeConfs());
        saveSlotConf(globalConf.getSlotConfs());
        saveSocialConf(globalConf.getSocialConfs());
    }

    private void saveSocialConf(RealmList<SocialConf> socialConfs) {
        if (socialConfRepository == null) {
            socialConfRepository = new SocialConfRepository(mRealm);
        }
        socialConfRepository.saveSocialConf(socialConfs);
    }

    /**
     * User config
     */

    private void saveUserConf(RealmList<UserConf> userConfs) {
        if (userConfRepository == null) {
            userConfRepository = new UserConfRepository(mRealm);
        }
        userConfRepository.saveUserConf(userConfs);
    }

    public RealmResults<UserConf> getUserConfs(){
        if (userConfRepository == null) {
            userConfRepository = new UserConfRepository(mRealm);
        }
        return userConfRepository.getUserConfs();
    }

    /**
     * End user config
     */

    private void saveSlotConf(RealmList<SlotConf> slotConfs) {
        if (slotConfRepository == null) {
            slotConfRepository = new SlotConfRepository(mRealm);
        }
        slotConfRepository.saveSlotConf(slotConfs);
    }

    private void saveParkingTypeConf(RealmList<ParkingTypeConf> parkingTypeConfs) {
        if (parkingTypeConfRepository == null) {
            parkingTypeConfRepository = new ParkingTypeConfRepository(mRealm);
        }
        parkingTypeConfRepository.saveParkingTypeConf(parkingTypeConfs);
    }

    private void saveFeeTypeConf(RealmList<FeeTypeConf> feeTypeConfs) {
        if (feeTypeConfigRepository == null) {
            feeTypeConfigRepository = new FeeTypeConfigRepository(mRealm);
        }
        feeTypeConfigRepository.saveFreeTypeConfig(feeTypeConfs);
    }

    private void saveCurrencyConf(RealmList<CurrencyConf> currencyConfs) {
        if (currencyConfRepository == null) {
            currencyConfRepository = new CurrencyConfRepository(mRealm);
        }
        currencyConfRepository.saveCurrencyConf(currencyConfs);
    }

    private void saveCarTypeConf(RealmList<CarTypeConf> carTypeConfs) {
        if (carTypeConfRepository == null) {
            carTypeConfRepository = new CarTypeConfRepository(mRealm);
        }
        carTypeConfRepository.saveCarTypeConfig(carTypeConfs);
    }

    public boolean saveToken(Token token) {
        if (tokenRepository == null) {
            tokenRepository = new TokenRepository(mRealm);
        }
        return tokenRepository.save(token);
    }

    public Token getToken(){
        if (tokenRepository == null) {
            tokenRepository = new TokenRepository(mRealm);
        }
        return tokenRepository.getToken();
    }

    public boolean saveUser(User user) {
        if (userRepository == null) {
            userRepository = new UserRepository(mRealm);
        }
        return userRepository.save(user);
    }
}
