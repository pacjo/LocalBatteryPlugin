package nodomain.pacjo.smartspacer.localbattery.targets

import android.content.ComponentName
import android.graphics.drawable.Icon
import com.kieronquinn.app.smartspacer.sdk.model.SmartspaceTarget
import com.kieronquinn.app.smartspacer.sdk.model.uitemplatedata.Text
import com.kieronquinn.app.smartspacer.sdk.provider.SmartspacerTargetProvider
import com.kieronquinn.app.smartspacer.sdk.utils.TargetTemplate
import nodomain.pacjo.smartspacer.localbattery.R

class LocalBatteryTarget: SmartspacerTargetProvider() {

    override fun getSmartspaceTargets(smartspacerId: String): List<SmartspaceTarget> {
        return listOf(TargetTemplate.Basic(
            id ="example_$smartspacerId",
            componentName = ComponentName(provideContext(), LocalBatteryTarget::class.java),
            title = Text("Hello World!"),
            subtitle = Text("Example"),
            icon = com.kieronquinn.app.smartspacer.sdk.model.uitemplatedata.Icon(
                Icon.createWithResource(
                    provideContext(),
                    R.drawable.ic_launcher_foreground
                )
            )
        ).create())
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