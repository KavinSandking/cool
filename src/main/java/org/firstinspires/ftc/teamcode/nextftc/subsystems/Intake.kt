package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.bindings.button
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses.BreakBeam
import org.firstinspires.ftc.teamcode.nextftc.util.functions.stateful

object Intake: Subsystem {
    val intake = MotorEx("intake").brakeMode().reversed()
    val breakBeam = BreakBeam("breakBeam").input()
    val conditional = button { count > 2 }

    enum class BreakBeamStates { DETECTED, NOT_DETECTING }
    enum class IntakeActions { ON, OFF, REVERSE, MANUAL }
    var breakBeamState: BreakBeamStates = BreakBeamStates.NOT_DETECTING
    var current = IntakeActions.OFF

    var ballCount = 0
    var lastDetected = false
    val count get() = ballCount
    const val WAIT = 0.25

    val auto = instant("on-auto") { current = IntakeActions.ON }
    val off = instant("off") { current = IntakeActions.OFF }
    val reverse = instant("reverse") { current = IntakeActions.REVERSE }
    val manual = instant("on-tele") { current = IntakeActions.MANUAL }

    val reset = instant { ballCount = 0 }

    override fun periodic() {
        val detected = breakBeam.state

        breakBeamState = if (detected && !lastDetected) BreakBeamStates.DETECTED
        else BreakBeamStates.NOT_DETECTING

        when(breakBeamState){
            BreakBeamStates.DETECTED -> ballCount ++
            BreakBeamStates.NOT_DETECTING -> ballCount
        }

        when(current){
            IntakeActions.ON -> set(1.0)
            IntakeActions.OFF -> set(0.0)
            IntakeActions.REVERSE -> set(-1.0)
            IntakeActions.MANUAL -> set(-Gamepads.gamepad2.rightStickY.get())
        }

        conditional whenBecomesTrue {
            SequentialGroup(
                wait(WAIT),
                reverse,
                reset
            )
        }
    }

    fun set(power: Double){
        intake.power = power
    }
}