package com.jk.spectr.ui.main

import android.os.Bundle
import android.view.*
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


class CountryListFragment : Fragment() {


    lateinit var binding: FragmentCountryListBinding
    lateinit var mainViewModel: MainViewModel


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
            mainViewModel.adapter.addItems(it)


        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.model = mainViewModel
        mainViewModel.liveData.observe(this, changeObserver)
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

        val search = menu.findItem(R.id.menu_sort)
        val searchView =
            search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        //search(searchView);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_sort) {
            //Todo
        }

        return false
    }
    // private lateinit var viewModel: MainViewModel


}
