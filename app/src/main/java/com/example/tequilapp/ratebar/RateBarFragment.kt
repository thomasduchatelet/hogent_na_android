package com.example.tequilapp.ratebar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelProviders.of
import androidx.navigation.fragment.findNavController
import com.example.tequilapp.R
import com.example.tequilapp.addbar.AddBarViewModel
import com.example.tequilapp.addbar.AddBarViewModelFactory
import com.example.tequilapp.database.TequilaDatabase
import com.example.tequilapp.databinding.AddBarFragmentBinding
import com.example.tequilapp.databinding.BarListFragmentBinding
import com.example.tequilapp.databinding.RateBarFragmentBinding
import java.lang.IllegalArgumentException

class RateBarFragment : Fragment() {
    private lateinit var viewModel: RateBarViewModel
    private var _binding: RateBarFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = RateBarFragmentBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TequilaDatabase.getInstance(application).tequilaDatabaseDao
        var id = RateBarFragmentArgs.fromBundle(requireArguments()).barId.toString()
        if (id.toLongOrNull() != null) {
            val viewModelFactory = RateBarViewModelFactory(id.toLong(), dataSource, application)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(RateBarViewModel::class.java)
        }
        viewModel.getBar().observe(viewLifecycleOwner, Observer { binding.bar = it })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRateBarSave.setOnClickListener{ onSaveRatingClick() }
    }

    private fun onSaveRatingClick() {
        var rating = binding.rateBarRating.rating.toDouble()
        try{
            viewModel.addRating(rating)
        } catch (e:IllegalArgumentException){
            Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
        }
        findNavController().navigate(RateBarFragmentDirections.actionRateBarFragmentToFirstFragment())
    }

}