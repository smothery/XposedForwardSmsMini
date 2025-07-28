package com.xmhzj.xpForwardSmsMini.common.action;

import android.os.Bundle;

import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;

import java.util.concurrent.Callable;

/**
 * Action + Callable
 */
public abstract class CallableAction implements Action<Bundle>, Callable<Bundle> {

    protected SmsMsg mSmsMsg;

    public CallableAction(SmsMsg smsMsg) {
        this.mSmsMsg = smsMsg;
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
