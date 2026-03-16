package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.Gamepads
import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.hardware.impl.VoltageCompensatingMotor

object Intake: Subsystem {
    val intake = VoltageCompensatingMotor(MotorEx("intake").reversed())

    enum class IntakeStates { CUSTOM, ON, OFF, REVERSE }
    var state: IntakeStates = IntakeStates.OFF

    val custom = instant { state = IntakeStates.CUSTOM }
    val on = instant { state = IntakeStates.ON }
    val off = instant { state = IntakeStates.OFF }
    val reverse = instant { state = IntakeStates.REVERSE }

    override fun periodic() {
        when(state){
            IntakeStates.CUSTOM -> intake.power = -Gamepads.gamepad1.rightStickY.get()
            IntakeStates.ON -> intake.power = 1.0
            IntakeStates.OFF -> intake.power = 0.0
            IntakeStates.REVERSE -> intake.power = -1.0
        }
    }
}