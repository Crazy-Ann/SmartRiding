package com.yjt.app.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RideStep;
import com.yjt.app.R;
import com.yjt.app.constant.Regex;
import com.yjt.app.constant.Temp;
import com.yjt.app.entity.RouteDetail;
import com.yjt.app.ui.adapter.RouteDetailAdapter;
import com.yjt.app.ui.adapter.binder.RouteDetailBinder;
import com.yjt.app.ui.base.BaseActivity;
import com.yjt.app.ui.sticky.FixedStickyViewAdapter;
import com.yjt.app.ui.widget.LinearLayoutDividerItemDecoration;
import com.yjt.app.utils.BundleUtil;
import com.yjt.app.utils.MapUtil;
import com.yjt.app.utils.ToastUtil;
import com.yjt.app.utils.ViewUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class SingleRouteDetailActivity extends BaseActivity {

    private RideRouteResult mResult;
    private TextView tvDistance;
    private RecyclerView rvRouteDetail;
    private LinearLayoutManager mLayoutManager;
    private FixedStickyViewAdapter mAdapter;
    private RidePath mPath;
    private RouteDetailHandler mHandler;

    private static class RouteDetailHandler extends Handler {

        private WeakReference<SingleRouteDetailActivity> mActivitys;

        public RouteDetailHandler(SingleRouteDetailActivity Activity) {
            mActivitys = new WeakReference<>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SingleRouteDetailActivity Activity = mActivitys.get();
            if (Activity != null) {
                switch (msg.what) {
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_detail);
        findViewById();
        setViewListener();
        initialize(savedInstanceState);
        setListener();
    }

    @Override
    protected void findViewById() {
        ViewUtil.getInstance().setToolBar(this, R.id.tbTitle, true);
        tvDistance = ViewUtil.getInstance().findView(this, R.id.tvDistance);
        rvRouteDetail = ViewUtil.getInstance().findView(this, R.id.rvRouteDetail);
    }

    @Override
    protected void setViewListener() {

    }

    @Override
    protected void initialize(Bundle savedInstanceState) {
        if (BundleUtil.getInstance().hasIntentExtraValue(this, Temp.ROUTE_INFO.getContent())) {
            mHandler = new RouteDetailHandler(this);
            mResult = BundleUtil.getInstance().getParcelableData(this, Temp.ROUTE_INFO.getContent());
            mPath = mResult.getPaths().get(0);
            tvDistance.setText(MapUtil.getInstance().getFriendlyTime((int) mPath.getDuration()) + Regex.LEFT_PARENTHESIS.getRegext() + MapUtil.getInstance().getFriendlyLength((int) mPath.getDistance()) + Regex.RIGHT_PARENTHESIS.getRegext());
            mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rvRouteDetail.setHasFixedSize(true);
            rvRouteDetail.setLayoutManager(mLayoutManager);
            rvRouteDetail.addItemDecoration(new LinearLayoutDividerItemDecoration(getResources().getColor(android.R.color.black), 1, LinearLayoutManager.VERTICAL));
            mAdapter = new RouteDetailAdapter(this, new RouteDetailBinder(this, rvRouteDetail), false);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    List<RouteDetail> details = new ArrayList<>();
                    List<RideStep> steps = mPath.getSteps();
                    int size = steps.size();
                    for (int i = -1; i <= size; i++) {
                        if (i < 0) {
                            RouteDetail detail = new RouteDetail();
                            detail.setDirection(R.mipmap.icon_dir_start);
                            detail.setLineVisible(View.GONE);
                            detail.setDirectionUpVisible(View.GONE);
                            detail.setDirectionDownVisible(View.VISIBLE);
                            detail.setRoutDetail(getString(R.string.start_off));
                            details.add(detail);
                        } else if (i < size) {
                            RideStep step = steps.get(i);
                            RouteDetail detail = new RouteDetail();
                            detail.setDirection(MapUtil.getInstance().getDriveActionID(step.getAction()));
                            detail.setLineVisible(View.VISIBLE);
                            detail.setDirectionUpVisible(View.VISIBLE);
                            detail.setDirectionDownVisible(View.VISIBLE);
                            detail.setRoutDetail(step.getInstruction());
                            details.add(detail);
                        } else {
                            RouteDetail detail = new RouteDetail();
                            detail.setDirection(R.mipmap.icon_dir_end);
                            detail.setLineVisible(View.VISIBLE);
                            detail.setDirectionUpVisible(View.VISIBLE);
                            detail.setDirectionDownVisible(View.GONE);
                            detail.setRoutDetail(getString(R.string.arrival));
                            details.add(detail);
                        }
                    }
                    mAdapter.setData(details);
                }
            });
            rvRouteDetail.setAdapter(mAdapter);
        } else {
            ToastUtil.getInstance().showToast(this, getString(R.string.route_prompt3), Toast.LENGTH_SHORT);
        }
    }

    @Override
    protected void setListener() {

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
}
