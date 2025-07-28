package com.xmhzj.xpForwardSmsMini.xp;

import android.util.Log;

import com.xmhzj.xposed.forwardSmsMini.BuildConfig;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;
import com.xmhzj.xpForwardSmsMini.xp.hook.BaseHook;
import com.xmhzj.xpForwardSmsMini.xp.hook.sms.SmsHandlerHook;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookEntry implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    boolean verboseLog = true;

    private final List<BaseHook> mHookList = new ArrayList<BaseHook>(){{
        add(new SmsHandlerHook());//InBoundsSmsHandler Hook
    }};

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        for (BaseHook hook : mHookList) {
            if (hook.hookInitZygote()) {
                hook.initZygote(startupParam);
            }
        }

        try {
            XSharedPreferences xsp = new XSharedPreferences(BuildConfig.APPLICATION_ID);
            if (verboseLog) {
                XLog.setLogLevel(Log.VERBOSE);
            } else {
                XLog.setLogLevel(BuildConfig.LOG_LEVEL);
            }
        } catch (Throwable t) {
            XLog.e("", t);
        }
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        for (BaseHook hook : mHookList) {
            if (hook.hookOnLoadPackage()) {
                hook.onLoadPackage(lpparam);
            }
        }
    }
}
