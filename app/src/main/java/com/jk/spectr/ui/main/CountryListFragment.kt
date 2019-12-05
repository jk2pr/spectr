package com.jk.spectr.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jk.spectr.R
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.FragmentCountryListBinding
import kotlin.collections.ArrayList


class CountryListFragment : Fragment() {


    lateinit var binding: FragmentCountryListBinding
    lateinit var mainViewModel: MainViewModel
    var finalData = mutableListOf<Country>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_country_list, container, false
        )
        return binding.root
    }


    private val changeObserver = Observer<List<Country>> { value ->
        value?.let {
            binding.progressBar.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            finalData.clear()
            finalData.addAll(it)
            mainViewModel.adapter.addItems(finalData)


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.model = mainViewModel
        mainViewModel.liveData.observe(this, changeObserver)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        val b = DividerItemDecoration(
            view.context,
            DividerItemDecoration.VERTICAL
        )
        //  b.setDrawable(getDrawable(view.context, R.drawable.item_seprator)!!)
        binding.recyclerView.addItemDecoration(b)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.menu_search)
        val searchView =
            search.actionView as SearchView

        search(searchView);
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newT: String): Boolean {
                var newText = newT.trim()
                newText = newText.toLowerCase()
                val newList: ArrayList<Country> = ArrayList()
                for (countries in finalData) {
                    val company: String = countries.company.toLowerCase()
                    if (company.contains(newText)) {
                        newList.add(countries)
                    }
                }
                mainViewModel.adapter.setFilter(newList)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sort) {
            //Todo

            //1. Creating the AlertDialog
            AlertDialog.Builder(context!!).apply {


                //2. Setting the title
                setTitle("Sort By")

                //3. Setting click handlers for each item of the list
                val items = mutableListOf<String>()
                items.add("Ascending")
                items.add("Descending")
                setItems(Array(items.size) { itemIndex -> items[itemIndex].toString() }) { dialog, which ->
                    if (which == 0)
                        mainViewModel.adapter.items.sortBy {
                            it.company
                        } else
                        mainViewModel.adapter.items.sortByDescending {
                            it.company
                        }
                    mainViewModel.adapter.notifyDataSetChanged()
                }
                show()
            }

        }

        return false
    }
    // private lateinit var viewModel: MainViewModel


}
