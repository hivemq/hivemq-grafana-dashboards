import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

buildscript {
    dependencies {
        classpath("com.google.code.gson:gson:2.10.1")
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

val combinedDashboardFile = File("hivemq-grafana-dashboard-combined.json")

val createPrometheusDashboard by tasks.registering {
    group = "dashboard"
    inputs.file(combinedDashboardFile)
    val outputFile = "hivemq-grafana-dashboard-prometheus-${project.version}.json"
    outputs.file(outputFile)
    doLast {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val dashboard = gson.fromJson(combinedDashboardFile.readText(), JsonObject::class.java)

        // remove influxdb queries
        dashboard["panels"].asJsonArray.filterNot {
            it.asJsonObject["type"].asString == "row"
        }.forEach { panel ->
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
        dashboard.asMap()["version"] = JsonPrimitive(project.version.toString())

        val target = layout.buildDirectory.dir("dashboards").get()
        target.asFile.mkdirs()
        target.file(outputFile).asFile.writeText(gson.toJson(dashboard))
    }
}

val createInfluxDbDashboard by tasks.registering {
    group = "dashboard"
    val outputFile = "hivemq-grafana-dashboard-influxdb-${project.version}.json"
    inputs.file(combinedDashboardFile)
    outputs.file(outputFile)
    doLast {
        val gson = GsonBuilder().setPrettyPrinting().create()
        val dashboard = gson.fromJson(combinedDashboardFile.readText(), JsonObject::class.java)

        // remove prometheus queries
        dashboard["panels"].asJsonArray.filterNot {
            it.asJsonObject["type"].asString == "row"
        }.forEach { panel ->
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
        dashboard.asMap()["version"] = JsonPrimitive(project.version.toString())

        val target = layout.buildDirectory.dir("dashboards").get()
        target.asFile.mkdirs()
        target.file(outputFile).asFile.writeText(gson.toJson(dashboard))
    }
}

githubRelease {
    token(System.getenv("githubToken"))
    releaseAssets(
        createInfluxDbDashboard,
        createPrometheusDashboard
    )
    allowUploadToExisting.set(true)
    releaseName(project.version.toString())
    tagName(project.version.toString())
    targetCommitish("master")
}