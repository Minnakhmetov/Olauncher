package app.olauncher.ui

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import app.olauncher.R

abstract class BaseTimerDialogFragment : DialogFragment() {
    private var timer: CountDownTimer? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure?")
                .setTitle("Stay focused")
                .setPositiveButton("Yes") { dialog, id ->
                    action()
                }
                .setNegativeButton("No") { dialog, id ->
                    // User cancelled the dialog.
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        dialog.setOnShowListener {
            timer?.cancel()
            val alertDialog = it as AlertDialog
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
            timer = object : CountDownTimer(10000, 1000) {
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

    abstract fun action();
}