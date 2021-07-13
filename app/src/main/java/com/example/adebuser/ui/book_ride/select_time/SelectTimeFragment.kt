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
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentSelectTimeBinding
import com.example.adebuser.ui.book_ride.BookRideFragment
import com.example.adebuser.ui.book_ride.select_time.modal.SelectTimeList
import com.example.adebuser.ui.me.favourite_rider.FavouriteDriverActivity
import com.example.adebuser.base.BaseFragment


class SelectTimeFragment : BaseFragment(), SelectTimeAdapter.SelectTimeListener {
    private val binding get() = _binding!!
    private var _binding: FragmentSelectTimeBinding? = null
    var selectTimeList= arrayListOf<SelectTimeList>()
    private var type: String? = null

    private val selectTimeAdapter: SelectTimeAdapter by lazy {
        SelectTimeAdapter(requireActivity(),this,selectTimeList , type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(type: String) =
            SelectTimeFragment().apply {
                arguments = Bundle().apply {
                    putString("type", type)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString("type")

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

        binding.rvPickupRider.adapter= selectTimeAdapter
        if(selectTimeList.isEmpty())
        {
            selectTimeList.add(SelectTimeList(0))
            selectTimeAdapter.notifyDataSetChanged()
        }

        binding.btnContinue.setOnClickListener {
            openDialog()
        }
        binding.closeFragment.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SelectTimeFragment)
                .commit()
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
            startActivityForResult(intent, 1);
            dialog.dismiss()


        }

        automaticBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@SelectTimeFragment)
                .commit()

            replaceFragmentFull(BookRideFragment.newInstance("booked"), "booked ride")
            dialog.dismiss()


        }

    }


}