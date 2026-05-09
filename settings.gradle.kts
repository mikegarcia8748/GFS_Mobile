pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GFS_Mobile"
include(":app")
include(":core:ui")
include(":core:navigation")
include(":core:domain")
include(":core:data")
include(":feature:auth")
include(":feature:ricemill")
include(":feature:dashboard")
include(":feature:payroll")
