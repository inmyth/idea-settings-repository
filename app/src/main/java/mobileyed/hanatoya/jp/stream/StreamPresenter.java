package mobileyed.hanatoya.jp.stream;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegInputStream;
import com.github.niqdev.mjpeg.MjpegView;

import java.util.HashMap;
import java.util.Map;

import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.MyApp;
import mobileyed.hanatoya.jp.main.Events;
import mobileyed.hanatoya.jp.models.Cam;
import mobileyed.hanatoya.jp.repo.CamDao;
import mobileyed.hanatoya.jp.utils.BusProvider;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class StreamPresenter implements StreamContract.Presenter{
    public static final int CODE_OK = 0;
    public static final int CODE_AUTH_FAIL = 403;
    public static final int CODE_ERROR_4 = 4;
    public static final int CODE_ERROR_5 = 5;

    private static final int TIMEOUT = 5;

    private CamDao camDao;
    private StreamContract.View view;
    private Cam cam;
    private boolean isError;

    public StreamPresenter( @NonNull StreamContract.View view, @NonNull CamDao camDao){
        this.camDao = camDao;
        view.setPresenter(this);
        this.view = view;

    }

    @Override
    public void start() {
        Bundle bundle = view.getBundle();
        long id = bundle.getLong(BasePresenter.KEY_ID);
        this.cam  = camDao.load(id);
        loadIpCam();
    }

    @Override
    public void close() {
        MyApp.getInstance().getBus().send(new Events.RequestBack());
    }

    @Override
    public void loadIpCam() {
        if (isError){
            return;
        }
        Mjpeg.newInstance()
                .credential(cam.getUsername(), cam.getPassword())
                .open(cam.getStreamUrl(), TIMEOUT)
                .subscribe(new Action1<MjpegInputStream>() {
                               @Override
                               public void call(MjpegInputStream mjpegInputStream) {
                                    view.draw(mjpegInputStream);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(getClass().getSimpleName(), "mjpeg error", throwable);
                                if(!isError){ //tiwhout this flag, this callback will be called many times
                                    isError = true;
                                    view.showError();
                                }
                            }
                        }
                );

    }
}
