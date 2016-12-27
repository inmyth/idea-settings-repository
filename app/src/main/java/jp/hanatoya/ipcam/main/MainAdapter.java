package jp.hanatoya.ipcam.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.hanatoya.ipcam.R;
import jp.hanatoya.ipcam.models.CamExt;


class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CamExt> camExts = new ArrayList<>();
    private MainFragment.MainFragmentListener listener;

    MainAdapter(MainFragment.MainFragmentListener listener) {
        this.listener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cam, parent, false);
        return new ViewHolderCam(convertView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderCam) {
            ViewHolderCam h = (ViewHolderCam) holder;
            final CamExt camExt = camExts.get(position);
            h.name.setText(camExt.getCam().getName());
            h.type.setText(camExt.getCam().getType());
//            h.status.setText(camExt.getStatus() == 0 ? context.getString(R.string.status_ok) : context.getString(R.string.status_error));
            h.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(camExt.getCam().getId());
                }
            });
            h.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStreamClick(camExt.getCam().getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return camExts.size();
    }

    static class ViewHolderCam extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.type) TextView type;
//        @BindView(R.id.status) TextView status;
        @BindView(R.id.edit) TextView edit;
        @BindView(R.id.img) ImageView img;


        ViewHolderCam(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    void swap(List<CamExt> newCamExts) {
        camExts.clear();
        camExts.addAll(newCamExts);
        notifyDataSetChanged();
    }
}
