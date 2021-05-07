package com.dynamo.chiragtest.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dynamo.chiragtest.R
import com.dynamo.chiragtest.model.RocketData
import com.dynamo.chiragtest.viewmodel.RocketViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class RocketDetailFragment : Fragment() {
    private var recipeData: RocketData? = null
    private var recipeViewModel: RocketViewModel? = null

    companion object {
        val KEY_RECIPE_DATA = "rocket_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeViewModel = ViewModelProviders.of(this).get(RocketViewModel::class.java)
        recipeData = arguments?.get(KEY_RECIPE_DATA) as RocketData
        recipeData?.let { bindView(recipeData) }

    }

    private fun bindView(rocketData: RocketData?) {
        rocketData?.apply {
            rocketImageData?.let {
                Glide.with(rocketImage)
                    .load(it.get(1))
                    .centerCrop()
                    .into(rocketImage)
            }
            description?.let {
                txtDescription.text=it
            }
            name?.let { txtHeading.text=it }
            companyName?.let {
                txtCompanyValue.text=companyName
            }
            launchDate?.let {
                txtDateValue.text=launchDate
            }
            location?.let {
                txtLocationValue.text=location
            }

        }
    }
}