package com.example.mainactivity.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mainactivity.R
import com.example.mainactivity.data.Constants.RESULT_ARG
import com.example.mainactivity.data.model.Result
import com.example.mainactivity.ui.list.MainFragmentDirections

class StoryFragment : Fragment() {

    private lateinit var cardView: CardView
    private lateinit var titleView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.result_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(RESULT_ARG) }?.apply {
            val item: Result = getParcelable<Result>(RESULT_ARG) as Result

            imageView = view.findViewById(R.id.results_image_view)
            getImage(item, imageView)
            (imageView.layoutParams as ConstraintLayout.LayoutParams).matchConstraintPercentWidth = 0.3f

            titleView = view.findViewById(R.id.results_title_text_view)
            titleView.text = item.title
            typeTextView = view.findViewById(R.id.results_category_text_view)
            typeTextView.text = item.type

            cardView = view.findViewById(R.id.result_item_card)
            cardView.setOnClickListener {
                launchDetail(view, item)
            }
        }
    }

    private fun getImage(item: Result, imageView: ImageView) {
        context?.let {
            Glide.with(it)
                .load(item.media[0].media_metadata[2].url)
                .transform(CenterCrop(), RoundedCorners(4))
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

    private fun launchDetail(view: View, item: Result) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(item)
        view.findNavController().navigate(action)
    }
}