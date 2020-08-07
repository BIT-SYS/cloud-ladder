plugins {
    java
    antlr
    id("com.gradle.build-scan").version("3.4")
}

group = "cn.edu.bit.cs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testCompile("junit", "junit", "4.12")
    antlr("org.antlr:antlr4:4.8")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    generateGrammarSource {
        arguments = arguments + listOf<String>("-visitor", "-long-messages", "-package", "grammar")
        outputDirectory = File("$buildDir/generated-src/antlr/main/grammar")
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}