package com.dynamo.chiragtest.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dynamo.chiragtest.R
import com.dynamo.chiragtest.interfaces.LaunchFragmentInterface
import com.dynamo.chiragtest.adapter.RocketListAdapter
import com.dynamo.chiragtest.model.RocketData
import com.dynamo.chiragtest.network.CallResponseStatus
import com.dynamo.chiragtest.viewmodel.RocketViewModel
import kotlinx.android.synthetic.main.fragment_rocket_list.*

class RocketListFragment : Fragment(), RocketListAdapter.OnClickRecipeInterface {
    private var rocketListAdapter: RocketListAdapter? = null
    private var rocketList = ArrayList<RocketData>()
    private var rocketViewModel: RocketViewModel? = null
    private var launchFragmentInterface: LaunchFragmentInterface? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rocket_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        bindView()
        retrieveRepositories()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is LaunchFragmentInterface) {
            launchFragmentInterface = context
        }
    }

    private fun bindView() {
        setRecipeObserver()
    }

    private fun setRecipeObserver() {
        rocketViewModel?.getRockets()?.observe(
            viewLifecycleOwner,
            Observer<CallResponseStatus<List<RocketData>>> {
                it?.let { output ->
                    when (output.status) {
                        CallResponseStatus.Status.SUCCESS -> {
                            progressbar.visibility = View.GONE
                            it.data?.let {
                                rocketList = it as ArrayList<RocketData>
                                setAdapter(rocketList)
                            }

                        }
                        CallResponseStatus.Status.ERROR -> {
                            progressbar.visibility = View.GONE
                            setErrorMessage(it.message)

                        }
                    }
                }
            })


    }



    private fun setErrorMessage(it: String?) {
        activity?.let { it1 ->
            AlertDialog.Builder(it1).setTitle(R.string.error)
                .setMessage(it)
                .setPositiveButton(android.R.string.ok) { _, _ -> activity?.finish() }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    private fun setAdapter(it: List<RocketData>) {
        rocketListAdapter?.dataSetChanged(it)

    }

    private fun initView() {
        rocketViewModel = ViewModelProviders.of(this).get(RocketViewModel::class.java)
        rocketListAdapter = RocketListAdapter(rocketList, this)
        val linearLayoutManager = LinearLayoutManager(activity)
        listView.layoutManager = linearLayoutManager
        listView.adapter = rocketListAdapter
    }

    private fun retrieveRepositories() {
        progressbar.visibility = View.VISIBLE
        rocketViewModel?.getRocketList()
    }

    override fun onClick(repoDto: RocketData?) {
        val bundle = Bundle()
        bundle.putParcelable(RocketDetailFragment.KEY_RECIPE_DATA, repoDto)
        launchFragmentInterface?.launchFragment(bundle, RocketDetailFragment())

    }


}