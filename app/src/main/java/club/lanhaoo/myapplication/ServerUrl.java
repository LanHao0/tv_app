package club.lanhaoo.myapplication;

public class ServerUrl {
    public String serverUrl = "http://192.168.1.106/movie/";

    public String getServerUrl(String api) {
        return serverUrl + api;
    }


}
