package nodomain.pacjo.smartspacer.localbattery.targets

import android.content.ComponentName
import android.content.Context.BATTERY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.BatteryManager
import com.kieronquinn.app.smartspacer.sdk.model.SmartspaceTarget
import com.kieronquinn.app.smartspacer.sdk.model.uitemplatedata.Text
import com.kieronquinn.app.smartspacer.sdk.provider.SmartspacerTargetProvider
import com.kieronquinn.app.smartspacer.sdk.utils.TargetTemplate
import nodomain.pacjo.smartspacer.localbattery.R

fun convertTime(timeInMilliseconds: Long): String {
    val hours = timeInMilliseconds / 3600000
    val minutes = timeInMilliseconds % 3600 / 60
    return if (hours > 0 && minutes > 0) {
        String.format("%d hours and %d minutes", hours, minutes)
    } else if (hours > 0) {
        String.format("%d hrs", hours)
    } else if (minutes > 0) {
        String.format("%d minutes", minutes)
    } else {
        "less than a minute"
    }
}

class LocalBatteryTarget: SmartspacerTargetProvider() {

    override fun getSmartspaceTargets(smartspacerId: String): List<SmartspaceTarget> {
        val batteryManager = context?.getSystemService(BATTERY_SERVICE) as BatteryManager
        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { ifilter ->
            context!!.registerReceiver(null, ifilter)
        }

        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val charging = status == BatteryManager.BATTERY_STATUS_CHARGING
        val chargingTimeRemaining = batteryManager.computeChargeTimeRemaining()
        val level = (
                (
                        (100f *
                                batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)!!) /
                                batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
                        )
                ).toInt()

        val title = "Charging"
        val subtitle = if (charging && chargingTimeRemaining > -1) {
            "${level}% — full in ${convertTime(chargingTimeRemaining)}"
        } else {
           "${level}%"
        }

        if (charging) {
            return listOf(TargetTemplate.Basic(
                id = "example_$smartspacerId",
                componentName = ComponentName(provideContext(), LocalBatteryTarget::class.java),
                title = Text(title),
                subtitle = Text(subtitle),
                icon = com.kieronquinn.app.smartspacer.sdk.model.uitemplatedata.Icon(
                    Icon.createWithResource(
                        provideContext(),
                        R.drawable.baseline_bolt
                    )
                )
            ).create().apply {
                canBeDismissed = false
            })
        } else {
            return emptyList()
        }
    }

    override fun getConfig(smartspacerId: String?): Config {
        return Config(
            label = "Local Battery",
            description = "Shows charging information",
            icon = Icon.createWithResource(provideContext(), R.drawable.ic_launcher_foreground),    //TODO: change
            broadcastProvider = "nodomain.pacjo.smartspacer.localbattery.broadcast.example"
        )
    }

    override fun onDismiss(smartspacerId: String, targetId: String): Boolean {
        return false
    }

}