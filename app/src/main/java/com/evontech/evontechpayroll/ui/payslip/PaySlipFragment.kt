package com.evontech.evontechpayroll.ui.payslip

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evontech.evontechpayroll.R
import com.evontech.evontechpayroll.databinding.FragmentLeaveBinding
import com.evontech.evontechpayroll.databinding.FragmentPaySlipBinding

class PaySlipFragment : Fragment() {
    lateinit var binding: FragmentPaySlipBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var paySlipViewModel = ViewModelProvider(this).get(PaySlipViewModel::class.java)
        binding = FragmentPaySlipBinding.inflate(inflater,container,false)
        val root: View = binding.root

        val textView: TextView = binding.textPayslip
        paySlipViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }


        return root
    }




    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val item = menu.findItem(R.id.action_refresh)
        item.isVisible = false
    }

}