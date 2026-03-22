package org.firstinspires.ftc.teamcode.nextftc.util.enums

import com.pedropathing.geometry.Pose
import org.firstinspires.ftc.teamcode.nextftc.opModes.BlueAuton
import org.firstinspires.ftc.teamcode.nextftc.opModes.RedAuton

enum class Alliance {
    RED, BLUE;

    companion object {
        var current = BLUE

        private val blueGoal = Pose(0.0, 141.5)
        private val blueStartAuto = Pose(23.5, 125.0, Math.toRadians(144.0))
        private val blueEndAuto = BlueAuton.end
        private val redEndAuto = RedAuton.end

        val goal: Pose
            get() = if (current == BLUE) blueGoal else blueGoal.mirror()

        val startAuto: Pose
            get() = if (current == BLUE) blueStartAuto else blueStartAuto.mirror()

        val startTele: Pose?
            get() = if (current == BLUE) blueEndAuto else redEndAuto
    }
}