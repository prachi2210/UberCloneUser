package com.example.adebuser.ui.book_ride.select_time

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.ViewModelProvider
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentSelectTimeHourlyBinding
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.book_ride.select_time.model.SelectTimeListHourly
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.ui.me.favourite_rider.FavouriteDriverActivity
import com.example.adebuser.utils.Status
import java.util.*


class SelectTimeHourlyFragment : BaseFragment(), SelectTimeHourlyAdapter.SelectTimeHourlyListener {
    private var _binding: FragmentSelectTimeHourlyBinding? = null
    private val binding get() = _binding!!
    private val TAG = SelectTimeHourlyFragment::class.java.simpleName
    private var timePeriodArray = arrayOf<String>()
    var selectTimeHourlyList = arrayListOf<SelectTimeListHourly>()
    private lateinit var selectTimeHourlyAdapter: SelectTimeHourlyAdapter
    private var bookRideRequest: BookRideRequest? = null
    private lateinit var bookViewModel: BookRideViewModel


    companion object {
        @JvmStatic
        fun newInstance(bookRideRequest: BookRideRequest) =
            SelectTimeHourlyFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("data", bookRideRequest)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookRideRequest = it.getParcelable<BookRideRequest>("data")
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

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(BookRideViewModel::class.java)



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
            if (bookRideRequest?.hourly.isNullOrEmpty() || bookRideRequest?.scheduleTime.isNullOrEmpty()) {
                (activity as HomeScreenActivity).setError(getString(R.string.pick_up_error))
            } else {
                openDialog()
            }
        }


        binding.closeFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@SelectTimeHourlyFragment)
                .commit()
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

                    bookRideRequest?.hourly = binding.tvManually.text.toString()

                } else {

                    binding.etTime.setText(
                        java.lang.String.format(
                            "%02d:%02d %s", hourOfDay, minute,
                            if (hourOfDay < 12) "AM" else "PM"
                        )
                    )

                    bookRideRequest?.scheduleTime = binding.etTime.text.toString()
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

            bookRideRequest?.hourly = selectTimeHourlyList[position].time
            binding.tvManually.text =
                requireActivity().resources.getString(R.string.select_manually)
            selectTimeHourlyAdapter.selectedItem = position

        } else
            selectTimeHourlyAdapter.selectedItem = -1
        selectTimeHourlyAdapter.notifyDataSetChanged()

    }

    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.driver_selection_layout)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        val cancelBtn: ImageView = dialog.findViewById(R.id.cancel)
        val favoriteBtn: LinearLayoutCompat = dialog.findViewById(R.id.select_favorite_driver)
        val automaticBtn: LinearLayoutCompat = dialog.findViewById(R.id.choose_automatically)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        favoriteBtn.setOnClickListener {
            val intent: Intent = Intent(requireActivity(), FavouriteDriverActivity::class.java)
            intent.putExtra("data", bookRideRequest)
            startActivityForResult(intent, 1)
            dialog.dismiss()


        }

        automaticBtn.setOnClickListener {
            bookViewModel.bookRide(bookRideRequest!!).observe(requireActivity(), androidx.lifecycle.Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            (activity as HomeScreenActivity).dismissDialog()
                            resource.data?.let { user ->
                                if (user.body()?.status.equals("success")) {
                                    requireActivity().supportFragmentManager.beginTransaction().remove(this@SelectTimeHourlyFragment)
                                        .commit()

                                    replaceFragmentFull(BookRideFragment.newInstance("booked", "searching driver"), "booked")
                                    dialog.dismiss()
                                } else {
                                    (activity as HomeScreenActivity).setError(user.body()?.msg.toString())

                                }
                            }

                        }
                        Status.ERROR -> {
                            (activity as HomeScreenActivity).dismissDialog()
                            (activity as HomeScreenActivity).setError(it.message.toString())
                        }
                        Status.LOADING -> {
                            (activity as HomeScreenActivity).showDialog()
                        }
                    }
                }
            })

        }

    }


}