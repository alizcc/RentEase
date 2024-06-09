package com.example_info.rentease.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example_info.rentease.R

/**
 * Created by AP-Jake
 * on 09/06/2024
 */

class AliceNavigator(
    private val fragmentManager: FragmentManager,
) {

    fun navigate(fragment: Fragment, clearBackStack: Boolean = false) {
        val transition = fragmentManager.beginTransaction()
        transition.replace(R.id.fragmentContainer, fragment)
        transition.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (!clearBackStack) {
            transition.addToBackStack(fragment.javaClass.name)
        }
        transition.commit()
    }

}
