package com.aokyu.dev.sample.result;

import com.aokyu.dev.sample.result.IntermediateFragment.ResultListener;
import com.aokyu.dev.sample.result.MockActivity.ResultKey;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends Activity implements ResultListener {

    private TextView mResultView;
    private Button mStartButton;
    private Button mStartAltButton;

    /* package */ static final int REQUEST_ITEM = 0x00000001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_screen);

        setupViews();
    }

    private void setupViews() {
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startMockActivity();
            }
        });
        mStartAltButton = (Button) findViewById(R.id.start_alt_button);
        mStartAltButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFromIntermediate();
            }
        });
        mResultView = (TextView) findViewById(R.id.result_view);
    }

    private void startActivityFromIntermediate() {
        FragmentManager manager = getFragmentManager();

        Fragment fragment = manager.findFragmentByTag(IntermediateFragment.TAG);
        if (fragment == null) {
            fragment = IntermediateFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(fragment, IntermediateFragment.TAG);
        transaction.commit();
    }

    private void removeIntermediate() {
        FragmentManager manager = getFragmentManager();

        Fragment fragment = manager.findFragmentByTag(IntermediateFragment.TAG);
        if (fragment != null) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    private void startMockActivity() {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, MockActivity.class);
        startActivityForResult(intent, REQUEST_ITEM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_ITEM:
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                showResult(extras);
            } else {
                showCancel();
            }
            break;
        }

        // For fragments.
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showResult(Bundle bundle) {
        String entry = bundle.getString(ResultKey.ENTRY_NUMBER);
        if (entry != null) {
            mResultView.setText(entry);
        } else {
            showError();
        }
    }

    private void showCancel() {
        Resources res = getResources();
        String message = res.getString(R.string.cancel_message);
        mResultView.setText(message);
    }

    private void showError() {
        Resources res = getResources();
        String message = res.getString(R.string.error_message);
        mResultView.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.result, menu);
        return true;
    }



    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case IntermediateFragment.FRAGMENT_REQUEST_ITEM:
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                showResult(extras);
            } else {
                showCancel();
            }
            break;
        }
        removeIntermediate();
    }

}
