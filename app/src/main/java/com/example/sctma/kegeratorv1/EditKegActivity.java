package com.example.sctma.kegeratorv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class EditKegActivity extends AppCompatActivity {

    Spinner kegSize;
    private int kegPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_keg);

        kegPos = getIntent().getIntExtra("KegPos", 0);

        kegSize = (Spinner) findViewById(R.id.kegSizeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kegSize_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        kegSize.setAdapter(adapter);
    }
}
