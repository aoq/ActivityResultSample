package com.aokyu.dev.sample.result;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public final class IntermediateFragment extends Fragment {

    /* package */ static final String TAG = IntermediateFragment.class.getSimpleName();

    /* package */ static final int FRAGMENT_REQUEST_ITEM = 0x00000010;

    private Context mContext;
    private ResultListener mListener;

    public IntermediateFragment() {
        super();
    }

    public static IntermediateFragment newInstance() {
        IntermediateFragment fragment = new IntermediateFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity != null) {
            mContext = activity.getApplicationContext();
        }

        if (activity instanceof ResultListener) {
            mListener = (ResultListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startMockActivity();
    }

    private void startMockActivity() {
        Intent intent = new Intent(mContext, MockActivity.class);
        startActivityForResult(intent, FRAGMENT_REQUEST_ITEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        dispatchResult(requestCode, resultCode, data);
    }

    private void dispatchResult(int reqeustCode, int resultCode, Intent data) {
        if (mListener != null) {
            mListener.onResult(reqeustCode, resultCode, data);
        }
    }

    public interface ResultListener {
        public void onResult(int requestCode, int resultCode, Intent data);
    }
}
