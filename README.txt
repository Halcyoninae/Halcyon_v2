====== Halcyon Music Player ======

A simplistic, robust music player,
with full feature transparency.

==================================

This repository contains the
source code for the Halcyon Music
Player.

Copyright (C):
    Jack Meng 
    2021
    <jackmeng0814@gmail.com>

Licensed under the following:

1. Source Code: Vendor
2. EULA & Binaries: VENDOR

(See section "Legals" for more
info).

----------------------------------

[?] Table of Contents
>
    Version Info
    Basic Description
    Extern.
    Legals
    Q&A
    Building
    Release & Download
    Package Layout
    MISC
    Sys. Requirements
<
----------------------------------

[!] Version Info

Note: Check /root/VERSION for the
absolute version info history.

Current Version: 3.4.1
Current Iteration: 3.0
Current Build Meta: Beta

Version Logic:
  > For each +1 in current version
  the iteration will go up by 0.1

----------------------------------

[!] Basic Description

Halcyon is a music player designed
with the following aspects in
mind:
  1. Simplicity for the user
  2. Absolute Optimizations
  3. Maximum User Control of Flow
  4. Easy API (if to be used as a
     library)
  5. A no cluster user interface

This is technically a third
iteration of an original project
titled MP4J. I ended the original
because of the sheer complexity
and noodle code that I wrote.

Currently the program supports:
  1. MP3
  2. WAV
  3. AIFF & AIFC* (partially tested)
  4. OGG (Vorbis)
  5. Theora
  6. Flac
  7. AU
  8. MP2 & MP1 Layers
Hopefully to support:
  1. AAC
  2. M4A
  3. WMA
  4. RAW* (Configurable by user)
  5. DSD
  6. APE
  7. ACC
  8. MID & SNG
  9. WAVPACK
  10. TTA
  11. MPC

Memory usage usually hovers at
around 30-50 mB of memory usage
no matter the file being played
back. This is according to Java
Mission Control, which may differ
from the actual committed Java
Heap which is allocated to the
individual Java Processes.

The program will allow for
multiple audio API integrations.
By default, the program must
support the standard Java Sound
abstraction (Tailwind) along
with the Tritonus Open Java
Sound API. The JavaFX media
platform shall only be required
if the "com.sun.media" package
is present.

Along with everything else, the
program features its own image
manipulation library, filesystem
API, and custom GUI components
that can be used.

----------------------------------

[!] Extern.

The term extern here is to denote
"external"; or anything foreign.

The minimization of third party
libraries is a must so that users
can have as much of the original
code as possible without
secondary licenses.

The only main reason things like
a Discord RPC and the FlatLaf Laf
are bundled with the program is
because they do not interfere
with audio playback. This is
unlike using someone else's audio
library for audio playback.

Currently there are a few 3rd
party libraries that I will try
and minimize the usage of and
fully find alternatives and adopt
them.

----------------------------------

[!] Legals

Legals refer to licensing of this
software and all other third
party libraries and software that
are bundled with it.

This program's source code is
provided under: Standard GPL2.0
(WITHOUT CLASSPATH EXCEPTION)

However, if you are to distribute
any pre-built binaries of any
kind are subject to the standard:
VENDOR_LICENSE.
    > See File: VENDOR_LICENSE

The software's own license can
be viewed from the file:
    1. LICENSE.md

Licenses for any third parties:
    1. LICENSE.txt

Standard Vendor Licensing:
    1. VENDOR_LICENSE

----------------------------------

[!] Q&A

  1. Why Java?
    > This was more of a first
    hand experience with Java,
    before I started learning it
    in school (previous MP4J prj).
    > It also challenged me for
    stronger methods of
    optimization methods, instead
    of using something like
    Object Pascal, C#, C/C++, or
    JavaScript/TypeScript.
    > Although I knew how to
    create apps and audio
    playback in C & C++, it
    felt like bigger fun to do it
    in Java ;)

  2. Why not JavaFX?
    > In the section "Extern.", I
    clearly state that I want to
    minimize the usage of third
    party libraries for as much
    as possible.
    > JavaFX would not improve
    any form of making this
    program faster. It only
    simplifies the process of
    creating graphical items
    using HTML/CSS.

----------------------------------

[!] Building

Note: Binary released built by the
original author is licensed under
the default Yttrium License.

Pre-req:
  This program was tested on
  Window10+11 & Linux (515) with
  zero to minimal faults.

Default testing kit:
  > JavaJDK -> 11+
  > JavaJRE -> 11+
  > JavaFX -> 12+

The code struct tree:
(src/com/jackmeng/halcyon/...)
  src -> Source Root
    com
      jackmeng/halcyoninae
        halcyon
          Halcyon.java -> Main()

----------------------------------

[!] Release & Download

Releases are provided under the
releases tab in this repository.

All releases & provided downloads
are provided by the following
official vendor(s):
    1. Jack Meng
    2. Halcyoninae
    3. Yttrium Terminus

NOTE: Any downloads and releases
under any circumstances, whether
it just be reuploaded are not
considered official vendors.
Official releases are only
provided and signed by the above
vendors.
Use extern. vendors with caution

All builds are designed to be an
archive file which will each
contain their respective usage
README (based on their version).

    Release Exec Types:
        1. General Archive Bundle
            -ZIP
            -Tarball
        2. Specifc Platform PKG
            -RPM
            -MSI
            -EXE
        3. Specific Platform Exec.
    Dev. Release Exec Types:
        1. General Archive Bundle
            -Tarball
            -Contains archives:
                -Documetnation
                -Raw Executables
                -Sampling Piece
            -Malimbica API

The dev release exec provides
the end-user with easier control
over the specific internals
of the program and are not made
for the average end-user.

NOTE: Downloading a FAT-JAR can
have extended setup requirements,
as there might be specific
platform dependent pkgs that are
required.

----------------------------------

[!] Package Layout

Source Root Locale: /src/
Source Tree:
  com/
    halcyoninae/
      halcyon/...
      cloudspin/...
      tailwind/...
      cosmos/...
      setup/...

Halcyon -> Default Running Package
  - Features the default backend
    utils for the program
CloudSpin -> Graphics Manipulation
  - Features default libraries
    related to graphics, like
    image manipulation.
Cosmos -> GUI Components
  - The first half of the program,
    which is the GUI user
    interface.
Tailwind -> Audio Processors
  - Features the audio playback
    library. This is the second
    half of the program.
Setup -> General runner that is
called to perform a setup routine
  - First that runs to make sure
    every required pkg is present

----------------------------------

[!] MISC

You can view the JavaDocs here:
halcyoninae.github.io/halcyon

You can read more about submitting
a FEATURE request or a bug report,
here:
/root/requests/README

Note: This is WIP

----------------------------------

[!] Sys. Requirements

Note: This is not final.
-> The JVM memory differs from the
actual memory b/c it allocates a
certain amount to itself. Check
with your RE vendor with specifics
  JVM:
    Check with your vendor
    Minimum-Version: 9
  Sys.:
    Minimum-Program-Memory: N/A
    CPU: x86 or x64 no ARM
    GPU: Any w/ OpenGL
    OS: Win32 & *NIX

==================================
      Made by Jack Meng :)
==================================
