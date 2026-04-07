package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.controllable.MotorGroup
import dev.nextftc.hardware.impl.MotorEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.repeat

object Catapults: Subsystem {
    val catapults = MotorGroup(
        MotorEx("right").brakeMode(), MotorEx("left").brakeMode().reversed()
    )

    const val WAIT = 0.1
    const val STABILIZE_WAIT = 0.000009
    const val VOLT_WAIT = 0.25

    val up = instant("up") { set(1) }
    val down = instant("down") { set(-1) }
    val stop = instant("stop") { set(0) }
    val slow = instant("slow") { set(0.35) }
    val volt = up.then(wait(VOLT_WAIT), stop).requires(this)
    val stabilize = repeat(slow.then(wait(STABILIZE_WAIT), down, wait(WAIT)), 2).requires(this)

    fun set(power: Int){
        catapults.power = power.toDouble()
    }

    fun set(power: Double){
        catapults.power = power
    }
}