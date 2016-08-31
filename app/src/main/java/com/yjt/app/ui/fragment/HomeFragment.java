package com.yjt.app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yjt.app.R;
import com.yjt.app.constant.Constant;
import com.yjt.app.constant.Temp;
import com.yjt.app.ui.activity.MapActivity;
import com.yjt.app.ui.activity.RouteActivity;
import com.yjt.app.ui.base.BaseFragment;
import com.yjt.app.ui.widget.SearchTextView;
import com.yjt.app.utils.InputUtil;
import com.yjt.app.utils.LogUtil;
import com.yjt.app.utils.SnackBarUtil;
import com.yjt.app.utils.ViewUtil;

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivExchange;
    private SearchTextView stvStartPoint;
    private SearchTextView stvPassPoint;
    private SearchTextView stvEndPoint;
    private ImageView ivVoice;
    private TextView tvSearch;

    private String mTempString;

    private double mStartLatitude;
    private double mStartLongitude;
    private double mPassLatitude;
    private double mPassLongitude;
    private double mEndLatitude;
    private double mEndLongitude;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        findViewById();
        setListener();
        initialize(savedInstanceState);
        return mRootView;
    }

    @Override
    public void onClick(View view) {
        InputUtil.getInstance().hideKeyBoard(getActivity(), view);
        switch (view.getId()) {
            case R.id.ivExchange:
                if (!TextUtils.equals(stvStartPoint.getText(), getString(R.string.start_point)) && !TextUtils.equals(stvEndPoint.getText(), getString(R.string.end_point)) || !TextUtils.equals(stvStartPoint.getText(), stvEndPoint.getText())) {
                    mTempString = stvStartPoint.getText();
                    stvStartPoint.setText(stvEndPoint.getText());
                    stvEndPoint.setText(mTempString);
                }
                break;
            case R.id.stvStartPoint:
                Bundle bundle1 = new Bundle();
                bundle1.putInt(Temp.POINT_TYPE.getContent(), Constant.PointType.START);
                startActivityForResult(RouteActivity.class, Constant.RequestCode.POINT, bundle1);
                break;
            case R.id.stvPassPoint:
                Bundle bundle2 = new Bundle();
                bundle2.putInt(Temp.POINT_TYPE.getContent(), Constant.PointType.PASS);
                startActivityForResult(RouteActivity.class, Constant.RequestCode.POINT, bundle2);
                break;
            case R.id.stvEndPoint:
                Bundle bundle3 = new Bundle();
                bundle3.putInt(Temp.POINT_TYPE.getContent(), Constant.PointType.END);
                startActivityForResult(RouteActivity.class, Constant.RequestCode.POINT, bundle3);
                break;
            case R.id.ivVoice:
                SnackBarUtil.getInstance().showSnackBar(view, "ivVoice", Snackbar.LENGTH_SHORT, Color.WHITE);
                break;
            case R.id.tvSearch:
                if (TextUtils.isEmpty(stvStartPoint.getText()) || TextUtils.equals(stvStartPoint.getText(), getString(R.string.start_point))) {
                    SnackBarUtil.getInstance().showSnackBar(view, getString(R.string.start_point), Snackbar.LENGTH_SHORT, Color.WHITE);
                } else if (TextUtils.isEmpty(stvEndPoint.getText()) || TextUtils.equals(stvEndPoint.getText(), getString(R.string.end_point))) {
                    SnackBarUtil.getInstance().showSnackBar(view, getString(R.string.end_point), Snackbar.LENGTH_SHORT, Color.WHITE);
                } else if (TextUtils.equals(stvStartPoint.getText(), stvEndPoint.getText()) || TextUtils.equals(stvStartPoint.getText(), stvPassPoint.getText()) || TextUtils.equals(stvPassPoint.getText(), stvEndPoint.getText())) {
                    SnackBarUtil.getInstance().showSnackBar(view, getString(R.string.search_prompt), Snackbar.LENGTH_SHORT, Color.WHITE);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(Temp.START_POINT.getContent(), stvStartPoint.getText());
                    bundle.putString(Temp.PASS_POINT.getContent(), stvPassPoint.getText());
                    bundle.putString(Temp.END_POINT.getContent(), stvEndPoint.getText());
                    startActivity(getActivity(), MapActivity.class, bundle);
                }
                break;
        }
    }

    @Override
    protected void findViewById() {
        ivExchange = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.ivExchange, this);

        stvStartPoint = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.stvStartPoint, this);
        stvPassPoint = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.stvPassPoint, this);
        stvEndPoint = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.stvEndPoint, this);
        stvStartPoint.setHint(R.string.start_point);
        stvStartPoint.setLineVisible(View.VISIBLE);
        stvPassPoint.setHint(R.string.pass_point);
        stvStartPoint.setLineVisible(View.VISIBLE);
        stvEndPoint.setHint(R.string.end_point);
        stvEndPoint.setLineVisible(View.GONE);

        ivVoice = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.ivVoice, this);
        tvSearch = ViewUtil.getInstance().findViewAttachOnclick(mRootView, R.id.tvSearch, this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initialize(Bundle savedInstanceState) {

    }

    @Override
    protected void getSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void setSavedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    protected void endOperation() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Constant.Common.RESULT_CODE) {
            String content = data.getStringExtra(Temp.POINT_CONTENT.getContent());
            switch (requestCode) {
                case Constant.RequestCode.POINT:
                    switch (data.getIntExtra(Temp.POINT_TYPE.getContent(), -1)) {
                        case Constant.PointType.START:
                            if (TextUtils.equals(content, getString(R.string.my_location))) {
                                if (TextUtils.equals(stvPassPoint.getText(), getString(R.string.my_location))) {
                                    stvPassPoint.setText(null);
                                } else if (TextUtils.equals(stvEndPoint.getText(), getString(R.string.my_location))) {
                                    stvEndPoint.setText(null);
                                }
                            }
                            stvStartPoint.setText(content);
                            mStartLongitude = data.getDoubleExtra(Temp.LOCATION_LONGITUDE.getContent(), 0.0);
                            mStartLatitude = data.getDoubleExtra(Temp.LOCATION_LATITUDE.getContent(), 0.0);
                            LogUtil.print("-------mStartLongitude:" + mStartLongitude);
                            LogUtil.print("-------mStartLatitude:" + mStartLatitude);
                            break;
                        case Constant.PointType.PASS:
                            if (!TextUtils.equals(content, getString(R.string.pass_point))) {
                                if (TextUtils.equals(content, getString(R.string.my_location))) {
                                    if (TextUtils.equals(stvStartPoint.getText(), getString(R.string.my_location))) {
                                        stvStartPoint.setText(null);
                                    } else if (TextUtils.equals(stvEndPoint.getText(), getString(R.string.my_location))) {
                                        stvEndPoint.setText(null);
                                    }
                                }
                                stvPassPoint.setText(content);
                                mPassLongitude = data.getDoubleExtra(Temp.LOCATION_LONGITUDE.getContent(), 0.0);
                                mPassLatitude = data.getDoubleExtra(Temp.LOCATION_LATITUDE.getContent(), 0.0);
                            }
                            LogUtil.print("-------mPassLongitude:" + mPassLongitude);
                            LogUtil.print("-------mPassLatitude:" + mPassLatitude);
                            break;
                        case Constant.PointType.END:
                            if (TextUtils.equals(content, getString(R.string.my_location))) {
                                if (TextUtils.equals(stvStartPoint.getText(), getString(R.string.my_location))) {
                                    stvStartPoint.setText(null);
                                } else if (TextUtils.equals(stvPassPoint.getText(), getString(R.string.my_location))) {
                                    stvPassPoint.setText(null);
                                }
                            }
                            stvEndPoint.setText(content);
                            mEndLongitude = data.getDoubleExtra(Temp.LOCATION_LONGITUDE.getContent(), 0.0);
                            mEndLatitude = data.getDoubleExtra(Temp.LOCATION_LATITUDE.getContent(), 0.0);
                            LogUtil.print("-------mEndLongitude:" + mEndLongitude);
                            LogUtil.print("-------mEndLatitude:" + mEndLatitude);
                            break;
                    }
                    break;
            }
        }
    }
}
