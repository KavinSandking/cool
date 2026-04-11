package org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses

import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import dev.nextftc.bindings.button
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Catapults
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Intake
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Trajectories
import org.firstinspires.ftc.teamcode.utilityPoses.Alliance
import org.firstinspires.ftc.teamcode.utilityPoses.OpModeType
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

abstract class AutonomousOpMode: NextFTCOpMode() {
    val intake = Intake
    val catapults = Catapults
    val drivetrain = Drivetrain

    init {
        addComponents(
            SubsystemComponent(intake, catapults, drivetrain),
            PedroComponent(Constants::createFollower),
            BindingsComponent,
            BulkReadComponent
        )
    }

    val traj = Trajectories(PedroComponent.follower)
    private val prompter = Prompter(this)

    override fun onInit() {
        OpModeType.current = OpModeType.AUTON
        prompter.prompt("alliance", OptionPrompt("Select Alliance", Alliance.BLUE, Alliance.RED))
            .onComplete {
                val alliance: Alliance = prompter.get("alliance")
                Alliance.current = alliance
            }

        traj.paths()
    }

    override fun onWaitForStart() {
        prompter.run()
    }

    override fun onStartButtonPressed() {
        button { intake.count > 2 } whenTrue intake.clamp
    }
}