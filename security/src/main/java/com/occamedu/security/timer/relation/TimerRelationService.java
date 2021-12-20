package com.occamedu.security.timer.relation;

import cn.fabrice.jfinal.service.BaseService;
import com.jfinal.kit.Kv;
import com.occamedu.security.common.module.TimerRelation;

import java.math.BigInteger;
import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-09 23:05
 * @description
 */
public class TimerRelationService extends BaseService<TimerRelation> {
    public TimerRelationService() {
        super("timerRelation.", TimerRelation.class, "timer_relation");
    }

    public List<TimerRelation> listBoundary(long timerId) {
        Kv cond = Kv.by("timerId", timerId);
        return list(cond, "listBoundary");
    }

    public List<TimerRelation> listCommand(long timerId) {
        Kv cond = Kv.by("timerId", timerId);
        return list(cond, "listCommand");
    }

    public List<TimerRelation> listByTimer(long timerId){
        Kv cond = Kv.by("timerId", timerId);
        return list(cond, "listByTimer");
    }

    public boolean add(long timerId, int type, long targetId) {
        TimerRelation relation = new TimerRelation();
        relation.setTimerId(BigInteger.valueOf(timerId));
        relation.setType(type);
        relation.setTargetId(BigInteger.valueOf(targetId));
        return relation.save();
    }
}
