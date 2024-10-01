import org.gradle.api.JavaVersion.VERSION_1_8

plugins {
    kotlin("jvm") version "2.0.20"
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    tasks {
        withType<JavaCompile> {
            options.compilerArgs.add("-Xlint:-deprecation")
        }
        java {
            sourceCompatibility = VERSION_1_8
            targetCompatibility = VERSION_1_8
        }

        withType<Test> {
            useJUnitPlatform()
        }
    }

    dependencies {
        api("org.jetbrains.kotlin:kotlin-reflect:2.0.20")
        testApi(platform("org.junit:junit-bom:5.11.0"))

        testApi("org.junit.jupiter:junit-jupiter-api")
        testApi("org.junit.jupiter:junit-jupiter-engine")
        testApi("junit:junit:4.12")
    }

    sourceSets {
        main {
            java {
                srcDirs("src/main/exercises")
            }
            kotlin {
                srcDirs("src/slides")
                srcDirs("src/handouts")
                srcDirs("src/main/exercises")
                srcDirs("src/main/solutions")
            }
        }
        test {
            kotlin {
                srcDirs("src/test/exercises")
                srcDirs("src/test/solutions")
            }
        }
    }
}
