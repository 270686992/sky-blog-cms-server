-- Sky-Blog 核心业务数据表,不包括 lin-cms 内置表,内置表在 schema.sql 文件中
-- 导入 sql 时需将两个 sql 文件(schema.sql, sky_blog.sql)中的数据表一起导入

-- ----------------------------
-- 文章分类表
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章分类 ID,自增主键',
  `priority`       INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章分类权重,权重值越低,展示的位置越上',
  `online`         TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '文章分类的上线状态: 1-上线,0-下线',
  `name`           VARCHAR(50) NOT NULL COMMENT '文章分类名称',
  `icon`           VARCHAR(255) DEFAULT NULL COMMENT '文章分类图标 url',
  `create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`    DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`),
  KEY `del_priority_idx` (`delete_time`, `priority`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '文章分类表';

-- ----------------------------
-- 文章表
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章 ID,自增主键',
  `category_id`    INT(10) UNSIGNED NOT NULL COMMENT '文章所属分类的 ID',
  `title`          VARCHAR(50) NOT NULL COMMENT '文章标题',
  `description`    VARCHAR(255) DEFAULT NULL COMMENT '文章简述',
  `cover_image`    VARCHAR(255) DEFAULT NULL COMMENT '文章封面图 url',
  `publish_state`  TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章发布状态: 0-私密,1-发布',
  `views`          INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章阅读量',
  `priority`       INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章权重,权重值越低,展示的位置越上',
  `kind`           TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章类型: 0-转载,1-原创',
  `comment_number` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '文章评论数量',
  `enable_comment` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '文章评论开启状态: 1-允许评论,0-不允许评论',
  `create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`    DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`),
  KEY `category_id_del_idx` (`category_id`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '文章表';

-- ----------------------------
-- 文章内容表
-- ----------------------------
DROP TABLE IF EXISTS `article_content`;
CREATE TABLE IF NOT EXISTS `article_content` (
    `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容 ID,自增主键',
    `article_id`  INT(10) UNSIGNED NOT NULL COMMENT '文章内容所属文章的 ID',
    `content`     MEDIUMTEXT NOT NULL COMMENT '文章内容',
    `create_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `delete_time` DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `article_id_del_idx` (`article_id`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '文章内容表';

-- ----------------------------
-- 标签表
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签 ID,自增主键',
  `name`        VARCHAR(30) NOT NULL COMMENT '标签名称',
  `online`      TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '标签的上线状态: 1-上线,0-下线',
  `create_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time` DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '标签表';

-- ----------------------------
-- 标签和文章的中间表
-- ----------------------------
DROP TABLE IF EXISTS `tag_article`;
CREATE TABLE IF NOT EXISTS `tag_article` (
  `id`         INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签和文章间关系的 ID,自增主键',
  `tag_id`     INT(10) UNSIGNED NOT NULL COMMENT '标签 ID',
  `article_id` INT(10) UNSIGNED NOT NULL COMMENT '文章 ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tag_id_article_id_idx` (`tag_id`, `article_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '标签和文章的中间表';

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer`
(
    `id`          INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户 ID,自增主键',
    `username`    VARCHAR(20) NOT NULL COMMENT '用户名,用于登录使用,不可重复,业务代码处理时需注意',
    `email`       VARCHAR(50) NOT NULL COMMENT '用户邮箱,可用来登录和回复评论或留言时使用,不可重复,业务代码处理时需注意',
    `nickname`    VARCHAR(20) NOT NULL COMMENT '用户的昵称,不可重复,业务代码处理时需注意',
    `state`       TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '用户状态: 1-正常,0-冻结',
    `avatar`      VARCHAR(255) DEFAULT NULL COMMENT '用户头像 url',
    `create_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time` DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `delete_time` DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_del_idx` (`username`, `delete_time`),
    UNIQUE KEY `email_del_idx` (`email`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '用户表';

-- ----------------------------
-- 用户授权信息表
-- ----------------------------
DROP TABLE IF EXISTS `customer_identity`;
CREATE TABLE IF NOT EXISTS `customer_identity`
(
    `id`            INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '授权信息 ID,自增主键',
    `customer_id`   INT(10) UNSIGNED NOT NULL COMMENT '授权信息所属用户的 ID',
    `identity_type` VARCHAR(30) NOT NULL COMMENT '登录类型(手机号,邮箱,用户名或第三方应用名称(微信,微博等)',
    `identifier`    VARCHAR(100) NOT NULL COMMENT '标识(手机号,邮箱,用户名或第三方应用的唯一标识)',
    `credential`    VARCHAR(100) NOT NULL COMMENT '密码凭证(站内的保存密码,站外的不保存或保存 token)',
    `question`      VARCHAR(100) DEFAULT NULL COMMENT '找回密码问题',
    `answer`        VARCHAR(100) DEFAULT NULL COMMENT '找回密码答案',
    `create_time`   DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
    `update_time`   DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
    `delete_time`   DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
    PRIMARY KEY (id),
    UNIQUE KEY `customer_id_identifier_identity_type_del_idx` (`customer_id`, `identifier`, `identity_type`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '用户授权信息表';

-- ----------------------------
-- 文章评论表
-- 一级评论: 对文章的评论
-- 二级评论: 对文章下的评论的回复评论,这里包括二种: 回复的是一级评论,回复的是二级评论
-- 查询文章下的一级评论: SELECT * FROM `comment` WHERE `article_id` = 1 AND `root` = 1 AND `delete_time` IS NULL ORDER BY `id` DESC;
-- 查询评论下的回复: SELECT * FROM `comment` WHERE `parent_id` = 1 AND `root` = 0 AND `delete_time` IS NULL ORDER BY `id` DESC;
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE IF NOT EXISTS `comment` (
  `id`                 INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论 ID,自增主键',
  `article_id`         INT(10) UNSIGNED NOT NULL COMMENT '评论所属文章的 ID',
  `customer_id`        INT(10) UNSIGNED NOT NULL COMMENT '评论所属用户的 ID',
  `parent_id`          INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父级评论 ID',
  `parent_customer_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父级评论所属用户的 ID',
  `reply_id`           INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '被回复的评论的 ID',
  `reply_customer_id`  INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '被回复的评论所属用户的 ID',
  `root`               TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '标记当前评论是否为一级评论: 1-一级评论,0-二级评论;回复文章的是一级评论,其它的都是二级评论',
  `content`            VARCHAR(500) NOT NULL COMMENT '评论内容/回复内容',
  `ip`                 VARCHAR(50) NOT NULL COMMENT '评论者当前 IP',
  `address`            VARCHAR(100) NOT NULL COMMENT '评论者当前 IP 解析出的详细地址',
  `display_status`     TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否显示当前评论: 0-不显示, 1-显示',
  `create_time`        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`        DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`),
  KEY `article_id_root_del_idx` (`article_id`, `root`, `delete_time`),
  KEY `parent_id_root_del_idx` (`parent_id`, `root`, `delete_time`),
  KEY `customer_id_del_idx` (`customer_id`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '文章评论表';

-- ----------------------------
-- 留言表
-- ----------------------------
DROP TABLE IF EXISTS `leave_message`;
CREATE TABLE IF NOT EXISTS `leave_message` (
  `id`                 INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '留言 ID,自增主键',
  `customer_id`        INT(10) UNSIGNED NOT NULL COMMENT '留言所属用户的 ID',
  `parent_id`          INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父级留言 ID',
  `parent_customer_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '父级留言所属用户的 ID',
  `reply_id`           INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '被回复的留言的 ID',
  `reply_customer_id`  INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '被回复的留言所属用户的 ID',
  `root`               TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '标记当前留言是否为一级留言: 1-一级留言,0-二级留言',
  `content`            VARCHAR(500) NOT NULL COMMENT '留言内容/回复内容',
  `ip`                 VARCHAR(50) NOT NULL COMMENT '留言者当前 IP',
  `address`            VARCHAR(100) NOT NULL COMMENT '留言者当前 IP 解析出的详细地址',
  `display_status`     TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否显示当前留言: 0-不显示,1-显示',
  `create_time`        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`        DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`        DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`),
  KEY `parent_id_root_del_idx` (`parent_id`, `root`, `delete_time`),
  KEY `customer_id_del_idx` (`customer_id`, `delete_time`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '留言表';

-- ----------------------------
-- 友链表
-- ----------------------------
DROP TABLE IF EXISTS `friend_link`;
CREATE TABLE IF NOT EXISTS `friend_link` (
  `id`             INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '友链 ID,自增主键',
  `priority`       INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '友链权重,权重值越低,展示的位置越上',
  `kind`           TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '友链类型: 0-友情链接,1-推荐链接',
  `display_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否显示当前友链: 0-不显示,1-显示',
  `name`           VARCHAR(100) NOT NULL COMMENT '友链名称',
  `url`            VARCHAR(255) NOT NULL COMMENT '友链 url',
  `icon`           VARCHAR(255) DEFAULT NULL COMMENT '友链图标 url',
  `description`    VARCHAR(255) DEFAULT NULL COMMENT '友链描述',
  `create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`    DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`),
  KEY `kind_del_priority_idx` (`kind`, `delete_time`, `priority`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '友链表';

-- ----------------------------
-- 站点信息表
-- 业务处理时限制只能添加一个站点信息记录
-- ----------------------------
DROP TABLE IF EXISTS `web_site_info`;
CREATE TABLE IF NOT EXISTS `web_site_info` (
  `id`                      INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '站点信息 ID,自增主键',
  `name`                    VARCHAR(50) NOT NULL COMMENT '站点名称',
  `title`                   VARCHAR(255) NOT NULL COMMENT '站点个性标题',
  `signature`               VARCHAR(255) NOT NULL COMMENT '站点个性签名',
  `copyright_time`          VARCHAR(50) NOT NULL COMMENT '版权时间,例如: 2020-2020',
  `version_number`          VARCHAR(50) NOT NULL COMMENT '站点版本号,例如: V1.0.0',
  `icp_number`              VARCHAR(50) NOT NULL COMMENT 'ICP 备案号',
  `nickname`                VARCHAR(50) NOT NULL COMMENT '站点作者昵称,用于展示在前台',
  `author_avatar`           VARCHAR(255) NOT NULL COMMENT '站点作者头像 url',
  `icon`                    VARCHAR(255) NOT NULL COMMENT '站点图标 url',
  `commentator_avatar`      VARCHAR(255) NOT NULL COMMENT '评论者/留言者默认头像 url,用户没有设置头像时使用默认头像',
  `application_explain`     VARCHAR(500) DEFAULT NULL COMMENT '友链申请说明',
  `about_me_explain`        VARCHAR(500) DEFAULT NULL COMMENT '关于我的说明',
  `about_site_explain`      VARCHAR(500) DEFAULT NULL COMMENT '关于站点的说明',
  `about_copyright_explain` VARCHAR(500) DEFAULT NULL COMMENT '关于版权的说明',
  `special_explain`         VARCHAR(500) DEFAULT NULL COMMENT '特别说明',
  `announcement`            VARCHAR(500) DEFAULT NULL COMMENT '站点公告',
  `create_time`             DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `update_time`             DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '更新时间',
  `delete_time`             DATETIME(3) DEFAULT NULL COMMENT '删除时间,软删除',
  PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  COMMENT = '站点信息表';

-- ----------------------------
-- 站点信息表默认数据
-- ----------------------------
BEGIN;
INSERT INTO `web_site_info` VALUES (1, '博击长空', '简单的事情重复做,重复的事情坚持做,坚持的事情用心做。', '宝剑锋从磨砺出，梅花香自苦寒来。', '2020-2020', 'V1.0.0', '琼ICP备20000534号-1', '踏雪彡寻梅', 'http://bjck.xilikeli.cn/7087d584fc744806a12b7534e71435f6.jpg', 'http://bjck.xilikeli.cn/a562353f00e7421080ee9ce4842f310b.jpg', 'https://bjck.xilikeli.cn/f3f49f0806014b79a4cdaf68f087ce7c.jpg', '交换友链可发布您的友链信息在留言板:\n<br/>\n友链模板如下:\n<br/>\n名称：你的网站名称(如: 博击长空)\n<br/>\n网址：你的网站地址(如: https://www.xilikeli.cn)\n<br/>\n图标：你的网站图标地址(如: http://bjck.xilikeli.cn/a562353f00e7421080ee9ce4842f310b.jpg)\n<br/>\n描述：你的网站描述(如: 简单的事情重复做,重复的事情坚持做,坚持的事情用心做。)\n<br/>\n<br/>\n申请提交后若无其它原因将在 24 小时内审核,如超过时间还未通过,请私信我.(各种额外因素)', '一个热爱编程的大三学生~~~\n<br/>\n希望借助本站点分享我的文章能够帮助到各位朋友,如有写的不好的地方也望各位大佬不吝啬教 O(∩_∩)O\n<br/>\n可通过该邮箱联系到我: <a href=\"mailto:sky_txxunmei@163.com\">sky_txxunmei@163.com</a>', '本站点创建于 2020 年 3 月,主要用于编写个人在关于编程方面的一些总结文章,希望能够帮助到大家。\n<br/>\n本站结构:\n<br/>\n前端: 使用了燕十三大佬开源的博客模板: <a href=\"https://gitee.com/yssgit/yan_shisan_blog_template\" target=\"_blank\">模板链接</a>\n<br/>\n后端: 采用 SpringBoot + MySql\n<br/>\n服务器: 本站采用阿里云提供的服务器 ESC。', '本站采用「<a href=\"https://creativecommons.org/licenses/by-nc/4.0/deed.zh\" target=\"_blank\">署名-非商业性使用 4.0 国际 (CC BY-NC 4.0)</a>」创作共享协议。只要在使用时注明出处，那么您可以可以对本站所有原创内容进行转载、节选、二次创作，但是您不得对其用于商业目的。', '本站文章仅代表个人观点，和任何组织或个人无关。\n<br/>\n本站文章有时可能会使用网上搜索到的图片,如有侵权,请联系我的邮箱进行删除。', '欢迎光临本站，希望本站的博文能够帮助到各位朋友 O(∩_∩)O ,谢谢支持,祝您生活愉快。', NOW(), NOW(), NULL);
COMMIT;