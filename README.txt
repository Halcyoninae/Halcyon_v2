====== Halcyon Music Player ======

A simplistic robust music player
written in Java.

----------------------------------

This repository contains the 
source code for the Halcyon Music 
Player.

Copyright(C)Jack Meng 2023

Licensed under the following:

1. Source Code: GPL-2.0
2. EULA & Binaries: Yttrium

----------------------------------

[!] Version Info

Note: Check /root/VERSION for the
absolute version info history.

Current Version: 3.2
Current Iteration: 3.0

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
  3. Maximum User Control
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
Hopefully to support:
  1. OGG (Vorbis)
  2. AIFF
  3. FLAC
  4. MP4*
  5. AU
  6. AVI*

* - Video formats that will have
their audio track separated.

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

The code struct tree:
(src/com/jackmeng/halcyon/...)
  src -> Source Root
    com
      jackmeng
        halcyon
          Halcyon.java -> Main()

----------------------------------

[!] Package Layout

Source Root Locale: /src/
Source Tree:
  com/
    jackmeng/
      halcyon/...
      cloudspin/...
      tailwind/...
      cosmos/...

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

----------------------------------
                                  
[!] MISC                          
                                  
You can view the JavaDocs here:   
yttrium-terminus.github.io/halcyon

You can read more about submitting
a FEATURE request or a bug report,
here:
/root/External/README

Note: This is WIP

----------------------------------
      Made by Jack Meng :)    
==================================
