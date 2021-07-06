package com.flyingjetski.budgeteer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.flyingjetski.budgeteer.models.*

class Adapters {
    class CategoryGridAdapter(
        private val context: Context,
        private val categories: ArrayList<Category>
    ): BaseAdapter() {

        var selectedCategoryId: String? = null
        private val imageViews: ArrayList<ImageView> = ArrayList()
        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.item_icon, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val amountTextView = rowView.findViewById(R.id.categoryLabelTextView) as TextView

            iconImageView.setOnClickListener {
                selectedCategoryId = categories[position].id
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconImageView)

            idTextView.text = categories[position].id
            amountTextView.text = categories[position].label
            iconImageView.setImageResource(categories[position].icon)

            return rowView
        }

    }

    class CategoryIconGridAdapter(
        private val context: Context,
        private val resources: ArrayList<Int>
    ): BaseAdapter() {

        var selectedIconResource: Int = 0
        private val imageViews: ArrayList<ImageView> = ArrayList()
        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return resources.size
        }

        override fun getItem(position: Int): Any {
            return resources[position]
        }

        override fun getItemId(position: Int): Long {
            return resources[position].toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.item_icon, null, false)

            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            iconImageView.setOnClickListener {
                selectedIconResource = resources[position]
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconImageView)

            iconImageView.setImageResource(resources[position])

            return rowView
        }

        fun getPositionOfResource(resource: Int): Int {
            for (position in 0 until resources.size) {
                if (resources[position] == resource) {
                    return position
                }
            }
            return 0
        }

        fun selectIcon(position: Int) {
            imageViews[position+2].setBackgroundColor(Color.GRAY)
        }
    }

    class SourceGridAdapter(
        private val context: Context,
        private val sources: ArrayList<Source>
    ): BaseAdapter() {

        var selectedSourceId: String? = null
        private val imageViews: ArrayList<ImageView> = ArrayList()
        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return sources.size
        }

        override fun getItem(position: Int): Any {
            return sources[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.item_icon, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val amountTextView = rowView.findViewById(R.id.categoryLabelTextView) as TextView

            iconImageView.setOnClickListener {
                selectedSourceId = sources[position].id
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconImageView)

            idTextView.text = sources[position].id
            amountTextView.text = sources[position].label
            iconImageView.setImageResource(sources[position].icon)

            return rowView
        }

    }

    class BudgetListAdapter(
        private val context: Context,
        private val categories: ArrayList<Budget>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_budget, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
            val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
            val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
            val startDateTextView = rowView.findViewById(R.id.startDateTextView) as TextView
            val endDateTextView = rowView.findViewById(R.id.dateTextView) as TextView

            idTextView.text = categories[position].id
            iconImageView.setImageResource(categories[position].icon)
            labelTextView.text = categories[position].label
            amountTextView.text = categories[position].amount.toString()
            currencyTextView.text = categories[position].currency.toString()
            startDateTextView.text = Common.dateToString(categories[position].startDate)
            endDateTextView.text = Common.dateToString(categories[position].endDate)

            return rowView
        }

    }

    class SavingListAdapter(
        private val context: Context,
        private val categories: ArrayList<Saving>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_saving, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
            val targetTextView = rowView.findViewById(R.id.targetTextView) as TextView
            val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
            val deadlineTextView = rowView.findViewById(R.id.deadlineTextView) as TextView

            idTextView.text = categories[position].id
            iconImageView.setImageResource(categories[position].icon)
            labelTextView.text = categories[position].label
            targetTextView.text = categories[position].target.toString()
            currencyTextView.text = categories[position].currency.toString()
            deadlineTextView.text = Common.dateToString(categories[position].deadline)

            return rowView
        }

    }

    class WalletListAdapter(
        private val context: Context,
        private val categories: ArrayList<Wallet>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_wallet, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
            val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView

            idTextView.text = categories[position].id
            iconImageView.setImageResource(categories[position].icon)
            labelTextView.text = categories[position].label
            currencyTextView.text = categories[position].currency.toString()

            return rowView
        }

    }

    class CategoryListAdapter(
        private val context: Context,
        private val categories: ArrayList<Category>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_category, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val iconImageView = rowView.findViewById(R.id.iconImageView) as ImageView
            val amountTextView = rowView.findViewById(R.id.categoryLabelTextView) as TextView

            idTextView.text = categories[position].id
            iconImageView.setImageResource(categories[position].icon)
            amountTextView.text = categories[position].label

            return rowView
        }

    }

    class ExpenseListAdapter(
        private val context: Context,
        private val categories: ArrayList<Expense>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_expense, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
            val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
            val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
            val dateTextView = rowView.findViewById(R.id.dateTextView) as TextView

            idTextView.text = categories[position].id
            labelTextView.text = categories[position].label
            amountTextView.text = categories[position].amount.toString()
            currencyTextView.text = categories[position].currency.toString()
            dateTextView.text = Common.dateToString(categories[position].date)

            return rowView
        }

    }

    class IncomeListAdapter(
        private val context: Context,
        private val categories: ArrayList<Income>
    ): BaseAdapter() {

        private val inflater: LayoutInflater
                = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return categories.size
        }

        override fun getItem(position: Int): Any {
            return categories[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = inflater.inflate(R.layout.list_income, null, false)

            val idTextView = rowView.findViewById(R.id.idTextView) as TextView
            val labelTextView = rowView.findViewById(R.id.labelTextView) as TextView
            val amountTextView = rowView.findViewById(R.id.amountTextView) as TextView
            val currencyTextView = rowView.findViewById(R.id.currencyTextView) as TextView
            val dateTextView = rowView.findViewById(R.id.dateTextView) as TextView

            idTextView.text = categories[position].id
            labelTextView.text = categories[position].label
            amountTextView.text = categories[position].amount.toString()
            currencyTextView.text = categories[position].currency.toString()
            dateTextView.text = Common.dateToString(categories[position].date)

            return rowView
        }

    }
}