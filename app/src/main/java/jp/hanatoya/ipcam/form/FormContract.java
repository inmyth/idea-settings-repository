package jp.hanatoya.ipcam.form;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Spinner;

import jp.hanatoya.ipcam.BasePresenter;
import jp.hanatoya.ipcam.BaseView;
import jp.hanatoya.ipcam.models.Cam;


interface FormContract {

    public interface Presenter extends BasePresenter {
        void save();

        void testCam();

        Cam getCam();

        boolean isTesting();

        void delCam();

        void close();
    }


    public interface View extends BaseView<Presenter> {

        void setError(TextInputLayout textInputLayout);

        void clearError();

        String getString(EditText editText);

        String getModelFromSpinner(Spinner spinner);

        void showProgressBar(boolean show);

        void showBottomTab(boolean show);

        boolean isActive();

        Bundle getBundle();

        void populate(Cam cam);

        void showDeleteConfirmDialog();

        void showCancelConfirmDialog();
    }

}
