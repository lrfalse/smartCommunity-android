package com.ysjkj.smart.community.provider.router

/**
 * Created by yangqiang on 2018/3/28.
 */
object RouterPath {

    class Main {
        companion object {
            /**
             * 主页
             */
            const val HOME = "/main/home"

            /**
             * 主页菜单
             */
            const val HOME_MENU_FRAGMENT = "/main/menuFragment"
        }
    }

    /**
     * 家政
     */
    class HouseKeep {
        companion object {
            /**
             * 家政首页
             */
            const val HOME = "/houseKeep/home"

            /**
             * 保姆
             */
            const val BABY_SITTER_FRAGMENT = "/houseKeep/babySitterFragment"

            /**
             * 保姆详情
             */
            const val BABY_SITTER_INFO = "/houseKeep/babySitterInfo"

            /**
             * 服务经验
             */
            const val BABY_SITTER_EXPERIENCE_FRAGMENT = "/houseKeep/babySitterExperienceFragment"

            /**
             * 培训背景
             */
            const val BABY_SITTER_TRAIN_FRAGMENT = "/houseKeep/babySitterTrainFragment"

            /**
             * 保姆申请界面
             */
            const val BABY_SITTER_APPLY = "/houseKeep/babySitterApply"

            /**
             * 清洁fragment
             */
            const val CLEAN_FRAGMENT = "/houseKeep/cleanFragment"
        }
    }

    /**
     * 生活
     */
    class Life {
        companion object {
            /**
             * 生活主页fragment
             */
            const val HOME_FRAGMENT = "/life/mainFragment"

            /**
             * 商圈fragment
             */
            const val BUSINESS_FRAGMENT = "/life/bussinessFragment"

            /**
             * 邻居一起来 fragment
             */
            const val GROUP_SHOPPING_FRAGMENT = "/life/groupShoppingFragment"
        }
    }

    /**
     * 用户中心
     */
    class UserCenter {
        companion object {

            /**
             * 欢迎页
             */
            const val WELCOME = "/userCenter/welcome"

            /**
             * 用户登录
             */
            const val LOGIN = "/userCenter/login"
            /**
             * 注册账号
             */
            const val REGISTER_ACCOUNT = "/userCenter/registerAccount"

            /**
             * 设置密码
             */
            const val SET_PASSWORD = "/userCenter/setPassword"

            /**
             * 选择社区
             */
            const val CHOOSE_COMMUNIT = "/userCenter/chooseCommunit"

            /**
             * 设置房屋信息
             */
            const val SET_HOUSE_INFO = "/userCenter/setHouseInfo"
        }
    }

    class Notice {
        companion object {
            /**
             * banner公告
             */
            const val BANNER_FRAGMENT = "/notice/bannerFragment"

            /**
             * 公告列表
             */
            const val LIST = "/notice/list"
            /**
             * 公告详情
             */
            const val DETAIL = "/notice/detail"
        }
    }

    class Activitie {
        companion object {
            /**
             * banner
             */
            const val BANNER_FRAGMENT = "/activitie/bannerFragment"

            /**
             * 列表界面
             */
            const val LIST = "/activitie/list"

            /**
             * 列表界面
             */
            const val DETAIL = "/activitie/detail"
        }
    }

    class DoorGuard {
        companion object {

            /**
             * 红包界面
             */
            const val RED_PACKET_FRAGMENT = "/doorGuard/redPacketFragment"

            /**
             * 二维码开门界面
             */
            const val QR_CODE_FRAGMENT = "/doorGuard/qrCodeFragment"

            /**
             * 密码开门
             */
            const val PWD_FRAGMENT = "/doorGuard/pwdFragment"

            /**
             * 摇一摇
             */
            const val SHAKE_FRAGMENT = "/doorGuard/shakeFragment"

            /**
             * 远程开门
             */
            const val REMOTE_FRAGMENT = "/doorGuard/remoteFragment"

        }
    }
}