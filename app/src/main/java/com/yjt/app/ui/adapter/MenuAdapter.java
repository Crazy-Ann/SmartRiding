package com.yjt.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.yjt.app.model.Menu;
import com.yjt.app.ui.adapter.holder.MenuHolder;
import com.yjt.app.ui.base.BaseViewBinder;

/**
 * Created by yjt on 2016/8/18.
 */

public class MenuAdapter extends FixedStickyHeaderAdapter<Menu, MenuHolder> {
    
    public MenuAdapter(Context ctx, BaseViewBinder binder, boolean groupable) {
        super(ctx, binder, groupable);
    }

    @Override
    protected void onBindHeaderOrFooter(RecyclerView.ViewHolder holder, Object object) {
        super.onBindHeaderOrFooter(holder, object);
    }
    
    
}
