# Eclipse Origins 4: Solar Eclipse

> Welcome to _**Eclipse Origins 4**_, a love letter to Visual Basic 2D ORPG Engines. 

&emsp; This build, nicknamed _Solar Eclipse_, is version `0.2.3.1-alpha`.


## Table of Contents
- [Requirements](#requirements)
- [Sub-projects](#sub-projects)
  - [Core](#core)
  - [Server](#server)
  - [Editor](#editor)
  - [Client](#client)
- [Building](#building)
  - [Clean and Build All](#clean-and-build-all)
  - [Clean and Build Core](#clean-and-build-core)
  - [Clean and Build Server](#clean-and-build-server)
  - [Clean and Build Editor](#clean-and-build-editor)
  - [Clean and Build Client](#clean-and-build-client)
  - [Running Server](#running-server)
  - [Running Editor](#running-editor)
  - [Running Client](#running-client)
- [Execution](#execution)
- [Project Overview](#project-overview)
- [Errata](#errata)


## Requirements
- **Written in Java 17**
- * OpenJDK "Temurin" version 21-0.3+9 (Eclipse Adoptium JRE)
- **Built with:** Gradle 8.5 (Groovy configuration)

Currently only tested on Windows 10 & 11


## Sub-projects

### Core
The `core` sub-project includes essential libraries and classes shared across the other sub-projects.

| **Dependencies**:
- [slf4j 2.0.13](https://www.slf4j.org/)
- * [log4j 2.23.1](https://logging.apache.org/log4j/2.x/)
- [junit-jupiter 5.10.0](https://junit.org/junit5/)

### Server
The `server` sub-project handles the back-end operations of the game.

| **Dependencies**:
- `:core`

### Editor
The `editor` sub-project provides tools for developers to create and manage game content.

| **Dependencies**:
- `:core`

### Client
The `client` sub-project is the game front-end.  

| **Dependencies**:
- `:core`
- [lwjgl 3.3.3](https://www.lwjgl.org/)
- * [joml 1.10.5](https://joml-ci.github.io/JOML/)
- * [imgui-java 1.86.11](https://github.com/SpaiR/imgui-java)


## Building

### Clean and Build All
To clean & build all sub-projects, execute the following:
```sh
./gradlew clean 
./gradlew build
```

### Clean and Build Core
You must build, preferably in a clean environment, the `core` sub-project before building `server`, `editor`, or `client`.

To clean & build only the `core` sub-project, execute the following:
```sh
./gradlew :core:clean 
./gradlew :core:build
```

### Clean and Build Server
To clean & build only the `server` sub-project, execute the following:
```sh
./gradlew :server:clean 
./gradlew :server:build
```

### Clean and Build Editor
To clean & build only the `editor` sub-project, execute the following:
```sh
./gradlew :editor:clean 
./gradlew :editor:build
```

### Clean and Build Client
To clean & build only the `client` sub-project, execute the following:
```sh
./gradlew :client:clean 
./gradlew :client:build
```

### Running Server
You must run the `server` sub-project before `editor` or `client`.

To run the `server` sub-project, execute the following:
```sh
./gradlew :server:run
```

### Running Editor
To run the `editor` sub-project, execute the following:
```sh
./gradlew :server:run
```

### Running Client
To run the `client` sub-project, execute the following:
```sh
./gradlew :client:run
```


## Execution
| _TODO_


## Project Overview
The project's packages and modules exist within the `dev.atomixsoft.solar_eclipse` namespace.  The only module is `core` - `server`, `editor`, and `client` sub-projects are comprised of a series of packages.

| **Module**      | **Package**     | **Description** |
| ---------------------------------------- | ---------------------------------------- | -------------------------------------------------------------------------------- |
| _Core_ | _Game_ | _TODO_ |
| _Core_ | _Utils_ | _TODO_ |
| _Core_ | _Network_ | _TODO_ |
| _N/A_ | _Server_ | _TODO_ |
| _N/A_ | _Editor_ | _TODO_ |
| _N/A_ | _Client_ | _TODO_ |
| _N/A_ | _ClientAudio_ | _TODO_ |
| _N/A_ | _ClientGraphics_ | _TODO_ |
| _N/A_ | _ClientScene_ | _TODO_ |
| _N/A_ | _ClientUtil_ | _TODO_ |


## Errata
For a little background, take a look at these links:
- [Eclipse - Free MMORPG Maker - Index](https://web.archive.org/web/20110901224553/http://www.touchofdeathforums.com/smf/index.php)
- [Forums - Eclipse Origins](https://forum.eclipseorigins.com/)

Of course, make sure you also visit us at [[atomixsoft.dev]](https://atomixsoft.dev/) !

> We hope you find **Eclipse Origins 4: Solar Eclipse** a very clean, somewhat nostalgic, highly performant, and infinitely flexible engine.  For any issues or contributions, please refer to our [issue tracker](#) or [contributing guide](#).

_Enjoy!_
