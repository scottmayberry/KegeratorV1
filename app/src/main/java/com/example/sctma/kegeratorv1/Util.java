package com.example.sctma.kegeratorv1;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

import java.util.Hashtable;

/**
 * Created by SMAYBER8 on 7/14/2017.
 */

public class Util {
    public static final int ADMIN_REQUEST = 1;
    public static final int POUR_REQUEST = 2;
    public static final int USER_INFO_REQUEST = 3;
    public static final int CONTACT_INFO_REQUEST = 4;


    static DatabaseReference ref;
    static Hashtable<String, User> userHashTable;
    static Hashtable<String, RFID> rfidHashTable;
    static Hashtable<String, Balance> balanceHashTable;
    static KegInfo kegInfo[] = new KegInfo[2];

    static Context mContext;
}
