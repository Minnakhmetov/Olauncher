package app.olauncher.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import app.olauncher.R
import app.olauncher.helper.ButtonWithTimer


class OpenAppWithDelayDialogFragment : DialogFragment() {
    companion object {
        const val RESULT_KEY = "open_app_with_delay_dialog_result"
    }

    private var result: Boolean = false
    private lateinit var buttonWithTimer: ButtonWithTimer

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.app_delay_message))
                .setPositiveButton(R.string.open) { _, _ -> result = true }
                .setNegativeButton(R.string.close) { _, _ -> result = false }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        buttonWithTimer = ButtonWithTimer(dialog.getButton(AlertDialog.BUTTON_POSITIVE), getString(R.string.open), 3)
        buttonWithTimer.startTimer()

        return dialog
    }

    override fun onPause() {
        super.onPause()
        buttonWithTimer.startTimer()
    }

    override fun onResume() {
        super.onResume()
        buttonWithTimer.cancelTimer()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT_KEY, result)
    }
}