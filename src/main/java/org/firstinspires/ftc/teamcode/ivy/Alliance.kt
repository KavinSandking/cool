package org.firstinspires.ftc.teamcode.ivy

import com.pedropathing.geometry.Pose

enum class Alliance {
    RED, BLUE;

    companion object {
        var current = BLUE

        private val blueGoal = Pose(0.0, 141.5)
        private val blueStart = Pose(23.5, 125.0, Math.toRadians(144.0))

        val goal: Pose
            get() = if (current == BLUE) blueGoal else blueGoal.mirror()

        val start: Pose
            get() = if (current == BLUE) blueStart else blueStart.mirror()
    }
}