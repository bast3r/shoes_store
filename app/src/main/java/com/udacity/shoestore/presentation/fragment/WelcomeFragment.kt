package com.udacity.shoestore.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.WelcomeFragmentBinding
import java.lang.RuntimeException

class WelcomeFragment : Fragment() {
    private var _binding: WelcomeFragmentBinding? = null
    private val binding: WelcomeFragmentBinding
        get() = _binding ?: throw RuntimeException("WelcomeFragmentBinding is null")
    private val args: WelcomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.welcome_fragment,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.welcomeHeader.text = getString(R.string.welcome_header, args.login)
        binding.welcomeShortDescription.isVisible = args.newUser

        binding.onboardingContinueButton.setOnClickListener {
            val destinationId = if (args.newUser) {
                R.id.welcomeFragment_to_onboardingFragment
            } else {
                R.id.welcomeFragment_to_shoesListFragment
            }
            findNavController().navigate(destinationId)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}