package com.yarud.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener{

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val demo: DemoDetails = parent?.adapter?.getItem(position) as DemoDetails
        startActivity(Intent(this, demo.activityClass))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val listAdapter: ListAdapter = CustomArrayAdapter(this, DemoDetailsList.DEMOS)

        // Find the view that will show empty message if there is no demo in DemoDetailsList.DEMOS
        val emptyMessage = findViewById<View>(R.id.empty)
        with(findViewById<ListView>(R.id.list)) {
            adapter = listAdapter
            onItemClickListener = this@MainActivity
            emptyView = emptyMessage
        }
    }

    /**
     * A custom array adapter that shows a {@link FeatureView} containing details about the demo.
     *
     * @property context current activity
     * @property demos An array containing the details of the demos to be displayed.
     */
    @SuppressLint("ResourceType")
    class CustomArrayAdapter(context: Context, demos: List<DemoDetails>) :
        ArrayAdapter<DemoDetails>(context, R.layout.feature, demos) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val demo: DemoDetails? = getItem(position)
            return (convertView as? FeatureView ?: FeatureView(context)).apply {
                if (demo != null) {
                    setTitleId(demo.titleId)
                    setDescriptionId(demo.descriptionId)
                    contentDescription = resources.getString(demo.titleId)
                }
            }
        }
    }
}
