# scripts

> A collection of useful scripts to automate everyday development and operations tasks.

[![Java CI with Maven](https://github.com/rahilsh/scripts/actions/workflows/maven.yml/badge.svg)](https://github.com/rahilsh/scripts/actions/workflows/maven.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/projects/jdk/21/)

This repository is a grab-bag of automation helpers written in **Java**, **Bash**, and **SQL**.
The Java parts are built with Maven and use Guice for dependency injection and Retrofit for HTTP;
the Bash and SQL scripts are standalone utilities for AWS, Kafka, Git, Kubernetes, and PostgreSQL.

## Table of contents

- [Prerequisites](#prerequisites)
- [Getting started](#getting-started)
- [Repository layout](#repository-layout)
- [Scripts overview](#scripts-overview)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [Security](#security)
- [License](#license)

## Prerequisites

Depending on which scripts you use:

- **Java 21+** and **Maven 3.9+** — for the Java scripts.
- **Bash** — for the shell scripts.
- **AWS CLI** and **jq** — for the SQS script.
- **kubectl** / a valid kubeconfig — for the Kubernetes script.
- **psql** (PostgreSQL client) and **pgloader** — for the SQL scripts.

## Getting started

Clone the repository and build the Java module:

```bash
git clone https://github.com/rahilsh/scripts.git
cd scripts
mvn -B package
```

Run one of the Java entry points, for example:

```bash
mvn -q exec:java -Dexec.mainClass=in.rsh.scripts.TestScript
```

> Most scripts are intended to be copied, tweaked for your environment, and run individually.
> Read the source before running anything against real infrastructure.

## Repository layout

```
src/main/
├── java/in/rsh/scripts/     # Maven/Guice based Java utilities
│   ├── Main.java            # Guice injector + Retrofit/config wiring
│   ├── TestScript.java      # Minimal sample entry point
│   └── kubernetes/          # Pod-watching / alerting helpers
├── bash/                    # Standalone shell scripts
│   ├── aws/                 # SQS DLQ tooling
│   ├── git/                 # git subtree helper
│   └── kafka/               # Kafka topic creation
├── sql/                     # PostgreSQL / pgloader helpers
│   ├── common/              # CSV → DB update helpers
│   ├── postgres/            # Queries, grants, upserts
│   └── pgloader/            # Migration load files
└── resources/config/        # local.properties (see Configuration)
```

## Scripts overview

| Script | Language | Description |
| ------ | -------- | ----------- |
| `TestScript` | Java | Minimal example of injecting a config property and printing it. |
| `kubernetes/K8sScript` | Java | Watches a pod's status and posts an alert to a webhook. |
| `bash/CheckIfSameFileExistInAnotherFolder.sh` | Bash | Lists files in one folder that are missing from another. |
| `bash/aws/sqs_save_dlq_messages_and_delete.sh` | Bash | Drains an SQS DLQ, logging messages before deleting them. |
| `bash/git/add_git_subtree.sh` | Bash | Adds a remote repository as a squashed git subtree. |
| `bash/kafka/createTopic.sh` | Bash | Creates a Kafka topic with a single command. |
| `sql/common/csv_to_db_query.sh` | Bash/SQL | Applies row-by-row JSON/amount updates from a CSV file. |
| `sql/postgres/*.sql` | SQL | Assorted PostgreSQL queries, grants, and conflict-aware copies. |
| `sql/pgloader/sakila.load` | pgloader | Example MySQL → PostgreSQL migration definition. |

## Configuration

The Java scripts read configuration from `src/main/resources/config/local.properties`
via [Apache Commons Configuration](https://commons.apache.org/proper/commons-configuration/),
and the values are bound as named Guice properties in `Main.java`.

> **Do not commit secrets.** Keep real endpoints, tokens, and credentials out of version control.

## Contributing

Contributions are welcome! Please read [CONTRIBUTING.md](CONTRIBUTING.md) and the
[Code of Conduct](CODE_OF_CONDUCT.md) before opening an issue or pull request.

## Security

Found a vulnerability? Please follow the process in [SECURITY.md](SECURITY.md) instead of
opening a public issue.

## License

Distributed under the [Apache License 2.0](LICENSE).
