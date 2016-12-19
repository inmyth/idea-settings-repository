package jp.hanatoya.ipcam.main;


public class Events {

    public static class SwitchToStream {
        long id;

        SwitchToStream(long id) {
            this.id = id;
        }
    }

    public static class SwitchToEdit {
        long id;

        SwitchToEdit(long id) {
            this.id = id;
        }
    }


    public static class RequestBack{}

    public static class CameraPing{
        public boolean isOk;

        public CameraPing(boolean isOk) {
            this.isOk = isOk;
        }
    }
}
