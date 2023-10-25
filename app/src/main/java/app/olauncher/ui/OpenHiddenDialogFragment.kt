package app.olauncher.ui

import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import app.olauncher.R
import app.olauncher.data.Constants

class OpenHiddenDialogFragment : BaseTimerDialogFragment() {
    override fun action() {
        findNavController().navigate(
            R.id.action_stayFocusedDialogFragment_to_appListFragment,
            bundleOf(Constants.Key.FLAG to Constants.FLAG_HIDDEN_APPS)
        )
    }
}