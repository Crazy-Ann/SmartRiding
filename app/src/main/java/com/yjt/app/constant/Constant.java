package com.yjt.app.constant;

public class Constant {

    //bugly
    public static final String BUGLY_APP_ID = "900018257";

    public static final String START = "start";
    public static final String END = "end";

    public static final int SIMULATED_NAVIGATION_SPEED = 100;
    public static final int LATLNG_ZOOM = 15;

    public static final String Kilometer = "\u516c\u91cc";// "公里";
    public static final String Meter = "\u7c73";// "米";
    public static final String ByFoot = "\u6b65\u884c";// "步行";
    public static final String To = "\u53bb\u5f80";// "去往";
    public static final String Station = "\u8f66\u7ad9";// "车站";
    public static final String TargetPlace = "\u76ee\u7684\u5730";// "目的地";
    public static final String StartPlace = "\u51fa\u53d1\u5730";// "出发地";
    public static final String About = "\u5927\u7ea6";// "大约";
    public static final String Direction = "\u65b9\u5411";// "方向";

    public static final String GetOn = "\u4e0a\u8f66";// "上车";
    public static final String GetOff = "\u4e0b\u8f66";// "下车";
    public static final String Zhan = "\u7ad9";// "站";

    public static final String cross = "\u4ea4\u53c9\u8def\u53e3"; // 交叉路口
    public static final String type = "\u7c7b\u522b"; // 类别
    public static final String address = "\u5730\u5740"; // 地址
    public static final String PrevStep = "\u4e0a\u4e00\u6b65";
    public static final String NextStep = "\u4e0b\u4e00\u6b65";
    public static final String Gong = "\u516c\u4ea4";
    public static final String ByBus = "\u4e58\u8f66";
    public static final String Arrive = "\u5230\u8FBE";// 到达

    public static final String DAY_NIGHT_MODE = "daynightmode";
    public static final String DEVIATION = "deviationrecalculation";
    public static final String JAM = "jamrecalculation";
    public static final String TRAFFIC = "trafficbroadcast";
    public static final String CAMERA = "camerabroadcast";
    public static final String SCREEN = "screenon";
    public static final String THEME = "theme";
    public static final String ISEMULATOR = "isemulator";


    public static final String ACTIVITYINDEX = "activityindex";

    public static final int SIMPLEHUDNAVIE = 0;
    public static final int EMULATORNAVI = 1;
    public static final int SIMPLEGPSNAVI = 2;
    public static final int SIMPLEROUTENAVI = 3;

    public static final int COLOR_DEFAULT = 0x999999;
    public static final int SIZE_DEFAULT = 0x999999;

    public static final int DRAWABLE_TOP = 0x000001;
    public static final int DRAWABLE_LEFT = 0x000002;
    public static final int DRAWABLE_RIGHT = 0x000003;
    public static final int DRAWABLE_BOTTOM = 0x000004;

    public static class ITEM_POSITION {
        public static final int HOME = 0;
        public static final int DEVICE = 1;
        public static final int MESSAGE = 2;
        public static final int SETTING = 3;
        public static final int SEARCH_DEVICE = 0;
        public static final int GENERAL_SETTING = 1;
        public static final int CHECK_UPDATE = 2;
        public static final int CLEAR_DATA = 3;
        public static final int BREAK_LINK = 4;
        public static final int ABOUT_DEVICE = 5;
    }

    public static class ITEM_TYPE {
        public static final int HEADER_VIEW = 0x5001;
        public static final int CONTENT_VIEW = 0x5003;
        public static final int FOOTER_VIEW = 0x5004;
        public static final int OTHER_VIEW = 0x5005;
    }

    public static class PAGE {
        public static final int HOME = 0x01;
        public static final int DEVICE = 0x02;
        public static final int MESSAGE = 0x03;
        public static final int SETTING = 0x04;
    }
}
