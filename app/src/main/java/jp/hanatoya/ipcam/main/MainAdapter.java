package jp.hanatoya.ipcam.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.hanatoya.ipcam.R;
import jp.hanatoya.ipcam.models.Cam;


class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Cam> cams = new ArrayList<>();
    private Context context;
    private MainFragment.MainFragmentListener listener;

    MainAdapter(Context context, MainFragment.MainFragmentListener listener) {
        this.context = context;
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
            final Cam cam = cams.get(position);
            h.name.setText(cam.getName());
            h.type.setText(cam.getType());
//            h.status.setText(cam.getStatus() == 0 ? context.getString(R.string.status_ok) : context.getString(R.string.status_error));
            h.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEditClick(cam.getId());
                }
            });
            h.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onStreamClick(cam.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cams.size();
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

    void swap(List<Cam> newCams) {
        cams.clear();
        cams.addAll(newCams);
        notifyDataSetChanged();
    }
}
