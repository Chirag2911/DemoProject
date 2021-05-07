package com.dynamo.chiragtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.dynamo.chiragtest.fragment.RocketDetailFragment
import com.dynamo.chiragtest.fragment.RocketListFragment
import com.dynamo.chiragtest.interfaces.LaunchFragmentInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),LaunchFragmentInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        launchFragment(fragment = RocketListFragment())
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.rocket_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    override fun launchFragment(bundle: Bundle?, fragment: Fragment?) {
        val fragment1 = supportFragmentManager.findFragmentByTag(fragment?.javaClass?.simpleName)
        if (fragment is RocketDetailFragment) {
            supportActionBar?.title = getString(R.string.rocket_detail)
        }
        if (fragment1 == null) {
            fragment?.arguments = bundle
            fragment?.let { it1 ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, it1, it1.javaClass.simpleName)
                    .addToBackStack(it1.javaClass.simpleName)
                    .commit()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
            supportActionBar?.title = getString(R.string.rocket_list)
        }
    }
}
