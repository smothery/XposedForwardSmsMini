package com.xmhzj.xpForwardSmsMini.common.action;

import android.content.Context;
import android.os.Bundle;

import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;

import java.util.concurrent.Callable;

/**
 * Action + Callable
 */
public abstract class CallableAction implements Action<Bundle>, Callable<Bundle> {

    protected Context context;

    protected SmsMsg smsMsg;

    protected String keyword;

    public CallableAction(Context context, SmsMsg smsMsg, String keyword) {
        this.context = context;
        this.smsMsg = smsMsg;
        this.keyword = keyword;
    }

    @Override
    public Bundle call() {
        try {
            return action();
        } catch (Throwable t) {
            XLog.e("Error in CallableAction#call()", t);
            return null;
        }
    }
}
