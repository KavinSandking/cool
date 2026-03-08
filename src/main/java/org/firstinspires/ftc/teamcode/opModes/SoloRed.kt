package org.firstinspires.ftc.teamcode.opModes

import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.util.Alliance
import org.firstinspires.ftc.teamcode.util.pedropathing.Constants
import org.firstinspires.ftc.teamcode.util.pedropathing.Trajectories
import org.firstinspires.ftc.teamcode.util.subsystems.Catapults
import org.firstinspires.ftc.teamcode.util.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.util.subsystems.Intake
import org.firstinspires.ftc.teamcode.util.subsystems.Robot

class SoloRed : NextFTCOpMode() {
    init {
        addComponents(
            SubsystemComponent(Drivetrain, Intake, Catapults, Robot),
            PedroComponent(Constants::createFollower),
            BulkReadComponent,
            BindingsComponent
        )
    }


    override fun onInit() {
        Robot.alliance = Alliance.RED
        Trajectories.paths(follower)
        Catapults.down
    }

    override fun onStartButtonPressed() {
        SequentialGroup(
            Robot.scoreFirst(Trajectories.score1),
            Robot.intake(Trajectories.line2),
            Robot.score(Trajectories.score2),
            Robot.openGate(Trajectories.openGate),
            Robot.overload(0.1).endAfter(1.0),
            Robot.gateScore(Trajectories.score3),
            Robot.intake(Trajectories.line1),
            Robot.score(Trajectories.score4),
            Robot.intake(Trajectories.line3),
            Robot.score(Trajectories.leave)
        )
    }
}