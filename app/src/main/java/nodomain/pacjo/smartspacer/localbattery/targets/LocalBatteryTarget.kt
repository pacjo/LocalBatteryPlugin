package nodomain.pacjo.smartspacer.localbattery.targets

import android.content.ComponentName
import android.content.Context.BATTERY_SERVICE
import android.graphics.drawable.Icon
import android.os.BatteryManager
import android.util.Log
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
        val timeToCharge: Long = batteryManager.computeChargeTimeRemaining()        // TODO: convert to human-readable string
        val batteryCurrent: Int = BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE
        // TODO: val batteryVoltage: Int = BatteryManager.????

        var title = "Charging"
        var subtitle = "Full in ${convertTime(timeToCharge)}"
//        if (timeToCharge.toInt() == -1) {
//            Log.i("MainActivity", "Can't get remaining time, skipping (${timeToCharge})")
//            title = "$batteryCurrent mA"
//            subtitle = ""
//        }

        if (timeToCharge.toInt() > -1) {
            Log.i("MainActivity", "Got remaining charge time: ${convertTime(timeToCharge)}")
            return listOf(TargetTemplate.Basic(
                id ="example_$smartspacerId",
                componentName = ComponentName(provideContext(), LocalBatteryTarget::class.java),
                title = Text(title),
                subtitle = Text(subtitle),
                icon = com.kieronquinn.app.smartspacer.sdk.model.uitemplatedata.Icon(
                    Icon.createWithResource(
                        provideContext(),
                        R.drawable.baseline_bolt_24
                    )
                )
            ).create())
        } else {
            Log.i("MainActivity", "Can't get remaining charge time, returning empty target")
            return emptyList()
        }

    }

    override fun getConfig(smartspacerId: String?): Config {
        return Config(
            label = "Local Battery",
            description = "Shows charging information",
            icon = Icon.createWithResource(provideContext(), R.drawable.ic_launcher_foreground),    //TODO: change
            // TODO: refreshPeriodMinutes = // Optional: How often the refresh broadcast for this Target should be called, in minutes (defaults to 0, never)
            // TODO: compatibilityState = // Optional: A CompatibilityState object representing whether this Target is compatible with the device or not (defaults to always compatible)
            // TODO: broadcastProvider = // Optional: The authority of a Broadcast Provider attached to this Target (see Broadcast Providers page)
        )
    }

    override fun onDismiss(smartspacerId: String, targetId: String): Boolean {
        //Handle dismissal of a Target, returning `true` when dismissed, or `false` if not dismissed
        //TODO
        return true
    }

//    override fun onProviderRemoved(smartspacerId: String) {
//        //Handle removal of the Target (optional)
//    }

}