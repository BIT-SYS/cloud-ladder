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

    val generateASTListener by registering {
        description = "Generate AstVisitor Interface"

        val visitor = "src/main/java/ast/AstVisitor.java"
        val nodes = "src/main/java/ast/node"
        inputs.dir(nodes)
        outputs.file(visitor)

        doLast {
            val sb = StringBuilder()
            sb.append("package ast;\n\n")
            val list = file(nodes).walk().toList()
            val ast = file(nodes).parentFile
            list.filter { it.isDirectory }.forEach {
                sb.append("import ast.${it.toRelativeString(ast).replace(File.separatorChar, '.')}.*;\n")
            }
            sb.append("\npublic interface AstVisitor{\n")
            list.filter { it.isFile }.map { it.name.substringBefore(".java") }.forEach {
                sb.append("    public void visit($it node);\n")
            }
            sb.append('}')
            File(visitor).apply {
                delete()
                createNewFile()
                writeText(sb.toString())
            }
        }
    }

    compileJava {
        dependsOn.add(generateASTListener)
    }
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}