package org.firstinspires.ftc.teamcode.nextftc

import com.pedropathing.geometry.Pose

enum class Alliance(val pose: Pose) {
    BLUE(Pose()), RED(Pose()),
    BLUE_GOAL(Pose(0.0, 141.5)), RED_GOAL(Pose(0.0, 141.5).mirror()),
    BLUE_START_POSE(Pose(23.5, 125.0, Math.toRadians(144.0))), RED_START_POSE(
        Pose(
            23.5,
            125.0,
            Math.toRadians(144.0)
        ).mirror());

    companion object {
        var current: Alliance = BLUE_START_POSE
    }
}