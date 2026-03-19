package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.qualcomm.robotcore.hardware.DigitalChannel
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.stateful

object Intake: Subsystem {
    val intake = MotorEx("intake").brakeMode().reversed()

    val breakBeam: DigitalChannel by lazy { ActiveOpMode.hardwareMap.get(DigitalChannel::class.java, "breakBeam") }
    enum class BreakBeamStates { DETECTED, NOT_DETECTING }
    var state: BreakBeamStates = BreakBeamStates.NOT_DETECTING
    var ballCount = 0
    val lastDetected = false
    val count get() = ballCount

    val auto = instant { intake.power = 1.0 }
    val off = instant { intake.power = 0.0 }
    val reverse = instant { intake.power = -1.0 }
    val manual = instant { intake.power = -Gamepads.gamepad2.rightStickY.get() }

    val reset = instant { ballCount = 0 }
    val overload = stateful({ count > 2 }, wait(0.25).then(reverse, instant { ballCount = 0 })).setInterruptible(false).requires(this)

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