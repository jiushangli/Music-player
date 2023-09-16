package com.example.m5.frag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.m5.R
import com.example.m5.activity.MainActivity
import com.example.m5.ui.netEaseMineActivity.NetEaseMineActivity
import com.example.m5.databinding.FragmentNeteaseEverythingBinding
import com.example.m5.ui.NetEaseCloudMusic.NeBrowseActivity
import com.example.m5.ui.searchMusic.SearchActivity

class NeteaseEverything : Fragment() {
    companion object {
        @Suppress("StaticFieldLeak")
        lateinit var binding: FragmentNeteaseEverythingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().theme.applyStyle(MainActivity.currentTheme[MainActivity.themeIndex], true)
        val view = inflater.inflate(R.layout.fragment_netease_everything, container, false)
        binding = FragmentNeteaseEverythingBinding.bind(view)

        binding.NetEaseBtnNF.setOnClickListener {
            val intent =
                Intent(
                    requireContext(),
                    NetEaseMineActivity::class.java
                ).setAction("your.custom.action")
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        binding.BrowseBtnNF.setOnClickListener {
            val intent =
                Intent(
                    requireContext(),
                    NeBrowseActivity::class.java
                ).setAction("your.custom.action")
            ContextCompat.startActivity(requireContext(), intent, null)
        }

        binding.SearchNF.setOnClickListener {
            val intent =
                Intent(
                    requireContext(),
                    SearchActivity::class.java
                ).setAction("your.custom.action")
            ContextCompat.startActivity(requireContext(), intent, null)
        }


        return view
    }

    override fun onResume() {
        super.onResume()
        binding.root.visibility = View.VISIBLE

    }
}