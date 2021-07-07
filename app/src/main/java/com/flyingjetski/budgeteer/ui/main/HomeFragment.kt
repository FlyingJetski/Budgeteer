package com.flyingjetski.budgeteer.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.databinding.FragmentStatisticsBinding
import org.eazegraph.lib.models.PieModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val a = PieModel("Food", 15.0f, Color.parseColor("#36c9b8"))
        val b = PieModel("Electricity", 7.3f, Color.parseColor("#7543ba"))
        val c = PieModel("Water", 3.9f, Color.parseColor("#69c74a"))
        binding.pieChart.addPieSlice(a)
        binding.pieChart.addPieSlice(b)
        binding.pieChart.addPieSlice(c)

        return binding.root
    }

}