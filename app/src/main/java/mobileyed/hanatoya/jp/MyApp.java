package mobileyed.hanatoya.jp;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;


import mobileyed.hanatoya.jp.repo.DaoMaster;
import mobileyed.hanatoya.jp.repo.DaoSession;
import mobileyed.hanatoya.jp.utils.RxBus;


public class MyApp extends Application {

    private DaoSession daoSession;
    private static MyApp instance;

    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        bus = new RxBus();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mobileyed.hanatoya.jp", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static MyApp getInstance() {
        return instance;
    }

    public RxBus getBus() {
        return bus;
    }

}
