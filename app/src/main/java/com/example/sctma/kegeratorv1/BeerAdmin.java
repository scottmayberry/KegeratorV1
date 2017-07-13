package com.example.sctma.kegeratorv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class BeerAdmin extends AppCompatActivity {

    private CardView keg1;
    private CardView keg2;

    private View.OnClickListener kegLis = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), EditKegActivity.class);
            intent.putExtra("KegPos", Integer.parseInt(v.getTag().toString()));
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_admin);

        keg1 = (CardView)findViewById(R.id.keg1CardView);
        keg2 = (CardView)findViewById(R.id.keg2CardView);

        keg1.setTag(0);
        keg2.setTag(1);

        keg1.setOnClickListener(kegLis);
        keg2.setOnClickListener(kegLis);
    }
}
