package org.firstinspires.ftc.teamcode.ivy

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import dev.nextftc.bindings.button
import dev.nextftc.bindings.range
import org.firstinspires.ftc.teamcode.nextftc.Alliance
import org.firstinspires.ftc.teamcode.ivy.subsystems.*
import kotlin.math.abs

class Teleop: OpMode() {
    lateinit var intake: Intake
    lateinit var catapults: Catapults
    lateinit var drivetrain: Drivetrain

    var headingLock = false

    override fun init() {
        intake = Intake
        catapults = Catapults
        drivetrain = Drivetrain

        button { gamepad1.a }.toggleOnBecomesTrue()
            .whenBecomesTrue { Alliance.current = Alliance.BLUE_GOAL }
            .whenBecomesFalse { Alliance.current = Alliance.RED_GOAL }
    }

    override fun start() {
        range { gamepad2.right_stick_y }.inRange(-0.1, 0.1)
            .whenFalse { intake.manual().schedule() }
            .whenTrue { intake.off().schedule() }

        button { gamepad2.right_bumper }.toggleOnBecomesTrue()
            .whenBecomesTrue { catapults.up().schedule() }
            .whenBecomesFalse { catapults.down().schedule() }

        button { gamepad2.left_bumper }.whenBecomesTrue { catapults.stabilize().schedule() }

        button { gamepad1.left_bumper }
            .toggleOnBecomesTrue()
            .whenTrue { headingLock = true }
            .whenFalse { headingLock = false }
    }

    override fun loop(){
        if (headingLock){
            drivetrain.headingLock.schedule()
        } else {
            drivetrain.drive.schedule()
        }
        drivetrain.follower.update()
    }

}