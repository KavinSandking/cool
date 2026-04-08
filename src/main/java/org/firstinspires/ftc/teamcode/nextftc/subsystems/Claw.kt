package org.firstinspires.ftc.teamcode.nextftc.subsystems

import dev.nextftc.core.commands.conditionals.IfElseCommand
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.hardware.impl.ServoEx
import org.firstinspires.ftc.teamcode.nextftc.util.functions.decider
import org.firstinspires.ftc.teamcode.nextftc.util.functions.sequential

object Claw: Subsystem {
    val claw = ServoEx("claw")

    var isOpen = false

    val open = instant("open") { claw.position = 1.0 }
    val close = instant("close") { claw.position = 0.0 }

    val toggle = sequential(
        instant { isOpen = !isOpen },
        decider(
            { isOpen }, close, open
        )
    ).requires(this)

    override fun initialize() {
        close()
    }
}