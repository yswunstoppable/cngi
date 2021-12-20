package com.occamedu.security.common.kit;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import expect4j.Expect4j;
import expect4j.matches.EofMatch;
import expect4j.matches.Match;
import expect4j.matches.RegExpMatch;
import expect4j.matches.TimeoutMatch;
import org.apache.oro.text.regex.MalformedPatternException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author fye
 * @email fh941005@163.com
 * @date 2020-08-14 16:18
 * @description
 */
public class Test2 {
    //登入SSH时的控制信息
    //设置不提示输入密码、不显示登入信息等
    public static class LocalUserInfo implements UserInfo {
        String passwd;

        @Override
        public String getPassword() {
            return passwd;
        }

        @Override
        public boolean promptYesNo(String str) {
            return true;
        }

        @Override
        public String getPassphrase() {
            return null;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return true;
        }

        @Override
        public boolean promptPassword(String message) {
            return true;
        }

        @Override
        public void showMessage(String message) {

        }
    }

    private static final int COMMAND_EXECUTION_SUCCESS_OPCODE = -2;
    private static final String BACKSLASH_R = "\r";

    /**
     * 执行配置命令
     *
     * @param commands 要执行的命令，为字符数组
     * @return 执行结果
     */
    public String executeCommands(String[] commands, String ip, int port, String user, String password, Integer sshTimeout) {
        Session session = null;
        ChannelShell channel = null;
        Expect4j expect = null;

        try {
            session = new JSch().getSession(user, ip, port);
            session.setPassword(password);
            session.setConfig(bulidConfig());
            // 设置不提示输入密码、不显示登入信息等
            session.setUserInfo(new LocalUserInfo());
            session.connect();
            channel = (ChannelShell) session.openChannel("shell");
            expect = new Expect4j(channel.getInputStream(), channel.getOutputStream());
            channel.connect(sshTimeout + 1000);

            // 存放返回值
            StringBuffer buffer = new StringBuffer();
            // 执行语句
            List<Match> lstPattern = bulidPattern(buffer, sshTimeout);

            for (String strCmd : commands) {
                System.out.println(strCmd);
                if (expect.expect(lstPattern) != COMMAND_EXECUTION_SUCCESS_OPCODE) {
                    expect.send(strCmd);
                    expect.send(BACKSLASH_R);
                }
            }
            expect.expect(lstPattern);
            return buffer.toString().toLowerCase();

        } catch (Exception ex) {
            return String.format("[@CMDUtilShell] host=[%s] user=[%s] [error=%s]", ip, user, ex.getMessage());
        } finally {
            if (expect != null) {
                expect.close();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    /**
     * 构建配置项
     */
    private Hashtable<String, String> bulidConfig() {
        Hashtable<String, String> config = new Hashtable<>();
        config.put("userauth.gssapi-with-mic", "no");
        config.put("StrictHostKeyChecking", "no");
        return config;
    }

    /**
     * 构建模式
     */
    private List<Match> bulidPattern(StringBuffer buffer, Integer sshTimeout) throws MalformedPatternException {
        List<Match> lstPattern = new ArrayList<>();
        // 终止符
        // todo:存入数据库使用参数配置
        String[] regEx = {"~]#", "~#", ":~#",};
        for (String regexElement : regEx) {
            RegExpMatch mat = new RegExpMatch(regexElement, x -> {
                buffer.append(x.getBuffer());
            });
            lstPattern.add(mat);
        }

        lstPattern.add(new EofMatch(x -> {
        }));

        // 设置超时时间
        lstPattern.add(new TimeoutMatch(sshTimeout, x -> {
        }));

        return lstPattern;
    }

    public static void main(String[] args) {
        Test2 test2 = new Test2();
        String res=test2.executeCommands(new String[]{"conf", "address attack-ip", "ip 39.109.104.201/32", "exit", "exit", "exit"},
                "10.172.10.158", 22, "lorick", "lorick2009", 1000);
        System.out.println(res);
    }
}
