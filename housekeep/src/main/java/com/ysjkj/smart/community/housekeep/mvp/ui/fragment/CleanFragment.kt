package com.ysjkj.smart.community.housekeep.mvp.ui.fragment

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseFragment
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.bindAdapter
import com.piaolac.core.ext.dontAnima
import com.piaolac.core.ext.grid
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.housekeep.R
import com.ysjkj.smart.community.provider.router.RouterPath
import kotlinx.android.synthetic.main.hk_fragment_clean.*

/**
 * 清洁
 * @author yangqiang
 * @create 2018-04-26 下午5:25
 **/
@Route(path = RouterPath.HouseKeep.CLEAN_FRAGMENT)
class CleanFragment : BaseFragment<EmptyPresenter>() {
    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.hk_fragment_clean)
    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {

        rcl_clean.apply {
            grid(2, 0)
            dontAnima()
            bindAdapter(mutableListOf(Pair("日常保洁", R.drawable.hk_clean_clean),
                    Pair("皮具护理", R.drawable.hk_clean_leather),
                    Pair("家电清洗", R.drawable.hk_clean_home_appliances),
                    Pair("除螨清洗", R.drawable.hk_clean_acarid)),
                    R.layout.hk_recycle_item_clean, mapOf()) { helper, item ->
                helper.setText(R.id.tx_title, item.first)
                helper.setImageResource(R.id.iv_clean, item.second)
            }
        }
    }
}