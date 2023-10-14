package com.example.m5.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.m5.R
import com.example.m5.databinding.ActivityNetEaseMineBinding
import com.example.m5.databinding.ActivityRecommendBinding
import com.example.m5.databinding.FragmentNeteaseEverythingBinding
import com.example.m5.databinding.FragmentRecommendMusicBinding

class RecommendMusic : Fragment() {

    lateinit var binding: ActivityRecommendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityRecommendBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {

    }

}