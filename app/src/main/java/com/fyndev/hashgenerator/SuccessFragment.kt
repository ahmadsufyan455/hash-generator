package com.fyndev.hashgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fyndev.hashgenerator.databinding.FragmentSuccessBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuccessFragment : Fragment() {

    private val args: SuccessFragmentArgs by navArgs()

    private var _binding: FragmentSuccessBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessBinding.inflate(inflater, container, false)

        binding.tvHash.text = args.hash

        binding.btnCopy.setOnClickListener {
            onCopyClicked()
        }

        return binding.root
    }

    private fun onCopyClicked() {
        lifecycleScope.launch {
            copyToClipboard(args.hash)
            applyAnimation()
        }
    }

    private fun copyToClipboard(hash: String) {
        val clipBoardManager = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Encrypted Text", hash)
        clipBoardManager.setPrimaryClip(clipData)
    }

    private suspend fun applyAnimation() {
        binding.include.bgMessage.animate().translationY(80f).duration = 200L
        binding.include.tvMessage.animate().translationY(80f).duration = 200L

        delay(2000L)

        binding.include.bgMessage.animate().translationY(-80f).duration = 500L
        binding.include.tvMessage.animate().translationY(-80f).duration = 500L
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}