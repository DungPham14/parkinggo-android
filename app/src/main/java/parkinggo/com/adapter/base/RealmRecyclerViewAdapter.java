/*
 * ******************************************************************************
 *  Copyright Ⓒ 2016. All rights reserved
 *  Author TrinhQuan. Create on 2016/8/24
 * ******************************************************************************
 */

package parkinggo.com.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import parkinggo.com.util.exception.RangeException;

public abstract class RealmRecyclerViewAdapter<VH extends RealmRecyclerViewAdapter.RealmViewHolder<DATA>,
        DATA extends RealmObject> extends CallbackableAdapter<VH, DATA> {

    private final RealmWrapper<DATA> mRealmWrapper;

    public RealmRecyclerViewAdapter(Context context) {
        super(context);
        mRealmWrapper = new RealmWrapper<>();
        mRealmWrapper.setListener(element -> notifyDataSetChanged());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRealmWrapper.registerListener();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRealmWrapper.releaseListener();
    }

    @Override
    public void release() {
        mRealmWrapper.release();
        super.release();
    }

    @Override
    public int getItemCount() {
        return mRealmWrapper.size();
    }

    public DATA getItemAtPosition(int position) {
        if (position < 0 || position >= getItemCount()) {
            throw new RangeException();
        }
        return mRealmWrapper.get(position);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        DATA data = getItemAtPosition(position);
        holder.bindData(data, position);
    }

    public void setData(RealmList<DATA> realmList) {
        mRealmWrapper.setData(realmList);
        notifyDataSetChanged();
    }

    public void setData(RealmResults<DATA> realmResults) {
        mRealmWrapper.setData(realmResults);
        notifyDataSetChanged();
    }

    public void appendData(RealmList<DATA> realmList) {
        mRealmWrapper.appendData(realmList);
        notifyDataSetChanged();
    }

    public void appendData(RealmResults<DATA> realmResults) {
        mRealmWrapper.appendData(realmResults);
        notifyDataSetChanged();
    }

    public static abstract class RealmViewHolder<DATA extends RealmObject>
            extends BaseViewHolder<DATA> {

        public RealmViewHolder(View itemView) {
            super(itemView);
        }
    }
}
