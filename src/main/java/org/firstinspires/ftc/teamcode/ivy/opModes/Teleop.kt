package org.firstinspires.ftc.teamcode.ivy.opModes

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import dev.nextftc.bindings.button
import dev.nextftc.bindings.range
import dev.nextftc.ftc.NextFTCOpMode
import org.firstinspires.ftc.teamcode.ivy.Alliance
import org.firstinspires.ftc.teamcode.ivy.subsystems.Catapults
import org.firstinspires.ftc.teamcode.ivy.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.ivy.subsystems.Intake

class Teleop: NextFTCOpMode() {
    var headingLock = false
    val prompter = Prompter(this)

    override fun onInit() {
        prompter.prompt("alliance", OptionPrompt("Select Alliance: ", Alliance.BLUE, Alliance.RED))
            .onComplete {
                val alliance: Alliance = prompter.get("alliance")
                Alliance.current = alliance
                this.telemetry.addData("Alliance", alliance)
            }
    }

    override fun onWaitForStart() {
        prompter.run()
    }

    override fun onStartButtonPressed() {
        range { gamepad2.right_stick_y }.inRange(-0.1, 0.1)
            .whenFalse { Intake.manual.schedule() }
            .whenTrue { Intake.off.schedule() }

        button { gamepad2.right_bumper }.toggleOnBecomesTrue()
            .whenBecomesTrue { Catapults.up.schedule() }
            .whenBecomesFalse { Catapults.down.schedule() }

        button { gamepad2.left_bumper }.whenBecomesTrue { Catapults.stabilize.schedule() }

        button { gamepad1.left_bumper }
            .toggleOnBecomesTrue()
            .whenTrue { headingLock = true }
            .whenFalse { headingLock = false }
    }

    override fun onUpdate(){
        if (headingLock){
            Drivetrain.headingLock.schedule()
        } else {
            Drivetrain.drive.schedule()
        }
    }

}