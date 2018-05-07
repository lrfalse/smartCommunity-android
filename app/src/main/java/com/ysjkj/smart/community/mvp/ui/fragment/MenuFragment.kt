package com.ysjkj.smart.community.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.grid
import com.piaolac.core.ext.jump
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.main_fragment_menu.*

/**
 * 功能菜单
 * @author yangqiang
 * @create 2018-04-25 上午11:23
 **/
@Route(path = RouterPath.Main.HOME_MENU_FRAGMENT)
class MenuFragment : BaseFragment<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.main_fragment_menu)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        initMenu()
    }


    /**
     * 初始化菜单
     */
    private fun initMenu() {
        val data = listOf(Menu("开门", R.drawable.main_home_menu_key),
                Menu("缴费", R.drawable.main_home_menu_pay),
                Menu("维修", R.drawable.main_home_menu_maintain),
                Menu("政务", R.drawable.main_home_menu_government),
                Menu("医疗", R.drawable.main_home_menu_medical_treatment),
                Menu("家政", R.drawable.main_home_menu_house_keep, RouterPath.HouseKeep.HOME),
                Menu("众筹", R.drawable.main_home_menu_many),
                Menu("更多", R.drawable.main_home_menu_more))

        rcl_menu.apply {
            grid(4)
            bindAdapter(data, R.layout.main_recycle_item_home_menu, mapOf()) { helper, item ->
                helper.setImageResource(R.id.iv_icon, item.icon)
                helper.setText(R.id.tx_title, item.title)
            }.setOnItemClickListener { _, _, position ->
                if (!data[position].url.isNullOrEmpty()) {
                    jump(data[position].url)
                }

            }
            isNestedScrollingEnabled = false
        }

    }

    data class Menu(val title: String, val icon: Int, var url: String = "")
}