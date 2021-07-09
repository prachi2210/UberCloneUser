package com.example.adebuser.ui.book_ride.select_time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.databinding.FragmentSelectTimeBinding
import com.example.adebuser.ui.me.ProfileFragment
import com.wizebrains.adventmingle.base.BaseFragment


class SelectTimeFragment : BaseFragment(), SelectTimeAdapter.SelectTimeListener {
    private val binding get() = _binding!!
    private var _binding: FragmentSelectTimeBinding? = null
    var selectTimeList= arrayListOf<SelectTimeList>()

    private val selectTimeAdapter: SelectTimeAdapter by lazy {
        SelectTimeAdapter(requireActivity(),this,selectTimeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): SelectTimeFragment = SelectTimeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTimeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val params: ViewGroup.LayoutParams = binding.rvPickupRider.layoutParams
        params.height = 500
        binding.rvPickupRider.layoutParams = params
        binding.rvPickupRider.adapter=selectTimeAdapter
        if(selectTimeList.isEmpty())
        {
            selectTimeList.add(SelectTimeList(0))
            selectTimeAdapter.notifyDataSetChanged()
        }


    }

    override fun onAddPickup(position:Int) {
        selectTimeList.add(SelectTimeList(0))
        selectTimeAdapter.notifyDataSetChanged()
    }

    override fun onRemovePickup(position: Int) {
        selectTimeList.removeAt(position)
        selectTimeAdapter.notifyDataSetChanged()
    }


}