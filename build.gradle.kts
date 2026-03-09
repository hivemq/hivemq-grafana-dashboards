import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

buildscript {
    dependencies {
        classpath("com.google.code.gson:gson:2.13.2")
    }
}

plugins {
    alias(libs.plugins.defaults)
    alias(libs.plugins.githubRelease)
    java
}

val createDashboards by tasks.registering {
    group = "dashboard"
    dependsOn(createPrometheusDashboard)
    dependsOn(createInfluxDbDashboard)
}

val combinedDashboardFile = layout.projectDirectory.file("hivemq-grafana-dashboard-combined.json")

val createPrometheusDashboard by tasks.registering {
    group = "dashboard"
    inputs.file(combinedDashboardFile)
    val outputFile = layout.buildDirectory.dir("dashboards").get()
        .file("hivemq-grafana-dashboard-prometheus-${project.version}.json")
    outputs.file(outputFile)
    val projectVersion = project.version.toString()
    doLast {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val dashboard = gson.fromJson(combinedDashboardFile.asFile.readText(), JsonObject::class.java)

        // remove influxdb queries
        // remove prometheus queries
        dashboard["panels"].asJsonArray.flatMap {
            if (it.asJsonObject["type"].asString == "row") {
                it.asJsonObject["panels"].asJsonArray.asList()
            } else {
                listOf(it)
            }
        }
            .filterNot {
                it.asJsonObject["type"].asString == "row"
            }
            .forEach { panel ->
                panel.asJsonObject["targets"].asJsonArray.asList().removeIf {
                    it.asJsonObject["datasource"].asJsonObject["type"].asString == "influxdb"
                }
            }

        // remove influxdb datasource variable
        val datasourceVariables = dashboard["templating"].asJsonObject["list"]
        datasourceVariables.asJsonArray.asList().removeIf {
            it.asJsonObject["query"].asString == "influxdb"
        }

        // rename the dashboard
        dashboard.asMap()["title"] = JsonPrimitive("HiveMQ Platform (Prometheus)")

        // set the version
        dashboard.asMap()["version"] = JsonPrimitive(projectVersion)

        outputFile.asFile.writeText(gson.toJson(dashboard))
    }
}

val createInfluxDbDashboard by tasks.registering {
    group = "dashboard"
    val outputFile =
        layout.buildDirectory.dir("dashboards").get().file("hivemq-grafana-dashboard-influxdb-${project.version}.json")
    inputs.file(combinedDashboardFile)
    outputs.file(outputFile)
    val projectVersion = project.version.toString()
    doLast {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val dashboard = gson.fromJson(combinedDashboardFile.asFile.readText(), JsonObject::class.java)

        // remove prometheus queries
        dashboard["panels"].asJsonArray.flatMap {
            if (it.asJsonObject["type"].asString == "row") {
                it.asJsonObject["panels"].asJsonArray.asList()
            } else {
                listOf(it)
            }
        }
            .filterNot {
                it.asJsonObject["type"].asString == "row"
            }
            .forEach { panel ->
                panel.asJsonObject["targets"].asJsonArray.asList().removeIf {
                    it.asJsonObject["datasource"].asJsonObject["type"].asString == "prometheus"
                }
            }

        // remove prometheus datasource variable
        val datasourceVariables = dashboard["templating"].asJsonObject["list"]
        datasourceVariables.asJsonArray.asList().removeIf {
            it.asJsonObject["query"].asString == "prometheus"
        }

        // rename the dashboard
        dashboard.asMap()["title"] = JsonPrimitive("HiveMQ Platform (InfluxDB)")

        // set the version
        dashboard.asMap()["version"] = JsonPrimitive(projectVersion)

        outputFile.asFile.writeText(gson.toJson(dashboard))
    }
}

githubRelease {
    authorization.set(System.getenv("githubToken"))
    releaseAssets.from(
        createInfluxDbDashboard,
        createPrometheusDashboard
    )
    allowUploadToExisting.set(true)
    releaseName.set(project.version.toString())
    tagName.set(project.version.toString())
    targetCommitish.set("master")
    owner.set("hivemq")
}
