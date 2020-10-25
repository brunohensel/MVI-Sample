package com.example.mvisample.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvisample.R
import com.example.mvisample.model.BlogPost
import com.example.mvisample.presentation.adapter.BlogListAdapter
import com.example.mvisample.presentation.state.MainStateEvent.GetBlogPostsEvent
import com.example.mvisample.presentation.state.MainStateEvent.GetUserEvent
import com.example.mvisample.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(), BlogListAdapter.Interaction {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    lateinit var mainAdapter: BlogListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("invalid activity")

        subscribeObservers()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val itemDecoration = TopSpacingItemDecoration(30)
            addItemDecoration(itemDecoration)
            mainAdapter = BlogListAdapter(this@MainFragment)
            adapter = mainAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->

            println("DEBUG: DataState: $dataState")
            //loading and massages will be handles in the Main Activity
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogs -> viewModel.setBlogListData(blogs) }
                    mainViewState.user?.let { user -> viewModel.setUserData(user) }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let {
                println("DEBUG: Setting blog posts to Recyclerview: $it")
                mainAdapter.submitList(it)
            }

            viewState.user?.let {
                println("DEBUG: Setting user data to Recyclerview: $it")
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> triggerGetUserEvent()
            R.id.action_get_blogs -> triggerGetBlogEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetBlogEvent() {
        viewModel.setStateEvent(GetBlogPostsEvent)
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: Clicked: $position and $item")
    }
}