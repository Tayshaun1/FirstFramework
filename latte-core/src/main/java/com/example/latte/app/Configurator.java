package com.example.latte.app;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tession on 2018/4/21.
 * 配置文件的存储和获取
 */

public class Configurator {

    /**
     * 因为我们要存储的可能是各种东西，所以这里用object，灵活性就比较高了
     * 这里不用WeakHashMap是因为这个集合会被内存回收，但是配置项一般是伴随着整个app的生命周期的
     */
    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();

    /**
     * 图片框架
     */
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();

    public static Configurator getInstance() {
        return Holder.configurator;
    }

    private Configurator() {
        //这时初始化必然没有完成
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    final HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    /**
     * 设置配置完成
     */
    public final void configure() {
        initIcons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    private static class Holder {
        private static Configurator configurator = new Configurator();
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    /**
     * 初始化字体 , 这是个通用的东西，所以在初始化是就应该加载好，所以就放到configure中
     */
    private void initIcons() {
        //如果里面有字体了
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            //第一个已经被我初始化了,所以我们要从1开始
            for (int i = 0; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    /**
     * 加入自己的图片库
     * @return
     */
    public Configurator withIcon(IconFontDescriptor icon){
        ICONS.add(icon);
        return this;
    }

    /**
     * 说一个思想
     * 在我们写类变量或者方法变量时，尽量让他的变量不可变性达到最大化
     */
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    /**
     * 返回需要的配置
     *
     * @param key
     * @param <T>
     * @return
     */
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key);
    }
}
