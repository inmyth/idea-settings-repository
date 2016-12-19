package mobileyed.hanatoya.jp.stream;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.niqdev.mjpeg.DisplayMode;
import com.github.niqdev.mjpeg.MjpegInputStream;
import com.github.niqdev.mjpeg.MjpegView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileyed.hanatoya.jp.BasePresenter;
import mobileyed.hanatoya.jp.MyApp;
import mobileyed.hanatoya.jp.R;
import mobileyed.hanatoya.jp.main.Events;
import mobileyed.hanatoya.jp.models.Cam;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class StreamFragment extends Fragment implements StreamContract.View{


    @BindView(R.id.coordinator) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.stream) MjpegView streamView;

    private StreamPresenter presenter;

    public static StreamFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(BasePresenter.KEY_ID, id);
        StreamFragment fragment = new StreamFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stream, container, false);
        ButterKnife.bind(this, view);
        presenter.start();
        return view;
    }

    
    
    @Override
    public void onPause() {
        streamView.stopPlayback();
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadIpCam();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Bundle getBundle() {
        return getArguments();
    }

    @Override
    public void draw(MjpegInputStream mjpegInputStream) {
        streamView.setSource(mjpegInputStream);
        streamView.setDisplayMode(DisplayMode.BEST_FIT);
        streamView.showFps(true);
    }

    @Override
    public void showError(){
        streamView.stopPlayback();
        Snackbar.make(coordinatorLayout, R.string.error_stream, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.close();
                    }
                })
                .show();
    }

    @Override
    public void setPresenter(StreamContract.Presenter presenter) {
        this.presenter = (StreamPresenter)presenter;
    }

    @OnClick(R.id.up)
    @Override
    public void up() {
        presenter.up(getActivity());
    }

    @OnClick(R.id.left)
    @Override
    public void left() {presenter.left(getActivity());}

    @Override
    @OnClick(R.id.right)
    public void right() {
    presenter.right(getActivity());
    }

    @Override
    @OnClick(R.id.down)
    public void down() {
    presenter.down(getActivity());
    }

    @Override
    @OnClick(R.id.center)
    public void center() {
    presenter.center(getActivity());
    }
}
