package com.example.workshopapp.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workshopapp.MainActivity
import com.example.workshopapp.MainViewModel
import com.example.workshopapp.R
import com.example.workshopapp.adapter.WorkShopAdapter
import com.example.workshopapp.model.WorkShopModel
import com.example.workshopapp.utils.Resource


class WorkShopList : Fragment(), WorkShopAdapter.EventListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var workshopAdapter: WorkShopAdapter

    private lateinit var rvWorkshop: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var mContext: Context
    private lateinit var listener: EventListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        listener = context as EventListener

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_shop_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).getWorkshopViewModel()
        viewModel.setAppTitle(SCREEN_TITLE)
        initUI(view)

        initWorkShopRecyclerView()

        viewModel.getWorkShopData()
        viewModel.getWorkShopListObserver().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()

                    response.data?.let { workshop ->
                        workshopAdapter.setData(workshop)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                                mContext.applicationContext,
                                "Something went wrong",
                                Toast.LENGTH_SHORT
                        ).show()
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Update -> {
                    response.data?.let { workshop ->
                        workshopAdapter.setData(workshop)
                    }
                }
            }
        })
    }

    private fun hideProgressBar() {
        pbLoading.visibility = View.GONE
    }

    private fun showProgressBar() {
        pbLoading.visibility = View.VISIBLE
    }

    private fun initUI(view: View) {
        rvWorkshop = view.findViewById(R.id.rv_workshop)
        pbLoading = view.findViewById(R.id.progress_bar)
    }

    private fun initWorkShopRecyclerView() {
        workshopAdapter = WorkShopAdapter(mContext, this)
        rvWorkshop.apply {
            adapter = workshopAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        private const val TAG = "WorkshopTAG"
        private const val SCREEN_TITLE = "Workshop List"
    }

    override fun onWorkshopClicked(workShopModel: WorkShopModel) {
        listener.onWorkshopClicked(workShopModel)
    }

    interface EventListener {
        fun onWorkshopClicked(workShopModel: WorkShopModel)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search_option_menu, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView = SearchView(
                (mContext as MainActivity).supportActionBar!!.themedContext
        )

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)
        item.setActionView(searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                Handler(Looper.getMainLooper()).postDelayed({ viewModel.displayCurrentList(query) }, 300)
                return true
            }
        })
        searchView.setOnClickListener { }
    }
}