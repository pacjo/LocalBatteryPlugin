package nodomain.pacjo.smartspacer.localbattery.providers

import android.content.Intent
import android.content.IntentFilter
import com.kieronquinn.app.smartspacer.sdk.provider.SmartspacerBroadcastProvider
import com.kieronquinn.app.smartspacer.sdk.provider.SmartspacerTargetProvider
import nodomain.pacjo.smartspacer.localbattery.targets.LocalBatteryTarget

class BatteryBroadcastProvider: SmartspacerBroadcastProvider() {

    override fun onReceive(intent: Intent) {
        SmartspacerTargetProvider.notifyChange(requireContext(), LocalBatteryTarget::class.java)        // TODO: fix, could result in errors on older android versions
    }

    override fun getConfig(smartspacerId: String): Config {
        return Config(
            intentFilters = listOf(IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        )
    }

}