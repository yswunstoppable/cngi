package com.occamedu.security.common.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.jfinal.config.Plugins;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.occamedu.security.common.module._MappingKit;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2019-09-28 12:05
 * @description 数据库模块配置类
 */
public class ModuleConfig {
    /**
     * 设置数据库插件
     *
     * @param plugins 全局插件
     */
    public void setDbPlugin(Plugins plugins) {
        // 读取数据库配置文件
        Prop prop = PropKit.use("db_config.properties");
        // 使用druid数据库连接池进行操作
        DruidPlugin druidPlugin = new DruidPlugin(prop.get("database_url").trim(),
                prop.get("database_user").trim(), prop.get("database_password").trim());
        // 如果数据库配置文件信息在第一次this.loadPropertyFile(...)文件中则可直接使用getProperty(...)拿到对应值
        // 或者在此使用this.loadPropertyFile(...)那么此后PropKit.get(...)获取的均是此次配置文件中的值
        druidPlugin.addFilter(new StatFilter());
        druidPlugin.setMaxActive(20);
        druidPlugin.setMinIdle(5);
        druidPlugin.setInitialSize(5);
        druidPlugin.setConnectionInitSql("set names utf8mb4");
        druidPlugin.setValidationQuery("select 1 from dual");
        plugins.add(druidPlugin);
        // 配置数据库活动记录插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        Engine engine = arp.getEngine();
        // 上面的代码获取到了用于 sql 管理功能的 Engine 对象，接着就可以开始配置了
        engine.setToClassPathSourceFactory();
        engine.addSharedMethod(StrKit.class);
        arp.setShowSql(true);
        //设置sql文件存储的基础路径，此段代码表示设置为classpath目录
        arp.setBaseSqlTemplatePath(null);
        //所有模块sql
        arp.addSqlTemplate("sql/all.sql");
        // 对应关系映射（需等待运行Generator语句生成后替换）
        _MappingKit.mapping(arp);
        plugins.add(arp);
    }
}
