plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.25"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.zht"
version = "1.0-SNAPSHOT"

repositories {
    // 添加阿里云的仓库【修改】
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }
}

// 添加依赖【修改】
dependencies {
    implementation("cn.hutool:hutool-all:5.8.32")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.52")
    implementation ("com.alibaba:easyexcel:4.0.3")
//    implementation ("org.projectlombok:lombok:1.18.32")
    implementation("org.slf4j:slf4j-log4j12:1.7.5")
}

// 配置 Gradle IntelliJ 插件
// 更多信息请参阅： https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.2.6")
    type.set("IC") // 目标 IDE 平台

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // 设置 JVM 兼容版本
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
        // 设置编码为 UTF-8，防止中文乱码【修改】
        options.encoding="UTF-8"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    // 设置编码为 UTF-8，防止控制台中文乱码【修改】
    withType<JavaExec>{
        jvmArgs = listOf("-Dfile.encoding=UTF-8","-Dsun.stdout.encoding=UTF-8","-Dsun.stderr.encoding=UTF-8")
    }

    // 配置插件兼容的IDE版本范围
    patchPluginXml {
        // 设置插件支持的最低IDE版本
        sinceBuild.set("232")
        // 设置插件支持的最高IDE版本，使用通配符表示该版本系列都支持
        untilBuild.set("252.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
