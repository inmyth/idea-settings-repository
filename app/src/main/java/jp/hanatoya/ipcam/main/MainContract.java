package jp.hanatoya.ipcam.main;

import java.util.List;

import jp.hanatoya.ipcam.BasePresenter;
import jp.hanatoya.ipcam.BaseView;
import jp.hanatoya.ipcam.models.Cam;


public interface MainContract {


    public interface Presenter extends BasePresenter{

        void toEdit(long id);

        void toStream(long id);
    }


    public interface View extends BaseView<Presenter>{

        void populate(List<Cam> newCamList);

        void addClick();


    }
}
