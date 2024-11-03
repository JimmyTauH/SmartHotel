# 四用户版本代码骨架

项目运行：
前端：vscode打开或者你自己喜欢的前端编写工具；
1：npm run serve即可
2：如果1有问题先npm install ，再npm run serve，遇到项目图片有问题在管理端重新上传即可

后端：
1：确保使用正确的远程数据库配置文件。HotelMSEnd/target/classes/application.yml

​	两用户初始版本使用hotelms数据库，四用户版本使用hotelms4数据库。

3：在IDE中启动SpringbootApplication.java，启动项启动，项目第一次打开记得多刷新刷新maven把工程的配置都刷新好，有时候不是项目问题是还没准备好。



登录使用：

管理员账号：admin   admin （只允许后台添加）
普通客户账号示例：sanqi    sanqi    （可以通过界面自行注册） 
前台账号： recep recep
保洁账号： server1 123456

如果需要查看/修改数据库，可用mysql连接42.194.239.205:3306上的数据库,用户名为lighthouse【两用户是hotelms，四用户是hotelms4】，尽量不要修改别人的记录。【注意是mysql不是ssh!】

mysql -u lighthouse -h lighthouse -p
然后输入密码123456即可



### 说明：
这是四用户的基本软件骨架。原本两用户的版本在Tag: TwoUser下。

该骨架未合并最新的LuoYang&GeYang的修改，也未合并其他任何人的修改。

该版本可以下载直接运行。