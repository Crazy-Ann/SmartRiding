package com.yjt.app.base;

import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.amap.api.navi.AMapNavi;
import com.iflytek.cloud.SpeechUtility;
import com.tencent.bugly.crashreport.CrashReport;
import com.yjt.app.constant.Constant;
import com.yjt.app.service.BluetoothService;
import com.yjt.app.utils.ActivityUtil;
import com.yjt.app.utils.AnimationUtil;
import com.yjt.app.utils.BluetoothUtil;
import com.yjt.app.utils.CrashHandler;
import com.yjt.app.utils.DensityUtil;
import com.yjt.app.utils.FragmentUtil;
import com.yjt.app.utils.InputUtil;
import com.yjt.app.utils.LogUtil;
import com.yjt.app.utils.MapUtil;
import com.yjt.app.utils.NetworkUtil;
import com.yjt.app.utils.SharedPreferenceUtil;
import com.yjt.app.utils.SnackBarUtil;
import com.yjt.app.utils.StrictModeUtil;
import com.yjt.app.utils.TTSUtil;
import com.yjt.app.utils.ToastUtil;
import com.yjt.app.utils.TypefaceUtil;
import com.yjt.app.utils.VersionUtil;
import com.yjt.app.utils.ViewUtil;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mApplication;
    private BluetoothService mService;
    private BluetoothGatt mGatt;

    public static BaseApplication getInstance() {
        return mApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
//        StrictModeUtil.getInstance().initialize();
        super.onCreate();
        LogUtil.d("---->" + getClass().getName(), this.getClass().getSimpleName()
                + " onCreate() invoked!!");
        mApplication = this;
        SpeechUtility.createUtility(this, Constant.IFLY_APP_ID);
        CrashReport.initCrashReport(getApplicationContext(), Constant.BUGLY_APP_ID, true);
//        CrashHandler.getInstance().initialize();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.d("---->" + getClass().getName(), this.getClass().getSimpleName() + " onLowMemory() invoked!!");
        AnimationUtil.releaseInstance();
        BluetoothUtil.releaseInstance();
        CrashHandler.releaseInstance();
        DensityUtil.releaseInstance();
        FragmentUtil.releaseInstance();
        InputUtil.releaseInstance();
        NetworkUtil.releaseInstance();
        SharedPreferenceUtil.releaseInstance();
        SnackBarUtil.releaseInstance();
        VersionUtil.releaseInstance();
        ViewUtil.releaseInstance();
    }

    public void releaseReference() {
        AMapNavi.getInstance(BaseApplication.getInstance()).destroy();
        TTSUtil.getInstance().destroy();
        AnimationUtil.releaseInstance();
        BluetoothUtil.releaseInstance();
        CrashHandler.releaseInstance();
        DensityUtil.releaseInstance();
        FragmentUtil.releaseInstance();
        InputUtil.releaseInstance();
        MapUtil.releaseInstance();
        NetworkUtil.releaseInstance();
        SharedPreferenceUtil.releaseInstance();
        SnackBarUtil.releaseInstance();
        StrictModeUtil.releaseInstance();
        ToastUtil.releaseInstance();
        VersionUtil.releaseInstance();
        ViewUtil.releaseInstance();
        FragmentUtil.releaseInstance();
        TypefaceUtil.releaseInstance();
        ActivityUtil.removeAll();
        setBluetoothGatt(null);
        setService(null);
        mApplication = null;
    }

    public BluetoothService getService() {
        return mService;
    }

    public void setService(BluetoothService service) {
        this.mService = service;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mGatt;
    }

    public void setBluetoothGatt(BluetoothGatt gatt) {
        this.mGatt = gatt;
    }
}
