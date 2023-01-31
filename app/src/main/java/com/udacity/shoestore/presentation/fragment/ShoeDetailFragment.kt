package com.udacity.shoestore.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ShoeDetailFragmentBinding
import com.udacity.shoestore.domain.models.Shoe
import com.udacity.shoestore.presentation.viewmodel.ShoeStoreViewModel
import java.lang.RuntimeException


class ShoeDetailFragment : Fragment() {

    private var _binding: ShoeDetailFragmentBinding? = null
    private val binding: ShoeDetailFragmentBinding
        get() = _binding ?: throw RuntimeException("ShoeDetailFragmentBinding is null")

    private val shoeViewModel: ShoeStoreViewModel by activityViewModels()

    private val args: ShoeDetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

//        val toolbar = (activity as? MainActivity)?.toolbar
//        setSupportActionBar(toolbar)
//        val actionBar = supportActionBar
//        actionBar?.setDisplayHomeAsUpEnabled(true)

        args.shoeDetail?.let { shoeViewModel.loadShoeDetail(it) }

        shoeViewModel.shoeDetail.observe(viewLifecycleOwner) {
            val isReview = it?.let { shoe ->
                binding.shoeDetailCompany.setText(shoe.company)
                binding.shoeDetailName.setText(shoe.name)
                binding.shoeDetailDescription.setText(shoe.description)
                binding.shoeDetailSize.setText("${shoe.size}")
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
            findNavController().popBackStack()
        }

    }

    private fun validateForm(): Boolean {
        var isValid = true

        return isValid
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}