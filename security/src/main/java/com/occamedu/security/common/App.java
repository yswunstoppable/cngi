package com.occamedu.security.common;

import cn.fabrice.jfinal.ext.cros.interceptor.CrossInterceptor;
import cn.fabrice.jfinal.ext.render.MyRenderFactory;
import cn.fabrice.jfinal.interceptor.ParaValidateInterceptor;
import cn.fabrice.jfinal.plugin.ValidationPlugin;
import cn.fabrice.kit.json.FJsonFactory;
import cn.fabrice.kit.log.Slf4jLogFactory;
import com.jfinal.aop.Aop;
import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;
import com.occamedu.security.DefaultSysController;
import com.occamedu.security.center.route.CenterRoute;
import com.occamedu.security.common.config.ModuleConfig;
import com.occamedu.security.common.interceptor.AuthInterceptor;
import com.occamedu.security.common.module.WhiteList;
import com.occamedu.security.dashboard.DashboardController;
import com.occamedu.security.log.route.LogRoute;
import com.occamedu.security.shreshold.ShresholdRoute;
import com.occamedu.security.source.SourceController;
import com.occamedu.security.timer.TimerController;
import com.occamedu.security.timer.TimerRunnerService;
import com.occamedu.security.timer.TimerService;
import com.occamedu.security.user.constant.UserConstants;
import com.occamedu.security.user.route.UserRoute;
import com.occamedu.security.whitelist.WhiteListController;
import net.dreamlu.event.EventPlugin;
import sun.rmi.runtime.Log;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-09-28 12:01
 * @description 应用配置启动类
 */
public class App extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        PropKit.use("base_config.properties");
        //设置开发模式
        me.setDevMode(PropKit.getBoolean("devMode", true));
        // 设置基础文件上传路径
        me.setBaseUploadPath(PropKit.get("baseUploadPath").trim());
        //设置基础下载路径
        me.setBaseDownloadPath(PropKit.get("baseDownloadPath").trim());
        // 设置最大POST请求大小
        me.setMaxPostSize(PropKit.getInt("maxPostSize"));
        //设置日志
        me.setLogFactory(new Slf4jLogFactory());
        //设置render工厂类
        me.setRenderFactory(new MyRenderFactory());
        //设置json
        me.setJsonFactory(new FJsonFactory("access_token"));
        //设置允许AOP注入
        me.setInjectDependency(true);

        UserConstants.Result.init();
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/test", DefaultSysController.class);
        //用户相关路由配置
        me.add(new UserRoute());
        me.add(new CenterRoute());
        me.add(new LogRoute());
        me.add(new ShresholdRoute());
        me.add("/dashboard", DashboardController.class);
        me.add("/whitelist", WhiteListController.class);
        me.add("/source", SourceController.class);
        me.add("/timer", TimerController.class);
    }

    @Override
    public void configEngine(Engine me) {
    }

    @Override
    public void configPlugin(Plugins me) {
        //配置数据库插件
        new ModuleConfig().setDbPlugin(me);
        //添加规则校验插件
        me.add(new ValidationPlugin());
        //增加缓存插件
        me.add(new EhCachePlugin());
    }

    @Override
    public void configInterceptor(Interceptors me) {
        //添加跨越解决方案
        me.add(new CrossInterceptor(UserConstants.TOKEN_NAME, true));
        //全局认证拦截器类
        me.add(new AuthInterceptor());
        //参数校验拦截器
        me.add(new ParaValidateInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    @Override
    public void onStart() {
        System.out.println("app starting...");
        TimerService timerService = Aop.get(TimerService.class);
        timerService.start();
    }

    @Override
    public void onStop() {
        System.out.println("app stopping...");
    }

    public static void main(String[] args) {
        UndertowServer.start(App.class);
    }
}
