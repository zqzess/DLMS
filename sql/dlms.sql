/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : dlms

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 12/11/2021 17:50:32
*/
CREATE DATABASE dlms;
USE dlms;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for brace
-- ----------------------------
DROP TABLE IF EXISTS `brace`;
CREATE TABLE `brace`  (
  `brace` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职称名称',
  PRIMARY KEY (`brace`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of brace
-- ----------------------------
INSERT INTO `brace` VALUES ('副教授');
INSERT INTO `brace` VALUES ('博士研究生导师');
INSERT INTO `brace` VALUES ('教授');
INSERT INTO `brace` VALUES ('硕士研究生导师');
INSERT INTO `brace` VALUES ('讲师');
INSERT INTO `brace` VALUES ('辅导员');

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `classname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '班级名称',
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名称',
  PRIMARY KEY (`classname`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES ('临2002', '临床医学');
INSERT INTO `class` VALUES ('机1701', '机器人工程');
INSERT INTO `class` VALUES ('计1909', '计算机科学与技术');
INSERT INTO `class` VALUES ('计1910', '计算机科学与技术');
INSERT INTO `class` VALUES ('软1908', '软件工程');
INSERT INTO `class` VALUES ('软1910', '软件工程');

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院名称',
  PRIMARY KEY (`college`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES ('化学与工程学院');
INSERT INTO `college` VALUES ('医学院');
INSERT INTO `college` VALUES ('建筑与工程学院');
INSERT INTO `college` VALUES ('机械与动力学院');
INSERT INTO `college` VALUES ('生命科学与技术学院');
INSERT INTO `college` VALUES ('艺术学院');
INSERT INTO `college` VALUES ('计算机科学与技术学院');
INSERT INTO `college` VALUES ('车辆工程学院');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`department`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('后勤处');
INSERT INTO `department` VALUES ('国际部');
INSERT INTO `department` VALUES ('学工处');
INSERT INTO `department` VALUES ('教务处');

-- ----------------------------
-- Table structure for function
-- ----------------------------
DROP TABLE IF EXISTS `function`;
CREATE TABLE `function`  (
  `id` int(3) UNSIGNED ZEROFILL NOT NULL COMMENT '功能序号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '功能名称或描述',
  `type` int(11) NOT NULL COMMENT '所属对象',
  `state` int(1) NOT NULL COMMENT '功能状态0为开启，1为关闭',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of function
-- ----------------------------
INSERT INTO `function` VALUES (110, '教师查看个人信息', 1, 0);
INSERT INTO `function` VALUES (111, '教师修改个人信息', 1, 1);
INSERT INTO `function` VALUES (122, '教师创建修改表', 1, 0);
INSERT INTO `function` VALUES (210, '部门管理员管理人员', 2, 0);
INSERT INTO `function` VALUES (211, '部门管理员管理班级', 2, 0);
INSERT INTO `function` VALUES (310, '学院管理员管理部门', 3, 0);
INSERT INTO `function` VALUES (311, '学院管理员管理专业', 3, 1);
INSERT INTO `function` VALUES (1010, '学生查看个人信息', 0, 0);
INSERT INTO `function` VALUES (1011, '学生修改个人信息', 0, 0);
INSERT INTO `function` VALUES (1023, '已毕业学生查看个人信息', 0, 1);
INSERT INTO `function` VALUES (1024, '已毕业学生修改个人信息', 0, 1);
INSERT INTO `function` VALUES (1025, '未入学学生查看个人信息', 0, 1);
INSERT INTO `function` VALUES (1026, '未入学学生修改个人信息', 0, 1);

-- ----------------------------
-- Table structure for subject
-- ----------------------------
DROP TABLE IF EXISTS `subject`;
CREATE TABLE `subject`  (
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '专业名称',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学院',
  PRIMARY KEY (`subject`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of subject
-- ----------------------------
INSERT INTO `subject` VALUES ('临床医学', '医学院');
INSERT INTO `subject` VALUES ('机器人工程', '机械与动力学院');
INSERT INTO `subject` VALUES ('物联网工程技术', '计算机科学与技术学院');
INSERT INTO `subject` VALUES ('计算机科学与技术', '计算机科学与技术学院');
INSERT INTO `subject` VALUES ('车辆制造技术', '车辆工程学院');
INSERT INTO `subject` VALUES ('软件工程', '计算机科学与技术学院');

-- ----------------------------
-- Table structure for tablecontent
-- ----------------------------
DROP TABLE IF EXISTS `tablecontent`;
CREATE TABLE `tablecontent`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tableid` bigint(20) NOT NULL COMMENT '表格id',
  `userid` bigint(20) NOT NULL COMMENT '填写人id',
  `jsoncontent` json NOT NULL COMMENT '填写内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tablecontent
-- ----------------------------
INSERT INTO `tablecontent` VALUES (1, 1001, 2000000, '{\"object1\": \"里先锋\", \"object2\": \"男\", \"object3\": \"南京工业大学\", \"object4\": \"12345678\", \"object5\": \"计算机科学与技术\", \"object6\": \"计算机科学与技术学院\"}');
INSERT INTO `tablecontent` VALUES (2, 1004, 2000001, '{\"object1\": \"sss\", \"object2\": \"男\", \"object3\": \"委屈委屈恶趣味去问但\", \"object4\": \"1111111\", \"object5\": \"大撒大撒\", \"object6\": \"撒打算\"}');
INSERT INTO `tablecontent` VALUES (3, 1004, 2000002, '{\"object1\": \"里先锋\", \"object2\": \"男\", \"object3\": \"南京工业大学\", \"object4\": \"12345678\", \"object5\": \"计算机科学与技术\", \"object6\": \"计算机科学与技术学院\"}');
INSERT INTO `tablecontent` VALUES (4, 1001, 2000001, '{\"object1\": \"zdsd\", \"object2\": \"男\", \"object3\": \"fdcdc\", \"object4\": \"22312\", \"object5\": \"dscxc\", \"object6\": \"xzczc\"}');
INSERT INTO `tablecontent` VALUES (5, 1011, 2000001, '{\"object1\": \"xsx\", \"object2\": \"xsd\", \"object3\": \"xsadsa\", \"object4\": \"dasd\", \"object5\": \"sadas\", \"object6\": \"sdas\"}');

-- ----------------------------
-- Table structure for tableinfo
-- ----------------------------
DROP TABLE IF EXISTS `tableinfo`;
CREATE TABLE `tableinfo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '表格id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表格名称',
  `creator_id` bigint(20) NOT NULL COMMENT '创建人id',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `state` int(11) NOT NULL COMMENT '填写状态,0待发布，1已发布，2已截止',
  `college` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学院',
  `department` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门',
  `subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业',
  `classname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级',
  `type` int(11) NOT NULL COMMENT '0只发布给学生，1只发布给老师',
  `table_structure` json NOT NULL COMMENT '数据库结构',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10017 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tableinfo
-- ----------------------------
INSERT INTO `tableinfo` VALUES (1001, '测试表1', 2000000, '周群镇', '2021-11-08 10:02:43', 1, '', '教务处', '', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1003, '测试表3', 2000000, '周群镇', '2021-11-08 10:04:20', 2, '计算机科学与技术学院', '', '计算机科学与技术', '计1910', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1004, '测试表4', 2000000, '周群镇', '2021-11-08 10:04:57', 1, '计算机科学与技术学院', '', '计算机科学与技术', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1005, '测试表5', 2000000, '周群镇', '2021-11-08 10:04:57', 1, '计算机科学与技术学院', '', '计算机科学与技术', '', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1006, '测试表6', 2000000, '周群镇', '2021-11-08 11:29:42', 1, '计算机科学与技术学院', '', '计算机科学与技术', '', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1007, '测试表7', 2000000, '周群镇', '2021-11-08 11:32:09', 1, '', '教务处', '', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1008, '测试表8', 2000000, '周群镇', '2021-11-08 12:12:11', 1, '计算机科学与技术学院', '', '', '', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1009, '测试表9', 2000000, '周群镇', '2021-11-08 12:12:11', 1, '医学院', '', '', '', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1010, '测试表10', 2000000, '周群镇', '2021-11-08 12:15:51', 1, '计算机科学与技术学院', '', '', '', 0, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (1011, '测试表11', 2000000, '周群镇', '2021-11-08 12:36:50', 1, '计算机科学与技术学院', '', '', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (10013, '测试表13', 2000000, '周群镇', '2021-11-08 10:04:57', 1, '计算机科学与技术学院', '教务处', '计算机科学与技术', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"地址\", \"value\": \"object3\"}, {\"label\": \"电话\", \"value\": \"object4\"}, {\"label\": \"专业\", \"value\": \"object5\"}, {\"label\": \"学院\", \"value\": \"object6\"}]}');
INSERT INTO `tableinfo` VALUES (10015, '实机测试', 2000000, '周群镇', '2021-11-11 12:40:05', 1, '计算机科学与技术学院', '', '计算机科学与技术', '', 1, '{\"structure\": [{\"label\": \"姓名\", \"value\": \"object1\"}, {\"label\": \"性别\", \"value\": \"object2\"}, {\"label\": \"奖惩\", \"value\": \"object3\"}]}');
INSERT INTO `tableinfo` VALUES (10016, '游戏汇总', 2000000, '周群镇', '2021-11-11 12:43:34', 1, '计算机科学与技术学院', '', '计算机科学与技术', '', 0, '{\"structure\": [{\"label\": \"平常打游戏吗\", \"value\": \"object1\"}, {\"label\": \"哪种类型的游戏\", \"value\": \"object2\"}, {\"label\": \"举一个你最喜欢玩的游戏\", \"value\": \"object3\"}]}');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学号工号，纯数字',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
  `peopleid` varchar(255) DEFAULT NULL,
  `gender` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '性别',
  `college` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '学院',
  `department` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '部门',
  `subject` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '专业',
  `classname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '班级',
  `brace` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '职称',
  `state` int NOT NULL COMMENT '在职情况或入学情况,0在职，1离职，2未入学，3已入学，4毕业',
  `type` int NOT NULL COMMENT '身份,0学生，1教师，2部门管理员，3学院管理员，4学校管理员',
  `phone` bigint DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '通讯地址',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2000008 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (2000000, 'zqzess', null, '男', '计算机科学与技术学院', '', '', '', '', 0, 4, 0, null, '0210');
INSERT INTO `users` VALUES (2000001, 'scy', null, '男', '计算机科学与技术学院', '', '计算机科学与技术', '', '博士研究生导师', 0, 1, 0, null, '1234');
INSERT INTO `users` VALUES (2000002, 'ljl', '1321243124124123123xad', '男', '计算机科学与技术学院', '', '计算机科学与技术', '计1910', '', 3, 0, 11111111111, null, '1234');
INSERT INTO `users` VALUES (2000003, 'lxf', null, '男', '', '教务处', '', '', '', 0, 2, null, null, '1234');
INSERT INTO `users` VALUES (2000004, 'yhw', null, '男', '机械与动力学院', '', '机器人工程', '机1701', '', 4, 0, null, null, '1234');
INSERT INTO `users` VALUES (2000005, 'xxxj', null, '女', '计算机科学与技术学院', '', '', '', '', 0, 3, null, null, '1234');
INSERT INTO `users` VALUES (2000006, 'jjx', null, '女', '医学院', '', '临床医学', '临2002', '', 0, 1, null, null, '1234');
INSERT INTO `users` VALUES (2000007, 'xwed', null, '男', '医学院', '', '临床医学', '', '', 0, 1, 0, '', '1234');


SET FOREIGN_KEY_CHECKS = 1;
