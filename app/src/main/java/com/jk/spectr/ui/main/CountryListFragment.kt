package com.jk.spectr.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.appcompat.view.ActionMode
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jk.spectr.MainActivity
import com.jk.spectr.R
import com.jk.spectr.callbacks.FragmentCallBack
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.FragmentCountryListBinding


class CountryListFragment : Fragment(), FragmentCallBack {


    lateinit var binding: FragmentCountryListBinding
    lateinit var mainViewModel: MainViewModel
    var finalData = mutableListOf<Country>()
    private var isSelectionModeActivated: Boolean = false


    private var actionMode: ActionMode? = null
    private val actionModeCallback: ActionMode.Callback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode, menu: Menu?): Boolean {
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.menu_action_mode, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_fav -> {

                    mainViewModel.adapter.getSelectedItems().forEach {
                        it.isFav = true
                    }
                    Toast.makeText(
                        activity, " Selected Country is your favorite now",
                        Toast.LENGTH_SHORT
                    ).show()
                    mode.finish()
                    true
                } R.id.menu_follow -> {

                    mainViewModel.adapter.getSelectedItems().forEach {
                        it.isFollowing = true
                    }
                    Toast.makeText(
                        activity, " You are now following selected Country",
                        Toast.LENGTH_SHORT
                    ).show()
                    mode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            isSelectionModeActivated=false
            resetSelection()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        mainViewModel =
            ViewModelProviders.of(this, MyViewModelFactory(this)).get(MainViewModel::class.java)
        binding.model = mainViewModel
        mainViewModel.liveData.observe(this, changeObserver)
        setHasOptionsMenu(true)


    }


    override fun onPause() {
        super.onPause()
        mainViewModel.liveData.removeObserver(changeObserver)
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

        search(searchView)
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
                setItems(Array(items.size) { itemIndex -> items[itemIndex] }) { _, which ->
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

    override fun onFav() {

    }

    override fun onFollow() {

    }

    override fun onItemClick(view: View, item: Country) {
        if (isSelectionModeActivated) {
            addToSelected(item)
            return

        }
        val data = Bundle()
        val members = item.members
        data.putParcelableArrayList("MEMBERS", members)


        view.findNavController().navigate(R.id.action_mainFragment_to_memberFragment, data)

    }

    private fun addToSelected(item: Country) {
        val index = mainViewModel.adapter.items.indexOf(item)
        if (index >= 0)
            mainViewModel.adapter.items[index].isSelected = true
        mainViewModel.adapter.notifyItemChanged(index, item.isSelected)
    }

    private fun resetSelection() {
        for (i in 0 until mainViewModel.adapter.items.size) {
            val d = mainViewModel.adapter.items[i]
            if (d.isSelected) {
                d.isSelected = false
                mainViewModel.adapter.notifyItemChanged(i, d.isSelected)
            }
        }

    }


    override fun invalidateRequest(isLongPress: Boolean, country: Country) {
        val mainActivity = activity as MainActivity
        actionMode = mainActivity.startSupportActionMode(actionModeCallback)!!
        this.isSelectionModeActivated = isLongPress
        // activity?.invalidateOptionsMenu()
        addToSelected(country)
    }


}
