package org.firstinspires.ftc.teamcode.nextftc.subsystems

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.wait
import org.firstinspires.ftc.teamcode.nextftc.functions.follow
import org.firstinspires.ftc.teamcode.nextftc.functions.sequential

object Routines {
    val catapults = Catapults
    val intake = Intake

    fun preload(pathChain: PathChain) = sequential(
        follow(pathChain).and(catapults.down),
        catapults.up
    )

    fun shoot(pathChain: PathChain) = sequential(
        follow(pathChain).and(intake.reverse),
        intake.off.then(catapults.stabilize),
        wait(0.8),
        catapults.up.and(intake.reset),
    )

    fun intake(pathChain: PathChain) = sequential(
        follow(pathChain).and(intake.auto),
        wait(0.5)
    )
}