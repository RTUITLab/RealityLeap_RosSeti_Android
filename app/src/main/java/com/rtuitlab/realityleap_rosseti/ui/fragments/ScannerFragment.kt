package com.rtuitlab.realityleap_rosseti.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.rtuitlab.realityleap_rosseti.R
import com.rtuitlab.realityleap_rosseti.extensions.shortToast
import com.rtuitlab.realityleap_rosseti.viewmodels.ScannerViewModel
import kotlinx.android.synthetic.main.fragment_scanner.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ScannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

    private val viewModel: ScannerViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_scanner, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCodeScanner()
        setObservers()
    }

    private fun initCodeScanner() {
        codeScanner = CodeScanner(requireActivity(), scannerView).apply {
            decodeCallback = DecodeCallback {
                requireActivity().runOnUiThread {
                    requireActivity().shortToast(it.text).show()
                }
            }
            errorCallback = ErrorCallback {
                requireActivity().runOnUiThread {
                    requireActivity().shortToast("Error: ${it.message}").show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setObservers() {
        viewModel.employeesLiveData.observe(viewLifecycleOwner) {
            viewModel.userHash = viewModel.userHash
        }
        viewModel.authResultLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_scannerFragment_to_inspectionsListFragment)
        }
    }
}