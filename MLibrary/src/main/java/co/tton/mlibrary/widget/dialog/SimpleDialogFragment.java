package co.tton.mlibrary.widget.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import co.tton.mlibrary.R;


/**
 * Created by Administrator on 2015/8/27.
 *
 */
public class SimpleDialogFragment extends DialogFragment {

    private static FragmentActivity fragmentActivity;
    private String msg = "";
    private DialogFragmentListener listener;
    private static SimpleDialogFragment mSimpleDialogFragment;

    private Button positive_btn, negative_btn;
    private TextView dialog_msg_tv;


    public static SimpleDialogFragment with(FragmentActivity fragmentActivity) {

        SimpleDialogFragment.fragmentActivity = fragmentActivity;

        if (mSimpleDialogFragment == null) {
            mSimpleDialogFragment = new SimpleDialogFragment();
        }

        return mSimpleDialogFragment;
    }

    public SimpleDialogFragment message(String msg) {

        if (fragmentActivity != null) {
            this.msg = msg;
        } else {

            throw new IllegalArgumentException("FragmentActivity must not be null.");
        }
        return mSimpleDialogFragment;
    }


    public SimpleDialogFragment message(int msgId) {

        if (fragmentActivity != null) {
            this.msg = fragmentActivity.getResources().getString(msgId);
        } else {

            throw new IllegalArgumentException("FragmentActivity must not be null.");
        }
        return mSimpleDialogFragment;
    }

    public SimpleDialogFragment attach(DialogFragmentListener listener) {

        if (fragmentActivity != null) {
            this.listener = listener;
        } else {

            throw new IllegalArgumentException("FragmentActivity must not be null.");
        }

        return mSimpleDialogFragment;
    }

    public void showDialog() {
        if (fragmentActivity != null) {
            mSimpleDialogFragment.show(fragmentActivity.getSupportFragmentManager(), msg);

        } else {

            throw new IllegalArgumentException("FragmentActivity must not be null.");
        }

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View v = inflater.inflate(R.layout.dialog_fragment_simple, null);

        dialog_msg_tv = (TextView) v.findViewById(R.id.dialog_msg_tv);
        dialog_msg_tv.setText(msg);

        negative_btn = (Button) v.findViewById(R.id.negative_btn);
        negative_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onNegative();
                }
                mSimpleDialogFragment.dismiss();
            }
        });


        positive_btn = (Button) v.findViewById(R.id.positive_btn);
        positive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) {
                    listener.onPositive();
                }
                mSimpleDialogFragment.dismiss();
            }
        });
        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
