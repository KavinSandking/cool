package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.core.commands.conditionals.IfElseCommand
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.impl.ServoEx

object Claw: Subsystem {
    val claw = ServoEx("claw")

    var isOpen = false

    val open = instant("open") { set(1) }
    val close = instant("close") { set(0) }
    val toggle = instant { isOpen = !isOpen }.then(IfElseCommand({ isOpen }, open, close))

    override fun initialize() {
        close()
    }

    fun set(position: Double){
        claw to position
    }

    fun set(position: Int){
        claw to position.toDouble()
    }
}