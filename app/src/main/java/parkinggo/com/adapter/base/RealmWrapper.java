package parkinggo.com.adapter.base;

import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmWrapper<DATA extends RealmObject> implements Releasable {
    private RealmResults<DATA> realmResults;
    private RealmList<DATA> realmList;
    private RealmChangeListener<RealmResults<DATA>> mChangeListener;

    public RealmWrapper() {
    }

    public RealmWrapper(RealmResults<DATA> realmResults) {
        this.realmResults = realmResults;
        registerListener();
    }

    public RealmWrapper(RealmList<DATA> realmList) {
        this.realmList = realmList;
    }

    @Override
    public void release() {
        releaseListener();
        realmList = null;
        realmResults = null;
    }

    public void setData(RealmList<DATA> realmList) {
        releaseListener();
        realmResults = null;
        this.realmList = realmList;
    }

    public void setData(RealmResults<DATA> realmResults) {
        releaseListener();
        realmList = null;
        this.realmResults = realmResults;
        registerListener();
    }

    public void appendData(RealmList<DATA> realmList) {
        releaseListener();
        realmResults = null;
        for (DATA data : realmList) {
            this.realmList.add(data);
        }
    }

    public void appendData(RealmResults<DATA> realmResults) {
        releaseListener();
        realmList = null;
        for (DATA data : realmResults) {
            this.realmResults.add(data);
        }
        registerListener();
    }

    public void setListener(RealmChangeListener<RealmResults<DATA>> mChangeListener) {
        this.mChangeListener = mChangeListener;
        registerListener();
    }

    public void registerListener() {
        if (realmResults != null && mChangeListener != null) {
            realmResults.addChangeListener(mChangeListener);
        }
    }

    public void releaseListener() {
        if (realmResults != null && mChangeListener != null) {
            realmResults.removeChangeListener(mChangeListener);
        }
    }

    public int size() {
        if (realmResults != null) {
            return realmResults.size();
        }
        if (realmList != null) {
            return realmList.size();
        }
        return 0;
    }

    public DATA get(int position) {
        if (realmResults != null) {
            return realmResults.get(position);
        }
        if (realmList != null) {
            return realmList.get(position);
        }
        return null;
    }
}
