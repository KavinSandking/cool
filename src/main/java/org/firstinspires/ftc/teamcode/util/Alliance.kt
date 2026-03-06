package org.firstinspires.ftc.teamcode.util

import com.pedropathing.geometry.Pose

enum class Alliance(val goal: Pose) {
    BLUE(Pose(0.0, 141.5)), RED(Pose(0.0, 141.5).mirror())
}
