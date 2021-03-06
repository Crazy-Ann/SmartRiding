package com.yjt.app.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.yjt.app.R;
import com.yjt.app.constant.Constant;
import com.yjt.app.constant.Temp;
import com.yjt.app.ui.base.BaseActivity;
import com.yjt.app.ui.widget.CustomDrivePathOverlay;
import com.yjt.app.ui.widget.CustomRidePathOverlay;
import com.yjt.app.ui.widget.fab.FloatingActionButton;
import com.yjt.app.ui.widget.fab.FloatingActionMenu;
import com.yjt.app.utils.BundleUtil;
import com.yjt.app.utils.LogUtil;
import com.yjt.app.utils.MapUtil;
import com.yjt.app.utils.SnackBarUtil;
import com.yjt.app.utils.ToastUtil;
import com.yjt.app.utils.ViewUtil;

import java.util.List;


public class SingleLineMapActivity extends BaseActivity implements View.OnClickListener, AMap.OnMapClickListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener, AMap.OnMapLoadedListener {

    private MapView mvMap;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabDetail;
    private FloatingActionButton fabNavigation;

    private LatLonPoint mStartPoint;
    //        private LatLonPoint mPassPoint;
    private LatLonPoint mEndPoint;

    private AMap mAmap;
    private RouteSearch mSearch;
    private RouteResult mResult;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_single_line);
        findViewById();
        setViewListener();
        initialize(savedInstanceState);
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mvMap.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mvMap.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mvMap.onDestroy();
    }

    @Override
    protected void findViewById() {
        mvMap = ViewUtil.getInstance().findView(this, R.id.mvMap);
        fabMenu = ViewUtil.getInstance().findViewAttachOnclick(this, R.id.fabMenu, this);
        fabDetail = new FloatingActionButton(this);
        fabDetail.setId(R.id.fabDetail);
        fabDetail.setSize(FloatingActionButton.SIZE_MINI);
        fabDetail.setColorNormalResId(android.R.color.white);
        fabDetail.setColorPressedResId(R.color.gray_979797);
        fabDetail.setIcon(R.mipmap.icon_start);
        fabNavigation = new FloatingActionButton(this);
        fabNavigation.setId(R.id.fabNavigation);
        fabNavigation.setSize(FloatingActionButton.SIZE_MINI);
        fabNavigation.setColorNormalResId(android.R.color.white);
        fabNavigation.setColorPressedResId(R.color.gray_979797);
        fabNavigation.setIcon(R.mipmap.icon_end);
    }

    @Override
    protected void setViewListener() {
        fabDetail.setOnClickListener(this);
        fabNavigation.setOnClickListener(this);
    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        mvMap.onCreate(savedInstanceState);
        mDialog = ViewUtil.getInstance().showProgressDialog(this, null, getString(R.string.location_prompt), null, false);
        if (BundleUtil.getInstance().hasBundleExtraValue(this, Temp.START_LOCATION_LONGITUDE.getContent()) && BundleUtil.getInstance().hasBundleExtraValue(this, Temp.START_LOCATION_LATITUDE.getContent())) {
            LogUtil.print("---->显示 StartLongitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.START_LOCATION_LONGITUDE.getContent()));
            LogUtil.print("---->显示 StartLatitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.START_LOCATION_LATITUDE.getContent()));
            mStartPoint = new LatLonPoint(BundleUtil.getInstance().getDoubleData(this, Temp.START_LOCATION_LATITUDE.getContent()), BundleUtil.getInstance().getDoubleData(this, Temp.START_LOCATION_LONGITUDE.getContent()));
        }
//        if (BundleUtil.getInstance().hasBundleExtraValue(this, Temp.PASS_LOCATION_LONGITUDE.getContent()) && BundleUtil.getInstance().hasBundleExtraValue(this, Temp.PASS_LOCATION_LATITUDE.getContent())) {
//            LogUtil.print("---->显示 PassLongitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.PASS_LOCATION_LONGITUDE.getContent()));
//            LogUtil.print("---->显示 PassLatitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.PASS_LOCATION_LATITUDE.getContent()));
//            mPassPoint = new LatLonPoint(BundleUtil.getInstance().getDoubleData(this, Temp.PASS_LOCATION_LATITUDE.getContent()), BundleUtil.getInstance().getDoubleData(this, Temp.PASS_LOCATION_LONGITUDE.getContent()));
//        }
        if (BundleUtil.getInstance().hasBundleExtraValue(this, Temp.END_LOCATION_LONGITUDE.getContent()) && BundleUtil.getInstance().hasBundleExtraValue(this, Temp.END_LOCATION_LATITUDE.getContent())) {
            LogUtil.print("---->显示 EndLongitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.END_LOCATION_LONGITUDE.getContent()));
            LogUtil.print("---->显示 EndLatitude:" + BundleUtil.getInstance().getDoubleData(this, Temp.END_LOCATION_LATITUDE.getContent()));
            mEndPoint = new LatLonPoint(BundleUtil.getInstance().getDoubleData(this, Temp.END_LOCATION_LATITUDE.getContent()), BundleUtil.getInstance().getDoubleData(this, Temp.END_LOCATION_LONGITUDE.getContent()));
        }

        if (mAmap == null) {
            mAmap = mvMap.getMap();
            mAmap.getUiSettings().setZoomControlsEnabled(false);
        }

        mSearch = new RouteSearch(this);
        if (mStartPoint != null && mEndPoint != null) {
            mAmap.addMarker(new MarkerOptions().position(MapUtil.getInstance().convertToLatLng(mStartPoint)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_start)));
//            if (mPassPoint != null) {
//                List<LatLonPoint> points = new ArrayList<>();
//                points.add(mPassPoint);
//                mAmap.addMarker(new MarkerOptions().position(MapUtil.getInstance().convertToLatLng(mPassPoint)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
//            }
            mAmap.addMarker(new MarkerOptions().position(MapUtil.getInstance().convertToLatLng(mEndPoint)).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_end)));
//            mSearch.calculateDriveRouteAsyn(new RouteSearch.DriveRouteQuery(new RouteSearch.FromAndTo(mStartPoint, mEndPoint), RouteSearch.DrivingNoHighWaySaveMoney, null, null, null));
//            mSearch.calculateWalkRouteAsyn(new RouteSearch.WalkRouteQuery(new RouteSearch.FromAndTo(mStartPoint, mEndPoint), RouteSearch.WalkMultipath));
            mSearch.calculateRideRouteAsyn(new RouteSearch.RideRouteQuery(new RouteSearch.FromAndTo(mStartPoint, mEndPoint), RouteSearch.RidingFast));
        } else {
            ToastUtil.getInstance().showToast(this, getString(R.string.route_prompt1), Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void setListener() {
        mAmap.setOnMapLoadedListener(this);
        mAmap.setOnMapClickListener(this);
        mAmap.setOnMarkerClickListener(this);
        mAmap.setOnInfoWindowClickListener(this);
        mAmap.setInfoWindowAdapter(this);
        mSearch.setRouteSearchListener(this);
    }

    @Override
    protected void getSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void setSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void permissionRequestIntent() {

    }

    @Override
    protected void permissionRequestResult() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabDetail:
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable(Temp.ROUTE_INFO.getContent(), mResult);
                startActivity(SingleRouteDetailActivity.class, bundle1);
                break;
            case R.id.fabNavigation:
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable(Temp.ROUTE_INFO.getContent(), mResult);
                startActivity(SigleLineNavigationActivity.class, bundle2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapLoaded() {
        LogUtil.print("---->onMapLoaded");
        mAmap.showBuildings(true);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        LogUtil.print("---->onMapClick");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LogUtil.print("---->onMarkerClick");
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        LogUtil.print("---->onInfoWindowClick");
    }

    @Override
    public View getInfoWindow(Marker marker) {
        LogUtil.print("---->getInfoWindow");
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LogUtil.print("---->getInfoContents");
        return null;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int resultCode) {
        LogUtil.print("---->onBusRouteSearched");
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int resultCode) {
        LogUtil.print("---->onDriveRouteSearched");
        this.mResult = driveRouteResult;
        ViewUtil.getInstance().hideDialog(mDialog, this);
        mAmap.clear();
        if (resultCode == Constant.Map.GEOCODE_SEARCH_SUCCESS) {
            if (driveRouteResult != null) {
                List<DrivePath> paths = driveRouteResult.getPaths();
                if (paths != null && paths.size() > 0) {
                    for (DrivePath path : driveRouteResult.getPaths()) {
                        LogUtil.print("---->path:" + paths.size());
                        CustomDrivePathOverlay overLay = new CustomDrivePathOverlay(mAmap, path, driveRouteResult.getStartPos(), driveRouteResult.getTargetPos(), null);
                        overLay.setRouteWidth(getResources().getDimension(R.dimen.dp_10));
                        overLay.setColor(true);
                        overLay.setNodeIconVisible(true);
                        overLay.setPassMarkerVisible(true);
                        overLay.removeMarkerAndLine();
                        overLay.addRouteToMap(Color.BLUE);
                        overLay.zoomToSpan();
                    }
                } else {
                    SnackBarUtil.getInstance().showSnackBar(this, getString(R.string.route_prompt2), Snackbar.LENGTH_SHORT);
                }
            }
        } else {
            MapUtil.getInstance().showMapError(this, resultCode);
        }

        fabMenu.addButton(fabDetail);
        fabMenu.addButton(fabNavigation);
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int resultCode) {
        LogUtil.print("---->onWalkRouteSearched");
        this.mResult = walkRouteResult;
        ViewUtil.getInstance().hideDialog(mDialog, this);
        mAmap.clear();
        if (resultCode == Constant.Map.GEOCODE_SEARCH_SUCCESS) {
            if (walkRouteResult != null) {
                List<WalkPath> paths = walkRouteResult.getPaths();
                if (paths != null && paths.size() > 0) {
                    for (WalkPath path : paths) {
                        WalkRouteOverlay overlay = new WalkRouteOverlay(this, mAmap, path, walkRouteResult.getStartPos(), walkRouteResult.getTargetPos());
                        overlay.removeFromMap();
                        overlay.setNodeIconVisibility(false);
                        overlay.addToMap();
                        overlay.zoomToSpan();
                    }
                } else {
                    ToastUtil.getInstance().showToast(this, getString(R.string.route_prompt2), Toast.LENGTH_SHORT);
                }
            }
        } else {
            MapUtil.getInstance().showMapError(this, resultCode);
        }

        fabMenu.addButton(fabDetail);
        fabMenu.addButton(fabNavigation);
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int resultCode) {
        LogUtil.print("---->onRideRouteSearched");
        this.mResult = rideRouteResult;
        ViewUtil.getInstance().hideDialog(mDialog, this);
        mAmap.clear();
        if (resultCode == Constant.Map.GEOCODE_SEARCH_SUCCESS) {
            if (rideRouteResult != null) {
                List<RidePath> paths = rideRouteResult.getPaths();
                if (paths != null && paths.size() > 0) {
                    for (RidePath path : rideRouteResult.getPaths()) {
                        LogUtil.print("---->path:" + paths.size());
                        CustomRidePathOverlay overLay = new CustomRidePathOverlay(mAmap, path, rideRouteResult.getStartPos(), rideRouteResult.getTargetPos(), null);
                        overLay.setRouteWidth(getResources().getDimension(R.dimen.dp_8));
                        overLay.setColor(true);
                        overLay.setNodeIconVisible(true);
                        overLay.setPassMarkerVisible(true);
                        overLay.removeMarkerAndLine();
                        overLay.addRouteToMap(Color.GREEN);
                        overLay.zoomToSpan();
                    }
                } else {
                    SnackBarUtil.getInstance().showSnackBar(this, getString(R.string.route_prompt2), Snackbar.LENGTH_SHORT);
                }
            }
        } else {
            MapUtil.getInstance().showMapError(this, resultCode);
        }

        fabMenu.addButton(fabDetail);
        fabMenu.addButton(fabNavigation);
    }
}
