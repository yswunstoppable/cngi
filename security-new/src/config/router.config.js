// eslint-disable-next-line
import { BasicLayout, BlankLayout } from '@/layouts'

const RouteView = {
  name: 'RouteView',
  render: (h) => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: '首页' },
    redirect: '/dashboard',
    children: [
      // dashboard
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index'),
        meta: { title: '仪表盘', icon: 'dashboard' }
      },

      // monitor
      {
        path: '/host',
        name: 'Host',
        component: RouteView,
        meta: { title: '主机监控', icon: 'database' },
        redirect: '/host/list',
        children: [
          {
            path: '/host/list',
            name: 'HostList',
            component: () => import('@/views/host/list'),
            meta: { title: '监控列表', keepAlive: false }
          }, {
            path: '/host/disk',
            name: 'HostDisk',
            component: () => import('@/views/host/disk'),
            meta: { title: '磁盘空间', keepAlive: false }
          }, {
            path: '/host/process',
            name: 'HostProcess',
            component: () => import('@/views/host/process'),
            meta: { title: '进程空间', keepAlive: false }
          }
        ]
      },
      // 流量监控
      {
        path: '/flow',
        name: 'Flow',
        component: RouteView,
        meta: { title: '流量监控', icon: 'wifi' },
        children: [
          {
            path: '/flow/newtwork',
            name: 'NewtworkFlow',
            component: () => import('@/views/flow/network'),
            meta: { title: '网络流量', keepAlive: false }
          },
          {
            path: '/flow/carriage',
            name: 'SystemCarriage',
            component: () => import('@/views/flow/carriage'),
            meta: { title: '系统负载', keepAlive: false }
          }
        ]
      },
      // 日志监控
      {
        path: '/log',
        name: 'Syslog',
        component: RouteView,
        meta: { title: '日志监控', icon: 'reconciliation' },
        children: [
          {
            path: '/log/source',
            name: 'SourceLog',
            component: () => import('@/views/log/SourceLog'),
            meta: { title: '网络日志', keepAlive: false }
          },
          {
            path: '/log/execute',
            name: 'ExuLog',
            component: () => import('@/views/log/ExuLog'),
            meta: { title: '操作日志', keepAlive: false }
          }
        ]
      },
      // 环境监控
      {
        path: '/environment',
        name: 'Environment',
        component: RouteView,
        meta: { title: '环境监控', icon: 'gateway' },
        children: [
          {
            path: '/environment/temperature',
            name: 'EnvTemp',
            component: () => import('@/views/environment/temperature'),
            meta: { title: '机房温度', keepAlive: false }
          },
          {
            path: '/environment/humidity',
            name: 'EnvHumidity',
            component: () => import('@/views/environment/humidity'),
            meta: { title: '机房湿度', keepAlive: false }
          }
        ]
      },
      // 消息报警
      {
        path: '/msg',
        name: 'msg',
        component: RouteView,
        meta: { title: '消息报警', icon: 'message' },
        redirect: '/msg/mail',
        children: [
          {
            path: '/msg/setting',
            name: 'MsgSetting',
            component: RouteView,
            meta: { title: '渠道设置', keepAlive: false },
            children: [
              {
                path: '/msg/setting/mail',
                name: 'MsgMail',
                component: () => import('@/views/msg/mail'),
                meta: { title: '邮件配置', keepAlive: false }
              },
              {
                path: '/msg/setting/sms',
                name: 'MsgSms',
                component: () => import('@/views/msg/sms'),
                meta: { title: '短信配置', keepAlive: false }
              }
            ]
          },
          {
            path: '/msg/threshold ',
            name: 'MsgThreshold',
            component: () => import('@/views/msg/threshold'),
            meta: { title: '阈值设置', keepAlive: false }
          },
          {
            path: '/msg/list ',
            name: 'MsgList',
            component: () => import('@/views/msg/list'),
            meta: { title: '报警记录', keepAlive: false }
          }
        ]
      },
      // 用户管理
      {
        path: '/user',
        name: 'user',
        component: RouteView,
        meta: { title: '权限管理', icon: 'user' },
        redirect: '/user/list',
        children: [
          {
            path: '/user/role',
            name: 'UserRole',
            component: () => import('@/views/user/Role'),
            meta: { title: '角色管理', keepAlive: false }
          },
          {
            path: '/user/list',
            name: 'UserList',
            component: () => import('@/views/user/List'),
            meta: { title: '人员管理', keepAlive: false }
          }
          // {
          //   path: '/user/setting',
          //   name: 'UserSetting',
          //   component: () => import('@/views/user/Setting'),
          //   meta: { title: '修改密码', keepAlive: false }
          // }
        ]
      },
      // 联动防御
      {
        path: '/',
        name: 'Security',
        component: RouteView,
        meta: { title: '联动防御', icon: 'bars' },
        redirect: '/center',
        children: [
          // 白名单
          {
            path: '/whitelist/list',
            name: 'WhiteList',
            component: () => import('@/views/whitelist/WhiteList'),
            meta: { title: '白名单', keepAlive: false }
          },
          // 定时处理
          {
            path: '/timer',
            name: 'Time',
            component: () => import('@/views/timer/List'),
            meta: { title: '定时处理', icon: 'history' }
          },
          // 联动中心
          {
            path: '/center',
            name: 'Center',
            component: RouteView,
            meta: { title: '联动中心', icon: 'rocket' },
            redirect: '/center/boundary',
            children: [
              {
                path: '/center/boundary',
                name: 'Boundary',
                component: () => import('@/views/center/Boundary'),
                meta: { title: '边界管理', keepAlive: false }
              },
              {
                path: '/center/command',
                name: 'Command',
                component: () => import('@/views/center/Command'),
                meta: { title: '命令管理', keepAlive: false }
              }
            ]
          },
          // 感知源
          {
            path: '/source',
            name: 'Source',
            component: RouteView,
            meta: { title: '感知源', icon: 'safety-certificate' },
            redirect: '/source/add',
            children: [
              {
                path: '/source/add',
                name: 'SourceAdd',
                component: () => import('@/views/source/Add'),
                meta: { title: '添加源', keepAlive: false }
              },
              {
                path: '/source/list',
                name: 'SourceList',
                component: () => import('@/views/source/List'),
                meta: { title: '源列表', keepAlive: false }
              }
            ]
          }
        ]
      }
    ]
  },
  {
    path: '*', redirect: '/404', hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: BlankLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'recover',
        name: 'recover',
        component: undefined
      }
    ]
  },

  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }

]
