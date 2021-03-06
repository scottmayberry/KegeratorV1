package com.example.sctma.kegeratorv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.sctma.kegeratorv1.Util.kegInfo;

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

        keg1 = (CardView) findViewById(R.id.keg1CardView);
        keg2 = (CardView) findViewById(R.id.keg2CardView);

        keg1.setTag(0);
        keg2.setTag(1);


        if (!kegInfo[0].isActive())
            setKeg1Visibility(true);
        else
            setKeg1Visibility(false);
        if (!kegInfo[1].isActive())
            setKeg2Visibility(true);
        else
            setKeg2Visibility(false);
        keg1.setOnClickListener(kegLis);
        keg2.setOnClickListener(kegLis);
        setKeg1Info();
        setKeg2Info();
    }

    public void setKeg1Info() {
        ((TextView)findViewById(R.id.name1Text)).setText(kegInfo[0].getName());
        ((TextView)findViewById(R.id.style1Text)).setText(kegInfo[0].getStyle());
        ((TextView)findViewById(R.id.beersLeft1Text)).setText(kegInfo[0].getRoundedBeersLeft());
        ((TextView)findViewById(R.id.perOunce1Text)).setText(kegInfo[0].getRoundedPricePerOunce());
        ((TextView)findViewById(R.id.perBeer1Text)).setText(kegInfo[0].getRoundedPricePerBeer());
    }//set keg 1 info
    public void setKeg2Info() {
        ((TextView)findViewById(R.id.name2Text)).setText(kegInfo[1].getName());
        ((TextView)findViewById(R.id.style2Text)).setText(kegInfo[1].getStyle());
        ((TextView)findViewById(R.id.beersLeft2Text)).setText(kegInfo[1].getRoundedBeersLeft());
        ((TextView)findViewById(R.id.perOunce2Text)).setText(kegInfo[1].getRoundedPricePerOunce());
        ((TextView)findViewById(R.id.perBeer2Text)).setText(kegInfo[1].getRoundedPricePerBeer());
    }//set keg 2 info
    public void setKeg1Visibility(boolean b){

        int vis1;
        int vis2;
        if(b) {
            vis1 = View.VISIBLE;
            vis2 = View.GONE;
        }
        else
        {
            vis1 = View.GONE;
            vis2 = View.VISIBLE;
        }
        findViewById(R.id.inactive1Text).setVisibility(vis1);
        findViewById(R.id.keg1Image).setVisibility(vis2);
        findViewById(R.id.name1TitleText).setVisibility(vis2);
        findViewById(R.id.name1Text).setVisibility(vis2);
        findViewById(R.id.style1TitleText).setVisibility(vis2);
        findViewById(R.id.style1Text).setVisibility(vis2);
        findViewById(R.id.beersLeft1TitleText).setVisibility(vis2);
        findViewById(R.id.beersLeft1Text).setVisibility(vis2);
        findViewById(R.id.perOunce1TitleText).setVisibility(vis2);
        findViewById(R.id.perBeer1TitleText).setVisibility(vis2);
        findViewById(R.id.style1TitleText).setVisibility(vis2);
        findViewById(R.id.perBeer1Text).setVisibility(vis2);
        findViewById(R.id.perOunce1Text).setVisibility(vis2);
    }//keg1 visibility
    public void setKeg2Visibility(boolean b)
    {
        int vis1;
        int vis2;
        if(b) {
            vis1 = View.VISIBLE;
            vis2 = View.GONE;
        }
        else
        {
            vis1 = View.GONE;
            vis2 = View.VISIBLE;
        }
        findViewById(R.id.inactive2Text).setVisibility(vis1);
        findViewById(R.id.keg2Image).setVisibility(vis2);
        findViewById(R.id.name2TitleText).setVisibility(vis2);
        findViewById(R.id.name2Text).setVisibility(vis2);
        findViewById(R.id.style2TitleText).setVisibility(vis2);
        findViewById(R.id.style2Text).setVisibility(vis2);
        findViewById(R.id.beersLeft2TitleText).setVisibility(vis2);
        findViewById(R.id.beersLeft2Text).setVisibility(vis2);
        findViewById(R.id.perOunce2TitleText).setVisibility(vis2);
        findViewById(R.id.perBeer2TitleText).setVisibility(vis2);
        findViewById(R.id.style2TitleText).setVisibility(vis2);
        findViewById(R.id.perBeer2Text).setVisibility(vis2);
        findViewById(R.id.perOunce2Text).setVisibility(vis2);
    }
}
