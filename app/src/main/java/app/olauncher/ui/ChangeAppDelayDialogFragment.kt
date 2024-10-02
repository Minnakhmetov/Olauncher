package app.olauncher.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.olauncher.R
import app.olauncher.databinding.ChangeAppDelayDialogBinding
import app.olauncher.helper.ButtonWithTimer


class ChangeAppDelayDialogFragment : DialogFragment() {
    companion object {
        const val RESULT_KEY = "change_app_delay_dialog_result"
        const val RESULT_CANCELED = -1
    }

    private var result = RESULT_CANCELED
    private var buttonWithTimer: ButtonWithTimer? = null
    private lateinit var binding: ChangeAppDelayDialogBinding

    private val args: ChangeAppDelayDialogFragmentArgs by navArgs()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ChangeAppDelayDialogBinding.inflate(layoutInflater)

        binding.secondsPicker.minValue = 0
        binding.secondsPicker.maxValue = 59
        binding.minutesPicker.minValue = 0
        binding.minutesPicker.maxValue = 59

        binding.secondsPicker.value = args.delay % 60
        binding.minutesPicker.value = args.delay / 60

        val dialog = activity?.let {
            // Use the Builder class for convenient dialog construction.
            val builder = AlertDialog.Builder(it)
            builder.setView(binding.changeAppDelayDialog)
            builder.setTitle("Change delay")
            builder
                .setPositiveButton(R.string.apply) { _, _ ->
                    result = binding.minutesPicker.value * 60 + binding.secondsPicker.value
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    result = RESULT_CANCELED
                }
            // Create the AlertDialog object and return it.
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

        dialog.setOnShowListener {
            val dialog = it as AlertDialog
            buttonWithTimer = ButtonWithTimer(
                dialog.getButton(AlertDialog.BUTTON_POSITIVE),
                getString(R.string.apply),
                args.delay
            )
            buttonWithTimer?.startTimer()
        }

        return dialog
    }

    override fun onPause() {
        super.onPause()
        buttonWithTimer?.cancelTimer()
    }

    override fun onResume() {
        super.onResume()
        buttonWithTimer?.startTimer()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(RESULT_KEY, result)
    }
}