package com.example.sctma.kegeratorv1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Hashtable;

import static com.example.sctma.kegeratorv1.Util.ADMIN_REQUEST;
import static com.example.sctma.kegeratorv1.Util.balanceHashTable;
import static com.example.sctma.kegeratorv1.Util.kegInfo;
import static com.example.sctma.kegeratorv1.Util.mContext;
import static com.example.sctma.kegeratorv1.Util.ref;
import static com.example.sctma.kegeratorv1.Util.rfidHashTable;
import static com.example.sctma.kegeratorv1.Util.userHashTable;

public class MainActivity extends AppCompatActivity {




    DropboxAPI<AndroidAuthSession> mApi;

    CardView keg1;
    CardView keg2;

    ChildEventListener userListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            User user = new User((String)dataSnapshot.child("name").getValue(),
                    (String) dataSnapshot.child("rfid").getValue(),
                    (String) dataSnapshot.child("username").getValue(),
                    (String) dataSnapshot.child("classification").getValue(),
                    (String) dataSnapshot.child("email").getValue());
            ref.child("Balances").child((String) dataSnapshot.child("username").getValue()).child("CurrentBalance").addChildEventListener(balanceListener);
            userHashTable.put(key, user);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            User user = userHashTable.get(dataSnapshot.getKey());
            user.setName((String)dataSnapshot.child("name").getValue());
            user.setRfid((String) dataSnapshot.child("rfid").getValue());
            user.setUsername((String) dataSnapshot.child("username").getValue());
            user.setClassification((String) dataSnapshot.child("classification").getValue());
            user.setEmail((String) dataSnapshot.child("email").getValue());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String username = userHashTable.remove(dataSnapshot.getKey()).getUsername();
            ref.child("Balances").child(username).child("CurrentBalance").removeEventListener(balanceListener);
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener rfidListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            rfidHashTable.put(key,
                    new RFID((String) dataSnapshot.child("rfid").getValue(),
                            (String) dataSnapshot.child("PushID").getValue()));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            RFID user = rfidHashTable.get(dataSnapshot.getKey());
            user.setRFID((String)dataSnapshot.child("rfid").getValue());
            user.setPushID((String) dataSnapshot.child("PushID").getValue());
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            rfidHashTable.remove(dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener balanceListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            balanceHashTable.put(dataSnapshot.getKey(), new Balance(getDoubleFromDatabase(dataSnapshot.child("balance").getValue())));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            Balance b = balanceHashTable.get(dataSnapshot.getKey());
            b.setBalance(getDoubleFromDatabase(dataSnapshot.child("balance").getValue()));
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            balanceHashTable.remove(dataSnapshot.getKey());
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    ChildEventListener kegListeners = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            int i = Integer.parseInt(dataSnapshot.getKey().toString());
            if(i > 1)
                return;
            kegInfo[i] = new KegInfo((String)dataSnapshot.child("Name").getValue(),
                    (String)dataSnapshot.child("KegSize").getValue(),
                    (String)dataSnapshot.child("Style").getValue(),
                    getDoubleFromDatabase(dataSnapshot.child("Spent").getValue()),
                    getDoubleFromDatabase(dataSnapshot.child("Fee").getValue()),
                    getDoubleFromDatabase(dataSnapshot.child("Saving").getValue()),
                    (String)dataSnapshot.child("Purchaser").getValue(),
                    (boolean)dataSnapshot.child("active").getValue());
            updateKegCardInfo(i);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            int i = Integer.parseInt(dataSnapshot.getKey().toString());
            if(i > 1)
                return;
            if(kegInfo[i] == null)
                return;
            kegInfo[i].setName((String)dataSnapshot.child("Name").getValue());
            kegInfo[i].setKegSize((String)dataSnapshot.child("KegSize").getValue());
            kegInfo[i].setStyle((String)dataSnapshot.child("Style").getValue());
            kegInfo[i].setSpent(getDoubleFromDatabase(dataSnapshot.child("Spent").getValue()));
            kegInfo[i].setFee(getDoubleFromDatabase(dataSnapshot.child("Fee").getValue()));
            kegInfo[i].setSavings(getDoubleFromDatabase(dataSnapshot.child("Saving").getValue()));
            kegInfo[i].setPurchaser((String)dataSnapshot.child("Purchaser").getValue());
            kegInfo[i].setActive((boolean)dataSnapshot.child("active").getValue());
            updateKegCardInfo(i);
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public void updateKegCardInfo(int i)
    {
        if(i == 0)
        {
            if(kegInfo[i] == null) {
                keg1.setVisibility(View.INVISIBLE);
                return;
            }
            if(kegInfo[i].isActive())
                keg1.setVisibility(View.VISIBLE);
            else
                keg1.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(kegInfo[i] == null) {
                keg2.setVisibility(View.INVISIBLE);
                return;
            }
            if(kegInfo[i].isActive())
                keg2.setVisibility(View.VISIBLE);
            else
                keg2.setVisibility(View.INVISIBLE);
        }
        int nT = getResources().getIdentifier("name" + (i+1) + "Text", "id", getPackageName());
        int sT = getResources().getIdentifier("style" + (i+1) + "Text", "id", getPackageName());
        int bL = getResources().getIdentifier("beersLeft" + (i+1) + "Text", "id", getPackageName());
        int pO = getResources().getIdentifier("perOunce" + (i+1) + "Text", "id", getPackageName());
        int pB = getResources().getIdentifier("perBeer" + (i+1) + "Text", "id", getPackageName());
        ((TextView) findViewById(nT)).setText(kegInfo[i].getName());
        ((TextView) findViewById(sT)).setText(kegInfo[i].getStyle());
        ((TextView) findViewById(bL)).setText(kegInfo[i].getRoundedBeersLeft());
        ((TextView) findViewById(pO)).setText(kegInfo[i].getRoundedPricePerOunce());
        ((TextView) findViewById(pB)).setText(kegInfo[i].getRoundedPricePerBeer());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //dropbox key
        //S6arYx_aWSAAAAAAAAAACu_x5KEdlFY7rgeMD2C78HZ5NxTp56vcbPUt22BWbvMU

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userHashTable = new Hashtable<>();
        rfidHashTable = new Hashtable<>();
        balanceHashTable = new Hashtable<>();

        mContext = getApplicationContext();

        // We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);

        //cardviews
        keg1 = (CardView)(findViewById(R.id.keg1CardView));
        keg2 = (CardView)(findViewById(R.id.keg2CardView));


        //click listeners for the buttons
        keg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on click listener to keg 1 screen
                Intent intent = new Intent(getApplicationContext(), PourActivity.class);
                startActivity(intent);
            }
        });
        keg2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //on click listener to keg 2 screen
                Intent intent = new Intent(getApplicationContext(), PourActivity.class);
                startActivity(intent);
            }
        });

        updateKegCardInfo(0);
        updateKegCardInfo(1);

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users").addChildEventListener(userListener);
        ref.child("RFID").addChildEventListener(rfidListener);
        ref.child("Kegs").addChildEventListener(kegListeners);

    }


    @Override
    protected void onResume(){
        super.onResume();
        AndroidAuthSession session = mApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();
            } catch (IllegalStateException e) {

            }
        }
    }//on resume

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }//on create options menu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menuAdmin:
                intent = new Intent(this, AdminTop.class);
                startActivityForResult(intent, ADMIN_REQUEST);
                return true;
            case R.id.menuContactInfo:
                intent = new Intent(this, ContactInfo.class);
                startActivity(intent);
                return true;
            case R.id.menuUserHistory:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }//onOptionsitemSelected

    private double getDoubleFromDatabase(Object o)
    {
        if(o.getClass().getName().toString().toLowerCase().equals("java.lang.long"))
            return ((Long)o).doubleValue();
        else
            //if(o.getClass().getName().toString().toLowerCase().equals("java.lang.double"))
            return ((Double)o).doubleValue();
    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(getString(R.string.APP_KEY),getString(R.string.APP_SECRET));
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        session.setOAuth2AccessToken(getString(R.string.APP_ACCESS_TOKEN));
        return session;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
