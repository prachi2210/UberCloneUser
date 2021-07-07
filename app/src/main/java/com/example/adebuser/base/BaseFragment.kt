package com.wizebrains.adventmingle.base

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
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

    private fun addFragment(fragment: Fragment){
        (activity as BaseFragment).addFragment(fragment)
    }

    private fun removeTopFragment(){
        (activity as BaseFragment).removeTopFragment()
    }

    private fun setTitle(resId: Int){
        activity?.setTitle(resId)
    }

    private fun setTitle(title:String){
        activity?.setTitle(title)
    }



}