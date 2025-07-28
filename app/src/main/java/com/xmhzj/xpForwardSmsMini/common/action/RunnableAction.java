package com.xmhzj.xpForwardSmsMini.common.action;

import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;

/**
 * Runnable + Action + Callable
 */
public abstract class RunnableAction extends CallableAction implements Runnable {

    public RunnableAction(SmsMsg smsMsg) {
        super(smsMsg);
    }

    @Override
    public void run() {
        call();
    }
}
