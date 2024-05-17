java.toolchain.languageVersion = JavaLanguageVersion.of(17)

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

sourceSets.main {
    java.setSrcDirs(listOf("src/"))
}

val appName = "EO-Core"
eclipse.project.name = "eo-core"