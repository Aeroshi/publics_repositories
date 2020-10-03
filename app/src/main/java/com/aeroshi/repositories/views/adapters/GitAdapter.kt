package com.aeroshi.repositories.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aeroshi.repositories.R
import com.aeroshi.repositories.data.entitys.Rep
import com.aeroshi.repositories.viewmodels.MainViewModel
import com.aeroshi.repositories.views.adapters.viewHolders.GitViewHolder


class GitAdapter(
    private val context: Context,
    private val mMainViewModel: MainViewModel
) :
    RecyclerView.Adapter<GitViewHolder>() {


    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    var repositories: ArrayList<Rep> = arrayListOf()


    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitViewHolder =
        GitViewHolder(inflater.inflate(R.layout.layout_list_repositories, parent, false))

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        val repository = repositories[position]

        holder.bind(repository)


        holder.itemView.setOnClickListener {
            mMainViewModel.mSelectedRepository.value = repositories[position]
            Navigation.findNavController(it)
                .navigate(R.id.navigate_home_to_repositoryDetailsFragment)
        }
    }

    fun update(repositories: ArrayList<Rep>) {
        this.repositories.addAll(repositories)
        notifyDataSetChanged()
    }

}

