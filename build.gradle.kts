import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.32"
    id("org.jetbrains.kotlin.kapt") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "1.5.3"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.4.32"
}

version = "0.1"
group = "com.example"
val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

micronaut {
    runtime("netty")
    testRuntime("kotest")
    processing {
        incremental(true)
        annotations("com.example.*")
        sourceSets(
            sourceSets.getByName("integrationTest")
        )
    }
}

application {
    mainClass.set("com.example.ApplicationKt")
}

dependencies {
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("javax.annotation:javax.annotation-api")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    implementation("io.micronaut:micronaut-validation")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    kaptTest("io.micronaut:micronaut-inject-java")
    integrationTestImplementation("io.micronaut.test:micronaut-test-kotest:2.3.7")
    integrationTestImplementation("io.mockk:mockk:1.10.5")
    integrationTestImplementation("io.kotest:kotest-runner-junit5-jvm:4.3.0")
}


java {
    sourceCompatibility = JavaVersion.toVersion("11")
}


val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

