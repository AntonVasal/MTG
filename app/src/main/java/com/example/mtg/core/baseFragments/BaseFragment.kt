package com.example.mtg.core.baseFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int
//    protected var dialog: SpotsDialog? = null
//    protected var mainActivity: Activity? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        dialog = SpotsDialog(requireContext(), " ")
//    }

//    fun getMainActivity() = activity as? MainActivity

}