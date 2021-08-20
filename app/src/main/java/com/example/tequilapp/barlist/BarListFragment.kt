package com.example.tequilapp.barlist

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tequilapp.R
import com.example.tequilapp.database.TequilaDatabase
import com.example.tequilapp.databinding.BarListFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BarListFragment : Fragment() {
    private lateinit var viewModel: BarListViewModel

    private var _binding: BarListFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = BarListFragmentBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TequilaDatabase.getInstance(application).tequilaDatabaseDao
        val viewModelFactory = BarListViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(BarListViewModel::class.java)

        val adapter = BarListAdapter(BarItemListener { barId -> viewModel.onBarClicked(barId) })
        binding.barList.adapter = adapter

        viewModel.navigateToBar.observe(viewLifecycleOwner, Observer { bar ->
            bar?.let {
                this.findNavController().navigate(BarListFragmentDirections.actionFirstFragmentToRateBarFragment(bar))
                viewModel.onBarNavigated()
            }
        })

        viewModel.bars.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (adapter != null) {
                    adapter.data = it
                }
            }
        })

        viewModel.sortOption.observe(viewLifecycleOwner, Observer { sortoption ->
            sortoption?.let { option ->
                if(adapter != null){
                    when(option){
                        SortOptions.NAME -> adapter.data = adapter.data.sortedWith(compareBy{it.name})
                        SortOptions.PRICE -> adapter.data = adapter.data.sortedWith(compareBy{it.price})
                        SortOptions.RATING -> adapter.data = adapter.data.sortedWith(compareByDescending{it.tequilaQuality})
                        SortOptions.PRICERATING -> adapter.data = adapter.data.sortedWith(compareByDescending{it.tequilaQuality / it.price})
                    }
                }
            }
        })

        showSortButtons(false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSortingClickListeners()
        binding.swtchShowFilters.setOnCheckedChangeListener{ _, isChecked ->
            showSortButtons(isChecked)
        }

        binding.fab.setOnClickListener { view ->
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun showSortButtons(checked: Boolean) {
        if(checked)
            binding.apply {
                btnSortName.visibility = View.VISIBLE
                btnSortPriceRating.visibility = View.VISIBLE
                btnSortPrice.visibility = View.VISIBLE
                btnSortRating.visibility = View.VISIBLE
            }
        else
            binding.apply {
                btnSortName.visibility = View.GONE
                btnSortPriceRating.visibility = View.GONE
                btnSortPrice.visibility = View.GONE
                btnSortRating.visibility = View.GONE
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSortingClickListeners(){
        binding.apply {
            btnSortName.setOnClickListener {
                viewModel.setSortOption(SortOptions.NAME)
            }
            btnSortPrice.setOnClickListener {
                viewModel.setSortOption(SortOptions.PRICE)
            }
            btnSortRating.setOnClickListener{
                viewModel.setSortOption(SortOptions.RATING)
            }
            btnSortPriceRating.setOnClickListener{
                viewModel.setSortOption(SortOptions.PRICERATING)
            }
        }
    }
}


enum class SortOptions {
    NAME,
    PRICE,
    RATING,
    PRICERATING
}