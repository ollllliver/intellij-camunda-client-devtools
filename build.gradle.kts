// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.

plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.15.0"
}

group = "de.hsrm.mi.ba"
version = "1.0.0"

repositories {
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
}

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2021.2.4")
  plugins.set(listOf("com.intellij.java"))
}

dependencies {
// https://mvnrepository.com/artifact/com.hubspot.jinjava/jinjava
  implementation("com.hubspot.jinjava:jinjava:2.7.1")
  implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.8.9")
}

tasks {
  buildSearchableOptions {
    enabled = false
  }

  patchPluginXml {
    version.set("${project.version}")
    sinceBuild.set("212")
    untilBuild.set("212.*")
  }
}
