package com.yjt.app.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yjt.app.base.BaseApplication;
import com.yjt.app.constant.Constant;
import com.yjt.app.ui.base.BaseActivity;

public class ViewUtil {

    private static ViewUtil mViewUtil;

    private ViewUtil() {
        // cannot be instantiated
    }

    public static synchronized ViewUtil getInstance() {
        if (mViewUtil == null) {
            mViewUtil = new ViewUtil();
        }
        return mViewUtil;
    }

    public static void releaseInstance() {
        if (mViewUtil != null) {
            mViewUtil = null;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setViewBackground(View view, Drawable background) {
        if (VersionUtil.getInstance().isJellyBean()) {
            view.setBackground(background);
        } else {
            view.setBackgroundDrawable(background);
        }
    }

    public void setViewBackground(View view, int drawableId) {
        Drawable drawable = view.getContext().getResources().getDrawable(drawableId);
        setViewBackground(view, drawable);
    }

    public boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public boolean isInvisible(View view) {
        return view.getVisibility() == View.INVISIBLE;
    }

    public boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }

    public void setViewVisible(View view) {
        view.setVisibility(View.VISIBLE);
    }

    public void setViewGone(View view) {
        view.setVisibility(View.GONE);
    }

    public void setViewInvisible(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void releaseImageDrawable(ImageView imageView) {
        if (imageView == null)
            return;
        Drawable d = imageView.getDrawable();
        if (d != null)
            d.setCallback(null);
        imageView.setImageDrawable(null);
        setViewBackground(imageView, null);
    }

    public void releaseLayoutDrawable(View view) {
        if (view == null)
            return;
        Drawable d = view.getBackground();
        if (d != null)
            d.setCallback(null);
        setViewBackground(view, null);
    }

    public void setToolBar(final BaseActivity activity, int toolbarId, boolean isHomeButtonEnable) {
        if (activity != null && toolbarId != Constant.View.RESOURCE_DEFAULT) {
            Toolbar toolbar = findView(activity, toolbarId);
            activity.setSupportActionBar(toolbar);
            if (activity.getSupportActionBar() != null) {
                activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeButtonEnable);
                toolbar.setTitle(activity.getTitle());
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.onFinish("setNavigationOnClickListener");
                    }
                });
            }
        }
    }

    public <V> V findView(Activity activity, @IdRes int resId) {
        return (V) activity.findViewById(resId);
    }

    public <V> V findView(View rootView, @IdRes int resId) {
        return (V) rootView.findViewById(resId);
    }

    public <V> V findViewAttachOnclick(Activity activity, @IdRes int resId, View.OnClickListener onClickListener) {
        View view = activity.findViewById(resId);
        view.setOnClickListener(onClickListener);
        return (V) view;
    }

    public <V> V findViewAttachOnclick(View rootView, @IdRes int resId, View.OnClickListener onClickListener) {
        View view = rootView.findViewById(resId);
        view.setOnClickListener(onClickListener);
        return (V) view;
    }


    public boolean isScrollable(ViewGroup group) {
        int totalHeight = 0;
        for (int i = 0; i < group.getChildCount(); i++) {
            totalHeight += group.getChildAt(i).getMeasuredHeight();
        }
        return group.getMeasuredHeight() < totalHeight;
    }

    public boolean isScrollTop(RecyclerView recyclerView) {
        if (recyclerView != null && recyclerView.getChildCount() > 0) {
            if (recyclerView.getChildAt(0).getTop() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isScrollTop(ListView listView) {
        if (listView != null && listView.getChildCount() > 0) {
            if (listView.getChildAt(0).getTop() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isScrollTop(ExpandableListView listView) {
        if (listView != null && listView.getChildCount() > 0) {
            if (listView.getChildAt(0).getTop() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isScrollTop(ScrollView scrollView) {
        if (scrollView != null) {
            if (scrollView.getScrollY() > 0) {
                return false;
            }
        }
        return true;
    }

    public void toggleView(View view, boolean show) {
        if (show) {
            setViewVisible(view);
        } else {
            setViewGone(view);
        }
    }

    public void setText(TextView textView, String content, int imageId, int width, int height, int position, boolean clickable) {
        textView.setText(content);
        if (imageId != -1) {
            Drawable drawable = BaseApplication.getInstance().getResources().getDrawable(imageId);
            drawable.setBounds(0, 0, width, height);
            switch (position) {
                case Constant.View.DRAWABLE_TOP:
                    textView.setCompoundDrawables(null, drawable, null, null);
                    break;
                case Constant.View.DRAWABLE_LEFT:
                    textView.setCompoundDrawables(drawable, null, null, null);
                    break;
                case Constant.View.DRAWABLE_RIGHT:
                    textView.setCompoundDrawables(null, null, drawable, null);
                    break;
                case Constant.View.DRAWABLE_BOTTOM:
                    textView.setCompoundDrawables(null, null, null, drawable);
                    break;
                default:
                    textView.setCompoundDrawables(drawable, null, null, null);
                    break;
            }
        }
        textView.setClickable(clickable);
    }

    public void setText(TextView textView, CharSequence text, Typeface font) {
        if (text != null) {
            textView.setText(text);
            textView.setTypeface(font);
        } else {
            ViewUtil.getInstance().setViewGone(textView);
        }
    }

    public void setText(Button button, CharSequence text, Typeface font, View.OnClickListener listener) {
        setText(button, text, font);
        if (listener != null) {
            button.setOnClickListener(listener);
        }
    }

    public AlertDialog showAlertDialog(Activity activity, String title, String message,
                                       String posLabel, String negLabel,
                                       DialogInterface.OnClickListener posListener,
                                       DialogInterface.OnClickListener negListener,
                                       DialogInterface.OnDismissListener canListener) {
        if (activity != null && !activity.isFinishing()) {
            Dialog dialog = new AlertDialog.Builder(activity).setTitle(title)
                    .setMessage(message).setPositiveButton(posLabel, posListener)
                    .setNegativeButton(negLabel, negListener).show();
            if (canListener != null) {
                dialog.setOnDismissListener(canListener);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            return (AlertDialog) dialog;
        } else {
            return null;
        }
    }

    protected AlertDialog showAlertDialog(Activity activity, View view, String posLabel,
                                          String negLabel, DialogInterface.OnClickListener posListener,
                                          DialogInterface.OnClickListener negListener,
                                          DialogInterface.OnDismissListener canListener) {
        if (activity != null && !activity.isFinishing()) {
            if (view != null) {
                ViewGroup group = (ViewGroup) view.getParent();
                if (group != null) {
                    group.removeAllViews();
                }
            }
            Dialog dialog = new AlertDialog.Builder(activity).setView(view)
                    .setPositiveButton(posLabel, posListener)
                    .setNegativeButton(negLabel, negListener).show();
            if (canListener != null) {
                dialog.setOnDismissListener(canListener);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            return (AlertDialog) dialog;
        } else {
            return null;
        }
    }

    public ProgressDialog showProgressDialog(Activity activity, String title,
                                             String message, DialogInterface.OnCancelListener cancelListener, boolean cancelable) {
        if (activity != null && !activity.isFinishing()) {
            lockScreenOrientation(activity);
            Dialog dialog = ProgressDialog.show(activity, title, message, true,
                                                true, cancelListener);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);
            return (ProgressDialog) dialog;
        } else {
            return null;
        }
    }

    public void hideDialog(Dialog dialog, Activity activity) {
        if (dialog != null && dialog.isShowing() && activity != null && !activity.isFinishing()) {
            dialog.dismiss();
            unLockScreenOrientation(activity);
        }
    }

    public void hideDialog(DialogFragment dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setSystemUiVisibility(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
//                        View.SYSTEM_UI_FLAG_FULLSCREEN |
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE);

    }

    public void lockScreenOrientation(Activity activity) {
        Configuration newConfig = activity.getResources().getConfiguration();
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 横屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 竖屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_NO) {
            // 键盘没关闭。屏幕方向为横屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (newConfig.hardKeyboardHidden == Configuration.KEYBOARDHIDDEN_YES) {
            // 键盘关闭。屏幕方向为竖屏
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public void unLockScreenOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
