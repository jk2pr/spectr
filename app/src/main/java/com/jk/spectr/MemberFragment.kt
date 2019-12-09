package com.jk.spectr


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jk.spectr.data.Country
import com.jk.spectr.databinding.FragmentMemberListBinding
import com.jk.spectr.ui.adapter.MemberAdapter
import java.util.*
import kotlin.collections.ArrayList


class MemberFragment : Fragment() {


    var finalData = mutableListOf<Country.Members>()

    lateinit var binding: FragmentMemberListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_member_list, container, false
        )
        return binding.root
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


        val members = arguments!!.getParcelableArrayList<Country.Members>("MEMBERS")
        finalData.clear()
        members?.forEach {
            finalData.add(it)
        }

        val adapter = MemberAdapter(members!!)
        binding.adapter = adapter
        setHasOptionsMenu(true)


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
                newText = newText.toLowerCase(Locale.getDefault())
                val newList: ArrayList<Country.Members> = ArrayList()
                for (member in finalData
                ) {
                    val company: String = member.name.toString().toLowerCase(Locale.getDefault())
                    if (company.contains(newText)) {
                        newList.add(member)
                    }
                }
                binding.adapter!!.setFilter(newList)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_sort -> {

                //1. Creating the AlertDialog
                AlertDialog.Builder(context!!).apply {


                    //2. Setting the title
                    setTitle("Sort By")

                    //3. Setting click handlers for each item of the list
                    val items = mutableListOf<String>()
                    items.add("Ascending")
                    items.add("Descending")
                    setItems(Array(items.size) { itemIndex -> items[itemIndex] }) { dialog, which ->
                        if (which == 0)
                            binding.adapter!!.items.sortBy {
                                it.name.toString()
                            } else
                            binding.adapter!!.items.sortByDescending {
                                it.name.toString()
                            }
                        binding.adapter!!.notifyDataSetChanged()
                    }
                    show()
                }

            }
           android. R.id.home ->{
            //    NavController(view!!.context).popBackStack(R.id.mainFragment, false);
              // view?.findNavController()?.navigate(R.id.action_mainFragment_to_memberFragment)
               activity?.onBackPressed()
           }
        }

        return false
    }


}
