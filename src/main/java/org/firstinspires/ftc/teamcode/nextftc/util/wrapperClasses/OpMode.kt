package org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses

import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Catapults
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Intake
import org.firstinspires.ftc.teamcode.nextftc.subsystems.Trajectories
import org.firstinspires.ftc.teamcode.nextftc.util.enums.Alliance
import org.firstinspires.ftc.teamcode.nextftc.util.enums.OpModeType
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

    val traj = Trajectories(PedroComponent.Companion.follower)
    private val prompter = Prompter(this)

    override fun onInit() {
        OpModeType.Companion.current = opModeType
        prompter.prompt("alliance", OptionPrompt("Select Alliance", Alliance.BLUE, Alliance.RED))
            .onComplete {
                val alliance: Alliance = prompter.get("alliance")
                Alliance.Companion.current = alliance
            }

        traj.paths()
    }

    override fun onWaitForStart() {
        prompter.run()
    }
}