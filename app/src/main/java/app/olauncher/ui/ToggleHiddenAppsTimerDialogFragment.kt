package app.olauncher.ui

import app.olauncher.data.Prefs

class ToggleHiddenAppsTimerDialogFragment : BaseTimerDialogFragment() {
    override fun action() {
        val prefs = Prefs(requireContext())

    }
}