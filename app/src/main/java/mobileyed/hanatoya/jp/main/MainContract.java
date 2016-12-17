package mobileyed.hanatoya.jp.main;

import java.util.List;

import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.BaseView;
import mobileyed.hanatoya.jp.models.Cam;


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
