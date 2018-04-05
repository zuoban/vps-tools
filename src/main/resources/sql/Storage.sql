-- auto Generated on 2018-04-05 01:42:19
-- DROP TABLE IF EXISTS `storage`;
drop database if exists toy_storage;
drop user if exists 'storage'@'localhost';
create database toy_storage CHARACTER SET utf8 COLLATE utf8_general_ci;
use toy_storage;
create user 'storage'@'localhost' identified by 'zuoban123456';
grant all privileges on storage.* to 'storage'@'localhost';
flush privileges;

CREATE TABLE storage(
    `id` INTEGER(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `key` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '唯一key',
    `name` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '文件名',
    `type` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '文件类型',
    `size` BIGINT NOT NULL DEFAULT -1 COMMENT '文件大小',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    `path` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '路径',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '存储表';
