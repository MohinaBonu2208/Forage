package com.bignerdranch.android.forage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.forage.Data.BaseApplication
import com.bignerdranch.android.forage.Data.Forage
import com.bignerdranch.android.forage.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private val navigationArgs: AddFragmentArgs by navArgs()

    private val viewModel: ForageViewModel by activityViewModels {
        ViewModelFactory((activity?.application as BaseApplication).dataBase.dao())
    }
    private lateinit var forage: Forage

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.id
        if (id > 0) {
            viewModel.getForage(id).observe(viewLifecycleOwner) { selectedForage ->
                forage = selectedForage
                bindForage(forage)
            }
            binding.delete.visibility = View.VISIBLE
            binding.delete.setOnClickListener { deleteForage(forage) }
        } else {
            binding.save.setOnClickListener { addForage() }
        }
    }

    private fun deleteForage(forage: Forage) {
        viewModel.delete(forage)
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }

    private fun bindForage(forage: Forage) {
        binding.apply {
            nameInput.setText(forage.name, TextView.BufferType.SPANNABLE)
            locationInput.setText(forage.location, TextView.BufferType.SPANNABLE)
            checkBox.isChecked = forage.inSeason
            noteInput.setText(forage.notes, TextView.BufferType.SPANNABLE)
        }
    }

    private fun addForage() {
        if (isValidEntry()) {
            viewModel.addForage(
                binding.nameInput.text.toString(),
                binding.locationInput.text.toString(),
                binding.checkBox.isChecked,
                binding.noteInput.text.toString()
            )
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(),
        binding.locationInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}