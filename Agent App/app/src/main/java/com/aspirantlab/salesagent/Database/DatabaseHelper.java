package com.aspirantlab.salesagent.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "BRiteDB";
    private static final int DATABASE_VERSION =10;
    private static final String TABLE_PRODUCTS = "productstable";
    private static final String COLUMN_PRO_ID = "id";
    private static final String COLUMN_PRODUCT_DETAILS = "productdetails";
    private static final String TABLE_CUSTOMERS = "customerstable";
    private static final String COLUMN_CUS_ID = "id";
    private static final String COLUMN_CUSTOMER_DETAILS = "customerdetails";
    ArrayList<JSONObject> productlist,customerlist ;

    String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_PRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PRODUCT_DETAILS + " VARCHAR" + ")";

    String CREATE_CUSTOMERS_TABLE = "CREATE TABLE " + TABLE_CUSTOMERS + "("
            + COLUMN_CUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CUSTOMER_DETAILS + " VARCHAR" + ")";

    public DatabaseHelper( Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CUSTOMERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
    }
    public void addProductsData(String data)
    {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues startVal = new ContentValues();

        startVal.put(COLUMN_PRODUCT_DETAILS, data);
        database.insert(TABLE_PRODUCTS, null, startVal);

        Log.d("product data", "inserted");
        database.close();
    }
    public void addcustomersData(String data)
    {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues startVal = new ContentValues();

        startVal.put(COLUMN_CUSTOMER_DETAILS, data);
        database.insert(TABLE_CUSTOMERS, null, startVal);

        Log.d("customer data", "inserted");
        database.close();
    }
    public ArrayList<JSONObject> getProductsData()
    {
        productlist =  new ArrayList<JSONObject>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cc = database.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        int count = cc.getCount();
        if(count > 0)
        {
            cc.moveToFirst();

            do{
                int OID = cc.getInt(0);
                String data = cc.getString(1);
                try {

                    JSONObject obj = new JSONObject(data);
                    productlist.add(obj);

                } catch (Throwable t) {

                }

            }
            while(cc.moveToNext());
        }
        cc.close();
          return productlist;
    }
    public ArrayList<JSONObject> getcustomersData()
    {
        customerlist =  new ArrayList<JSONObject>();
        SQLiteDatabase database = getReadableDatabase();

        Cursor cc = database.rawQuery("SELECT * FROM " + TABLE_CUSTOMERS, null);

        int count = cc.getCount();
        if(count > 0)
        {
            cc.moveToFirst();

            do{
                int OID = cc.getInt(0);
                String data = cc.getString(1);
                try {

                    JSONObject obj = new JSONObject(data);
                    customerlist.add(obj);

                } catch (Throwable t) {

                }

            }
            while(cc.moveToNext());
        }
        cc.close();
        return customerlist;
    }
    public  void  removeproducttable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_PRODUCTS);
        Cursor cc;
        cc = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS , null);
        int count = cc.getCount();

        Log.d("product table deleted",String.valueOf(count));
    }
    public  void  removecustomertable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_CUSTOMERS);
        Cursor cc;
        cc = db.rawQuery("SELECT * FROM " + TABLE_CUSTOMERS , null);
        int count = cc.getCount();

        Log.d("customer table deleted",String.valueOf(count));
    }
}
