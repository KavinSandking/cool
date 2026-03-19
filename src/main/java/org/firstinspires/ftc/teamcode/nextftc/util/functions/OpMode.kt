package org.firstinspires.ftc.teamcode.nextftc.util.functions

import com.skeletonarmy.marrow.prompts.OptionPrompt
import com.skeletonarmy.marrow.prompts.Prompter
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.nextftc.util.enums.Alliance
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Catapults
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Intake
import org.firstinspires.ftc.teamcode.nextftc.util.subsystems.Trajectories
import org.firstinspires.ftc.teamcode.pedropathing.Constants

abstract class OpMode: NextFTCOpMode() {
    val intake = Intake
    val catapults = Catapults
    val drivetrain = Drivetrain
    val traj = Trajectories

    init {
        addComponents(
            SubsystemComponent(intake, catapults, drivetrain),
            PedroComponent(Constants::createFollower),
            BindingsComponent,
            BulkReadComponent
        )
    }

    private val prompter = Prompter(this)

    override fun onInit() {
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