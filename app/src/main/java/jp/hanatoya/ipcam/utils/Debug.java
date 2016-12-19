package jp.hanatoya.ipcam.utils;

import android.widget.EditText;

/**
 * Created by martin on 12/16/2016.
 */

public class Debug {

    public static void setCam(EditText name,
                       EditText host,
                       EditText port,
                       EditText username,
                       EditText password
    ) {
        name.setText("test");
        host.setText("192.168.1.253");
        port.setText("8883");
        username.setText("subaron");
        password.setText("subaroncam");

    }
}
