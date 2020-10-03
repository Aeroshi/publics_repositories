package com.aeroshi.repositories.views.adapters.viewHolders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aeroshi.repositories.R
import com.aeroshi.repositories.data.entitys.Rep

class GitViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    private val textViewRepositoryName: TextView = view.findViewById(R.id.textView_repository_name)
    private val textViewUsername: TextView = view.findViewById(R.id.textView_username)

    fun bind(repository: Rep) {
        textViewRepositoryName.text = repository.name
        textViewUsername.text = repository.owner.login
    }

}