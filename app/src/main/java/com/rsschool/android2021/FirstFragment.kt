package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        // TODO: val min = ...
        // TODO: val max = ...
        val minFieldText: TextView? = view.findViewById(R.id.min_value)
        var min: Int
        val maxFieldText: TextView? = view.findViewById(R.id.max_value)
        var max: Int




        generateButton?.setOnClickListener {
            // TODO: send min and max to the SecondFragment
            val tempMinString = minFieldText?.text.toString()
            val tempMaxString = maxFieldText?.text.toString()
            var errorMessage = ""
            when {
                tempMinString.isEmpty() -> errorMessage = getString(R.string.error_empty_value_for_min_field)
                tempMaxString.isEmpty() -> errorMessage = getString(R.string.error_empty_value_for_max_field)
                Int.MAX_VALUE < tempMinString.toLong() -> errorMessage = getString(R.string.error_too_much_bigger_number_for_min_number)
                tempMinString[0] == '-' -> errorMessage = getString(R.string.error_negative_number_for_min_number)
                Int.MAX_VALUE < tempMaxString.toLong() -> errorMessage = getString(R.string.error_too_much_bigger_number_for_max_number)
                tempMaxString[0] == '-' -> errorMessage = getString(R.string.error_negative_number_for_max_number)
                tempMaxString.toInt() < tempMinString.toInt() -> errorMessage = getString(R.string.error_min_number_more_then_max_number)
                else -> {
                    min = tempMinString.toInt()
                    max = tempMaxString.toInt()
                    activity
                        ?.supportFragmentManager
                        ?.beginTransaction()
                        ?.replace(R.id.container, SecondFragment.newInstance(min, max))
                        ?.commit()
                }
            }
            if (errorMessage.isNotEmpty()) Toast.makeText(context, errorMessage, errorMessage.length).show()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}