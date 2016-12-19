package jp.hanatoya.ipcam.main;

import android.support.annotation.NonNull;

import java.util.List;

import jp.hanatoya.ipcam.MyApp;
import jp.hanatoya.ipcam.models.Cam;
import jp.hanatoya.ipcam.repo.CamDao;


 class MainPresenter implements MainContract.Presenter{

    @NonNull private MainContract.View view;
    @NonNull private CamDao camDao;


     MainPresenter(@NonNull MainContract.View view, @NonNull CamDao camDao) {
        this.view = view;
        this.view.setPresenter(this);
        this.camDao = camDao;
    }


    @Override
    public void toEdit(long id) {
        MyApp.getInstance().getBus().send(new Events.SwitchToEdit(id));
    }

    @Override
    public void toStream(long id) {
        MyApp.getInstance().getBus().send(new Events.SwitchToStream(id));
    }

    @Override
    public void start() {
        List<Cam> cams = camDao.loadAll();
        view.populate(cams);
    }



}
