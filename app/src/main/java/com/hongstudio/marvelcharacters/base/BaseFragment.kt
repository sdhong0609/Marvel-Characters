package com.hongstudio.marvelcharacters.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow

abstract class BaseFragment<VB : ViewBinding>(
    @LayoutRes layoutId: Int,
    private val binder: (View) -> VB
) : Fragment(layoutId) {

    protected var binding: VB? = null

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            binding = binder(view)
        }
        return view
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    protected fun <T> Flow<T>.observe(onChanged: (T) -> Unit) {
        asLiveData().observe(viewLifecycleOwner) {
            onChanged(it)
        }
    }
}
