package com.example.mainactivity.ui.detail

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mainactivity.R
import com.example.mainactivity.data.model.Result
import com.example.mainactivity.databinding.ResultDetailBinding


class DetailFragment : Fragment() {

    private lateinit var loadingView: ConstraintLayout
    private lateinit var errorView: ConstraintLayout

    private lateinit var binding: ResultDetailBinding
    private lateinit var safeArgs: DetailActivityArgs
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            safeArgs = DetailActivityArgs.fromBundle(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.result_detail, container, false)
        val view:View = binding.root
        binding.result = safeArgs.RESULTARG
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getImage(safeArgs.RESULTARG, binding.resultDetailImageView)
        (binding.resultDetailImageView.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.3f

        binding.resultDetailShareImageView.setOnClickListener {
            viewModel.launchShare(safeArgs.RESULTARG)
        }

        binding.resultDetailLikeImageView.setOnClickListener {
            viewModel.increaseLikeCount()
        }

        binding.resultDetailOverviewWebView.loadUrl(safeArgs.RESULTARG.url)
        binding.resultDetailOverviewWebView.webViewClient =
            object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                        Intent(Intent.ACTION_VIEW, Uri.parse(request?.url.toString())).apply {
                            startActivity(this)
                        }
                        return true
                }
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    loadingView.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView, url: String) {
                    loadingView.visibility = View.INVISIBLE
                }
            }

        loadingView = view.findViewById(R.id.loading_indicator_layout)
        loadingView.visibility = View.INVISIBLE
        errorView = view.findViewById(R.id.error_layout)
        errorView.visibility = View.INVISIBLE
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            val factory = DetailViewModelFactory()
            viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
            connectAndObserveViewModel(viewModel)
        }
    }

    private fun connectAndObserveViewModel(viewModel: DetailViewModel) {
        viewModel.intent.observe(viewLifecycleOwner,
            Observer {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, it.url)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            })

        viewModel.likeCount.observe(viewLifecycleOwner,
            Observer {
                binding.resultDetailLikeCountTextView.text = it.toString()
            })
    }


    private fun getImage(item: Result, imageView: ImageView) {
        imageView.context?.let {
            Glide.with(it)
                .load(item.media[0].media_metadata[2].url)
                .transform(CenterCrop(), RoundedCorners(4))
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }
}