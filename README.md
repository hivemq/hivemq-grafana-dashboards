# HiveMQ Platform Grafana Dashboards

![GitHub Release](https://img.shields.io/github/release/hivemq/hivemq-grafana-dashboards?style=flat)

This repository offers Grafana Dashboards to Monitor the HiveMQ Platform.
For simple conversions and better understandability all rates are displayed per second.

## Features

- A dashboard for Prometheus using [PromQL](https://prometheus.io/docs/prometheus/latest/querying/basics/).
- A dashboard for InfluxDB using [Flux](https://docs.influxdata.com/influxdb/cloud/query-data/flux/).
- Visualizations for the most important HiveMQ Platform metrics.
- Visualizations for the most important metrics of each HiveMQ Enterprise Extension.

## Prometheus

We offer a dedicated dashboard for Prometheus using
[PromQL](https://prometheus.io/docs/prometheus/latest/querying/basics/)

![prometheus-example.png](img%2Fprometheus-example.png)

### Getting Started with the Grafana Dashboard for Prometheus

- [Configure](https://grafana.com/docs/grafana/latest/datasources/prometheus/configure-prometheus-data-source/) your
  Prometheus data source.
- [Download](https://github.com/hivemq/hivemq-grafana-dashboards/releases/latest)
  the latest `hivemq-grafana-dashboard-prometheus-X.json` dashboard file.
- [Import](https://grafana.com/docs/grafana/latest/dashboards/build-dashboards/import-dashboards/) the downloaded json
  file in Grafana.
- Select your desired Prometheus data source in the top left corner of the Grafana Dashboard.

## InfluxDB 2.X

We offer a dedicated dashboard for InfluxDB using [Flux](https://docs.influxdata.com/influxdb/cloud/query-data/flux/).

![influxdb-example.png](img%2Finfluxdb-example.png)

### Getting Started with the Grafana Dashboard for InfluxDB

- [Configure](https://grafana.com/docs/grafana/latest/datasources/influxdb/) your InfluxDB data source in Grafana. Make
  sure to configure Flux as the InfluxDB query language for the data source.
- [Download](https://github.com/hivemq/hivemq-grafana-dashboards/releases/latest)
  the latest `hivemq-grafana-dashboard-influxdb-X.json` dashboard file.
- [Import](https://grafana.com/docs/grafana/latest/dashboards/build-dashboards/import-dashboards/) the downloaded json
  file in Grafana.
- Select your desired InfluxDB data source in the top left corner of the Grafana Dashboard.

## How to Contribute

- If you want to request a feature or report a bug,
  please [create a GitHub Issue using one of the provided templates](https://github.com/hivemq/hivemq-grafana-dashboards/issues/new/choose)
- If you want to make a contribution to the project, please have a look at the [contribution guide](CONTRIBUTING.md)
