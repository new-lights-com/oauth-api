## Prerequisites

Before working with this blueprint, make sure the following prerequisites have been met.

### Java 11

Java install is a bit tricky and requires Cask to be added to brew to install specific versions (at the time of writing
this).

```bash
brew tap homebrew/cask-versions
brew update
brew tap caskroom/cask
brew cask install adoptopenjdk11
```

### Gradle

```bash
brew install gradle
```

## Getting Started

Start by installing the dependencies

```bash
./gradlew build
```

Afterwards you can run the project with:

```bash
./gradlew bootRun
```
