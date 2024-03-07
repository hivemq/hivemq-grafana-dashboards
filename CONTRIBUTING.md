# Contributing to hivemq-grafana-dashboards

Welcome to the HiveMQ Community! Glad to see your interest in contributing to hivemq-grafana-dashboards.
For some general information on how the HiveMQ Community is organized and how contributions will be accepted please have a look at 
our [HiveMQ Community Repo](https://github.com/hivemq/hivemq-community). 

## Prerequisites

We recommend to develop your changes by using [IntelliJ IDEA](https://www.jetbrains.com/idea/). 
Therefore, the following guide will focus on creating your changes using IntelliJ. 

Furthermore, the following is needed to make your development efforts as smoothly as possible:
- Fill out and sign the [Contributor License Agreement](https://www.hivemq.com/downloads/Contributor_License_Agreement.pdf)
  - Send the signed agreement  to (contributing@hivemq.com) with the subject `CLA for Project: hivemq-grafana-dashboards`.
  - Please read this document carefully before signing it and keep a copy for your own records
  - Once we've received your signed CLA, you will be added to the list of contributors and our HiveMQ Development Team will review your contributions
  - In case the rights to all intellectual property created by you are maintained by your employer, 
  - including your work on HiveMQ Open Source Projects, you must ensure you have retained all rights to grant the license, or that your employer has agreed to a separate [Corporate CLA](https://www.hivemq.com/downloads/Corporate_Contributor_License_Agreement.pdf)
- A (local) Grafana installation to test your dashboard. Follow the [Grafana Installation Guide.](https://grafana.com/docs/grafana/latest/setup-grafana/installation/)
- A (local) Prometheus. Follow the [Prometheus Installation Guide.](https://prometheus.io/docs/prometheus/latest/installation/)
- A (local) InfluxDB. Follow the [InfluxDB Installation Guide.](https://docs.influxdata.com/influxdb/v2/install/)
- A (local) HiveMQ Platform to test generate test data.
  - [HiveMQ Prometheus Extension](https://github.com/hivemq/hivemq-prometheus-extension) installed to export metrics to Prometheus.
  - [HiveMQ InfluxDB Extension](https://github.com/hivemq/hivemq-influxdb-extension) installed to export metrics to InfluxDB.

## 1. Setup project

- The following steps will setup a triangle workflow, which will enable you to easily create, update and get your changes into the master branch
- Fork the repository by clicking "Fork" on the main project page (A fork will be created under https://github.com/yourgithubname/hivemq-grafana-dashboards.git)
- Clone your fork via IntelliJ by going to <i>File > New > New Project from Version Control</i> OR by using the command `git clone https://github.com/yourgithubname/hivemq-grafana-dashboards.git`
- Complete the triangle workflow setup by adding `https://github.com/hivemq/hivemq-grafana-dashboards.git` as a remote named _upstream_ via IntelliJ by going to <i>Git > Manage Remotes > + </i> OR by using the command `git remote add upstream https://github.com/hivemq/hivemq-grafana-dashboards.git`

## 2. Create a branch for your changes

- Decide on your branch name:
  - Branch types: feature, bugfix, improvement, cleanup (same as the label of a corresponding GitHub Issue)
  - Starting with type: `feature/`, `bugfix/`, `improvement/`, `cleanup/`
  - \+ task: lower case, spaces replaced with `-`
- Create your branch by using IntelliJ's branch functionality at the bottom of the IDE (<i>upstream/master > New Branch from 'upstream/master'</i>) OR by using the command `git checkout -b branch-type/your-branch-name`

## 3. Write your code

- Every contribution must work for Prometheus and InfluxDB.
- The file `hivemq-grafana-dashboard-combined.json` holds a grafana dashboard for 
  [PromQL](https://prometheus.io/docs/prometheus/latest/querying/basics/) and [Flux](https://docs.influxdata.com/influxdb/cloud/query-data/flux/). 
Use this file to develop your contribution for Prometheus and InfluxDB.
- Import `hivemq-grafana-dashboard-combined.json` into Grafana and modify it.
- Export `hivemq-grafana-dashboard-combined.json` into your project and review your changes.
- Use `./gradlew createDashboards` to create specific dashboards for InfluxDB and Prometheus.
- Import both specific dashboards in Grafana to review your changes.

## 4. Commit and Push
- [Reformat each changed file](https://www.jetbrains.com/help/idea/reformat-and-rearrange-code.html#reformat_file) using the .editorconfig settings of the project
- Commit your changes directly from IntelliJ by pressing `Command` + `K` OR via the command `git commit -m "your commit mesage"`
  - Commits should be as atomic as possible
  - Commit messages should describe the changes clearly
  - Commit messages should start with a capital letter for consistency
  - Commit messages should avoid exceeding the line length limit,
  - Instead use multiple lines, each describing one specific change
- Push your changes to the remote `origin` by pressing `Command` + `Shift` + `K` OR via the command `git push origin branch-type/branch-name`


## 5. Create a Pull Request

- By contributing your code, you agree to license your contribution under the terms of the
  [Apache License, Version 2.0](https://github.com/hivemq/hivemq-grafana-dashboards/blob/master/LICENSE)
- Go to your GitHub fork under https://github.com/yourgithubname/hivemq-grafana-dashboards.git
- Go to the <i>Pull Requests</i> tab
- Press the <i>New Pull Request</i> button
- Choose your branch and click <i>Create Pull Request</i>
- We will now try to review your submitted code as fast as possible
- To address requested changes during code review, simply push your changes to your remote as described above
- You may need to rebase your branch if you haven't worked on it for some time - To do so simply go to the <i>Git branch > upstream master > Rebase 'your-branch' onto 'upstream/master'</i> OR via the command `git rebase upstream/master` 
- As soon as the Code Reviewer has approved your Pull Request Merge your PR by clicking <i>Rebase and Merge</i>

## 🚀 Thank you for taking the time to contribute to hivemq-grafana-dashboards!  🚀

We truly appreciate and value your time and work. ❤️



