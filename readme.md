<div align="center">
  <img src="assets/cover_photo.png" width="400px" alt="BIThesis Icon">

## ☁ Cloud Ladder 程序设计语言

[![](https://flat.badgen.net/github/status/BIT-SYS/cloud-ladder)]()
[![](https://flat.badgen.net/github/contributors/BIT-SYS/cloud-ladder)]()
[![](https://badgen.net/github/open-issues/BIT-SYS/cloud-ladder)]()
[![](https://flat.badgen.net/github/license/BIT-SYS/cloud-ladder?color=purple)]()

Cloud Ladder 是一门面向云计算的教学语言。

</div>


## 内容
<details>
<summary>点击以展开</summary>

- [项目结构](#项目结构)
- [依赖](#依赖)
- [运行](#运行)
  * [IDEA](#idea)
- [编译与测试](#编译与测试)
  * [Windows](#windows)
  * [*nix & MAC OS](#*nix-%26-mac-os)
- [更新](#更新)
- [相关文档](#相关文档)
- [贡献者](#贡献者)
- [许可证](#许可证)
</details>


## 项目结构

```
.
├── readme.md               # 你所看到的文件
├── LICENSE                 # GPL3 许可证
├── .github/workflows/      # Github Action
├── scripts/                # 你所看到的文件
├── src/                    # 项目源代码
└── ...
```

## 依赖

- JDK 1.8

## 运行

### IDEA

```
# 直接执行这个类的 main 函数
src/test/java/ImageApiTester.java
```

## 编译与测试

### Windows

*如果你安装了最新版 gradle，也可以用 `gradle` 代替以下命令。*

构建项目:

```bash
./gradlew.bat build
```

测试项目:

```bash
./gradlew.bat test
./gradlew.bat testASTListener

```

### *nix & MAC OS

*如果你安装了最新版 gradle，也可以用 `gradle` 代替以下命令。*

构建项目:

```bash
./gradlew build
```

测试项目:

```bash
./gradlew test
./gradlew testASTListener

```

##更新
在其中添加了CLText可以对docx文件，doc文件，txt文件进行读取与存储
在测试文档之中使用了临时的CLAudio使用了可以播放多种声音的jl库，但是，经过商量决定使用javax库
在CLRtEnvironment中更改了filecall时的正则表达式。
在CLUtilFileUtils中加入了utf-8的规定避免了中文乱码。
在build.gradle中增加了对应的库名称，规定了使用utf-8和java版本

## 相关文档

- **[Current State of Cloud Ladder —— 2020年春 Cloud Ladder 初版实现心得分享](https://cloud-ladder.fkynjyq.com)**

## 贡献者

*本项目的存在离不开以下所有贡献者的辛勤贡献:*

[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/0)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/0)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/1)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/1)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/2)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/2)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/3)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/3)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/4)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/4)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/5)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/5)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/6)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/6)[![](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/images/7)](https://sourcerer.io/fame/fky2015/BIT-SYS/cloud-ladder/links/7)



## 许可证

基于 [The GNU General Public License v3.0](LICENSE) 发布。

