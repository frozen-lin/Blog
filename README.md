# Blog
## 本项目是基于SSM框架的简单易上手的个人博客系统，适合ssm初学者练手使用。    
预览地址:前台地址 :http://134.175.88.157 后台地址:http://134.175.88.157/admin    管理员默认测试账号:admin 密码:admin123，有兴趣的童鞋可以去测试玩玩。

## 使用到的技术及项目环境：
tomcat7，maven3.5，java8，mysql，redis，SSM框架， 富文本编辑器wangEditor，前端框架:前台bootstrap，后台layui(使用两个前端框架的目的就是练手)，ajax，实现restful风格等。

## 实现的功能有：
前台的评论增删、文章题目搜索、注册、登录、cookie自动登录、联系博主（使用博主的小号邮箱给博主邮箱发邮件），最新的三条评论展示。

## 后台的分类管理、文章管理、友链管理、轮播图管理\评论管理。

## 数据库是很简单的6张表：
文章表、轮播图表、分类表、评论表、友链表和用户表。

## redis作为缓存的存储策略：
hash："categoryHash"（存储全部分类）、 "articleHash"（存储全部文章）、 "linkHash"（存储全部友链）、"commentHash"（存储全部评论）、"carouselHash"（存储全部轮播图）。

list："article"+articleId（存储该文章下的评论。本想将评论存至所属的article的List中，但是发现同时提交多次评论会出现线程安全问题，导致评论被覆盖的现象。）

value："username:"+username(存储最近登录的用户，10天过期，过期后需要再从数据库中查找登录)、"mailNum"(存储的是前台发的邮件次数，后台点击邮件可清零。)

## 修改tomcat使用的编码格式：

默认情况下，tomcat使用的的编码方式：iso8859-1。

在tomcat下的conf/server.xml文件：

找到如下代码：    
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
这段代码规定了Tomcat监听HTTP请求的端口号等信息。

可以在这里添加一个属性：URIEncoding="UTF-8"，即可让tomcat以UTF-8的编码处理get请求。

修改后：
<Connector port="8080"  protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" URIEncoding="UTF-8" />

不添加的话该项目的get请求会出现乱码。
