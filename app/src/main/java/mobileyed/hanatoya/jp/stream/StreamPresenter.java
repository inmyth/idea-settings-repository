package mobileyed.hanatoya.jp.stream;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegInputStream;

import java.util.HashMap;
import java.util.Map;

import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.MyApp;
import mobileyed.hanatoya.jp.main.Events;
import mobileyed.hanatoya.jp.models.Cam;
import mobileyed.hanatoya.jp.repo.CamDao;
import mobileyed.hanatoya.jp.utils.VolleySingleton;
import rx.functions.Action1;


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

    @Override
    public void up(Context context) {
        VolleySingleton.getInstance(context).addToRequestQueue(setUpReq(cam.getUpUrl(), cam.getUsername(), cam.getPassword()));
    }

    @Override
    public void left(Context context) {
        VolleySingleton.getInstance(context).addToRequestQueue(setUpReq(cam.getLeftUrl(), cam.getUsername(), cam.getPassword()));
    }

    @Override
    public void right(Context context) {
        VolleySingleton.getInstance(context).addToRequestQueue(setUpReq(cam.getRightUrl(), cam.getUsername(), cam.getPassword()));
    }

    @Override
    public void down(Context context) {
        VolleySingleton.getInstance(context).addToRequestQueue(setUpReq(cam.getDownUrl(), cam.getUsername(), cam.getPassword()));
    }

    @Override
    public void center(Context context) {
        VolleySingleton.getInstance(context).addToRequestQueue(setUpReq(cam.getCenterUrl(), cam.getUsername(), cam.getPassword()));
    }


    private StringRequest setUpReq(String url, final String username, final String password){
        return new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Volley Up", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                String creds = String.format("%s:%s",username, password);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);
                return params;
            }
        };
    }
}
