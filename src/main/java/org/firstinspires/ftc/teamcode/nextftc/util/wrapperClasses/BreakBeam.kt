package org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses

import com.qualcomm.robotcore.hardware.DigitalChannel
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.hardware.delegates.LazyHardware

class BreakBeam constructor (sensorFactory: () -> DigitalChannel, name: String?) {
    constructor(name: String): this({ ActiveOpMode.hardwareMap[name] as DigitalChannel }, name)

    private val lazy = LazyHardware(sensorFactory)
    val breakBeam by lazy

    var mode: DigitalChannel.Mode
        get() = breakBeam.mode
        set(value){
            breakBeam.mode = value
        }

    val state: Boolean get() = breakBeam.state
    val name: String by lazy { name ?: breakBeam.deviceName }
    val version: Int get() = breakBeam.version
    val connectionInfo: String get() = breakBeam.connectionInfo

    fun input() = apply {
        lazy.applyAfterInit { mode = DigitalChannel.Mode.INPUT }
    }

    fun output() = apply {
        lazy.applyAfterInit { mode = DigitalChannel.Mode.OUTPUT }
    }
}