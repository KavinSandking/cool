package org.firstinspires.ftc.teamcode.nextftc.opModes

import com.pedropathing.geometry.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import org.firstinspires.ftc.teamcode.nextftc.util.enums.OpModeType
import org.firstinspires.ftc.teamcode.nextftc.util.functions.OpMode
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.preload
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.intake
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Routines.shoot

@Autonomous(name = "blue", group = "autons")
class BlueAuton: OpMode(OpModeType.AUTONOMOUS) {
    override fun onStartButtonPressed() {
        intake.overload()

        val auton = SequentialGroup(
            preload(traj.score1),
            intake(traj.line2),
            shoot(traj.score2),
            intake(traj.openGate),
            shoot(traj.score3),
            intake(traj.line1),
            shoot(traj.score4),
            intake(traj.line3),
            shoot(traj.leave)
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