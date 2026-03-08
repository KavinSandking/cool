package org.firstinspires.ftc.teamcode.util.subsystems

import com.qualcomm.robotcore.hardware.DigitalChannel
import dev.nextftc.core.commands.Command
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.ActiveOpMode

object BreakBeam: Subsystem {
    val breakBeam: DigitalChannel by lazy { ActiveOpMode.hardwareMap.get(DigitalChannel::class.java, "breakBeam") }

    enum class BreakBeamStates { DETECTED, NOT_DETECTING }

    var state: BreakBeamStates = BreakBeamStates.NOT_DETECTING
    var ballCount = 0
    val lastDetected = false

    val count get() = ballCount
    fun setCount(c: Int): Command = instant { ballCount = c }
    val overload = count >= 3

    override fun periodic() {
        val detected = breakBeam.state

        if (detected && !lastDetected) state = BreakBeamStates.DETECTED
        else state = BreakBeamStates.NOT_DETECTING

        when(state){
            BreakBeamStates.DETECTED -> ballCount ++
            BreakBeamStates.NOT_DETECTING -> ballCount
        }
    }


}