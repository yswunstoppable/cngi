#--------------------用户模块相关SQL操作--------------------#
#namespace("user")
#include("user/user.sql")
#end

#session相关的sql操作
#namespace("session")
#include("user/session.sql")
#end
#--------------------用户模块相关SQL操作--------------------#

#--------------------白名单相关SQL操作--------------------#
#namespace("whitelist")
#include("whitelist/whitelist.sql")
#end
#--------------------白名单相关SQL操作--------------------#

#--------------------联动中心相关SQL操作--------------------#
#namespace("boundary")
#include("center/boundary.sql")
#end

#namespace("command")
#include("center/command.sql")
#end
#--------------------联动中心相关SQL操作--------------------#

#--------------------感知源模块相关SQL操作--------------------#
#namespace("source")
#include("source/source.sql")
#end
#--------------------感知源模块相关SQL操作--------------------#

#--------------------定时模块相关SQL操作--------------------#
#namespace("timer")
#include("timer/timer.sql")
#end

#namespace("timerRelation")
#include("timer/timer_relation.sql")
#end

#namespace("timerRunnerLog")
#include("timer/timer_runner_log.sql")
#end
#--------------------定时模块相关SQL操作--------------------#

#--------------------日志模块相关SQL操作--------------------#
#namespace("attackSourceLog")
#include("log/attack_source_log.sql")
#end

#namespace("timerCommandLog")
#include("log/timer_command_log.sql")
#end
#--------------------日志模块相关SQL操作--------------------#

#--------------------预警阈值模块相关SQL操作--------------------#
#namespace("shreshold")
#include("shreshold/shreshold.sql")
#end

#--------------------预警阈值模块相关SQL操作--------------------#