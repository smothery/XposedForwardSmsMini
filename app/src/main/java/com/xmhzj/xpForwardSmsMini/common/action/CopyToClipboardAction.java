package com.xmhzj.xpForwardSmsMini.common.action;

import android.content.Context;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.xmhzj.xpForwardSmsMini.common.action.entity.SmsMsg;
import com.xmhzj.xpForwardSmsMini.common.constant.ConfigConst;
import com.xmhzj.xpForwardSmsMini.common.utils.ClipboardUtils;
import com.xmhzj.xpForwardSmsMini.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将验证码复制到剪切板
 */
public class CopyToClipboardAction extends RunnableAction {

    public CopyToClipboardAction(Context context, SmsMsg smsMsg, String keyword) {
        super(context, smsMsg, keyword);
    }

    @Override
    public Bundle action() {
        String body = smsMsg.getBody();
        String smsCode = getSmsCode(ConfigConst.codeRegex, keyword, body);
        if (StringUtils.isNotEmpty(smsCode)) {
            ClipboardUtils.copyToClipboard(context, smsCode);
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, String.format("【%s】已复制到剪贴板", smsCode), Toast.LENGTH_SHORT).show();
            });
        } else {
            new Handler(Looper.getMainLooper()).post(() -> {
                Toast.makeText(context, "未识别到验证码", Toast.LENGTH_SHORT).show();
            });
        }
        return null;
    }

    private static String getSmsCode(String codeRegex, String keyword, String content) {
        Pattern p = Pattern.compile(codeRegex);
        Matcher m = p.matcher(content);
        List<String> possibleCodes = new ArrayList<>();
        while (m.find()) {
            possibleCodes.add(m.group());
        }
        int size = possibleCodes.size();
        if (size == 0) {
            return "";
        }
        if (size == 1) {
            return possibleCodes.get(0);
        }
        String realCode = "";
        int minDistance = 0;
        for (String possibleCode : possibleCodes) {
            int distance = distanceToKeyword(keyword, possibleCode, content);
            if (minDistance == 0 || distance < minDistance) {
                minDistance = distance;
                realCode = possibleCode;
            }
        }
        return realCode;
    }

    /**
     * 计算可能的验证码与关键字的距离
     */
    private static int distanceToKeyword(String keyword, String possibleCode, String content) {
        int keywordIdx = content.indexOf(keyword);
        int possibleCodeIdx = content.indexOf(possibleCode);
        return Math.abs(keywordIdx - possibleCodeIdx);
    }
}
