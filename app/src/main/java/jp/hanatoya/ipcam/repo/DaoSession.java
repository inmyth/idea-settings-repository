package jp.hanatoya.ipcam.repo;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;
import jp.hanatoya.ipcam.models.Cam;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig camDaoConfig;

    private final CamDao camDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        camDaoConfig = daoConfigMap.get(CamDao.class).clone();
        camDaoConfig.initIdentityScope(type);

        camDao = new CamDao(camDaoConfig, this);

        registerDao(Cam.class, camDao);
    }
    
    public void clear() {
        camDaoConfig.getIdentityScope().clear();
    }

    public CamDao getCamDao() {
        return camDao;
    }

}
