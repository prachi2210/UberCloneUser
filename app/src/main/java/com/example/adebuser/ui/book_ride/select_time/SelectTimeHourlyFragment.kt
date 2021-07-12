package com.example.adebuser.ui.book_ride.select_time

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentSelectTimeHourlyBinding
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.ui.book_ride.select_time.modal.SelectTimeListHourly
import java.util.*


class SelectTimeHourlyFragment : BaseFragment(), SelectTimeHourlyAdapter.SelectTimeHourlyListener {
    private var _binding: FragmentSelectTimeHourlyBinding? = null
    private val binding get() = _binding!!
    private val TAG = SelectTimeHourlyFragment::class.java.simpleName
    private var timePeriodArray = arrayOf<String>()
    var selectTimeHourlyList = arrayListOf<SelectTimeListHourly>()
    private lateinit var selectTimeHourlyAdapter: SelectTimeHourlyAdapter


    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            SelectTimeHourlyFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }
    }


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
        generateTimePeriod()
        binding.ivClose.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@SelectTimeHourlyFragment)
                .commit()
        }


        //openTime
        binding.tvManually.setOnClickListener {
            openTimePicker(true)

        }

        binding.etTime.setOnClickListener {
            openTimePicker(false)
        }

        binding.btnConfirm.setOnClickListener {

        }
    }


    private fun generateTimePeriod() {
        for (i in timePeriodArray.indices) {
            selectTimeHourlyList.add(SelectTimeListHourly(false, timePeriodArray[i]))
        }
        selectTimeHourlyAdapter =
            SelectTimeHourlyAdapter(requireActivity(), this, selectTimeHourlyList)
        binding.rvTimePeriod.adapter = selectTimeHourlyAdapter


    }


    private fun openTimePicker(_24Hrs: Boolean) {
        val c: Calendar = Calendar.getInstance()
        val hour: Int = c.get(Calendar.HOUR_OF_DAY)
        val minute: Int = c.get(Calendar.MINUTE)


        //06:20 am

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            { view, hourOfDay, minute -> /*tv_time.setText("$hourOfDay:$minute")*/
                if (_24Hrs) {

                    onHourlyTime(0, true)
                    binding.tvManually.text = "${
                        java.lang.String.format(
                            "%02d:%02d", hourOfDay, minute
                        )
                    } Hrs"


                } else {

                    binding.etTime.setText(
                        java.lang.String.format(
                            "%02d:%02d %s", hourOfDay, minute,
                            if (hourOfDay < 12) "AM" else "PM"
                        )
                    )
                }


            },
            hour,
            minute,
            _24Hrs
        )
        timePickerDialog.show()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onHourlyTime(position: Int, clearAllSelection: Boolean) {
        if (!clearAllSelection) {
            binding.tvManually.text = requireActivity().resources.getString(R.string.select_manually)
            selectTimeHourlyAdapter.selectedItem=position

        }
        else
            selectTimeHourlyAdapter.selectedItem=-1
        selectTimeHourlyAdapter.notifyDataSetChanged()

    }


}