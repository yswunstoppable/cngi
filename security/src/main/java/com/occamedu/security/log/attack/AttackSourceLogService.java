package com.occamedu.security.log.attack;

import cn.fabrice.jfinal.service.BaseService;
import com.occamedu.security.common.module.AttackSourceLog;
import com.occamedu.security.web.vo.AttackInfo;
import com.occamedu.security.web.vo.LmAttackInfo;

import java.math.BigInteger;
import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 20:25
 * @description
 */
public class AttackSourceLogService extends BaseService<AttackSourceLog> {
    public AttackSourceLogService() {
        super("attackSourceLog.", AttackSourceLog.class, "attack_source_log");
    }

    public boolean add(long timerLogId, String attackIp, String attackAddress, int attackNumber, int attackType) {
        AttackSourceLog log = new AttackSourceLog();
        log.setTimerLogId(BigInteger.valueOf(timerLogId));
        log.setAttackIp(attackIp);
        log.setAttackAddress(attackAddress);
        log.setAttackNumber(attackNumber);
        log.setAttackType(attackType);
        return log.save();
    }

    public boolean addBySxf(long timerId, int attackType, List<AttackInfo> attackInfoList) {
        return attackInfoList.parallelStream().allMatch(attackInfo -> add(timerId, attackInfo.getSrc_ip(), attackInfo.getSrc_region(), Integer.parseInt(attackInfo.getAttack_count()), attackType));
    }

    public boolean addByLm(long timerId, int attackType, List<LmAttackInfo> attackInfoList) {
        return attackInfoList.parallelStream().allMatch(attackInfo -> add(timerId, attackInfo.getIp(), attackInfo.getLocation(), attackInfo.getAtk_count(), attackType));
    }
}
