package mobileyed.hanatoya.jp.stream;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

import com.github.niqdev.mjpeg.MjpegInputStream;
import com.github.niqdev.mjpeg.MjpegView;

import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.BaseView;

interface StreamContract {

    interface Presenter extends BasePresenter {

        void loadIpCam();

        void close();

    }

    interface View extends BaseView<Presenter>{

        Bundle getBundle();

        void draw(MjpegInputStream mjpegInputStream);

        void showError();
    }
}
