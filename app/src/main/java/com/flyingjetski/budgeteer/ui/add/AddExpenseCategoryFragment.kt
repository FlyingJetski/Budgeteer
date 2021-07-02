package com.flyingjetski.budgeteer.ui.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.AuthActivity
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.ExpenseCategory
import java.lang.reflect.Field


class AddExpenseCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_expense_category, container, false)

        val drawablesFields: Array<Field> = R.mipmap::class.java.fields
        val icons: ArrayList<Int> = ArrayList()
        var selectedIconResource: Int = 0

        for (field in drawablesFields) {
            try {
                icons.add(field.getInt(null))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.gridView.adapter = GridViewAdapter(this.requireContext(), icons)
        binding.gridView.setOnItemClickListener { parent, view, position, id ->
            selectedIconResource = icons[position]
            binding.selectedIcon.setImageResource(selectedIconResource)
        }

        binding.addButton.setOnClickListener{
            ExpenseCategory.insertExpenseCategory(
                ExpenseCategory(
                    AuthActivity().auth.uid.toString(),
                    selectedIconResource,
                    binding.label.text.toString()
                )
            )
            Navigation.findNavController(it).navigateUp()
        }

        return binding.root
    }



    class GridViewAdapter(
        private val context: Context,
        private val icons: ArrayList<Int>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return icons.size
        }

        override fun getItem(position: Int): Any {
            return icons[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.item_icon, null, false)

            val iconItem = rowView.findViewById(R.id.icon_item) as ImageView

            iconItem.setImageResource(icons[position])

            return rowView
        }
    }

}