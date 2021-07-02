package com.flyingjetski.budgeteer.ui.add

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseBinding
import com.flyingjetski.budgeteer.databinding.FragmentAddExpenseCategoryBinding
import com.flyingjetski.budgeteer.databinding.FragmentHomeBinding
import com.flyingjetski.budgeteer.databinding.FragmentViewExpenseCategoryBinding
import com.flyingjetski.budgeteer.models.Expense
import com.flyingjetski.budgeteer.models.ExpenseCategory
import org.w3c.dom.Text

class ViewExpenseCategoryFragment : Fragment() {

    lateinit var binding: FragmentViewExpenseCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_expense_category, container, false)

        ExpenseCategory.getExpenseCategory()
            .addSnapshotListener{
                    snapshot, _ ->
                run {
                    if (snapshot != null) {
                        val categories = ArrayList<ExpenseCategory>()
                        val documents = snapshot.documents
                        documents.forEach {
                            val category = it.toObject(ExpenseCategory::class.java)
                            if (category != null) {
                                category.id = it.id
                                categories.add(category)
                            }
                        }

                        binding.listView.adapter = ExpenseCategoryAdapter(
                            this.requireContext(),
                            categories,
                        )
                    }
                }
            }
//            .addOnCompleteListener{
//                if (it.isSuccessful) {
//                    val documentSnapshot = it.result
//                    if (documentSnapshot?.isEmpty == false) {
//                        val categories = documentSnapshot.toObjects(ExpenseCategory::class.java)
//                        binding.listView.adapter = ExpenseCategoryAdapter(this.requireContext(),
//                            categories as ArrayList<ExpenseCategory>
//                        )
//                    }
//                } else {
//                    Toast.makeText(this.context, "Failed retrieving data.",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }


        binding.listView.setOnItemClickListener{adapterView, view, position, id ->
            Log.d("NAV",
                (binding.listView.adapter.getItem(position) as ExpenseCategory).id.toString()
            )

            Navigation.findNavController(view).navigate(
                ViewExpenseCategoryFragmentDirections
                    .actionViewExpenseCategoryFragmentToEditExpenseCategoryFragment(
                        (binding.listView.adapter.getItem(position) as ExpenseCategory).id.toString()
                    )
            )
        }

        return binding.root
    }

    class ExpenseCategoryAdapter(
        private val context: Context,
        private val dataSource: ArrayList<ExpenseCategory>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return dataSource.size
        }

        override fun getItem(position: Int): Any {
            return dataSource[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_category, null, false)

            val idText = rowView.findViewById(R.id.id) as TextView
            val titleText = rowView.findViewById(R.id.title) as TextView
            val imageView = rowView.findViewById(R.id.icon) as ImageView

            idText.text = dataSource[position].id
            titleText.text = dataSource[position].label
            imageView.setImageResource(dataSource[position].icon)

            return rowView
        }
    }

}