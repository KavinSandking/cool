package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.impl.ServoEx

object Claw: Subsystem {
    val claw = ServoEx("claw")

    val open = instant("open") { set(1) }
    val close = instant("close") { set(0) }

    override fun initialize() {
        close()
    }

    fun set(position: Double){
        claw to(position)
    }

    fun set(position: Int){
        claw to(position.toDouble())
    }
}