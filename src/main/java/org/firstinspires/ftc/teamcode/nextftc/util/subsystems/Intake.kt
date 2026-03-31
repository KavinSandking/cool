package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.qualcomm.robotcore.hardware.DigitalChannel
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.ActiveOpMode
import dev.nextftc.ftc.GamepadEx
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
    const val WAIT = 0.25

    val auto = instant("on-auto") { set(1) }
    val off = instant("off") { set(0) }
    val reverse = instant("reverse") { set(-1) }
    val manual = instant("on-tele") { set(-Gamepads.gamepad2.rightStickY.get()) }

    val reset = instant { ballCount = 0 }
    val overload = stateful({ count > 2 }, wait(WAIT).then(reverse, reset)).setInterruptible(false).requires(this)

    override fun periodic() {
        val detected = breakBeam.state

        state = if (detected && !lastDetected) BreakBeamStates.DETECTED
        else BreakBeamStates.NOT_DETECTING

        when(state){
            BreakBeamStates.DETECTED -> ballCount ++
            BreakBeamStates.NOT_DETECTING -> ballCount
        }
    }

    fun set(power: Int){
        intake.power = power.toDouble()
    }

    fun set(power: Double){
        intake.power = power
    }
}