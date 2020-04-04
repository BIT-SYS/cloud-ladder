plugins {
    java
    antlr
}

group = "cn.edu.bit.cs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.generateGrammarSource {
    arguments = arguments + listOf("-visitor", "-long-messages")
}

dependencies {
    testCompile("junit", "junit", "4.12")
    compile("com.google.code.gson:gson:2.8.6")
    antlr("org.antlr:antlr4:4.8")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}