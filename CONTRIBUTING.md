# Contributing to JBoss DMR

Welcome to the JBoss DMR project! We welcome contributions from the community. This guide will walk you through the steps for getting started on our project.

- [Legal](#legal)
- [Forking the Project](#forking-the-project)
- [Issues](#issues)
  - [Good First Issues](#good-first-issues)
- [Setting up your Developer Environment](#setting-up-your-developer-environment)
- [Contributing Guidelines](#contributing-guidelines)
- [Community](#community)

## Legal

All contributions to this repository are licensed under the [Apache License](https://www.apache.org/licenses/LICENSE-2.0), version 2.0 or later, or, if another license is specified as governing the file or directory being modified, such other license.

All contributions are subject to the [Developer Certificate of Origin (DCO)](https://developercertificate.org/).
The DCO text is also included verbatim in the [dco.txt](dco.txt) file in the root directory of the repository.

### Compliance with Laws and Regulations

All contributions must comply with applicable laws and regulations, including U.S. export control and sanctions restrictions.
For background, see the Linux Foundation’s guidance:
[Navigating Global Regulations and Open Source: US OFAC Sanctions](https://www.linuxfoundation.org/blog/navigating-global-regulations-and-open-source-us-ofac-sanctions).


## Forking the Project
To contribute, you will first need to fork the [jboss-dmr](https://github.com/wildfly/jboss-dmr) repository.

This can be done by looking in the top-right corner of the repository page and clicking "Fork".

The next step is to clone your newly forked repository onto your local workspace. This can be done by going to your newly forked repository, which should be at `https://github.com/USERNAME/jboss-dmr`.

Then, there will be a green button that says "Code". Click on that and copy the URL.

Then, in your terminal, paste the following command:
```bash
git clone [URL]
```
Be sure to replace [URL] with the URL that you copied.

Now you have the repository on your computer!

## Issues

The JBoss DMR project uses JIRA to manage issues. All issues can be found [here](https://issues.redhat.com/projects/DMR/issues).

To create a new issue, comment on an existing issue, or assign an issue to yourself, you'll need to first [create a JIRA account](https://issues.redhat.com/).

Once you have selected an issue you'd like to work on, make sure it's not already assigned to someone else.

Before beginning work on an issue, it's good to start a thread in the [wildfly-developers channel in Zulip](https://wildfly.zulipchat.com/#narrow/channel/174184-wildfly-developers) to let others know what you'll be doing.



### Good First Issues
Want to contribute to the JBoss DMR project but aren't quite sure where to start? Look for issues with the `good-first-issue` label. These are a triaged set of issues that are great for getting started on our project. These can be found [here](https://issues.redhat.com/issues/?jql=project%20%3D%20DMR%20and%20labels%3Dgood-first-issue%20ORDER%20BY%20key%20DESC).

## Setting up your Developer Environment
You will need:

* JDK
* Git
* Maven
* An [IDE](https://en.wikipedia.org/wiki/Comparison_of_integrated_development_environments#Java)
  (e.g., [IntelliJ IDEA](https://www.jetbrains.com/idea/download/), [Eclipse](https://www.eclipse.org/downloads/), etc.)


First `cd` to the directory where you cloned the project (eg: `cd jboss-dmr`)

Add a remote ref to upstream, for pulling future updates.
For example:

```
git remote add upstream https://github.com/wildfly/jboss-dmr
```

It is recommended that you use a separate branch for every issue you work on. To keep things straightforward and memorable, you can name each branch using the JIRA issue number. This way, you can have multiple PRs open for different issues.
```bash
git checkout -b DMR-9999
```

To build `jboss-dmr` run:
```bash
mvn clean install
```

To skip the tests, use:

```bash
mvn clean install -DskipTests=true
```

To run only a specific test, use:

```bash
mvn clean install -Dtest=TestClassName
```

## Contributing Guidelines

When submitting a PR, please keep the following guidelines in mind:

1. In general, it's good practice to squash all of your commits into a single commit. For larger changes, it's ok to have multiple meaningful commits. If you need help with squashing your commits, feel free to ask us how to do this on your pull request. We're more than happy to help!

  1. Please include the JIRA issue you worked on in the title of your pull request and in your commit message. For example, for [DMR-9999](https://issues.redhat.com/browse/DMR-9999), the PR title and commit message should be `[DMR-9999] Create a testcase which verifies behaviour of the foo`.

2. Please include the link to the JIRA issue you worked on in the description of the pull request. For example, if your PR adds a fix for [DMR-9999](https://issues.redhat.com/browse/DMR-9999), the PR description should contain a link to https://issues.redhat.com/browse/DMR-9999.
