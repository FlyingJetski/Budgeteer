package com.flyingjetski.budgeteer.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.Adapters
import com.flyingjetski.budgeteer.Callback
import com.flyingjetski.budgeteer.databinding.FragmentViewSavingBinding
import com.flyingjetski.budgeteer.models.Budget
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Saving

class ViewSavingFragment : Fragment() {

    lateinit var binding: FragmentViewSavingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_saving, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        // Populate View
        Saving.getSaving(object: Callback {
            override fun onCallback(value: Any) {
                binding.listView.adapter = Adapters.SavingListAdapter(
                    requireContext(),
                    value as ArrayList<Saving>,
                )
            }
        })

        // Set Listeners
//        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
//            Navigation.findNavController(view).navigate(
//                ViewSavingFragmentDirections
//                    .actionViewSavingFragmentToEditSavingFragment(
//                        (binding.listView.adapter.getItem(position) as Saving).id.toString()
//                    )
//            )
//        }
    }

}