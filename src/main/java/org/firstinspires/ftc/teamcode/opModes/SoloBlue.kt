package org.firstinspires.ftc.teamcode.opModes

import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.instant
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.util.Alliance
import org.firstinspires.ftc.teamcode.util.followPath
import org.firstinspires.ftc.teamcode.util.pedropathing.Constants
import org.firstinspires.ftc.teamcode.util.pedropathing.Trajectories
import org.firstinspires.ftc.teamcode.util.subsystems.Catapults
import org.firstinspires.ftc.teamcode.util.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.util.subsystems.Intake
import org.firstinspires.ftc.teamcode.util.subsystems.Robot

class SoloBlue: NextFTCOpMode(){
    init {
        addComponents(
            SubsystemComponent(Drivetrain, Intake, Catapults),
            PedroComponent(Constants::createFollower),
            BulkReadComponent,
            BindingsComponent
        )
    }

    val trajectories = Trajectories()

    override fun onInit() {
        Robot.alliance = Alliance.BLUE
        trajectories.paths(follower)
        Robot.catapults.down
    }

    override fun onStartButtonPressed() {
        SequentialGroup(
            Robot.scoreFirst(trajectories.score1),
            Robot.intake(trajectories.line2),
            Robot.score(trajectories.score2),
            Robot.openGate(trajectories.openGate),
            Robot.gateScore(trajectories.score3),
            Robot.intake(trajectories.line1),
            Robot.score(trajectories.score4),
            Robot.intake(trajectories.line3),
            Robot.score(trajectories.leave)
        )
    }
}