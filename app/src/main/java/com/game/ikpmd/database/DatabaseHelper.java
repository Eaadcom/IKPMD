package com.game.ikpmd.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.game.ikpmd.list.AttackListAdapter;
import com.game.ikpmd.models.Attack;
import com.game.ikpmd.models.City;
import com.google.gson.Gson;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    public static final String dbName = "app.db";
    public static final int dbVersion = 1;

    private DatabaseHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);
    }

    // Singleton
    public static synchronized DatabaseHelper getHelper (Context ctx){
        if (mInstance == null){
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    public static synchronized DatabaseHelper getHelper (){
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseInfo.AppTables.CITYTABLE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.CityColumn.UniqueIdentifier + " TEXT," +
                DatabaseInfo.CityColumn.Owner + " TEXT," +
                DatabaseInfo.CityColumn.Name + " TEXT," +
                DatabaseInfo.CityColumn.XAxisPosition + " TEXT," +
                DatabaseInfo.CityColumn.YAxisPosition + " TEXT," +
                DatabaseInfo.CityColumn.Swordsman + " TEXT," +
                DatabaseInfo.CityColumn.Archer + " TEXT," +
                DatabaseInfo.CityColumn.Horseman + " TEXT);"
        );

        db.execSQL("CREATE TABLE " + DatabaseInfo.AppTables.ATTACKTABLE + " (" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.AttackColumn.TargetCityUniqueId + " TEXT," +
                DatabaseInfo.AttackColumn.OriginCityUniqueId + " TEXT," +
                DatabaseInfo.AttackColumn.ASwordsman + " TEXT," +
                DatabaseInfo.AttackColumn.AArcher + " TEXT," +
                DatabaseInfo.AttackColumn.AHorseman + " TEXT," +
                DatabaseInfo.AttackColumn.Arrivaltime + " TEXT," +
                DatabaseInfo.AttackColumn.UniqueAttackIdentifier + " TEXT," +
                DatabaseInfo.AttackColumn.Arrived + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.AppTables.CITYTABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.AppTables.ATTACKTABLE);
        onCreate(db);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values){
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }

    public void insertCityIntoSQLiteDatabase(City city){

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.CityColumn.UniqueIdentifier, city.getUniqueIdentifier());
        values.put(DatabaseInfo.CityColumn.Owner, city.getOwner());
        values.put(DatabaseInfo.CityColumn.Name, city.getName());
        values.put(DatabaseInfo.CityColumn.XAxisPosition, city.getxAxisPosition());
        values.put(DatabaseInfo.CityColumn.YAxisPosition, city.getyAxisPosition());
        values.put(DatabaseInfo.CityColumn.Swordsman, gson.toJson(city.getSwordsman()));
        values.put(DatabaseInfo.CityColumn.Archer, gson.toJson(city.getSwordsman()));
        values.put(DatabaseInfo.CityColumn.Horseman, gson.toJson(city.getSwordsman()));

        insert(DatabaseInfo.AppTables.CITYTABLE, null, values);
    }

    public void insertAttackIntoSQLiteDatabase(Attack attack){

        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(DatabaseInfo.AttackColumn.TargetCityUniqueId, attack.getTargetCityUniqueId());
        values.put(DatabaseInfo.AttackColumn.OriginCityUniqueId, attack.getOriginCityUniqueId());
        values.put(DatabaseInfo.AttackColumn.ASwordsman, gson.toJson(attack.getSwordsman()));
        values.put(DatabaseInfo.AttackColumn.AArcher, gson.toJson(attack.getArcher()));
        values.put(DatabaseInfo.AttackColumn.AHorseman, gson.toJson(attack.getHorseman()));
        values.put(DatabaseInfo.AttackColumn.Arrivaltime, attack.getArrivaltime());
        values.put(DatabaseInfo.AttackColumn.UniqueAttackIdentifier, attack.getUniqueAttackIdentifier());
        values.put(DatabaseInfo.AttackColumn.Arrived, gson.toJson(attack.isArrived()));

        insert(DatabaseInfo.AppTables.ATTACKTABLE, null, values);
    }

}
