package com.yjt.app.ui.listener.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface HeaderProvider {

    View getHeader(RecyclerView recyclerView, int position);

    void invalidate();
}
