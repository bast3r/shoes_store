package com.udacity.shoestore.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.ShoeListItemBinding
import com.udacity.shoestore.databinding.ShoesListFragmentBinding
import com.udacity.shoestore.domain.models.Shoe
import com.udacity.shoestore.presentation.viewmodel.ShoeStoreViewModel
import java.lang.RuntimeException


class ShoesListFragment : Fragment() {
    private var _binding: ShoesListFragmentBinding? = null
    private val binding: ShoesListFragmentBinding
        get() = _binding ?: throw RuntimeException("ShoesListFragmentBinding is null")

    private val shoeViewModel: ShoeStoreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater,
            R.layout.shoes_list_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    private fun setObservers() {
        shoeViewModel.shoeList.observe(viewLifecycleOwner) {
            binding.shoesListContainer.removeAllViews()
            it.forEach { shoe ->
                createAndPopulateShoeItem(shoe)
            }
        }
    }

    private fun setListeners() {
        binding.shoesListFab.setOnClickListener {
            findNavController().navigate(
                ShoesListFragmentDirections.shoesListFragmentToShoeDetailFragment(null)
            )
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {}
            })
    }

    private fun createAndPopulateShoeItem(shoe: Shoe) {
        val item = DataBindingUtil.inflate<ShoeListItemBinding>(
            layoutInflater,
            R.layout.shoe_list_item,
            binding.shoesListContainer,
            false
        )
        binding.shoesListContainer.addView(
            item.root
        )
        item.shoeItemCompany.text = shoe.company
        item.shoeItemName.text = shoe.name
        item.shoeItemSize.text = "${shoe.size}"
        item.shoeItemDescription.text = shoe.description

        item.root.setOnClickListener {
            findNavController().navigate(
                ShoesListFragmentDirections.shoesListFragmentToShoeDetailFragment(shoe)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}