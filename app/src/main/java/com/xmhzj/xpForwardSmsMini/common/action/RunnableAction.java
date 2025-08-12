package com.xmhzj.xpForwardSmsMini.common.action;

import android.content.Context;
import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;

/**
 * Runnable + Action + Callable
 */
public abstract class RunnableAction extends CallableAction implements Runnable {

    public RunnableAction(Context context, SmsMsg smsMsg, String keyword) {
        super(context, smsMsg, keyword);
    }

    @Override
    public void run() {
        call();
    }
}
