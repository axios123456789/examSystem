create database examdb;

CREATE TABLE `t_student`  (
                              `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                              `username` varchar(255) ,
                              `password` varchar(255) ,
                              `tel` varchar(255),
                              `email` varchar(255) ,
                              `stuNum` varchar(255) ,
                              `createTime` datetime ,
                              `nickName` varchar(255)
)


CREATE TABLE `t_user`  (
                           `id` int(11)  PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                           `username` varchar(255) ,
                           `password` varchar(255) ,
                           `email` varchar(255) ,
                           `tel` varchar(11) ,
                           `sex` int(1),
                           `headImg` varchar(255) ,
                           `createTime` datetime ,
                           `type` int(2)
)


CREATE TABLE `t_role`(
                         `id` int(11)  PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                         `name` varchar(255)
)


    # 试卷等级  困难 一般 简单
CREATE TABLE `t_level`(
                          `id` int(2)  PRIMARY KEY  NOT NULL AUTO_INCREMENT,
                          `name` varchar(255)
)

    # 试卷表
CREATE TABLE `t_paper`  (
                            `id` int(11)   PRIMARY KEY NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) NULL DEFAULT NULL COMMENT '试卷名',
                            `levelid` int(2) NULL DEFAULT NULL  COMMENT '试卷水平',
                            `startTime` datetime NULL DEFAULT NULL COMMENT '开始时间',
                            `endTime` datetime NULL DEFAULT NULL COMMENT '结束时间',
                            `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间'
)

    # 题目类型表  选择 填空 判断 简答
CREATE TABLE `t_questiontype`  (
                                   `id` int(2)  PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                   `name` varchar(255)  NULL DEFAULT NULL
)

    # 简答题老师阅卷

# 题库表
CREATE TABLE `t_questionbank`  (
                                   `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                   `questionTitle` text  NULL COMMENT '题目',
                                   `questionAnswer` varchar(255)  NULL DEFAULT NULL COMMENT '正确答案  （判断题： 0或者选择 1）',
                                   `q_typeid` int(5) NULL DEFAULT NULL COMMENT '题目类型',
                                   `status` int(5) NULL DEFAULT NULL COMMENT '状态    0 删除 1有效',
                                   `createTime` datetime NULL DEFAULT NULL,
                                   `grade` int(10) NULL DEFAULT NULL COMMENT '分数'
)

    # 选择题目的选项
CREATE TABLE `t_xzt_options`  (
                                  `id` int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
                                  `optionA` varchar(255) NULL DEFAULT NULL,
                                  `optionB` varchar(255)  NULL DEFAULT NULL,
                                  `optionC` varchar(255)  NULL DEFAULT NULL,
                                  `optionD` varchar(255)  NULL DEFAULT NULL,
                                  `questionId` int(11) NULL DEFAULT NULL
)

create table `t_paper_question` (
                                    `id` int primary key not null auto_increment,
                                    `paperId` int null,
                                    `questionId` int null
)

create table `t_scoredetail` (
                                 `id` int primary key not null auto_increment,
                                 `stuId` int null,
                                 `paperId` int null,
                                 `questionId` int null,
                                 `questionTitle` varchar(255) null,
                                 `q_typeid` int null,
                                 `questionAnswer` varchar(255) null,
                                 `questionScore` varchar(255) null,
                                 `correntAnswer` varchar(255) null,
                                 `correntScore` varchar(255) null
)
