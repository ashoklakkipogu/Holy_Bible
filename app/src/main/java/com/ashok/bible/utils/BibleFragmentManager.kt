package com.ashok.bible.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.ashok.bible.R


public class BibleFragmentManager {
    companion object {
        var mFragmentManager: FragmentManager? = null

        fun init(activity: FragmentActivity) {
            mFragmentManager = activity.supportFragmentManager
        }

        public fun addFragment(fragment: Fragment, isAddToStack: Boolean) {
            val fragmentTransaction = mFragmentManager!!.beginTransaction()
            if (isAddToStack)
                fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
            //fragmentTransaction.add(R.id.frame, fragment, fragment.javaClass.simpleName)
            fragmentTransaction.commit()

        }


        fun replaceFragment(fragment: Fragment, isAddToStack: Boolean) {
            val fragmentTransaction = mFragmentManager!!.beginTransaction()
            if (isAddToStack)
                fragmentTransaction.addToBackStack(fragment.javaClass.simpleName)
            //fragmentTransaction.replace(R.id.frame, fragment, fragment.javaClass.simpleName)
            fragmentTransaction.commit()

        }

        fun isFragmentOnTop(fragment: Fragment): Boolean {
            if (mFragmentManager!!.backStackEntryCount == 0) {
                return false
            }
            var fragmentNameOnStackTop: String? = ""
            val topEntry = mFragmentManager!!.getBackStackEntryAt(
                mFragmentManager!!.backStackEntryCount - 1
            )
            fragmentNameOnStackTop = topEntry.name
            return if (fragmentNameOnStackTop != null) fragmentNameOnStackTop == fragment.javaClass.simpleName else false
        }

        fun getLastLoadedFragment(): Fragment? {
            var fragment: Fragment? = null
            if (mFragmentManager!!.backStackEntryCount > 0) {
                fragment =
                    mFragmentManager!!.findFragmentByTag(
                        mFragmentManager!!.getBackStackEntryAt(
                            mFragmentManager!!.backStackEntryCount - 1
                        ).name
                    )
            }
            return fragment
        }

        fun popBackStack() {
            if (mFragmentManager!!.backStackEntryCount > 0) {
                mFragmentManager!!.popBackStack()
            }
        }
    }


}