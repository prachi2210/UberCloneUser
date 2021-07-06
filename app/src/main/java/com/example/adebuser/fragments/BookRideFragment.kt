package com.example.adebuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.adebuser.R


class BookRideFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("")
            param2 = it.getString("")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_ride, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    private fun openFragment(fragment: Fragment, tag: String) {
        replaceSlidingFragment(
           requireActivity().supportFragmentManager,
            fragment,
            tag,
            R.id.flContainerSlide
        )
    }

    private fun closeFragment() {
        if ( requireActivity().supportFragmentManager.backStackEntryCount > 0) {
            requireActivity().supportFragmentManager.popBackStackImmediate()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookRideFragment().apply {
                arguments = Bundle().apply {
                    putString("", param1)
                    putString("", param2)
                }
            }
    }

    private fun replaceSlidingFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String, @IdRes container: Int
    ) {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_up_dialog, 0, 0,
            R.anim.slide_down_dialog
        ).replace(container, fragment, tag).commit()
    }
}