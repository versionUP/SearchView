package com.example.prashanth.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prashanth.R
import com.example.prashanth.models.PorcupineItem
import com.example.prashanth.obtainViewModel
import com.example.prashanth.repository.PorcupineDataRepositoryImpl
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson

const val DEFAULT = "You have no recent searches"

internal class MainFragment : Fragment() {

    private lateinit var textInputLayout : TextInputLayout
    private lateinit var autoCompleteTv: AutoCompleteTextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var porcupineAdapter: PorcupineAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var hintText :TextView




    private val viewModel by lazy {
        obtainViewModel {
            MainFragmentViewModel(
                PorcupineDataRepositoryImpl()
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.page_title)

        view.run {
            progressBar = this.findViewById<ProgressBar>(R.id.loading_indicator)
            hintText = this.findViewById<TextView>(R.id.loader_hintText)
            textInputLayout = this.findViewById(R.id.p_search_bar)
            autoCompleteTv = this.findViewById(R.id.porcupine_text)
        }


        val stringAdapter = ArrayAdapter( view.context,
            android.R.layout.simple_dropdown_item_1line, listOf(recentSearches()))
        autoCompleteTv.setAdapter(stringAdapter)

        autoCompleteTv.doAfterTextChanged {
            viewModel.handleUserInput(it.toString())
        }

        porcupineAdapter = PorcupineAdapter(mutableListOf(),::porcupineSelected)

        recyclerView = view.findViewById<RecyclerView>(R.id.main_rv_id)
        recyclerView.run {
            layoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL, false)
            adapter = porcupineAdapter
        }

        viewModel.apply {
            porcupineData.observe(viewLifecycleOwner, Observer {
                porcupineAdapter.refreshList(it)
            })

            dialogError.observe(viewLifecycleOwner, Observer {
                it?.let {
                    showAlert(it)
                }
            })

            dialogError.observe(viewLifecycleOwner, Observer {
                it?.let {
                    showAlert(it)
                }
            })

            isShowSpinner.observe(viewLifecycleOwner, Observer {
                showOrHideLoading(it)
            })
        }
    }

    private fun recentSearches():List<String> =
        if (viewModel.recentSearches.isEmpty())
            mutableListOf(DEFAULT) else viewModel.recentSearches

    private fun showOrHideLoading(it:Boolean) {
        progressBar.isVisible = it
        hintText.isVisible = it

    }

    private fun porcupineSelected(item: PorcupineItem){
        val bundle = Bundle()
        bundle.putString("PORCUPINE_ITEM",Gson().toJson(item))
        findNavController().navigate(R.id.main_to_detail,bundle)
    }


    private fun showAlert(it: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setTitle(it)
            .setMessage(it)

        dialogBuilder.setPositiveButton(
            "Ok",
            DialogInterface.OnClickListener { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            })

        dialogBuilder.create().also {
            it.setCanceledOnTouchOutside(true)
            it.setCancelable(true)
            it.show()
        }
    }


}
