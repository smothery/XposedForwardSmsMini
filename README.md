# XposedForwardSmsMini

### Xposed 转发短信模块 mini 无界面版

基于 XposedForwardSms (https://github.com/xtjun/XposedForwardSms) 制作

* 原版 XposedForwardSms 作者已停更，不支持 Android 14

* fock 版增加了对 Android 14 的支持 (https://github.com/XiaoMiHongZhaJi/XposedForwardSms) 


### 此 mini 版的修改如下

* 去除原版的配置界面，改为在代码中配置
* 无需后台常驻，无需自启动权限，无需占用额外资源，简单快捷实现功能
* 支持 Android 14

### 制作此 mini 版的原因如下：

* 转发短信时，http 请求是由被 hook 的 app `(phone / sms)` 发出的，而转发的配置信息是在模块 app 中储存的，这就需要被 hook 的 app 去读取模块 app 中的配置信息

* 而随着 Android 不断更新，app 权限越收越紧，如今 Android 想要实现`读取其他 app 的配置`这个功能，只能通过`ContentProvider`方案

* 这一方案需要模块 app 实现`ContentProvider`接口，被 hook 的 app 实现`ContentResolver`接口

* 这样一来，不但使代码变得复杂，资源消耗增大，更大的问题是：`ContentProvider`的调用过程，容易被国产定制系统的省电相关功能识别为`链式唤醒`，进而被阻止，导致转发失效。

* 因此，最简单直接的方法就显而易见了：`直接将配置写到 hook 代码中`，避免跨 app 读取配置，连 app 的配置界面都省了

* 缺点就是：缺少灵活性，不能方便地修改配置信息，想要修改需要重新打包和签名。

* 但是短信转发的配置信息，一般情况下也不需要什么变动，调试好之后基本上也不会改变了，所以感觉还好？

* 如果你不厌其烦地看完了，麻烦点个`小星星`吧~

### 使用方法：

* 配置类：[ConfigConst.java](app%2Fsrc%2Fmain%2Fjava%2Fcom%2Fxmhzj%2FxpForwardSmsMini%2Fcommon%2Fconstant%2FConfigConst.java)

* 需要先将其中的配置项修改为自己的配置信息，然后重新打包和签名 apk

* 如果不清楚每项配置的含义，可参考原版 XposedForwardSms 中的设置项

* 如果没有本地 SDK 环境，也可以使用 GitHub 的`Action`功能打包 apk，相关配置已在 [workflows](.github%2Fworkflows) 中写好

* 之后下载 apk 到本地，使用`MT管理器`等工具对 apk 签名安装即可
