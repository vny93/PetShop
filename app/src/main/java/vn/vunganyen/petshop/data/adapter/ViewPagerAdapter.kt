package vn.vunganyen.petshop.data.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.vunganyen.petshop.screens.client.myOrder.delivered.DeliveredFragment
import vn.vunganyen.petshop.screens.client.myOrder.transport.TransportFragment
import vn.vunganyen.petshop.screens.client.myOrder.waitForConfirm.WaitFragment

class ViewPagerAdapter : FragmentStateAdapter{

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)

    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        lateinit var fm : Fragment
        when (position) {
            0 -> {
                fm = WaitFragment()
            }
            1 -> {
                fm = TransportFragment()
            }
            2 -> {
                fm = DeliveredFragment()
            }
            3 -> {
                fm = DeliveredFragment()
            }
            4 -> {
                fm = DeliveredFragment()
            }
            5 -> {
                fm = DeliveredFragment()
            }
        }
        return fm
    }
}