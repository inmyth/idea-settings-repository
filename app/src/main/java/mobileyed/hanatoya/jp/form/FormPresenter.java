package mobileyed.hanatoya.jp.form;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.niqdev.mjpeg.Mjpeg;
import com.github.niqdev.mjpeg.MjpegInputStream;

import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.MyApp;
import mobileyed.hanatoya.jp.main.Events;
import mobileyed.hanatoya.jp.models.Cam;
import mobileyed.hanatoya.jp.repo.CamDao;
import mobileyed.hanatoya.jp.utils.BusProvider;
import rx.functions.Action1;


public class FormPresenter implements FormContract.Presenter {
    private static final int TIMEOUT = 5;
    private Cam cam;
    private boolean isTesting;
    private boolean istestOk;


    @NonNull
    private CamDao camDao;

    @NonNull
    private FormContract.View view;


    public FormPresenter(@NonNull FormContract.View view, @NonNull CamDao camDao) {
        this.view = view;
        this.view.setPresenter(this);
        this.camDao = camDao;
    }

    @Override
    public void start() {
        Bundle bundle = view.getBundle();
        view.showProgressBar(false);
        view.showBottomTab(true);
        if (bundle != null) {
            long id = bundle.getLong(BasePresenter.KEY_ID, -1l);
            if (id != -1l){
                this.cam = camDao.queryBuilder()
                        .where(CamDao.Properties.Id.eq(id))
                        .list()
                        .get(0);
                view.populate(this.cam);
                return;
            }
        }
        this.cam = new Cam();
        this.cam.setId(null);
    }

    @Override
    public void testCam() {
        isTesting = true;
        istestOk = false;
        Mjpeg.newInstance()
                .credential(cam.getUsername(), cam.getPassword())
                .open(cam.getStreamUrl(), TIMEOUT)
                .subscribe(new Action1<MjpegInputStream>() {
                               @Override
                               public void call(MjpegInputStream mjpegInputStream) {
                                   isTesting = false;
                                   istestOk = true;
                                    MyApp.getInstance().getBus().send(new Events.CameraPing(true));
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                isTesting = false;
                                if (!istestOk) {
                                    MyApp.getInstance().getBus().send(new Events.CameraPing(false));
                                }
                            }
                        }
                );
//        queue.cancelAll(TAG_PING);
//        Log.e("Volley", "start");
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, cam.getStreamUrl(),
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("ErrorResponse", "trig");
//            }
//        }){
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                Log.e("NetworkRes", String.valueOf(response.statusCode));
//                return super.parseNetworkResponse(response);
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<String, String>();
//                String creds = String.format("%s:%s",cam.getUsername(),cam.getPassword());
//                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//                params.put("Authorization", auth);
//                return params;
//            }
//        };
//
//        stringRequest.setTag(TAG_PING);
//        queue.add(stringRequest);

    }

    @Override
    public void save() {
        if(cam.getId() == null){
            long m = camDao.insert(cam);
        }else{
            long a = camDao.insertOrReplace(cam);
        }
    }

    @Override
    public void delCam() {
        camDao.deleteByKey(cam.getId());
        close();
    }



    @Override
    public boolean isTesting() {
        return isTesting;
    }

    public Cam getCam() {
        return cam;
    }

    @Override
    public void close() {
        MyApp.getInstance().getBus().send(new Events.RequestBack());
    }
}
