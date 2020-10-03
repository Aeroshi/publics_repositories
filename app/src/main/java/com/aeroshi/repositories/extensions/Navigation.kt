package com.aeroshi.repositories.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController


fun Fragment.navigate(id: Int) {
    findNavController(requireView()).navigate(id)
}
