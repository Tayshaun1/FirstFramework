package com.example.administrator.firstframe;

import com.example.latte.activities.ProxyActivity;
import com.example.latte.delegates.LattDelegate;

/**
 * @author Administrator
 */
public class ExampleActivity extends ProxyActivity {

    @Override
    public LattDelegate setRootDelegate() {
        return new ExampleDelegate();
    }
}
