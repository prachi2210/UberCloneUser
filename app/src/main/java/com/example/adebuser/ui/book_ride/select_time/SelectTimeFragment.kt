package com.example.adebuser.ui.book_ride.select_time

import android.app.Dialog
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
import com.example.adebuser.databinding.FragmentSelectTimeBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.select_time.model.SelectTimeList
import com.example.adebuser.ui.me.favourite_rider.FavouriteDriverActivity
import com.example.adebuser.base.BaseFragment
import com.example.adebuser.base.ViewModelProviderFactory
import com.example.adebuser.data.api.ApiHelper
import com.example.adebuser.data.api.RetrofitBuilder
import com.example.adebuser.ui.book_ride.BookRideViewModel
import com.example.adebuser.ui.book_ride.booking_request.BookRideRequest
import com.example.adebuser.ui.home.HomeScreenActivity
import com.example.adebuser.utils.Status
import java.text.SimpleDateFormat
import java.util.*


class SelectTimeFragment : BaseFragment(), SelectTimeAdapter.SelectTimeListener {
    private val binding get() = _binding!!
    private var _binding: FragmentSelectTimeBinding? = null
    var selectTimeList = arrayListOf<SelectTimeList>()
    private var type: String? = null
    private var bookRideRequest: BookRideRequest? = null

    private lateinit var bookViewModel: BookRideViewModel

    private val selectTimeAdapter: SelectTimeAdapter by lazy {
        SelectTimeAdapter(requireActivity(), this, selectTimeList, type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String, bookRideRequest: BookRideRequest) =
            SelectTimeFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                    putParcelable("data", bookRideRequest)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")
            bookRideRequest = it.getParcelable<BookRideRequest>("data")
        }
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

        bookViewModel = ViewModelProvider(
            this,
            ViewModelProviderFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(BookRideViewModel::class.java)

        binding.rvPickupRider.adapter = selectTimeAdapter
        if (selectTimeList.isEmpty()) {
            selectTimeList.add(SelectTimeList(0))
            selectTimeAdapter.notifyDataSetChanged()
        }

        binding.btnContinue.setOnClickListener {
            if (bookRideRequest?.scheduleTime.isNullOrEmpty()) {
                (activity as HomeScreenActivity).setError(getString(R.string.pick_up_error))
            } else {
                openDialog()
            }
        }
        binding.closeFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .remove(this@SelectTimeFragment)
                .commit()
        }


    }

    override fun onAddPickup(position: Int) {
        selectTimeList.add(SelectTimeList(0))
        selectTimeAdapter.notifyDataSetChanged()
    }

    override fun onRemovePickup(position: Int) {
        selectTimeList.removeAt(position)
        selectTimeAdapter.notifyDataSetChanged()
    }

    override fun getSelectedTime(time: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate: String = sdf.format(Date())
        bookRideRequest?.scheduleTime = currentDate.plus(" ").plus(time)

    }


    private fun openDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.driver_selection_layout)
        dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent);
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
                startActivityForResult(intent, 1);
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
                                    requireActivity().supportFragmentManager.beginTransaction().remove(this@SelectTimeFragment)
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