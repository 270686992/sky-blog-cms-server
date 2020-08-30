<h1 align="center">
  Sky-Blog-CMS 服务端
</h1>

<p align="center">

  <a href="https://spring.io/" rel="nofollow">
  <img src="https://img.shields.io/badge/spring%20boot-2.2.5.RELEASE-green" alt="spring boot" data-canonical-src="https://img.shields.io/badge/spring%20boot-2.2.5.RELEASE-green" style="max-width:100%;">
  </a>
  
  <a href="https://pypi.org/project/Lin-CMS/" rel="nofollow">
  <img src="https://img.shields.io/badge/mybatis--plus-3.3.0-yellow" alt="mybatis-plus" data-canonical-src="https://img.shields.io/badge/mybatis--plus-3.3.0-yellow" style="max-width:100%;">
  </a>
  
  <a href="https://mybatis.plus/" rel="nofollow">
  <img src="https://img.shields.io/badge/license-MIT-lightgrey.svg" alt="LISENCE" data-canonical-src="https://img.shields.io/badge/license-MIT-lightgrey.svg" style="max-width:100%;">
  </a>
  
</p>

<blockquote align="center">
 本项目的 CMS 后端基于 Lin-CMS 进行开发, <em>Lin-CMS</em> 是林间有风团队经过大量项目实践所提炼出的一套<strong>内容管理系统框架</strong>。<br>
 Lin-CMS 可以有效的帮助开发者提高 CMS 的开发效率。
</blockquote>

<p align="center">
  <a href="##预览">预览</a>&nbsp;|&nbsp;
  <a href="##简介">简介</a>&nbsp;|&nbsp;
  <a href="##如何使用">如何使用</a>
</p>

## 预览

### 线上 demo

- 博客地址: [www.xilikeli.cn](https://www.xilikeli.cn)

- CMS 端地址: [admin.xilikeli.cn](https://admin.xilikeli.cn)
    - 游客用户的账号密码: guest, 123456

## 简介

Sky-Blog-CMS 是基于 Lin CMS 开发的博客后台管理系统，CMS 前端请访
问 [CMS 前端仓库](https://github.com/270686992/sky-blog-cms-frontend)

博客前端查看请访问 [博客前端仓库](https://github.com/270686992/sky-blog-frontend)

博客前端的服务端查看请访问 [博客前端的服务端仓库](https://github.com/270686992/sky-blog-server)

Lin CMS 地址: [https://github.com/TaleLin](https://github.com/TaleLin)

## 如何使用

1. 将项目依赖引入之后, 首先使用下列语句创建数据库(数据库只需引入一次, CMS 端和 C 端使用同一个数据库)

    ```sql
    drop database if exists sky_blog;
    create database sky_blog default character set utf8mb4 collate utf8mb4_general_ci;
    use sky_blog;
    ```

2. 接着在此数据库中导入项目 resources 目录下的 schema.sql 和 sky_blog.sql 文件。

3. 接着将 application.yml 中的邮箱配置和项目业务相关配置中的配置填写为自己的配置。

4. 接着将 application-dev.yml 和 application-prod.yml 中的数据源配置和 reids 相关的配置切换为自己的配置。

5. 接着将项目 test 目录下的代码生成器类 `CodeGenerator` 中的作者名称和数据源配置切换为自己的配置即可使用该代码生成器。(可选,如果需要使用该生成器生成代码扩展项目的话,该代码生成器为 Lin CMS 自带)

6. 最后将项目 `src/main/java/io/github/talelin/latticy/extension/file` 下的 config.yml 中的文件上传配置换为自己的配置以及在 `UploaderConfiguration` 类中切换为对应的上传方式。(文件上传相关信息可参阅 Lin CMS 的官方文档)

7. 完成以上步骤即可启动 CMS 后端应用,默认本地运行的端口号为 5800,CMS 默认登录账号密码为 root,123456。

