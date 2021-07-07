package com.example.adebuser.ui.book_ride.select_car

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.IdRes
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.FragmentManager
import com.example.adebuser.R
import com.example.adebuser.databinding.FragmentCarTypeBinding
import com.example.adebuser.ui.me.favourite_rider.FavouriteRiderActivity
import com.example.adebuser.ui.payment_method.PaymentFragment
import com.wizebrains.adventmingle.base.BaseFragment


class CarTypeFragment : BaseFragment() {


    private var _binding: FragmentCarTypeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCarTypeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCloseFragment.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                .commit()
        }

        binding.paymentMethod.setOnClickListener {

            addSlidingFragment(
                requireActivity().supportFragmentManager,
                PaymentFragment.newInstance("home"), "payment",
                R.id.frame_container
            )
        }

        binding.bookRide.setOnClickListener {
            openDialog()
        }
    }


    private fun openFragment(fragment: Fragment, tag: String) {
        addSlidingFragment(
            requireActivity().supportFragmentManager,
            fragment,
            tag,
            R.id.flContainerSlide
        )
    }

    private fun addSlidingFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String, @IdRes container: Int
    ) {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_up_dialog, 0, 0,
            R.anim.slide_down_dialog
        ).add(container, fragment, tag).addToBackStack(null).commit()
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
            val intent: Intent = Intent(requireActivity(), FavouriteRiderActivity::class.java)
            startActivity(intent)
        }

        automaticBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().remove(this@CarTypeFragment)
                .commit()

//            openFragment(PaymentFragment.newInstance("home"), "payment")


        }

    }

}