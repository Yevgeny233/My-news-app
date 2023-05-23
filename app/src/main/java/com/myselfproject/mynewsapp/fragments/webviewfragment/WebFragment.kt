package com.myselfproject.mynewsapp.fragments.webviewfragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.myselfproject.mynewsapp.KEY_URL
import com.myselfproject.mynewsapp.databinding.FragmentWebBinding

class WebFragment : Fragment() {
    private lateinit var binding: FragmentWebBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.webView.webViewClient = object : NewsWebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }

        val urlString = requireArguments().getString(KEY_URL)
        binding.webView.loadUrl(urlString!!)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        binding.webView.goBack()
                    } else {
                        if (isEnabled) {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                }

            })

    }

}