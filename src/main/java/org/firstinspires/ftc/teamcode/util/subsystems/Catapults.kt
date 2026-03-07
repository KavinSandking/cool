package org.firstinspires.ftc.teamcode.util.subsystems

import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import dev.nextftc.hardware.impl.VoltageCompensatingMotor

object Catapults: Subsystem {
    val catapults = VoltageCompensatingMotor(
        MotorGroup(
            MotorEx("right").brakeMode(),
            MotorEx("left").reversed().brakeMode()
        )
    )

    enum class CatapultStates { UP, SLOW, DOWN, OFF }
    var state: CatapultStates = CatapultStates.OFF
    const val WAIT_TIME = 0.2
    const val STABILIZE_TIME = 0.000005

    val up = instant { state = CatapultStates.UP }
    val slow = instant { state = CatapultStates.SLOW }
    val down = instant { state = CatapultStates.DOWN }
    val off = instant { state = CatapultStates.OFF }

    val upTeleop = SequentialGroup(up, wait(WAIT_TIME), off).requires(this)
    val shoot = SequentialGroup(up, wait(WAIT_TIME), down).requires(this)
    val shootSlow = SequentialGroup(up, wait(WAIT_TIME), down).requires(this)
    val stabilize = SequentialGroup(slow, wait(STABILIZE_TIME), down).requires(this)


    override fun periodic() {
        when(state){
            CatapultStates.UP -> catapults.power = 1.0
            CatapultStates.SLOW -> catapults.power = 0.35
            CatapultStates.DOWN -> catapults.power = -1.0
            CatapultStates.OFF -> catapults.power = 0.0
        }
    }
}