package com.udacity.shoestore.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ShoeDetailFragmentBinding
import com.udacity.shoestore.domain.models.Shoe
import com.udacity.shoestore.presentation.viewmodel.ShoeStoreViewModel


class ShoeDetailFragment : Fragment() {

    private var _binding: ShoeDetailFragmentBinding? = null
    private val binding: ShoeDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("ShoeDetailFragmentBinding is null")

    private val shoeViewModel: ShoeStoreViewModel by activityViewModels()

    private val args: ShoeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.shoe_detail_fragment,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.shoeDetail?.let { shoeViewModel.loadShoeDetail(it) }

        shoeViewModel.shoeDetail.observe(viewLifecycleOwner) {
            val isReview = it?.let { shoe ->
                binding.shoeDetail = shoe
                true
            } ?: false

            binding.shoeDetailSaveButton.isVisible = !isReview
        }

        binding.shoeDetailSaveButton.setOnClickListener {
            if (validateForm()) {
                val shoe = Shoe(
                    company = binding.shoeDetailCompany.text.toString(),
                    name = binding.shoeDetailName.text.toString(),
                    size = binding.shoeDetailSize.text.toString().toDouble(),
                    description = binding.shoeDetailDescription.text.toString()
                )
                shoeViewModel.createNewShoe(shoe)
                Toast.makeText(context, getString(R.string.save_is_successful), Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }

        binding.shoeDetailCancelButton.setOnClickListener {
            shoeViewModel.clearData()
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    shoeViewModel.clearData()
                    findNavController().popBackStack()
                }
            })
    }

    private fun validateForm(): Boolean {
        var isValid = true
        val nameEditView = binding.shoeDetailName.apply { error = null }
        val companyEditView = binding.shoeDetailCompany.apply { error = null }
        val descriptionEditView = binding.shoeDetailDescription.apply { error = null }
        val sizeEditView = binding.shoeDetailSize.apply { error = null }

        if (nameEditView.text.isNullOrEmpty()) {
            nameEditView.error = getString(R.string.msg_fill_shoe_name)
            isValid = false
        }
        if (companyEditView.text.isNullOrEmpty()) {
            companyEditView.error = getString(R.string.msg_fill_shoe_brand)
            isValid = false
        }
        if (descriptionEditView.text.isNullOrEmpty()) {
            descriptionEditView.error = getString(R.string.msg_fill_shoe_description)
            isValid = false
        }
        if (sizeEditView.text.isNullOrEmpty()) {
            sizeEditView.error = getString(R.string.msg_fill_shoe_size)
            isValid = false
        } else {
            val size = sizeEditView.text.toString().toDouble()
            if (size < 25 || size > 55) {
                sizeEditView.error = getString(R.string.msg_fill_shoe_size_correct)
                isValid = false
            }
        }

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}