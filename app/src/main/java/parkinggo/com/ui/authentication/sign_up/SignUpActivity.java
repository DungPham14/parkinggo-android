package parkinggo.com.ui.authentication.sign_up;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.RealmResults;
import parkinggo.com.R;
import parkinggo.com.adapter.authentication.UserTypeAdapter;
import parkinggo.com.data.model.UserConf;
import parkinggo.com.ui.base.BaseActivityWithDailog;

public class SignUpActivity extends BaseActivityWithDailog implements SignUpMvpView, AdapterView.OnItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btn_sign_in)
    Button btnSignUp;

    UserTypeAdapter adapter;

    @Inject
    SignUpPresenter presenter;

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        initialToolbar();
        presenter.initialView(this);
        presenter.loadUserType();
    }

    private void initialToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvTitle.setText(getString(R.string.sign_up_title));
    }

    @Override
    public void showUserType(RealmResults<UserConf> userConfs) {
        adapter = new UserTypeAdapter(this, R.layout.item_multiple_choice, userConfs);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void changeButtonText(@StringRes int strId) {
        btnSignUp.setText(getString(strId));
    }

    @Override
    public void navigateSignUpDriver() {

    }

    @Override
    public void navigateSignRenter() {

    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.setUserType(adapter.getItem(position));
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
