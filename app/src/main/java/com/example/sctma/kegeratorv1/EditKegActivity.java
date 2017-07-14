package com.example.sctma.kegeratorv1;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static com.example.sctma.kegeratorv1.Util.kegInfo;
import static com.example.sctma.kegeratorv1.Util.ref;

public class EditKegActivity extends AppCompatActivity {

    Spinner kegSize;
    private int kegPos;
    private EditText nameEdit;
    private EditText style;
    private EditText costOfKeg;
    private EditText feeToPurchaser;
    private EditText savings;
    private EditText purchaserName;
    private TextView errorText;


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

        nameEdit = (EditText) findViewById(R.id.kegNameEditText);
        style = (EditText) findViewById(R.id.kegStyleEditText) ;
        costOfKeg = (EditText) findViewById(R.id.originalCostEditText);
        feeToPurchaser = (EditText) findViewById(R.id.feeEditText);
        savings = (EditText) findViewById(R.id.savingsEditText);
        purchaserName = (EditText) findViewById(R.id.purchaserNameEditText);
        errorText = (TextView) findViewById(R.id.errorText);

        fillOutKegInfo();
        setEditFieldsEnabled(false);

        if(!kegInfo[kegPos].isActive())
        {
            editMode(true);
        }//keg info
        else
            editMode(false);
    }
    public void editMode(boolean b)
    {
        setEditFieldsEnabled(b);
        if(b) {
            ((Button) findViewById(R.id.submitKegButton)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.cancelKegButton)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.editKegButton)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.finishKegButton)).setVisibility(View.GONE);
        }
        else
        {
            ((Button) findViewById(R.id.submitKegButton)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.cancelKegButton)).setVisibility(View.GONE);
            ((Button) findViewById(R.id.editKegButton)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.finishKegButton)).setVisibility(View.VISIBLE);
        }
    }
    public void fillOutKegInfo()
    {
        if(kegInfo[kegPos] == null)
            return;
        if(kegInfo[kegPos].isActive())
        {
            nameEdit.setText(kegInfo[kegPos].getName());
            style.setText(kegInfo[kegPos].getStyle());
            costOfKeg.setText("" + kegInfo[kegPos].getSpent());
            feeToPurchaser.setText("" + kegInfo[kegPos].getFee());
            purchaserName.setText(kegInfo[kegPos].getPurchaser());
            savings.setText("" + kegInfo[kegPos].getSavings());
            kegSize.setSelection(KegInfo.kegSizeToSpinnerPosition(kegInfo[kegPos].getKegSize()));
        }
    }//fill out keg info
    public void onEditPressed(View v)
    {
        editMode(true);
    }
    public void onFinishPressed(View v)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setTitle("Reset the beer count? Current beer count is " + kegInfo[kegPos].getRoundedBeersLeft() + ". Resetting the beer count will set the beer count at " + KegInfo.kegSizeToBeers(kegInfo[kegPos].getKegSize()) + ".");
        adb.setPositiveButton("Finish Keg Off", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ref.child("Kegs").child("" + kegPos).child("active").setValue(false);
                kegInfo[kegPos].setActive(false);
                Toast.makeText(getApplicationContext(), "Keg Finished Off", Toast.LENGTH_SHORT).show();
                finish();
            } });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            } });
        adb.show();
    }
    public void onCompletePressed(View v)
    {
        if(nameEdit.getText().toString().equals("") || style.getText().toString().equals("") || costOfKeg.getText().toString().equals("")
                || purchaserName.getText().toString().equals("") || feeToPurchaser.getText().toString().equals("")
                || savings.getText().toString().equals(""))
        {
            errorText.setVisibility(View.VISIBLE);
            return;
        }
        else {
            errorText.setVisibility(View.GONE);
            KegInfo nK = new KegInfo(nameEdit.getText().toString(),
                    kegSize.getSelectedItem().toString(),
                    style.getText().toString(),
                    Double.parseDouble(costOfKeg.getText().toString()),
                    Double.parseDouble(feeToPurchaser.getText().toString()),
                    Double.parseDouble(savings.getText().toString()),
                    purchaserName.getText().toString(),
                    true);
            final double beersLeft = kegInfo[kegPos].getBeersLeft();
            ref.child("Kegs").child("" + kegPos).child("Fee").setValue(nK.getFee());
            ref.child("Kegs").child("" + kegPos).child("KegSize").setValue(nK.getKegSize());
            ref.child("Kegs").child("" + kegPos).child("Name").setValue(nK.getName());
            ref.child("Kegs").child("" + kegPos).child("Purchaser").setValue(nK.getPurchaser());
            ref.child("Kegs").child("" + kegPos).child("Saving").setValue(nK.getSavings());
            ref.child("Kegs").child("" + kegPos).child("Spent").setValue(nK.getSpent());
            ref.child("Kegs").child("" + kegPos).child("Style").setValue(nK.getStyle());
            ref.child("Kegs").child("" + kegPos).child("active").setValue(nK.isActive());
            //ref.child("Kegs").child("" + 2).setValue(nK);
            kegInfo[kegPos] = nK;
            if(kegInfo[kegPos].isActive())
            {
                AlertDialog.Builder adb = new AlertDialog.Builder(this);
                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.setTitle("Reset the beer count? Current beer count is " + kegInfo[kegPos].getRoundedBeersLeft() + ". Resetting the beer count will set the beer count at " + KegInfo.kegSizeToBeers(kegInfo[kegPos].getKegSize()) + ".");
                adb.setPositiveButton("Reset Beer Count", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Beer Count Reset", Toast.LENGTH_SHORT).show();
                        finish();
                    } });
                adb.setNegativeButton("Keep Beer Count", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        kegInfo[kegPos].setBeersLeft(beersLeft);
                        Toast.makeText(getApplicationContext(), "Beer Count Kept", Toast.LENGTH_SHORT).show();
                        finish();
                    } });
                adb.show();
            }

        }
    }
    public void onCancelPressed(View v)
    {
        if(kegInfo[kegPos].isActive()) {
            errorText.setVisibility(View.GONE);
            fillOutKegInfo();
            editMode(false);
        }
        else
            finish();
    }
    public void setEditFieldsEnabled(boolean b)
    {
        nameEdit.setEnabled(b);
        style.setEnabled(b);
        costOfKeg.setEnabled(b);
        feeToPurchaser.setEnabled(b);
        savings.setEnabled(b);
        purchaserName.setEnabled(b);
        kegSize.setEnabled(b);
    }

}
