package com.wizebrains.adventmingle.base

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.adebuser.R


abstract class BaseFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

             activity?.window?.statusBarColor  = ContextCompat.getColor(requireContext(), R.color.appColor)
             activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

         }*/
        // activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.appColor)
    }


    fun openDialogFragment(mFragment: DialogFragment, mString: String) {
        var fragmentManager = childFragmentManager
        mFragment.isCancelable = false
        mFragment.show(fragmentManager, mString)
    }

    private fun addFragment(fragment: Fragment) {
        (activity as BaseFragment).addFragment(fragment)
    }

    private fun removeTopFragment() {
        (activity as BaseFragment).removeTopFragment()
    }

    private fun setTitle(resId: Int) {
        activity?.setTitle(resId)
    }

    private fun setTitle(title: String) {
        activity?.setTitle(title)
    }


    open fun openFragmentSmall(fragment: Fragment, tag: String) {
        addSlidingFragment(
            requireActivity().supportFragmentManager,
            fragment,
            tag,
            R.id.flContainerSlide
        )
    }


    open fun openFragmentFull(fragment: Fragment, tag: String) {
        addSlidingFragment(
            requireActivity().supportFragmentManager,
            fragment,
            tag,
            R.id.frame_container
        )
    }


    open fun addSlidingFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String, @IdRes container: Int
    ) {
        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_up_dialog, 0, 0,
            R.anim.slide_down_dialog
        ).add(container, fragment, tag).addToBackStack(null).commit()
    }

    open fun replaceFragmentFull(fragment: Fragment, tag: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, fragment, tag).commit()
    }

    open fun replaceFragmentSmall(fragment: Fragment, tag: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flContainerSlide, fragment, tag).commit()
    }

    fun disappearKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

}