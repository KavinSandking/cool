package org.firstinspires.ftc.teamcode.util.subsystems

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.SubsystemGroup
import org.firstinspires.ftc.teamcode.util.Alliance
import org.firstinspires.ftc.teamcode.util.followPath
import org.firstinspires.ftc.teamcode.util.pedropathing.Trajectories

object Robot: SubsystemGroup(Drivetrain, Catapults, Intake) {
    var alliance = Alliance.BLUE

    val intake = Intake
    val catapults = Catapults
    val drivetrain = Drivetrain


    fun scoreFirst(p: PathChain): Command = SequentialGroup(
        followPath(p), wait(0.25), catapults.shoot
    )

    fun score(p: PathChain): Command = SequentialGroup(
        ParallelGroup(followPath(p), intake.reverse),
        intake.off,
        catapults.stabilize,
        wait(0.1),
        catapults.stabilize,
        wait(0.8),
        catapults.shoot
    )

    fun gateScore(p: PathChain): Command = SequentialGroup(
        ParallelGroup(followPath(p), intake.reverse),
        intake.off,
        catapults.stabilize,
        wait(0.1),
        catapults.stabilize,
        wait(0.8),
        catapults.shootSlow
    )

    fun intake(p: PathChain): Command = SequentialGroup(
        ParallelGroup(followPath(p), intake.reverse)
    )

    fun openGate(p: PathChain): Command = SequentialGroup(
        ParallelGroup(followPath(p), intake.on),
        wait(0.5)
    )

    override fun initialize() {
        drivetrain.goal = alliance.goal
    }
}