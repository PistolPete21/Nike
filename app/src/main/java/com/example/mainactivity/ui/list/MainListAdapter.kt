package com.example.mainactivity.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mainactivity.data.Constants
import com.example.mainactivity.data.model.Result
import com.example.mainactivity.ui.story.StoryFragment

class MainListAdapter(private val fragment: Fragment) : FragmentStateAdapter(fragment) {
    var results: List<Any> = emptyList()

    override fun getItemCount(): Int {
        return results.count()
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = StoryFragment()
        fragment.arguments = Bundle().apply {
            putParcelable(Constants.RESULT_ARG, results[position] as Result)
        }
        return fragment
    }
}