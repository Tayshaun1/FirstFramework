package com.example.latte.app;

import com.joanzapata.iconify.Icon;

/**
 * Created by Tession on 2018/4/21.
 */

public enum EcIcons implements Icon {
    //这里的代码，根据ExampleApp的FontAwesomeModule源码类似
    icon_scan('\ue606'),
    icon_ali_pay('\ue61a');

    private char charcter;

    EcIcons(char charcter) {
        this.charcter = charcter;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return charcter;
    }
}
