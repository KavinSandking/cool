package org.firstinspires.ftc.teamcode.nextftc.util.functions

import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import dev.nextftc.core.components.*
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.util.enums.Alliance
import org.firstinspires.ftc.teamcode.nextftc.util.enums.OpModeType
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.*
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

abstract class OpMode(val opModeType: OpModeType): NextFTCOpMode() {
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
        OpModeType.current = opModeType
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
}