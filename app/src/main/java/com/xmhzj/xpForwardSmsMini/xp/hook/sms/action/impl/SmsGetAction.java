package com.xmhzj.xpForwardSmsMini.xp.hook.sms.action.impl;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.xmhzj.xposed.forwardSmsMini.BuildConfig;
import com.xmhzj.xpForwardSmsMini.common.action.CallableAction;
import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.utils.StringUtils;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;

/**
 * 获取短信
 */
public class SmsGetAction extends CallableAction {

    public static final String SMS_MSG = "sms_msg";

    private Intent mSmsIntent;

    public SmsGetAction(SmsMsg smsMsg) {
        super(smsMsg);
    }

    public void setSmsIntent(Intent smsIntent) {
        mSmsIntent = smsIntent;
    }

    @Override
    public Bundle action() {
        return getSmsMsg();
    }

    private Bundle getSmsMsg() {
        mSmsMsg = SmsMsg.fromIntent(mSmsIntent);

        String sender = mSmsMsg.getSender();
        String msgBody = mSmsMsg.getBody();
        if (BuildConfig.DEBUG) {
            XLog.d("Sender: %s", sender);
            XLog.d("Body: %s", msgBody);
        } else {
            XLog.d("Sender: %s", StringUtils.escape(sender));
            XLog.d("Body: %s", StringUtils.escape(msgBody));
        }

        if (TextUtils.isEmpty(sender) || TextUtils.isEmpty(msgBody)) {
            return null;
        }

        mSmsMsg.setDate(System.currentTimeMillis());

        Bundle bundle = new Bundle();
        bundle.putParcelable(SMS_MSG, mSmsMsg);

        return bundle;
    }


}
