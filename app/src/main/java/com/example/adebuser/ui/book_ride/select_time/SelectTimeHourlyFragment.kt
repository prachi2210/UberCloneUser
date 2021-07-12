package com.example.adebuser.ui.book_ride.select_time

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentSelectTimeHourlyBinding
import com.example.adebuser.extensions.showToast
import com.example.adebuser.base.BaseFragment


class SelectTimeHourlyFragment : BaseFragment(), SelectTimeHourlyAdapter.SelectTimeHourlyListener {

    private var _binding: FragmentSelectTimeHourlyBinding? = null
    private val binding get() = _binding!!

    private var timePeriodArray = arrayOf<String>()

    private lateinit var  selectTimeHourlyAdapter: SelectTimeHourlyAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTimeHourlyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timePeriodArray = resources.getStringArray(R.array.time_period)
        selectTimeHourlyAdapter=SelectTimeHourlyAdapter(requireActivity(),timePeriodArray,this)
        binding.rvTimePeriod.adapter=selectTimeHourlyAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHourlyTime(position: Int) {
        requireActivity().showToast(timePeriodArray[position])
    }


}