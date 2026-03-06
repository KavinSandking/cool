package org.firstinspires.ftc.teamcode.util.subsystems

import dev.nextftc.core.subsystems.SubsystemGroup
import org.firstinspires.ftc.teamcode.util.Alliance

object Robot: SubsystemGroup(Drivetrain, Catapults, Intake) {
    var alliance = Alliance.BLUE

    val intake = Intake
    val catapults = Catapults
    val drivetrain = Drivetrain

    override fun initialize() {
        drivetrain.goal = alliance.goal
    }
}