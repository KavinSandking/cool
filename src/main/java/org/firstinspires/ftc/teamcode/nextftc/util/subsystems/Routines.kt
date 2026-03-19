package org.firstinspires.ftc.teamcode.nextftc.util.subsystems

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.wait
import dev.nextftc.extensions.pedro.FollowPath
import org.firstinspires.ftc.teamcode.nextftc.util.functions.follow

object Routines {
    val catapults = Catapults
    val intake = Intake

    fun first(pathChain: PathChain) = SequentialGroup(
        follow(pathChain).and(catapults.down),
        catapults.up
    )

    fun rest(pathChain: PathChain) = SequentialGroup(
        follow(pathChain).and(intake.reverse),
        intake.off.then(catapults.stabilize),
        wait(0.8),
        catapults.up.and(intake.reset)
    )

    fun intake(pathChain: PathChain) = SequentialGroup(
        follow(pathChain).and(intake.manual),
        wait(0.5)
    )
}