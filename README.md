# oa
一、介绍

1、本项目为90%开源项目，用户可以用户开发学习，用户可自主开发除工作流以外的业务系统。

2、感兴趣欢迎您关注我的微信公众号：https://mp.weixin.qq.com/s/oB7wp11ZCLaKIFc67zG7VA

二、软件架构
本项目JDK8x64+SpringBoot+MyBatis+Redis+Durid+Beetl的框架组合，自研工作流引擎，支持可视化表单设计与流程设计。支持分布式部署。功能完善能够满足中大型企业办公需要。本项目自2020-08-17日起，为全面支持mysql8以上版本，启用SpringBoot 2.3x的版本。同时由于多数兄弟反应，代码编译时间太长，故重新设计了新的代码结构，望大家理解，若有需要的源代码被打到了jar中，可与我们联系，我们可以视情况提供。

三、安装教程

1、mvn install lib/目录中的jar 文件

  mvn  install:install-file  -DgroupId=cyunsoft.bean  -DartifactId=cyunsoft-bean  -Dversion=0.0.1-SNAPSHOT  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/cyunsoft-bean-0.0.1-SNAPSHOT.jar

  mvn  install:install-file  -DgroupId=cyunsoft.bi  -DartifactId=cyunsoft-bi  -Dversion=0.0.1-SNAPSHOT  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/cyunsoft-bi-0.0.1-SNAPSHOT.jar

  mvn  install:install-file  -DgroupId=cyunsoft.common  -DartifactId=cyunsoft-common  -Dversion=0.0.1-SNAPSHOT  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/cyunsoft-common-0.0.1-SNAPSHOT.jar

  mvn  install:install-file  -DgroupId=cyunsoft.coreservice  -DartifactId=cyunsoft-coreservice  -Dversion=0.0.1-SNAPSHOT  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/cyunsoft-coreservice-0.0.1-SNAPSHOT.jar

  mvn  install:install-file  -DgroupId=cyunsoft.imservice  -DartifactId=cyunsoft-imservice  -Dversion=0.0.1-SNAPSHOT  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/cyunsoft-imservice-0.0.1-SNAPSHOT.jar

  mvn  install:install-file  -DgroupId=com.oracle  -DartifactId=ojdbc8  -Dversion=12.2.0.1  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/ojdbc8.jar

  mvn  install:install-file  -DgroupId=com.dingtalk.open  -DartifactId=taobao-sdk-java-auto  -Dversion=1479188381469-20200218  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/taobao-sdk-java-auto_1479188381469-20200218.jar

  mvn  install:install-file  -DgroupId=com.zhuozhengsoft  -DartifactId=pageoffice  -Dversion=4.6.0.4  -Dpackaging=jar  -Dfile=E:/ideaWorkSpace/oa/lib/pageoffice4.6.0.4.jar

2、安装MYSQL8.0.21版本 后导入lib/mysql.sql

3、运行cyunsoft-appservice中的AppGo.java 即可启动项目。

四、使用说明

演示地址：http://oa.cyunsoft.com 账户：admin 密码：123456

若有问题请联系：

QQ：1946098085

WX：y1946098085 

邮件：xiaobo2458@163.com

五、开源宗旨

1、本项目主要用于开发者了解企业办系统的基本功能，共同开发适合本国国情的工作流引擎。

2、技术交流群QQ:733902044 群内不定时的发放技术文档。

3、开源项目不容易，大家感觉可以加个星。
 
### 欢迎关注全栈开发私房菜，每天进步一点点

![二维码](https://mmbiz.qpic.cn/mmbiz_jpg/6Nme2xEYxU6TP2Dicn0XEibZgq4jBLYqm3Sb5qP9l4SicWry0LCibch9gkwZ8BMwzwEsgDE4HUicHve6QHAEKQNc5bg/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

### **扫描二维码关注【全栈开发私房菜】**

![支付宝](https://mmbiz.qlogo.cn/mmbiz_jpg/6Nme2xEYxU5vpdLchtBYM2UjPdzvjySQZq0sJoVvmbTsjeP6LXLIvEqJz4sOvgPSQszjjJCuMNbg0ZrcSMCyCQ/0?wx_fmt=jpeg)
![微信](https://mmbiz.qlogo.cn/mmbiz_jpg/6Nme2xEYxU5vpdLchtBYM2UjPdzvjySQrzEeGUx4naoTpBHaOW6EicC1NUTIIv1ovJWPgziaXoFiawM4XCRia1CDPA/0?wx_fmt=jpeg)
