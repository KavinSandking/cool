package org.firstinspires.ftc.teamcode.nextftc.util.wrapperClasses

import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.core.subsystems.Subsystem
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent

abstract class TestOpMode(vararg subsystems: Subsystem): NextFTCOpMode() {
    init {
        addComponents(
            SubsystemComponent(subsystems = subsystems),
            BindingsComponent,
            BulkReadComponent
        )
    }
}