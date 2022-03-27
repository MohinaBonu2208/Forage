package com.bignerdranch.android.forage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.forage.Data.BaseApplication
import com.bignerdranch.android.forage.Data.Forage
import com.bignerdranch.android.forage.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var forage: Forage

    private val viewModel: ForageViewModel by activityViewModels {
        ViewModelFactory((activity?.application as BaseApplication).dataBase.dao())
    }
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.id

        viewModel.getForage(id).observe(viewLifecycleOwner) { selectedItem ->
            forage = selectedItem
            bind()
        }


    }

    fun bind() {
        binding.apply {
            nameTv.text = forage.name
            locationTv.text = forage.location
            noteTv.text = forage.notes
            if (forage.inSeason) {
                checkBoxTv.text = getString(R.string.in_season)
            } else {
                checkBoxTv.text = getString(R.string.not_season)
            }
            floatingActionButton.setOnClickListener {
                val action = DetailFragmentDirections.actionDetailFragmentToAddFragment(forage.id)
                findNavController().navigate(action)
            }
            locationTv.setOnClickListener { launchMap() }
        }

    }

    private fun launchMap() {

        val address = forage.location
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}
