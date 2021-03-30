package com.example.mainactivity.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mainactivity.R
import com.example.mainactivity.ui.util.HorizontalMarginItemDecoration
import com.example.mainactivity.ui.util.ZoomOutPageTransformer


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var errorView: ConstraintLayout
    private lateinit var loadingView: ConstraintLayout
    private var listAdapter: MainListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (listAdapter == null) {
            activity?.let {
                listAdapter = MainListAdapter(this)
            }
        }

        viewPager = view.findViewById(R.id.viewpager)
        context?.let {
            val itemDecoration = HorizontalMarginItemDecoration(
                it,
                R.dimen.current_viewpager_horizontal_margin
            )
            viewPager.addItemDecoration(itemDecoration)
        }
        viewPager.apply {
            offscreenPageLimit = 1
            adapter = listAdapter
            setPageTransformer(ZoomOutPageTransformer())
        }

        errorView = view.findViewById(R.id.error_layout)
        loadingView = view.findViewById(R.id.loading_indicator_layout)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            val factory = MainViewModelFactory(requireContext())
            viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
            connectAndObserveViewModel(viewModel)
        }
    }

    private fun connectAndObserveViewModel(viewModel: MainViewModel) {
        viewModel.data.observe(viewLifecycleOwner,
            Observer {
                listAdapter?.results = it
                listAdapter?.notifyDataSetChanged()
            })

        viewModel.showErrorView.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    errorView.visibility = View.VISIBLE
                } else {
                    errorView.visibility = View.INVISIBLE
                }
            })

        viewModel.isLoading.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    loadingView.visibility = View.VISIBLE
                } else {
                    loadingView.visibility = View.INVISIBLE
                }
            })
    }
}