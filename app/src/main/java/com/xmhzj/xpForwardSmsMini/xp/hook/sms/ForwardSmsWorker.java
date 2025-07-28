package com.xmhzj.xpForwardSmsMini.xp.hook.sms;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xmhzj.xpForwardSmsMini.common.constant.ConfigConst;
import com.xmhzj.xposed.forwardSmsMini.BuildConfig;
import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.utils.StringUtils;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;
import com.xmhzj.xpForwardSmsMini.xp.hook.sms.action.impl.ForwardSmsAction;
import com.xmhzj.xpForwardSmsMini.xp.hook.sms.action.impl.SmsGetAction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForwardSmsWorker {
    private final Intent mSmsIntent;
    private final ScheduledExecutorService mScheduledExecutor;

    ForwardSmsWorker(Intent smsIntent) {
        mSmsIntent = smsIntent;
        mScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }

    public void parse() {
        if (!ConfigConst.isEnabled) {
            XLog.i("xposedForwardSmsMini disabled, exiting");
            return;
        }
        if (ConfigConst.isVerboseLogMode) {
            XLog.setLogLevel(Log.VERBOSE);
        } else {
            XLog.setLogLevel(BuildConfig.LOG_LEVEL);
        }

        //获取短信
        SmsGetAction smsGetAction = new SmsGetAction(null);
        smsGetAction.setSmsIntent(mSmsIntent);
        ScheduledFuture<Bundle> smsParseFuture = mScheduledExecutor.schedule(smsGetAction, 0, TimeUnit.MILLISECONDS);

        SmsMsg smsMsg;
        try {
            Bundle parseBundle = smsParseFuture.get();
            if (parseBundle == null) {
                XLog.e("smsParseBundle empty");
                return;
            }
            smsMsg = parseBundle.getParcelable(SmsGetAction.SMS_MSG);
        } catch (Exception e) {
            XLog.e("Error occurs when get SmsGetAction call value", e);
            return;
        }

        //过滤短信内容
        boolean filterFlag = true;

        if(ConfigConst.filterEnable){
            String filterKeywords = ConfigConst.filterKeywords;
            if (StringUtils.isNotEmpty(filterKeywords)){
                Matcher matcher = Pattern.compile(filterKeywords).matcher(smsMsg.getBody());
                if (!matcher.find()) {
                    filterFlag = false;
                }
            }
        }

        //转发短信
        if (filterFlag) {
            new Thread(new ForwardSmsAction(smsMsg)).start();
        }
    }
}
