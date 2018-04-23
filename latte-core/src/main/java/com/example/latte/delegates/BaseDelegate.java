package com.example.latte.delegates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by Tession on 2018/4/23.
 * 不希望之后new出它的实例，所以用抽象类
 */

public abstract class BaseDelegate extends SwipeBackFragment {

    private Unbinder mUnbinder = null;

    /**
     * 可以传入布局id也可以是一个view,所以这里用object来接受是最好的，这里是写框架的技巧
     *
     * @return
     */
    public abstract Object setLayout();

    /**
     * 强制之类实现这个方法
     *
     * @param savedInstanceState
     * @param rooView            根视图
     */
    public abstract void onBindView(@Nullable Bundle savedInstanceState, View rooView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = null;
        if (setLayout() instanceof Integer) {
            //这里是返回布局id
            rootView = inflater.inflate((Integer) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        }
        if (rootView != null) {
            //绑定我们的资源
            mUnbinder = ButterKnife.bind(this, rootView);
            onBindView(savedInstanceState, rootView);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
