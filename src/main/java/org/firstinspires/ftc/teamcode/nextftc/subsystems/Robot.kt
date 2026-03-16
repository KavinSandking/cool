package org.firstinspires.ftc.teamcode.nextftc.subsystems

import com.pedropathing.paths.PathChain
import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.wait
import dev.nextftc.core.subsystems.SubsystemGroup
import org.firstinspires.ftc.teamcode.nextftc.Alliance
import org.firstinspires.ftc.teamcode.nextftc.util.followPath
import org.firstinspires.ftc.teamcode.nextftc.util.parallel
import org.firstinspires.ftc.teamcode.nextftc.util.sequential

object Robot: SubsystemGroup(Drivetrain, Catapults, Intake, BreakBeam) {
    var alliance = Alliance.BLUE_GOAL

    fun scoreFirst(p: PathChain): Command = sequential(
        followPath(p), wait(0.25), Catapults.shoot
    )

    fun score(p: PathChain): Command = sequential(
        parallel(followPath(p), Intake.reverse),
        Intake.off,
        Catapults.stabilize,
        wait(0.1),
        Catapults.stabilize,
        wait(0.8),
        Catapults.shoot
    )

    fun gateScore(p: PathChain): Command = sequential(
        parallel(followPath(p), Intake.reverse),
        Intake.off,
        Catapults.stabilize,
        wait(0.1),
        Catapults.stabilize,
        wait(0.8),
        Catapults.shootSlow
    )

    fun intake(p: PathChain): Command = sequential(
        parallel(followPath(p), Intake.reverse)
    )

    fun openGate(p: PathChain): Command = sequential(
        parallel(followPath(p), Intake.on),
        wait(0.5)
    )

    fun overload(t: Double): Command = sequential(
        Intake.on.until { BreakBeam.overload },
        wait(0.1),
        Intake.reverse
    )

    override fun initialize() {
        Drivetrain.goal = alliance.pose
    }
}