package org.firstinspires.ftc.teamcode.nextftc.opModes

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import org.firstinspires.ftc.teamcode.nextftc.util.functions.OpMode
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.first
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.intake
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.rest

@Autonomous(name = "blue")
class BlueAuton: OpMode() {
    override fun onStartButtonPressed() {
        val auton = SequentialGroup(
            first(traj.score1),
            intake(traj.line2),
            rest(traj.score2),
            intake(traj.openGate),
            rest(traj.score3),
            intake(traj.line1),
            rest(traj.score4),
            intake(traj.line3),
            rest(traj.leave)
        )

        auton()
    }

    override fun onStop() {
        end = follower.pose
    }

    companion object {
        lateinit var end: Pose
    }
}