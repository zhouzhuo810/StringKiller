# StringKiller
一个简洁的Android Studio插件。用于一键将layout文件夹中的text字符串资源提取到strings.xml中。


### 插件下载地址：[点这里](https://github.com/zhouzhuo810/StringKiller/blob/master/StringKiller.jar)


## 应用场景
安卓开发过程中，大部分开发者为了偷懒，直接将字符串资源写在布局文件中，
这种方式主要有如下缺点：
- 导入工程中文容易出现乱码；
- 国际化不利于统一翻译；
- 修改字符串内容时不能快速定位;

因此，为了解决这一系列问题，本人开发了这个插件。

## 功能特性
- 目前支持android:text和android:hint两个属性提取字符串。
- 支持智能跳过@string/xxx.
- 资源采用【"布局文件名称"+"text"/"hint_text"+序号】的命名方式，
如【@string/activity_main_text_0】代表activity_main.xml文件中
第1个android:text字符串资源。
- 如果strings.xml文件中有内容给，新提取的字符串会自动追加在后面，不会覆盖。
- 默认快捷键：Alt+3

## 示例图片

![demo](https://github.com/zhouzhuo810/StringKiller/blob/master/stringkiller.gif)


## License

```
Copyright © zhouzhuo810

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
