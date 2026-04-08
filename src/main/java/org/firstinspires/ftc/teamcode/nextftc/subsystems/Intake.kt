package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.bindings.button
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.sequential
import org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses.BreakBeam

object Intake: Subsystem {
    val intake = MotorEx("intake").brakeMode().reversed()
    val breakBeam = BreakBeam("breakBeam").input()

    var lastDetected = false
    var count = 0

    val auto = instant("on-auto") { intake.power = 1.0 }
    val off = instant("off") { intake.power = 0.0 }
    val reverse = instant("reverse") { intake.power = -1.0 }
    val manual = instant("on-tele") {
        intake.power = -Gamepads.gamepad2.rightStickY.get()
    }

    val reset = instant { count = 0 }
    val clamp = sequential(wait(0.25), reverse, reset).requires(this)

    override fun periodic() {
        val detected = breakBeam.state

        if (detected && !lastDetected) count++
        lastDetected = detected
    }
}