package com.example.spaceapp.ui.main.TODOActivity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.spaceapp.MainActivity
import com.example.spaceapp.R


class DialogEditerFragment(
    val flag: Int,
    val position : Int = 0
    ) : DialogFragment() {

    lateinit var editText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView: View =
            requireActivity().layoutInflater.inflate(R.layout.dialogue_editer, null)
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            .setTitle("Введите задачу")
            .setView(contentView)
            .setPositiveButton(
                "Добавить",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    editText = contentView.findViewById(R.id.input_task)
                    val answer: String = editText.text.toString()
                    (requireActivity() as TODOActivity).onDialogResult(answer, flag,position)

                }
            )
        return builder.create()
    }
}