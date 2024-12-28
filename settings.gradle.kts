pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()


    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://www.jitpack.io" ) }
        maven {
            url = uri("https://cardinalcommerceprod.jfrog.io/artifactory/android")
            credentials {
                username = "braintree_team_sdk"
                password = "cmVmdGtuOjAxOjIwMzgzMzI5Nzg6Q3U0eUx5Zzl5TDFnZXpQMXpESndSN2tBWHhJ"
            }
        }


    }
}

rootProject.name = "Doc_Sach_Truc_Tuyen"
include(":app")
