# Halcyon (MP4J 3.0)

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/09115c9807c64bfbb92e6bc4bc71c48b)](https://www.codacy.com/gh/exoad/halcyon/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=exoad/halcyon&amp;utm_campaign=Badge_Grade) [![Codacy Security Scan](https://github.com/exoad/halcyon/actions/workflows/codacy.yml/badge.svg)](https://github.com/exoad/halcyon/actions/workflows/codacy.yml) [![DevSkim](https://github.com/exoad/halcyon/actions/workflows/devskim.yml/badge.svg)](https://github.com/exoad/halcyon/actions/workflows/devskim.yml) [![Codespaces Prebuilds](https://github.com/exoad/halcyon/actions/workflows/codespaces/create_codespaces_prebuilds/badge.svg)](https://github.com/exoad/halcyon/actions/workflows/codespaces/create_codespaces_prebuilds)

[![Download Halcyon](https://a.fsdn.com/con/app/sf-download-button)](https://sourceforge.net/projects/halcyon4j/files/latest/download)

*Copyright (C) 2021-2022 Halcyon (MP4J) to Jack Meng*

> This is rework of an original project by me [MP4J](https://github.com/Exoad4JVM/mp4j)

This is a music player written in Java with a nice GUI to facilitate your music
playing needs.

> Notice: This is not a guide on how to use the program, instead just a simple README for the main repository. A user-guide markdown document will be created later.

### Version Info
This README is updated for the version: 3.1 [For the program]
This README's constant version is 3.0 [For the iteration]

This program currently has a lot of features yet to be implemented. And with these new additions also bring a lot of new bugs that needs
fixing!

## Installation

Currently there are no direct pre-compiled binary and executables that I have made
for you to simply download and run. However, you can download this project and run it from source.

It is recommended to have Java 8 or above in order to run this program either from source or 
from a pre-compiled binary. Note: Tag for forcing optimizations upon the program may or may not work for
different runtimes, but for my own side I used Corretto-16-JDK, if you feel like it is not doing the correct thing, you may disable this feature.

**Main Class:** `Halcyon` [here](src/com/jackmeng/halcyon/Halcyon.java)<br>
**Linker Manifest:** `MANIFEST.MF` [here](src/META-INF/MANIFEST.MF)

## Audio Framework

This program uses a heavily modified version of [this](https://github.com/RalleYTN/SimpleAudio) framework with my own
implementations to improve performance and fit with the needs of this program. If you are going to use the framework,
you should not use the JAR from this repository. Instead, use it from here: https://github.com/exoad/SimpleAudio/releases/tag/1.0ex as there might be broken implementations that might not work from this project's inclusion of the framework.

This engine currently enables playing wav & mp3. However, soon it will allow for other types (check [features](./docs/FEATURES.txt/)

## Current

Currently this program is still in development and is not yet ready for consumer usage,
with yet some missing features and bugs. (Check out [BUGS](docs/BUGS.txt) & [FEATURES](docs/FEATURES.txt)).

However, improvements upon it's backend audio framework will be needed in order to introduce newer features to the program.

## Contributing

The current state of the repository will not enable the usage of the ISSUEs tab. To contribute, like suggest a feature or report a bug, you must edit the [BUGS](docs/BUGS.txt) OR [FEATURES](docs/FEATURES.txt) files via a pull request. I will then review it from there.

Why you may ask? It is because adding this PR only feature allows me to better look at the issues on my side without having to pull up GitHub or another
Git service every time I want to look at issues.

## Legals

This program uses external libraries and framework, and their attributed licenses
can be found [here](LICENSE.txt).

## Screenshots
*Note these are not the latest screenshots for the current main branch.*<br>
<img src="docs/unknown.png" alt="sc1" width="35%" height="35%" />
<img src="docs/unknown2.png" alt="sc2" width="35%" height="35%" />
