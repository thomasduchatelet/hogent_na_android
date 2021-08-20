package com.example.tequilapp.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tequilapp.databinding.AboutBinding

class AboutFragment : Fragment() {
    private var _binding: AboutBinding? = null
    private lateinit var viewModel: AboutViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AboutBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.error.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.aboutTequilaLabel.text = "connect to internet to learn more about tequila"
            }
        })

        viewModel.response.observe(viewLifecycleOwner, Observer {
            binding.tequilaDescription.text = it
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}