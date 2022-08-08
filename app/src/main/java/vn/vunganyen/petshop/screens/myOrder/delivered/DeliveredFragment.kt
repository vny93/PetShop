package vn.vunganyen.petshop.screens.myOrder.delivered

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.FragmentDeliveredBinding

class DeliveredFragment : Fragment() {
    lateinit var binding : FragmentDeliveredBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDeliveredBinding.inflate(inflater,container,false)


        return binding.root
    }

}