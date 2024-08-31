package com.example_info.rentease.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example_info.rentease.R

class AliceNavigator(
    private val fragmentManager: FragmentManager,
) {

    fun navigateAndClearAll(fragment: Fragment) {
        val transition = fragmentManager.beginTransaction()
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        transition.replace(R.id.fragmentContainer, fragment)
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transition.commit()
    }

    fun navigate(fragment: Fragment, clearBackStack: Boolean = false) {
        val transition = fragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainer, fragment)
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (!clearBackStack) {
            transition.addToBackStack(fragment.javaClass.name)
        }
        transition.commit()
    }

    fun popBackStack() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        }
    }

}
