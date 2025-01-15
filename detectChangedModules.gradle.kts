import org.gradle.api.tasks.Exec
import java.io.ByteArrayOutputStream

// Task to get the latest Git tag
val getLatestTag = tasks.register("getLatestTag", Exec::class) {
    commandLine = listOf("git", "describe", "--tags", "--abbrev=0")
    standardOutput = ByteArrayOutputStream()
    doLast {
        val latestTag = standardOutput.toString().trim()
        println("Latest tag: $latestTag")
    }
}

// Task to find changed files since the last tag
val findChangedFiles = tasks.register("findChangedFiles", Exec::class) {
    dependsOn(getLatestTag)
    //val latestTag = "v1.0.0-alpha.1"//(getLatestTag.get().standardOutput as ByteArrayOutputStream).toString().trim()
    val latestTag = "v1.0.0-alpha.1"
    println("Latest tag3: $latestTag")
    commandLine = listOf("git", "diff", "--name-only", "$latestTag..HEAD")
    standardOutput = ByteArrayOutputStream()
    doLast {
        println("Latest tag2: $latestTag")
        val changedFiles = standardOutput.toString().trim().lines()
        println("Changed files since $latestTag:\n${changedFiles.joinToString("\n")}")
    }
}

// Task to detect changed modules
tasks.register("detectChangedModules") {
    dependsOn(findChangedFiles)
    doLast {
        val changedFiles = (findChangedFiles.get().standardOutput as ByteArrayOutputStream)
            .toString()
            .trim()
            .lines()

        val modulePaths = subprojects.map { it.projectDir.relativeTo(rootDir).path }

        val affectedModules = modulePaths.filter { path ->
            changedFiles.any { it.startsWith(path) }
        }

        println("Affected modules: ${affectedModules.joinToString(", ")}")
    }
}