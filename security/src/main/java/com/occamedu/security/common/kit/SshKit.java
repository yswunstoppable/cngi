package com.occamedu.security.common.kit;

import cn.fabrice.common.pojo.BaseResult;
import cn.fabrice.common.pojo.DataResult;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import com.jfinal.kit.JsonKit;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-06-10 21:03
 * @description
 */
public class SshKit {
    private String host;
    private String username;
    private String password;
    private Session session;
    protected ChannelExec exec;
    private BaseResult connectResult;

    public SshKit(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.connectResult = connect();
    }

    private BaseResult connect() {
        //建立连接
        JSch jsch = new JSch();
        try {
            this.session = jsch.getSession(this.username, this.host, 22);
            this.session.setPassword(password);
            this.session.setConfig("StrictHostKeyChecking", "no");
//            this.session.setTimeout(60000);
            this.session.connect(60000);
            this.exec = (ChannelExec) this.session.openChannel("exec");
            return BaseResult.ok();
        } catch (JSchException e) {
            return BaseResult.fail(e.getLocalizedMessage());
        }
    }

    public BaseResult sendCommand(String command) {
        //发送指令
        try {
            InputStream in = this.exec.getInputStream();
            this.exec.setCommand(command);
            this.exec.connect();
            String s = IOUtils.toString(in, "UTF-8");
            in.close();
            return DataResult.data(s);
        } catch (Exception e) {
            return DataResult.fail(e.getLocalizedMessage());
        }
    }

    public BaseResult sendContinuousCommand(String[] commandList) {
        try {
            StringBuilder resultBuilder = new StringBuilder();
            ChannelShell channelShell = (ChannelShell) this.session.openChannel("shell");
            //从远端到达的数据，都能从这个流读取到
            InputStream inputStream = channelShell.getInputStream();
            channelShell.connect();
            //写入该流的数据，都将发送到远程端
            OutputStream outputStream = channelShell.getOutputStream();
            //使用PrintWriter就是为了使用println 这个方法
            //好处就是不需要每次手动给字符加\n
            PrintWriter printWriter = new PrintWriter(outputStream);
            for (String content : commandList) {
                printWriter.println(content);
            }
            //为了结束本次交互，防止命令层级太深
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
//            printWriter.println("exit");
            //把缓冲区的数据强行输出
            printWriter.flush();
            byte[] tmp = new byte[1024];
            int count = 0;
            while (true) {
                while (inputStream.available() > 0) {
                    int i = inputStream.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    String s = new String(tmp, 0, i);
                    System.out.println("次数：" + (count++) + "结果：" + s);
                    if (s.contains("--More--")) {
                        outputStream.write((" ").getBytes());
                        outputStream.flush();
                    }
                    resultBuilder.append(s);
                }
//                channelShell.disconnect();
//                if (channelShell.isClosed()) {
                System.out.println(channelShell.getExitStatus());
                System.out.println(JSONObject.toJSONString(channelShell));
                if (channelShell.getExitStatus() == 0) {
                    break;
                }
                Thread.sleep(1000);
            }
            outputStream.close();
            inputStream.close();
            channelShell.disconnect();
            return DataResult.data(resultBuilder.toString().trim().replaceAll("\r\n", "\n"));
        } catch (Exception e) {
            return DataResult.fail(e.getLocalizedMessage());
        }
    }

    public void close() {
        this.session.disconnect();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public BaseResult getConnectResult() {
        return connectResult;
    }

    public void setConnectResult(BaseResult connectResult) {
        this.connectResult = connectResult;
    }

    public static void main(String[] args) throws JSchException, IOException {
        //建立连接
        String host = "10.172.10.158";
        String userName = "lorick";
        String password = "lorick2009";

//        String host = "47.95.210.208";
//        String userName = "root";
//        String password = "Best2020";

        SshKit sshKit = new SshKit(host, userName, password);
        BaseResult result = sshKit.sendContinuousCommand(new String[]{"conf", "address attack-ip", "ip 2.2.2.2/12", "exit", "exit", "exit"});
        System.out.println(JsonKit.toJson(result));
        sshKit.close();
    }
}
