package com.igzafer.visualizingsortingalgorithms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igzafer.visualizingsortingalgorithms.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //lets goooooo
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}