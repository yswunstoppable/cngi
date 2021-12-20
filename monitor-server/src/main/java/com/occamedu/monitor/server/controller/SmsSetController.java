package com.occamedu.monitor.server.controller;

import cn.hutool.json.JSONUtil;
import com.occamedu.monitor.server.entity.MemState;
import com.occamedu.monitor.server.entity.SmsSet;
import com.occamedu.monitor.server.service.LogInfoService;
import com.occamedu.monitor.server.service.SmsSetService;
import com.occamedu.monitor.server.sms.SmsTemplate;
import com.occamedu.monitor.server.sms.SmsUtil;
import com.occamedu.monitor.server.util.msg.WarnMailUtil;
import com.occamedu.monitor.server.util.msg.WarnSmsUtil;
import com.occamedu.monitor.server.util.staticvar.StaticKeys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:SmsSetController.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: SmsSetController.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Controller
@RequestMapping("/smsset")
public class SmsSetController {


    private static final Logger logger = LoggerFactory.getLogger(SmsSetController.class);

    @Resource
    private SmsSetService smsSetService;
    @Resource
    private LogInfoService logInfoService;


    /**
     * 根据条件查询列表
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "list")
    public String SmsSetList(SmsSet SmsSet, Model model, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            List<SmsSet> list = smsSetService.selectAllByParams(params);
            if (list.size() > 0) {
                model.addAttribute("smsSet", list.get(0));
            }
        } catch (Exception e) {
            logger.error("查询短信设置错误", e);
            logInfoService.save("查询短信设置错误：", e.toString(), StaticKeys.LOG_ERROR);

        }
        String msg = request.getParameter("msg");
        if (!StringUtils.isEmpty(msg)) {
            if (msg.equals("save")) {
                model.addAttribute("msg", "保存成功");
            } else if (msg.equals("test")) {
                String result = request.getParameter("result");
                if ("success".equals(result)) {
                    model.addAttribute("msg", "测试发送成功");
                } else {
                    model.addAttribute("msg", "测试发送失败，请查看日志");
                }
            } else {
                model.addAttribute("msg", "删除成功");
            }
        } else {
            model.addAttribute("msg", "");
        }

        return "sms/view";
    }


    /**
     * 保存短信设置信息
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "save")
    public String saveSmsSet(SmsSet smsSet, Model model, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(smsSet.getId())) {
                smsSetService.save(smsSet);
            } else {
                smsSetService.updateById(smsSet);
            }
            StaticKeys.smsSet = smsSet;
        } catch (Exception e) {
            logger.error("保存短信设置信息错误：", e);
            logInfoService.save("短信设置信息错误", e.toString(), StaticKeys.LOG_ERROR);
        }
        return "redirect:/smsset/list?msg=save";
    }

    @RequestMapping(value = "test")
    public String test(SmsSet smsSet, Model model, HttpServletRequest request) {
        String result = "success";
        try {
            if (StringUtils.isEmpty(smsSet.getId())) {
                smsSetService.save(smsSet);
            } else {
                smsSetService.updateById(smsSet);
            }
            StaticKeys.smsSet = smsSet;
            MemState memState = new MemState();
            memState.setHostname("test ip");
            memState.setUsePer(0.98);
            WarnSmsUtil.sendWarnInfo(memState);
        } catch (Exception e) {
            logger.error("测试短信设置信息错误：", e);
            logInfoService.save("测试短信设置信息错误", e.toString(), StaticKeys.LOG_ERROR);
        }
        return "redirect:/smsset/list?msg=test&result=" + result;
    }

    /**
     * 删除告警短信信息
     *
     * @param model
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "del")
    public String delete(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String errorMsg = "删除告警短信设置错误：";
        try {
            if (!StringUtils.isEmpty(request.getParameter("id"))) {
                smsSetService.deleteById(request.getParameter("id"));
                StaticKeys.smsSet = null;
            }
        } catch (Exception e) {
            logger.error(errorMsg, e);
            logInfoService.save(errorMsg, e.toString(), StaticKeys.LOG_ERROR);
        }

        return "redirect:/smsset/list?msg=save";
    }


}
