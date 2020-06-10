CREATE TABLE `basic_information`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一主键',
    `serviceId`     int(10) NOT NULL COMMENT '应用标识',
    `instanceName`  varchar(150)   DEFAULT NULL COMMENT '实例名称',
    `appname`       varchar(20)    DEFAULT NULL COMMENT '应用名称',
    `address`       varchar(30)    DEFAULT NULL COMMENT '地址IP',
    `progress`      varchar(10)    DEFAULT NULL COMMENT '进程号',
    `host`          varchar(30)    DEFAULT NULL COMMENT 'host',
    `system`        varchar(10)    DEFAULT NULL COMMENT 'os',
    `language`      varchar(10)    DEFAULT NULL COMMENT '语言',
    `state`         varchar(5)     DEFAULT NULL COMMENT '状态',
    `online`        varchar(20)    DEFAULT NULL COMMENT '在线时长',
    `lastHeartbeat` datetime       DEFAULT NULL COMMENT '最后心跳',
    `startDate`     datetime       DEFAULT NULL COMMENT '启动时间',
    `instanceUUID`  varchar(64)    DEFAULT NULL COMMENT 'instanceUUID',
    `version`       varchar(20)    DEFAULT NULL COMMENT 'agent version',
    `hostIp`        varchar(50)    DEFAULT NULL COMMENT '物理机IP',
    PRIMARY KEY (`id`)
) ;
