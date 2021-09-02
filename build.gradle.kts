plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0" apply false
    id("io.papermc.paperweight.patcher") version "1.1.11"
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/") {
        content {
            onlyForConfigurations("paperclip")
        }
    }
    maven("https://maven.quiltmc.org/repository/release/") {
        content {
            onlyForConfigurations("remapper")
        }
    }
}

dependencies {
    remapper("org.quiltmc:tiny-remapper:0.4.3:fat")
    paperclip("io.papermc:paperclip:2.0.1")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(16))
        }
    }

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(16)
    }

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://ci.emc.gs/nexus/content/groups/aikar/")
        maven("https://repo.aikar.co/content/groups/aikar")
        maven("https://repo.md-5.net/content/repositories/releases/")
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://nexus.velocitypowered.com/repository/velocity-artifacts-snapshots/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

paperweight {
    serverProject.set(project(":Deepslate-Server"))

    useStandardUpstream("Purpur") {
        url.set(github("pl3xgaming", "Purpur"))
        ref.set(providers.gradleProperty("purpurCommit"))

        withStandardPatcher {
            baseName("Purpur")

            remapRepo.set("https://maven.quiltmc.org/repository/release/")
            decompileRepo.set("https://files.minecraftforge.net/maven/")
            
            apiOutputDir.set(layout.projectDirectory.dir("Deepslate-API"))
            serverOutputDir.set(layout.projectDirectory.dir("Deepslate-Server"))
        }
    }
}

