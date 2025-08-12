package com.xmhzj.xpForwardSmsMini.common.constant;

public class ConfigConst {


    // 总开关
    public static boolean isEnabled = true;

    // 是否仅转发验证码短信
    public static boolean filterEnable = false;

    // 是否复制验证码到剪贴板开关
    public static boolean copyToClipboard = true;

    // 详细日志
    public static boolean isVerboseLogMode = false;

    // 设备标识
    public static String deviceId = "yourDeviceId";

    // 推送通道选择
    public static String channelType = Const.CHANNEL_GET;

    // get Url (channelType = Const.CHANNEL_GET)
    public static String getUrl = "https://msg.example.com/{{title}}/{{content}}";

    // post Url (channelType = Const.CHANNEL_POST)
    public static String postUrl = "https://msg.example.com/";

    // post 类型
    public static String postType = Const.POST_TYPE_JSON;

    // post 请求内容
    public static String postContent = "{\"title\": {{title}}, \"content\": {{content}}}";

    // 钉钉配置 (channelType = Const.CHANNEL_DING)
    public static String dingKey = "dingKey";

    // Bark 配置 (channelType = Const.CHANNEL_BARK)
    public static String barkUrl = "barkUrl";

    // 企业微信企业 ID (channelType = Const.CHANNEL_WXCP)
    public static String wxCorpid = "wxCorpid";

    // 企业微信应用 AgentId
    public static String wxAgentid = "wxAgentid";

    // 企业微信应用 Secret
    public static String wxCorpsecret = "wxCorpsecret";

    // 企业微信推送 UID
    public static String wxTouser = "wxTouser";

    // 验证码正则表达式
    public static String codeRegex = "[a-zA-Z0-9]{4,8}";

    // 验证码过滤关键词
    public static String filterKeywords = "验证码|校验码|检验码|确认码|激活码|动态码|安全码|验证代码|校验代码|检验代码|激活代码|确认代码|动态代码|安全代码|登入码|认证码|识别码|短信口令|动态密码|交易码|上网密码|随机码|动态口令|驗證碼|校驗碼|檢驗碼|確認碼|激活碼|動態碼|驗證代碼|校驗代碼|檢驗代碼|確認代碼|激活代碼|動態代碼|登入碼|認證碼|識別碼|Code|code|CODE|word|WORD|Word";

}