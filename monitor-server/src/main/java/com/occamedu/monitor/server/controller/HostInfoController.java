package com.occamedu.monitor.server.controller;

import com.github.pagehelper.PageInfo;
import com.occamedu.monitor.server.entity.HostInfo;
import com.occamedu.monitor.server.service.HostInfoService;
import com.occamedu.monitor.server.service.LogInfoService;
import com.occamedu.monitor.server.util.staticvar.StaticKeys;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @version v2.3
 * @ClassName:HostInfoController.java
 * @author: http://www.wgstart.com
 * @date: 2019年11月16日
 * @Description: HostInfoController.java
 * @Copyright: 2017-2021 occamedu. All rights reserved.
 */
@Controller
@RequestMapping("/host")
public class HostInfoController {


    private static final Logger logger = LoggerFactory.getLogger(HostInfoController.class);


    @Resource
    private HostInfoService hostInfoService;
    @Resource
    private LogInfoService logInfoService;


    /**
     * 根据条件查询hostInfo信息列表
     *
     * @param hostInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "list")
    public String hostInfoList(HostInfo hostInfo, Model model) {
        Map<String, Object> params = new HashMap<String, Object>();
        try {
            PageInfo pageInfo = hostInfoService.selectByParams(params, Integer.valueOf(hostInfo.getPage()), Integer.valueOf(hostInfo.getPageSize()));
            model.addAttribute("page", pageInfo);
        } catch (Exception e) {
            logger.error("查询出错", e);
            logInfoService.save("hostInfoList", "查询监控主机错误：" + e.toString(), StaticKeys.LOG_ERROR);
        }
        return "app/list";
    }

    /**
     * 转到添加页面
     *
     * @param HostInfo
     * @param model
     * @return
     */
    @RequestMapping(value = "add")
    public String addHostInfo(HostInfo hostInfo, Model model) {
        return "app/add";
    }

    /**
     * 保存监控主机信息
     *
     * @param HostInfo
     * @param model
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "save")
    public String saveHostInfo(HostInfo HostInfo, Model model, HttpServletRequest request) {
        try {
            if (StringUtils.isEmpty(HostInfo.getId())) {
                hostInfoService.save(HostInfo);
            } else {
                HostInfo ho = hostInfoService.selectById(HostInfo.getId());
                ho.setIp(HostInfo.getIp());
                ho.setRemark(HostInfo.getRemark());
                hostInfoService.updateById(ho);
            }

        } catch (Exception e) {
            logger.error("保存监控主机错误：", e);
            logInfoService.save(HostInfo.getIp(), "保存监控主机错误：" + e.toString(), StaticKeys.LOG_ERROR);
        }
        return "redirect:/app/list";
    }


    /**
     * 查看监控主机
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "view")
    public String viewHostInfo(Model model, HttpServletRequest request) {
        String errorMsg = "查看监控主机错误：";
        String id = request.getParameter("id");
        HostInfo hostInfo = new HostInfo();
        try {
            hostInfo = hostInfoService.selectById(id);
            model.addAttribute("hostInfo", hostInfo);
        } catch (Exception e) {
            logger.error(errorMsg, e);
            logInfoService.save(hostInfo.getIp(), errorMsg + e.toString(), StaticKeys.LOG_ERROR);
        }
        return "app/view";
    }


    /**
     * 删除监控主机信息
     *
     * @param model
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "del")
    public String delete(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String errorMsg = "删除app信息错误：";
        String ip = "";
        try {
            if (!StringUtils.isEmpty(request.getParameter("id"))) {
                HostInfo hostInfo = hostInfoService.selectById(request.getParameter("id"));
                ip = hostInfo.getIp();
                hostInfoService.deleteById(request.getParameter("id").split(","));
            }
        } catch (Exception e) {
            logger.error(errorMsg, e);
            logInfoService.save(ip, errorMsg + e.toString(), StaticKeys.LOG_ERROR);
        }

        return "redirect:/app/list";
    }


}
