package com.aeroshi.repositories.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.aeroshi.repositories.R
import com.aeroshi.repositories.data.entitys.Rep
import com.aeroshi.repositories.views.adapters.viewHolders.GitViewHolder
import java.util.*
import kotlin.collections.ArrayList


class GitAdapter(
    private val context: Context,
    private val mItemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<GitViewHolder>(), Filterable {


    private val inflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    var repositories: ArrayList<Rep> = arrayListOf()
    var baseRepositories: ArrayList<Rep> = arrayListOf()

    interface ItemClickListener {
        fun onItemClick(rep: Rep)
    }


    override fun getItemCount(): Int = repositories.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GitViewHolder =
        GitViewHolder(inflater.inflate(R.layout.layout_list_repositories, parent, false))

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        val repository = repositories[position]

        holder.bind(repository)

        holder.itemView.setOnClickListener {
            mItemClickListener.onItemClick(repositories[position])
        }
    }

    fun update(repositories: ArrayList<Rep>) {
        this.repositories = repositories
        this.baseRepositories = repositories
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        var repositoriesFilterList: ArrayList<Rep>
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                repositoriesFilterList = if (charSearch.isEmpty()) {
                    baseRepositories
                } else {
                    val resultList = ArrayList<Rep>()
                    for (row in repositories) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = repositoriesFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                repositories = results?.values as ArrayList<Rep>
                notifyDataSetChanged()
            }

        }
    }


}

