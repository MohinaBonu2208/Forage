package com.bignerdranch.android.forage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.forage.Data.BaseApplication
import com.bignerdranch.android.forage.databinding.FragmentListBinding

class ListFragment : Fragment() {
    private val viewModel: ForageViewModel by activityViewModels {
        ViewModelFactory((activity?.application as BaseApplication).dataBase.dao())
    }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ForageListAdapter { forage ->
            val action = ListFragmentDirections.actionListFragmentToDetailFragment(forage.id)
            findNavController().navigate(action)


        }
        viewModel.allForages.observe(viewLifecycleOwner) { forages ->
            forages.let {
                adapter.submitList(it)
            }
        }
        binding.rView.layoutManager = LinearLayoutManager(this.context)
        binding.rView.adapter = adapter
        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}