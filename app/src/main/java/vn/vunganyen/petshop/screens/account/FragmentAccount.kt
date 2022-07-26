package vn.vunganyen.petshop.screens.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import vn.vunganyen.petshop.R
import vn.vunganyen.petshop.databinding.FragmentAccountBinding
import vn.vunganyen.petshop.databinding.FragmentCartBinding


class FragmentAccount : Fragment() {
    lateinit var binding: FragmentAccountBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

}