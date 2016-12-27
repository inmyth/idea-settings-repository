package jp.hanatoya.ipcam.models;


import android.util.Log;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.List;

import jp.hanatoya.ipcam.cam.CamAPI;
import jp.hanatoya.ipcam.cam.PLANEXCSWMV043GNV;
import jp.hanatoya.ipcam.cam.PanasonicVLCM210;
import jp.hanatoya.ipcam.repo.Cam;
import jp.hanatoya.ipcam.repo.Switch;

@Parcel
public class CamExt {

     @Transient CamAPI camAPI;
     Cam cam;

    public CamExt(){

    }

    public CamExt(Cam cam) {
        this.cam = cam;
    }

    public Cam getCam() {
        return cam;
    }

    public void setCam(Cam cam) {
        this.cam = cam;
    }

    public void initAPI() { // Match these with string resource
        if (this.cam.getType().equals("Panasonic VL-CM210")) {
            camAPI = new PanasonicVLCM210();
        } else if (this.cam.getType().equals("Planex CS-WMV043G-NV")) {
            camAPI = new PLANEXCSWMV043GNV();
        }
    }

    public String getStreamUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.getPath());
        return sb.toString();
    }

    public String getUpUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.up());
        return sb.toString();
    }

    public String getDownUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.down());
        return sb.toString();
    }

    public String getLeftUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.left());
        return sb.toString();
    }

    public String getRightUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.right());
        return sb.toString();
    }

    public String getCenterUrl() {
        StringBuffer sb = buildUrl();
        sb.append(camAPI.center());
        return sb.toString();
    }

    private StringBuffer buildUrl() {
        StringBuffer sb = new StringBuffer(this.cam.getProtocol());
        sb.append(this.cam.getHost());
        sb.append(":");
        sb.append(this.cam.getPort());
        return sb;
    }

    public String buildCgiUrl(Switch s){
        StringBuffer sb = new StringBuffer(this.cam.getProtocol());
        sb.append(this.cam.getHost());
        sb.append(":");
        sb.append(s.getPort());
        if (!s.getCgi().startsWith("/")){
            sb.append("/");
        }
        sb.append(s.getCgi());
        return sb.toString();

    }
}
