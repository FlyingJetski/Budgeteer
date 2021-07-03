package com.flyingjetski.budgeteer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.flyingjetski.budgeteer.models.Category
import com.flyingjetski.budgeteer.models.Source

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

            val idText = rowView.findViewById(R.id.item_id) as TextView
            val labelText = rowView.findViewById(R.id.item_label) as TextView
            val iconItem = rowView.findViewById(R.id.item_icon) as ImageView
            iconItem.setOnClickListener {
                selectedCategoryId = categories[position].id
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconItem)

            idText.text = categories[position].id
            labelText.text = categories[position].label
            iconItem.setImageResource(categories[position].icon)

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

            val iconItem = rowView.findViewById(R.id.item_icon) as ImageView
            iconItem.setOnClickListener {
                selectedIconResource = resources[position]
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconItem)

            iconItem.setImageResource(resources[position])

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

            val idText = rowView.findViewById(R.id.item_id) as TextView
            val titleText = rowView.findViewById(R.id.item_label) as TextView
            val imageView = rowView.findViewById(R.id.item_icon) as ImageView

            idText.text = categories[position].id
            titleText.text = categories[position].label
            imageView.setImageResource(categories[position].icon)

            return rowView
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

            val idText = rowView.findViewById(R.id.item_id) as TextView
            val labelText = rowView.findViewById(R.id.item_label) as TextView
            val iconItem = rowView.findViewById(R.id.item_icon) as ImageView
            iconItem.setOnClickListener {
                selectedSourceId = sources[position].id
                imageViews.forEach{
                    it.setBackgroundColor(Color.WHITE)
                }
                it.setBackgroundColor(Color.GRAY)
            }
            imageViews.add(iconItem)

            idText.text = sources[position].id
            labelText.text = sources[position].label
            iconItem.setImageResource(sources[position].icon)

            return rowView
        }

    }
}