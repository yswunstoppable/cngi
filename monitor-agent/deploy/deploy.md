# 将monitor-agent.zip解压到指定路径

# 修改config/application.yml
```yaml
server:
  port: 9998
  servlet:
    context-path: /monitor-agent
spring:
  application:
    name: monitor-agent
logging:
  path: ./log**

#自定义配置参数
base:
  #monitor-server端访问地址
  serverUrl: http://localhost:9999
  #本机ip，不要用localhost或127.0.0.1
  bindIp: 192.168.0.108
  #通信token，请和server端配置的wgToken保持一致
  wgToken: occamedu
```

# 启动agent
- Unix下 sh start.sh启动 sh stop.sh停止
- Windows下 双击start.bat启动 关闭窗口停止