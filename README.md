# cloud-admin

#### 介绍
{**以下是 Gitee 平台说明，您可以替换此简介**
Gitee 是 OSCHINA 推出的基于 Git 的代码托管平台（同时支持 SVN）。专为开发者提供稳定、高效、安全的云端软件开发协作平台
无论是个人、团队、或是企业，都能够用 Gitee 实现代码托管、项目管理、协作开发。企业项目请看 [https://gitee.com/enterprises](https://gitee.com/enterprises)}

#### 软件架构
软件架构说明

springboot
springcloud alibaba
nacos
seata
mybatis-plus

#### 第一次上传项目

1.  进入到本地需要往gitee上传的文件夹，右键点击选择git bash here。注意：如果此文件夹里有.get文件，请删除！
2. 输入命令 git init (为了给上传的文件夹添加.get 文件）
3. 输入命令 git remote add origin https://…get (后面的链接为gitee上的[克隆/下载] 的地址,为了给本地文件夹和gitee建立连接，记得替换，不要直接抄)
4. 输入命令git add . (注意命令后面有个“.”。将本地文件夹加入本地库)
5. 输入命令 git commit -m"xxx" (提交到本地库,"XXX"为提交备注或说明）
6. 输入命令 git push origin master ，成功后可在gitee上查看)。(补充一个强制提交代码到gitee上的命令git push -u origin master -f，能用git push origin master就不要用强制上传命令）


#### 更新项目

1. 输入命令git pull （先获取gitee上别人上传的代码）
2. 输入命令git add . (注意命令后面有个“.”。将本地文件夹加入本地库)
3. 输入命令 git commit -m"xxx" (提交到本地库,"XXX"为提交备注或说明）
4. 输入命令 git push origin master ，成功后可在gitee上查看，未成功很有可能是你没有在第一步输入命令git pull获取代码,导致代码冲突。(补充一个强制提交代码到gitee上的命令git push -u origin master -f，能用git push origin master就不要用强制上传命令）

#### 帮助文档
1. https://www.51mimu.com/html/news/1941.html
2. https://blog.csdn.net/qq_44732146/article/details/118469470?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_title~default-4.control&spm=1001.2101.3001.4242
3. https://blog.csdn.net/han949417140/article/details/116161114?ivk_sa=1024320u