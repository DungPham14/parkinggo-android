/*
 * ******************************************************************************
 *  Copyright Ⓒ 2017. All rights reserved
 *  Author HoanDC. Create on 12/04/2017.
 * ******************************************************************************
 */
package parkinggo.com.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import parkinggo.com.R;

public class ClearTextEditText extends AppCompatEditText implements View.OnTouchListener, View.OnFocusChangeListener,
        TextWatcherAdapter.TextWatcherListener {

    private Drawable xD;
    private OnClearTextListener listener;
    private OnTouchListener l;
    private OnFocusChangeListener f;
    private int mClearIconResource = R.drawable.ic_edittext_clear;

    public ClearTextEditText(Context context) {
        this(context, null);
    }

    public ClearTextEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearTextEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnClearListener(OnClearTextListener listener) {
        this.listener = listener;
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        this.l = l;
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener f) {
        this.f = f;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (getCompoundDrawables()[2] != null) {
            boolean tappedX = event.getX() > (getWidth() - getPaddingRight() - xD.getIntrinsicWidth());
            if (tappedX) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setText("");
                    if (listener != null) {
                        listener.didClearText();
                    }
                }
                return true;
            }
        }
        return l != null && l.onTouch(v, event);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            resetError();
            setClearIconVisible(isNotEmpty(String.valueOf(getText())));
        } else {
            setClearIconVisible(false);
        }
        if (f != null) {
            f.onFocusChange(v, hasFocus);
        }
    }

    private boolean isNotEmpty(String text) {
        return text.length() > 0;
    }

    @Override
    public void onTextChanged(EditText view, String text) {
        if (isFocused()) {
            resetError();
            setClearIconVisible(isNotEmpty(text));
        }
    }

    @SuppressWarnings("deprecation")
    private void init() {
        if (isInEditMode())
            return;
        xD = getCompoundDrawables()[2];
        if (xD == null) {
            xD = getResources().getDrawable(mClearIconResource);
        }
        assert xD != null;
        xD.setBounds(0, 0, xD.getIntrinsicWidth(), xD.getIntrinsicHeight());
        setClearIconVisible(false);
        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(new TextWatcherAdapter(this, this));
    }

    private void resetError() {
        if (getError() != null) {
            setError(null);
        }
    }

    protected void setClearIconVisible(boolean visible) {
        boolean wasVisible = (getCompoundDrawables()[2] != null);
        if (visible != wasVisible) {
            Drawable x = visible ? xD : null;
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], x, getCompoundDrawables()[3]);
        }
    }

    interface OnClearTextListener {
        void didClearText();
    }
}
