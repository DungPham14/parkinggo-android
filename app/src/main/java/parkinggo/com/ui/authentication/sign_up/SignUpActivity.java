package parkinggo.com.ui.authentication.sign_up;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmResults;
import parkinggo.com.R;
import parkinggo.com.adapter.authentication.UserTypeAdapter;
import parkinggo.com.data.model.User;
import parkinggo.com.data.model.UserConf;
import parkinggo.com.ui.base.BaseActivityWithDailog;
import parkinggo.com.util.StringHelper;

public class SignUpActivity extends BaseActivityWithDailog implements SignUpMvpView, AdapterView.OnItemSelectedListener {

    private final int REQUEST_CAMERA = 100;
    private final int REQUEST_GALLERY = 200;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
    @BindView(R.id.img_avatar)
    ImageView imgAvatar;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.edt_first_name)
    EditText edtFirstName;
    @BindView(R.id.edt_last_name)
    EditText edtLastName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_password)
    EditText edtPass;

    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.btn_sign_in)
    Button btnSignUp;

    UserTypeAdapter adapter;

    @Inject
    SignUpPresenter presenter;

    MaterialDialog materialDialog;

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

    @OnClick({R.id.img_avatar, R.id.img_delete, R.id.btn_sign_up})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_avatar:
                showPictureDialog();
                break;
            case R.id.img_delete:
                imgDelete.setVisibility(View.INVISIBLE);
                imgAvatar.setImageBitmap(null);
                imgAvatar.setImageResource(R.drawable.ic_img_default);
                break;
            case R.id.btn_send:
                // TODO validate
                String firstName = edtFirstName.getText().toString();
                String lastName = edtLastName.getText().toString();
                String email = edtEmail.getText().toString();
                String phone = edtPhone.getText().toString();
                String password = edtPass.getText().toString();
                if(StringHelper.isEmpty(firstName.trim())){
                    edtFirstName.requestFocus();
                    edtFirstName.setError(getString(R.string.error_field_required));
                    return;
                }
                User user = new User();
                presenter.implementSend(user);
                break;
            default:
                break;
        }
    }

    private void showPictureDialog() {
        if (materialDialog == null) {
            materialDialog = new MaterialDialog.Builder(this)
                    .theme(Theme.LIGHT)
                    .title(R.string.photo_dialog_title)
                    .items(R.array.photos)
                    .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                        @Override
                        public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                            switch (which) {
                                case 0:
                                    takePicture();
                                    break;
                                case 1:
                                    pickPhoto();
                                    break;
                                default:
                                    break;
                            }
                            return true;
                        }
                    })
                    .positiveText(R.string.action_ok)
                    .build();
        }
        materialDialog.show();
    }

    private void takePicture() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, REQUEST_CAMERA);
    }

    private void pickPhoto() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, REQUEST_GALLERY);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgAvatar.setImageBitmap(bitmap);
                imgDelete.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
