import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("multiplatform") version "1.6.21"
}

group = "com.ticky"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
            // TODO: add Headless Chrome to Dockerfile
            testTask {
                enabled = false
            }
        }
    }
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }
}

tasks.withType<Test> {
    testLogging {
        events(PASSED, SKIPPED, FAILED, STANDARD_OUT)

        addTestListener(
            object : TestListener {
                override fun beforeSuite(suite: TestDescriptor?) = Unit

                override fun afterSuite(suite: TestDescriptor?, result: TestResult?) {
                    if (suite?.parent == null) {
                        val s = "test result: ${result?.resultType}. ${result?.testCount} ran; ${result?.successfulTestCount} passed; ${result?.failedTestCount} failed"
                        println("\n${s}")
                    }
                }

                override fun beforeTest(testDescriptor: TestDescriptor?) = Unit

                override fun afterTest(testDescriptor: TestDescriptor?, result: TestResult?) = Unit
            }
        )
    }
}
