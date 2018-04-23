package com.example.latte.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.latte.R;
import com.example.latte.delegates.LattDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by Tession on 2018/4/23.
 * 仅仅作为一个容器
 * 注意，这里的SupportActivity是fragmentation库里的activity
 * 以后必然会有一个主activity来继承它，所以这里用abstract
 */

public abstract class ProxyActivity extends SupportActivity{

    /**
     * 用来返回根delegate
     * @return
     */
    public abstract LattDelegate setRootDelegate();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    private void initContainer(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState){
        //一般视图的跟容器都是FrameLayout,这里我就使用ContentFrameLayout
        final ContentFrameLayout container = new ContentFrameLayout(this);
        //这里需要传入一个id，但不能直接传数字，虽然可以运行，还是按规范来写比较好，建一个ids的资源文件
        container.setId(R.id.delegate_container);

        setContentView(container);
        if (savedInstanceState == null) {
            //第一次加载    loadRootFragment这是fragmentation里的SupportActivity独有的方法，不要搞错了
            loadRootFragment(R.id.delegate_container,setRootDelegate());
        }
    }

    //这里是单activity架构，所以这个activity退出了之后，整个应用也就退出了，这里可以做一些垃圾回收的工作
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //虽然我们知道写了不一定执行，因为gc不是立刻执行
        System.gc();
        System.runFinalization();
    }
}
