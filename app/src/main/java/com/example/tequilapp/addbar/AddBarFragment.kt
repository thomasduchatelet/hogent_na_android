package com.example.tequilapp.addbar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RatingBar
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.tequilapp.R
import com.example.tequilapp.barlist.BarListViewModel
import com.example.tequilapp.barlist.BarListViewModelFactory
import com.example.tequilapp.database.Bar
import com.example.tequilapp.database.TequilaDatabase
import com.example.tequilapp.databinding.AddBarFragmentBinding
import java.lang.IllegalArgumentException

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddBarFragment : Fragment(), RatingBar.OnRatingBarChangeListener {
    private lateinit var viewModel: AddBarViewModel

    private var _binding: AddBarFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddBarFragmentBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = TequilaDatabase.getInstance(application).tequilaDatabaseDao
        val viewModelFactory = AddBarViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AddBarViewModel::class.java)
        binding.addBarViewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners(view)
    }

    private fun setClickListeners(view: View) {
        binding.apply {
            root.setOnClickListener {
                hideKeyboard(it)
            }
            btnAddBar.setOnClickListener {
                onClickAddBar(view)
            }
        }.ratingBar.onRatingBarChangeListener = this
    }

    private fun onClickAddBar(view: View) {
        try {
            insertBar()
            notifyUser("Bar added")
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        } catch (e: IllegalArgumentException) {
            notifyUser(e.message.toString())
        } finally {
            hideKeyboard(view)
        }
    }

    private fun notifyUser(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun insertBar() {
        var name = binding.txtBarName.text.toString()
        var price = binding.txtTequilaPrice.text.toString()
        var rating = binding.ratingBar.rating.toDouble()
        viewModel.addBar(name, price, rating)
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        view?.let { hideKeyboard(it) }
    }
}