# Contributing to scripts

Thanks for taking the time to contribute! This project is a collection of small,
self-contained automation scripts, so contributions can be as simple as adding one
useful script or fixing a typo.

## Ways to contribute

- Report a bug or request a feature via [GitHub Issues](https://github.com/rahilsh/scripts/issues).
- Add a new script or improve an existing one.
- Improve documentation.

## Development setup

1. Fork the repository and clone your fork.
2. Create a branch for your change:
   ```bash
   git checkout -b feat/my-change
   ```
3. For Java changes, make sure the project builds and tests pass:
   ```bash
   mvn -B package
   ```

## Guidelines

- **Keep scripts self-contained.** Each script should be understandable and runnable
  on its own. Add a short comment header explaining what it does and how to run it.
- **No secrets.** Never commit real credentials, tokens, endpoints, or account IDs.
  Use placeholders such as `<account_id>` or read values from configuration.
- **Match the existing layout.** Put scripts under the appropriate `src/main/<lang>/<area>/`
  directory and update the "Scripts overview" table in the [README](README.md).
- **Java code style.** Target Java 21, keep classes small, and prefer standard library
  or existing dependencies over adding new ones.
- **Shell scripts.** Start with `#!/bin/bash`, `set -euo pipefail` where practical, and
  validate required arguments.

## Commit messages

Use clear, conventional-style messages where possible, for example:

```
feat: add script to rotate IAM access keys
fix: handle empty SQS response in dlq drainer
docs: document kafka topic script
chore: bump junit to 6.1.0
```

## Pull requests

1. Ensure your branch is up to date with `main`.
2. Confirm the build passes locally.
3. Open a pull request and fill in the template describing **what** changed and **why**.
4. Be responsive to review feedback.

By contributing, you agree that your contributions will be licensed under the
[Apache License 2.0](LICENSE), and that you follow the [Code of Conduct](CODE_OF_CONDUCT.md).
