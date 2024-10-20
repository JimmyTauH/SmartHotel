项目运行：
前端：vscode打开或者你自己喜欢的前端编写工具；
1：npm run serve即可
2：如果1有问题先npm install ，再npm run serve，遇到项目图片有问题在管理端重新上传即可

后端：
1：先数据库新建数据库（我的name是hotelms），运行SQL文件，运行成功看到库里表出来即可；
2：更改qpplication.yaml配置
密码修改成自己数据库的登录密码；
数据库名字url: jdbc:mysql://localhost:3306/hotelms? 我的数据库name是hotelms，你可以创建和我一样，如果不一样记得改成你自己的；

3：SpringbootApplication，启动项启动，项目第一次打开记得多刷新刷新maven把工程的配置都刷新好，有时候不是项目问题是还没准备好；

管理员账号：admin   admin
正常账号：可以自己注册，也可以用我的登录： sanqi    sanqi