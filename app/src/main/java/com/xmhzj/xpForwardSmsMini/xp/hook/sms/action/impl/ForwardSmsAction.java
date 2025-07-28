package com.xmhzj.xpForwardSmsMini.xp.hook.sms.action.impl;

import android.os.Bundle;

import com.xmhzj.xpForwardSmsMini.common.action.RunnableAction;
import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.constant.ConfigConst;
import com.xmhzj.xpForwardSmsMini.common.constant.Const;
import com.xmhzj.xpForwardSmsMini.common.utils.StringUtils;
import com.xmhzj.xpForwardSmsMini.common.utils.XHttpUtils;
import com.xmhzj.xpForwardSmsMini.common.utils.XLog;

/**
 * 记录验证码短信
 */
public class ForwardSmsAction extends RunnableAction {

    public ForwardSmsAction(SmsMsg smsMsg) {
        super(smsMsg);
    }

    @Override
    public Bundle action() {
        forwardSmsMsg(mSmsMsg);
        return null;
    }

    private void forwardSmsMsg(SmsMsg smsMsg) {
        XLog.d("start forward: ");
        String title = String.format("卡 %s 收到 %s 的新消息", smsMsg.getSubId(), smsMsg.getSender());
        String content = String.format("\n%s\n\n来自设备【%s】", smsMsg.getBody(), ConfigConst.deviceId);
        try {
            String res = null;
            switch (ConfigConst.channelType) {
                case Const.CHANNEL_GET:
                    res = XHttpUtils.custGet(ConfigConst.getUrl, title, content);
                    break;
                case Const.CHANNEL_POST:
                    res = XHttpUtils.custPost(ConfigConst.postUrl, ConfigConst.postType, ConfigConst.postContent, title, content);
                    break;
                case Const.CHANNEL_DING:
                    res = XHttpUtils.postDingTalk(ConfigConst.dingKey, title, content);
                    break;
                case Const.CHANNEL_BARK:
                    res = XHttpUtils.getBark(ConfigConst.barkUrl, title, content);
                    break;
                case Const.CHANNEL_WXCP:
                    long now = System.currentTimeMillis();
                    long expDate = Const.wxcpExpDate;
                    String token = Const.wxcpToken;
                    if (now > (expDate + 3600000)){
                        String wxcpToken = XHttpUtils.getWxcpToken(ConfigConst.wxCorpid, ConfigConst.wxCorpsecret);
                        if (StringUtils.isNotEmpty(wxcpToken)) {
                            token = wxcpToken;
                            Const.wxcpExpDate = now;
                            Const.wxcpToken = wxcpToken;
                        }
                    }
                    res = XHttpUtils.postWxcpMsg(token, ConfigConst.wxAgentid, ConfigConst.wxTouser, title, content);
                    break;
                default:
                    break;
            }
//            XLog.d("forward result: " + res);
        } catch (Exception e) {
            XLog.e("forward error: " + e.getMessage());
        }
    }
}
