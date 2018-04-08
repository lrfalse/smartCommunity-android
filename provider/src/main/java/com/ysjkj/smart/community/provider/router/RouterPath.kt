package com.ysjkj.smart.community.provider.router

/**
 * Created by yangqiang on 2018/3/28.
 */
object RouterPath {

    /**
     * 用户中心
     */
    class UserCenter {
        companion object {
            /**
             * 忘记密码
             */
            const val FORGET_PASSWORD = "/userCenter/login"
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