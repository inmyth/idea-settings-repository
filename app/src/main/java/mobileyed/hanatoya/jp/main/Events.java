package mobileyed.hanatoya.jp.main;


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

    public static class BackPressed{} //used to broadcast back btn to fragment
    public static class CameraPing{
        public boolean isOk;

        public CameraPing(boolean isOk) {
            this.isOk = isOk;
        }
    }
}
