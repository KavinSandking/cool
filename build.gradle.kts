plugins {
    id("dev.frozenmilk.teamcode") version "11.1.0-1.1.2"
    id("dev.frozenmilk.sinister.sloth.load") version "0.2.4"
}

ftc {
    kotlin()
    sdk.TeamCode("11.1.0")

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
        implementation(ftc("2.1.1"))
        implementation(telemetry)
    }

    acmerobotics {
        implementation(dashboard)
        implementation(roadrunner.core)
        implementation(roadrunner.actions)
        implementation(roadrunner.ftc)
    }

    implementation(ftControl.fullpanels)
}

dependencies {
    implementation("dev.nextftc:control2:0.0.3")
    implementation("dev.nextftc.control2:units:0.0.1")
    implementation("dev.nextftc.control2:linalg:0.0.1")
    implementation("io.github.kleongf:Cubelib:1.0.0-beta.1")
    implementation("com.pedropathing:ivy:1.0.0")
    implementation("com.skeletonarmyftc.marrow:core:1.0.0")
}