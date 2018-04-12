CREATE DATABASE  IF NOT EXISTS `ssc_block_chain`;
USE `ssc_block_chain`;

--
-- Table structure for table `tb_ssc_account`
--
DROP TABLE IF EXISTS `tb_ssc_account`;
CREATE TABLE `tb_ssc_account` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `wallet_name` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account_name` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_address` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `public_key` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `offline_wallet` varchar(800) COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_address` (`user_address`),
  KEY `idx_account_name` (`account_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_block`
--
DROP TABLE IF EXISTS `tb_ssc_block`;
CREATE TABLE `tb_ssc_block` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `block_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '区块hash',
  `block_num` bigint(20) unsigned NOT NULL COMMENT '块号',
  `block_size` bigint(16) unsigned NOT NULL COMMENT '块大小（字节）',
  `previous` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '前一个块块id',
  `trx_digest` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '块中交易的摘要',
  `prev_secret` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上轮secret',
  `next_secret_hash` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '本轮secret的hash',
  `random_seed` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '随机种子',
  `signee` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '产块者',
  `block_time` datetime NOT NULL COMMENT '产块时间',
  `trans_num` int(11) unsigned NOT NULL COMMENT '区块内交易数量',
  `trans_amount` bigint(20) unsigned NOT NULL COMMENT '区块内交易总金额',
  `trans_fee` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '区块内交易总手续费',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_block_id` (`block_id`) USING BTREE,
  KEY `idx_block_num` (`block_num`) USING BTREE,
  KEY `idx_signee` (`signee`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_contract_info`
--
DROP TABLE IF EXISTS `tb_ssc_contract_info`;
CREATE TABLE `tb_ssc_contract_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `contract_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约ID\r\n',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '合约名称',
  `coin_type` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bytecode` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字节码',
  `hash` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '字节码hash',
  `owner` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约拥有者公钥',
  `owner_address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约拥有者地址',
  `owner_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '合约拥有者名称',
  `type` tinyint(4) DEFAULT '0' COMMENT '0是其他 1是资产',
  `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '合约描述',
  `reg_time` datetime NOT NULL COMMENT '注册时间',
  `reg_trx_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '注册合约的交易id',
  `balance` bigint(20) unsigned NOT NULL,
  `circulation` bigint(20) DEFAULT NULL COMMENT '合约币发行总量',
  `status` int(3) unsigned NOT NULL DEFAULT '1' COMMENT '注册状态\r\n0 - 销毁\r\n1 - 临时\r\n2 - 永久',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_contract_id` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_contract_abi`
--
DROP TABLE IF EXISTS `tb_ssc_contract_abi`;
CREATE TABLE `tb_ssc_contract_abi` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `contract_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约id',
  `abi_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_abi_id` (`contract_id`),
  CONSTRAINT `fk_contract_id` FOREIGN KEY (`contract_id`) REFERENCES `tb_ssc_contract_info` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_contract_config`
--
DROP TABLE IF EXISTS `tb_ssc_contract_config`;
CREATE TABLE `tb_ssc_contract_config` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contract_id` varchar(100) NOT NULL COMMENT '合约id',
  `contract_name` varchar(255) NOT NULL COMMENT '合约名称',
  `url_index` tinyint(4) DEFAULT NULL COMMENT 'url代表的含义',
  `url` varchar(255) NOT NULL COMMENT 'url地址',
  `url_name` varchar(32) DEFAULT NULL COMMENT 'url名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_url_index_url_name` (`url_index`,`url_name`),
  KEY `idx_contract_id` (`contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合约链接配置信息';

--
-- Table structure for table `tb_ssc_contract_event`
--
DROP TABLE IF EXISTS `tb_ssc_contract_event`;
CREATE TABLE `tb_ssc_contract_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `contract_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约id',
  `event` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '事件名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_event_id` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_contract_storage`
--
DROP TABLE IF EXISTS `tb_ssc_contract_storage`;
CREATE TABLE `tb_ssc_contract_storage` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `contract_id` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合约id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `type` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_storage_id` (`contract_id`(191))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_transaction`
--
DROP TABLE IF EXISTS `tb_ssc_transaction`;
CREATE TABLE `tb_ssc_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trx_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '交易id',
  `block_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '区块hash',
  `block_num` bigint(20) NOT NULL COMMENT '块号',
  `block_position` int(11) NOT NULL COMMENT '交易在块中的位置',
  `trx_type` int(11) NOT NULL DEFAULT '0' COMMENT '0 - 普通转账\r\n1 - 代理领工资\r\n2 - 注册账户\r\n3 - 注册代理\r\n10 - 注册合约\r\n11 - 合约充值\r\n12 - 合约升级\r\n13 - 合约销毁\r\n14 - 调用合约\r\n15 - 合约出账\r\n',
  `coin_type` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `contract_id` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `from_acct` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '发起账号',
  `from_addr` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '发起地址',
  `to_acct` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '接收账号',
  `to_addr` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '接收地址',
  `sub_address` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` bigint(30) unsigned NOT NULL DEFAULT '0' COMMENT '金额',
  `fee` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '手续费\r\n如果是合约交易，包含gas消耗，注册保证金等',
  `memo` text COLLATE utf8mb4_unicode_ci COMMENT '备注',
  `trx_time` datetime NOT NULL COMMENT '交易时间',
  `called_abi` varchar(6000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调用的合约函数，非合约交易该字段为空',
  `abi_params` varchar(6000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '调用合约函数时传入的参数，非合约交易该字段为空',
  `event_type` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `event_param` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `extra_trx_id` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果交易id\r\n仅针对合约交易',
  `is_completed` tinyint(4) unsigned DEFAULT '0' COMMENT '合约调用结果\r\n0 - 成功\r\n1- 失败',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_block_id` (`block_id`),
  KEY `idx_block_num` (`block_num`) USING BTREE,
  KEY `idx_from_acct` (`from_acct`) USING BTREE,
  KEY `idx_from_addr` (`from_addr`) USING BTREE,
  KEY `idx_to_acct` (`to_acct`) USING BTREE,
  KEY `idx_to_addr` (`to_addr`) USING BTREE,
  KEY `idx_trx_time` (`trx_time`) USING BTREE,
  KEY `idx_trx_id` (`trx_id`) USING BTREE,
  KEY `idx_sub_address` (`sub_address`) USING BTREE,
  KEY `idx_contract_id` (`contract_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_transaction_ex`
--
DROP TABLE IF EXISTS `tb_ssc_transaction_ex`;
CREATE TABLE `tb_ssc_transaction_ex` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `trx_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '结果交易id',
  `orig_trx_id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '原始交易id',
  `from_acct` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '发起账户',
  `from_addr` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '发起地址',
  `to_acct` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '接收账户',
  `to_addr` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '接收地址',
  `amount` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '金额',
  `fee` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '手续费',
  `memo` varchar(3000) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `trx_time` datetime DEFAULT NULL,
  `trx_type` tinyint(4) NOT NULL DEFAULT '15',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_orig_trx_id` (`orig_trx_id`) USING BTREE,
  KEY `idx_trx_id` (`trx_id`) USING BTREE,
  KEY `idx_from_addr_to_addr` (`from_addr`,`to_addr`),
  KEY `idx_from_addr` (`from_addr`),
  KEY `idx_to_addr` (`to_addr`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_ssc_withdraw`
--
DROP TABLE IF EXISTS `tb_ssc_withdraw`;
CREATE TABLE `tb_ssc_withdraw` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `trx_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易id',
  `wallet_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '钱包名',
  `asset_symbol` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资产类型',
  `from_acct` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发起账号',
  `from_addr` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '发起地址',
  `to_acct` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '接收账号',
  `to_addr` varchar(70) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接收地址',
  `amount` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '金额',
  `memo` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  `block_trx_id` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_trx_id` (`trx_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Table structure for table `tb_user_address`
--
DROP TABLE IF EXISTS `tb_user_address`;
CREATE TABLE `tb_user_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `account_name` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_address` varchar(70) COLLATE utf8mb4_unicode_ci NOT NULL,
  `balance` bigint(20) NOT NULL DEFAULT '0',
  `coin_type` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `contract_id` varchar(70) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trans_num` int(11) NOT NULL DEFAULT '0' COMMENT '交易笔数',
  `last_trx_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '1999-12-31 16:00:00',
  PRIMARY KEY (`id`),
  KEY `idx_account_name` (`account_name`),
  KEY `idx_user_address` (`user_address`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
