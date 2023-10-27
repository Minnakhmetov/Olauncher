package app.olauncher.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import app.olauncher.R


class TimerDialogFragment : DialogFragment() {
    companion object {
        const val RESULT_KEY = "timer_dialog_result_key"
        private const val TIMER_IN_SECONDS = 10
    }

    private var result: Boolean = false
    private var timer: CountDownTimer? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.you_have_hidden_these_for_a_reason))
                .setPositiveButton(getString(R.string.yes)) { dialog, id ->
                    result = true
                }
                .setNegativeButton(R.string.no) { dialog, id ->
                    result = false
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        dialog.setOnShowListener {
            timer?.cancel()
            val alertDialog = it as AlertDialog
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            timer = object : CountDownTimer(TIMER_IN_SECONDS * 1000L, 1000) {
                override fun onTick(p0: Long) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).text = getString(R.string.yes_with_timer).format(p0 / 1000)
                }
                override fun onFinish() {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).text = getString(R.string.yes)
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                }
            }
            timer?.start()
        }

        return dialog
    }

    override fun onPause() {
        super.onPause()
        timer?.cancel()
    }

    override fun onResume() {
        super.onResume()
        timer?.start()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT_KEY, result)
    }
}