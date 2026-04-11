package org.firstinspires.ftc.teamcode.ivy.wrapperClasses

import com.pedropathing.ivy.Scheduler
import com.pedropathing.ivy.Scheduler.schedule
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import org.firstinspires.ftc.teamcode.ivy.subsystems.Robot
import org.firstinspires.ftc.teamcode.util.utilityPoses.Alliance
import org.firstinspires.ftc.teamcode.util.utilityPoses.OpModeType

abstract class TeleOpMode: OpMode() {
    val robot = Robot()

    val prompter = Prompter(this)

    override fun init() {
        robot.intake.count = 1

        OpModeType.current = OpModeType.TELEOP
        prompter.prompt("alliance", OptionPrompt("Select Alliance", Alliance.BLUE, Alliance.RED))
            .onComplete {
                val alliance: Alliance = prompter.get("alliance")
                Alliance.current = alliance
            }
    }

    override fun init_loop() {
        prompter.run()
    }

    override fun loop() {
        Scheduler.execute()

        schedule(
            robot.intake.periodic,
            robot.drivetrain.drive
        )
    }

    override fun stop() {
        Scheduler.reset()
    }
}