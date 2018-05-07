package com.ysjkj.smart.community.housekeep.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PhoneUtils
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.*
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.ext.confirmDialog
import com.ysjkj.smart.community.provider.router.RouterPath
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.hk_fragment_baby_sitter.*
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 保姆
 * @author yangqiang
 * @create 2018-04-25 下午3:11
 **/
@Route(path = RouterPath.HouseKeep.BABY_SITTER_FRAGMENT)
class BabySitterFragment : BaseFragment<EmptyPresenter>() {
    val imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523970072559&di=99e44c22b6e266ce1b74ee89197ef320&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F16%2F66%2F5682297f136a4_1024.jpg"
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_fragment_baby_sitter)

    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        rcl_baby_sitter.apply {
            dontAnima()
            linear(dip(5), colorRes = R.color.provider_bg_gray_color)
            bindAdapter(mutableListOf<String>().apply { (1..5).forEach { add("") } }, R.layout.hk_recycle_item_baby_sitter, mapOf()) { helper, _ ->
                helper.getView<CircleImageView>(R.id.iv_head).load(imgUrl)
                helper.addOnClickListener(R.id.tx_call)
            }.apply {
                setOnItemClickListener { adapter, view, position ->
                    jump(RouterPath.HouseKeep.BABY_SITTER_INFO)
                }
                setOnItemChildClickListener { adapter, view, position ->
                    confirmDialog("确认拨打客服热线咨询价格吗?\n023-88888888") {
                        PhoneUtils.dial("023-88888888")
                    }
                }
            }
        }
        iv_join.onClick {
            confirmDialog("是否填写资料加入我们的小队中来") {
                jump(RouterPath.HouseKeep.BABY_SITTER_APPLY)
            }
        }
    }
}