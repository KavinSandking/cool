package org.firstinspires.ftc.teamcode.util.utilityPoses

import com.pedropathing.geometry.Pose

object Poses {
    private val blueAutonStart = Pose(23.5, 125.0, Math.toRadians(144.0))
    private val blueGoal = Pose(0.0, 141.5)
    private val  resetPose = Pose()

    val startPose: Pose get() = when (Alliance.current) {
        Alliance.BLUE if OpModeType.current == OpModeType.AUTON -> blueAutonStart
        Alliance.RED if OpModeType.current == OpModeType.AUTON -> blueAutonStart.mirror()
        Alliance.BLUE if OpModeType.current == OpModeType.TELEOP -> Pose()
        else -> Pose()
    }

    val goalPose: Pose get() = if (Alliance.current == Alliance.BLUE) {
        blueGoal
    } else {
        blueGoal.mirror()
    }

    val relocalizePose: Pose get() = if (Alliance.current == Alliance.BLUE){
        resetPose
    } else {
        resetPose.mirror()
    }
}