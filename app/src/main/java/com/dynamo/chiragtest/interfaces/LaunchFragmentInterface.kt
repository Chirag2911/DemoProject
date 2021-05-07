package com.dynamo.chiragtest.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment

    interface LaunchFragmentInterface {
        fun launchFragment(bundle: Bundle? = null, fragment: Fragment? = null)
    }
