plugins {
    id("dev.frozenmilk.teamcode") version "11.0.0-1.1.0"
    id("dev.frozenmilk.sinister.sloth.load") version "0.2.4"
}

ftc {
    kotlin()
    sdk.TeamCode()

    dairy {
        implementation(Sloth)
        implementation(MercurialFTC)
    }

    next {
        implementation(ftc("1.1.0"))
        implementation(hardware("1.1.0"))
        implementation(bindings)
        implementation(control)
        implementation(pedro)
    }

    pedro {
        implementation(core)
        implementation(ftc)
    }

    implementation(acmerobotics.dashboard)
    implementation(ftControl.fullpanels)
}

dependencies {
    implementation("dev.nextftc:control2:0.0.3")
    implementation("dev.nextftc.control2:units:0.0.1")
    implementation("dev.nextftc.control2:linalg:0.0.1")
}