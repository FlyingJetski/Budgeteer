package com.flyingjetski.budgeteer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.flyingjetski.budgeteer.R
import com.flyingjetski.budgeteer.models.ExpenseCategory

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

        val titleText = rowView.findViewById(R.id.title) as TextView
        val imageView = rowView.findViewById(R.id.icon) as ImageView
        val subtitleText = rowView.findViewById(R.id.description) as TextView

        titleText.text = dataSource[position].icon
//        imageView.setImageResource(imgid[position])
        subtitleText.text = dataSource[position].label

        return rowView
    }
}