package jp.hanatoya.ipcam.main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import jp.hanatoya.ipcam.MyApp;
import jp.hanatoya.ipcam.R;
import jp.hanatoya.ipcam.form.FormFragment;
import jp.hanatoya.ipcam.form.FormPresenter;
import jp.hanatoya.ipcam.repo.DaoSession;
import jp.hanatoya.ipcam.stream.StreamFragment;
import jp.hanatoya.ipcam.stream.StreamPresenter;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private Subscription busSubscription;
    private static final String TAG = "tag";

//    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final DaoSession daoSession =  ((MyApp)getApplication()).getDaoSession();
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(R.string.app_name);

        this.busSubscription = MyApp.getInstance().getBus().toObserverable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Object>() {

                            @Override
                            public void call(Object o) {
                                if (o instanceof Events.SwitchToEdit){
                                    Events.SwitchToEdit event = (Events.SwitchToEdit)o;
                                    FormFragment formFragment = event.id == -1L ? FormFragment.newInstance() : FormFragment.newInstance(event.id);
                                    new FormPresenter(formFragment, daoSession.getCamDao());
                                    replaceFragment(formFragment);
                                }else if (o instanceof Events.RequestBack){
                                    onBackPressed();
                                }else if (o instanceof Events.SwitchToStream){
                                    Events.SwitchToStream event = (Events.SwitchToStream)o;
                                    StreamFragment streamFragment = StreamFragment.newInstance(event.id);
                                    new StreamPresenter(streamFragment, daoSession.getCamDao());
                                    replaceFragment(streamFragment);
                                }
                            }
                        }
                );
        MainFragment mainFragment = MainFragment.newInstance();
        new MainPresenter(mainFragment, daoSession.getCamDao());

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, mainFragment);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (busSubscription != null && !busSubscription.isUnsubscribed()) {
            busSubscription.unsubscribe();
        }
    }

    @Override
    public void onBackPressed() {
        
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, fragment, TAG);
        ft.addToBackStack(null);
        ft.commit();
    }
}
