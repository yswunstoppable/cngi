#!/bin/sh
#配置了ssh秘钥可免密登录同时有tar工具
#获取环境名
env=''

if [ x$1 != x ];then
    env=$1
else
    env='dev'
fi


#获取当前分支名
curr_branch=`git symbolic-ref --short -q HEAD`
echo '当前工作分支 => '${curr_branch}'\n'

echo '读取配置文件:'
deploy_host=`sed '/^'${env}_host'=/!d;s/.*=//' deploy.conf`
deploy_user=`sed '/^'${env}_user'=/!d;s/.*=//' deploy.conf`
deploy_path=`sed '/^'${env}_path'=/!d;s/.*=//' deploy.conf`
ssh_port=`sed '/^'${env}_ssh_port'=/!d;s/.*=//' deploy.conf`
if [ ${env} == 'pro' ];then
  read -p "当前要部署的是pro环境请输入密码 > " ssh_password
else
  ssh_password=`sed '/^'${env}_ssh_password'=/!d;s/.*=//' deploy.conf`
fi

echo '地址 => '${deploy_host}
echo '用户 => '${deploy_user}
echo '路径 => '${deploy_path}
echo '端口 => '${ssh_port}
echo '\n'


#echo '编译项目'
#mvn clean compile
#echo '\n'

echo '删除老版本'
ssh -p ${ssh_port} ${deploy_user}@${deploy_host} rm -rf ${deploy_path}/target/classes


echo '上传新版本'
### 上传文件夹
#scp -p${ssh_port} -r ./target ${deploy_user}@${deploy_host}:${deploy_path}/
#expect -c "
#set timeout 300
#spawn scp -p${ssh_port} -r ./target/classes ${deploy_user}@${deploy_host}:${deploy_path}/
#expect {
#        \"(yes/no)\" {send \"yes\r\"; exp_continue}
#        \"password:\" {send \"${ssh_password}\r\"}
#}
#interact
#"

# 上传压缩包
tar -czvf classes.tar ./target/classes/
scp -p${ssh_port} ./classes.tar ${deploy_user}@${deploy_host}:${deploy_path}/

## 解压
ssh -p ${ssh_port} ${deploy_user}@${deploy_host} tar -xzvf ${deploy_path}/classes.tar -C ${deploy_path}

echo '上传成功\n'

ssh -p ${ssh_port} ${deploy_user}@${deploy_host} ${deploy_path}/run.sh restart

echo '重启服务成功\n'


echo '部署成功'