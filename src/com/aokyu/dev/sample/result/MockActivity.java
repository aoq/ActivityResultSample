package com.aokyu.dev.sample.result;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MockActivity extends Activity {

    public final class ResultKey {
        public static final String ENTRY_NUMBER = "key_entry_number";

        private ResultKey() {}
    }

    private ListView mMockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_screen);

        setupViews();
    }

    private void setupViews() {
        mMockView = (ListView) findViewById(R.id.mock_view);
        mMockView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String result = String.valueOf(position + 1);
                Bundle extras = new Bundle();
                extras.putString(ResultKey.ENTRY_NUMBER, result);
                Intent intent = new Intent();
                intent.putExtras(extras);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
