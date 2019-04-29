package com.aspirantlab.salesagent.Services;

public class GetURL {

   static String serverip = "http://weknit.erponwheels.com/";
   // static String serverip = "http://192.168.1.215:8088/";

    public static String getloginserverip()
    {

        String url = serverip + "web/session/authenticate";
        return url;
    }
    public static String getcustomerlist()
    {

        String url = serverip + "web/dataset/call_kw";
        return url;
    }
    public static String getproductlist()
    {

        String url = serverip + "web/dataset/call_kw";
        return url;
    }
    public static String getdb()
    {

        String url = "portaldemo";
        return url;
    }
    public static String getServerip()
    {

        String url = serverip;
        return url;
    }
}
