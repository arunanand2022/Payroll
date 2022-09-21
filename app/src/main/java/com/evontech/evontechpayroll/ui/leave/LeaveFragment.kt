package com.evontech.evontechpayroll.ui.leave

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evontech.evontechpayroll.databinding.FragmentLeaveBinding

class LeaveFragment : Fragment() {

    private var _binding: FragmentLeaveBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val leaveViewModel = ViewModelProvider(this).get(LeaveViewModel::class.java)

        _binding = FragmentLeaveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textLeave
        leaveViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}