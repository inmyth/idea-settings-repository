package mobileyed.hanatoya.jp.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mobileyed.hanatoya.jp.R;
import mobileyed.hanatoya.jp.models.Cam;


public class MainFragment extends Fragment implements MainContract.View {

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.add) FloatingActionButton add;

    private MainPresenter presenter;
    private MainAdapter adapter;
    private MainFragmentListener mainFragmentListener = new MainFragmentListener() {

        @Override
        public void onEditClick(long id) {
            presenter.toEdit(id);
        }

        @Override
        public void onStreamClick(long id) {
            presenter.toStream(id);
        }
    };



     public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainAdapter(getActivity(), mainFragmentListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        presenter.start();
        return view;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = (MainPresenter)presenter;
    }


    @Override
    public void populate(List<Cam> newCamList){
        adapter.swap(newCamList);
    }

    @OnClick(R.id.add)
    @Override
    public void addClick() {
        presenter.toEdit(-1L);
    }


    interface MainFragmentListener {

        public void onEditClick(long id);
        public void onStreamClick(long id);

    }
}
