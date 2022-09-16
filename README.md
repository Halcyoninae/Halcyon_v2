# <strong>Halcyon</strong> : <br/> <em>A Simplistic & Robust Audio Player</em>
> <em>Copyright (C) Jack Meng 2021</em>
<hr>

![](https://img.shields.io/static/v1?label=Halcyon&message=b3.4.1&color=89EDA4&labelColor=21252B) 

<!--
    LICENSED UNDER VENDOR LICENSE
    SEE ./LICENSE

    Notice: This README is entirely
    WORK IN PROGRESS and subject
    to change at any time.

    COPYRIGHT © Jack Meng 2021
-->
<a href="https://halcyoninae.github.io/.github/">
<img src="https://raw.githubusercontent.com/Halcyoninae/.github/main/assets/img/3.4_1_2.png" alt="Snapshot from the GUI wrapper for the internal Audio API"
    width="369" align="right" />
</a>

### <strong><u>Description</u></strong>
> Halcyon is designed to be a lightweight
> and efficient native audio player that
> fits the needs of anyone.
- Features an intuitive API with inbuilt [localization](repo/text/localization.md)
- Efficient graphics pipelines to boost the nature of [Java's Swing Framework](https://jogamp.org/deployment/jogamp-next/javadoc/jogl/javadoc/overview-summary.html)
- Scalable plugin interfaces for [quick plugin development](repo/text/plugin_interfacing.md)

### <strong><u>Features</u></strong>
- Multiple [audio pipelines](repo/text/audio_lineage.md) to choose from and used to play audio efficiently
- Many supported audio formats (and more to be supported)
- Inbuilt Swing extendable (BlurredUILayer, Scalable Frames, etc.) that you can use in your own programs
- OpenGL backing for Swing Frameworks, enabling better performance and better flow control over the GUI wrapper.
- Lightweight image manipulation library (Cosmos / DeImage)

### <strong><u>Supported Formats</u></strong>
<strong>Currently Supporting</strong>

- MP3 (.mp3)
- WAVE (.wav)
- AIFF (.aiff)
- Vorbis OGG (.ogg, .oga)
- FLAC (.flac)
- AU (.au)
- MP2 & MP1 Layers (.mp2)
- Opus (.opus, .oga)

<details close>
<summary><strong>Soon To Support</strong></summary>

- AAC
- M4A
- WMA
- RAW
- DSD
- APE
- ACC
- MIDI
- SNG
- ACC
- WAVPACK
- TTA
- MPC
</details>
<hr>

## <strong>Why Halcyon?</strong>
<u>GUI Wrapper</u>

Halcyon's GUI program uses both Java Swing & OpenGL to display graphics on screen. However, for the majority, a <code>javax.swing</code> component will be used, with OpenGL used for
much more intensive graphical tasks.

There are many specific adaptations used in the program to get it up to par with much more mainstream GUI frameworks (like HTML/CSS). For example, the GUI blurring is all done via a layer on top of the image and not directly applied to the image, thus saving resources.

Image Manipulation is done via the internal lightweight library Cosmos. It serves as a way to display images to fit the needs of the GUI display.

Note: The internal Swing Components that are created and implemented are not designed to be used outside this program (also known as [localization](repo/text/localization.md)) and can cause unexpected results in your own programs if you are to use this as a library of sorts.

<u>Sound API</u>

The Java Sound API is pretty lackluster, and many features surrounding its usage are less user-friendly. Therefore, there are many adaptations and separate implementations by third parties. For example, JavaFX adopted its [MediaPlayer](https://docs.oracle.com/javafx/2/api/javafx/scene/media/MediaPlayer.html) via Java Sound (a wrapper of sorts). Then you also have a feature rich low level implementation of Java Sound, called [Tritonus Sound](https://www.tritonus.org/). However, it might seem correct to go with JavaFX's premade high-level abstraction, but it offers far less control over the ACTUAL audio data being streamed. Thus, we come to Halcyon's Tailwind Audio API (not to be confused with [TailwindCSS](https://tailwindcss.com/)). Tailwind offers a transition and a bridge between these frameworks and adaptations (if available) in a user-friendly and lightweight format. In offers high level functionalities such as just simple <code>play(file)</code> & <code>pause()</code> functions, but also offering much higher level functions such as <code>normalize(bytes[])</code> <code>read(bytes[])</code> (see the documentation for more).

Halcyon offers a way for users to not have to worry about convoluted low level implementations, meanwhile providing an equal balance between high & low level.

Currently, the Tailwind Framework can work with the following pipelines:

- Java Sound API (Standard Tailwind)
- Tritonus Sound
- JavaFX MediaPlayer (if available)
- OpenAL (via Jogamp & JOAL)

<hr>

## <strong>Packaging</strong>

This program is provided under [this license](LICENSE.txt).

<u>Package Layout</u>

Standard Layout:
<code>
src/
     com/
          jackmeng/
</code>
- You will find the designated packages regarding the Halcyon project in the dir: <em>halcyoninae</em>

<u>Release Style</u>

Releases will be pushed out in the following formats:

1. **Master Archive**
   - Contains all of the necessary components to almost run crossplatform native libraries altogether in a big archive file
   - Big on size and contains files you may not need on your specific hardware & operating system.
   - Contains a copy of the JavaDocs for this project
   - A sub version of this style is a separate archive per architecture and operating system
2. **Native Binary**
   - An archive file that has its main entrypoint compiled as a native image that is compatible with your operating system and hardware (x86 & x86_64)
   - Contains static & dynamic libraries that are linked during compile time
   - Does not contain a copy of the JavaDocs
   - Any runtime components are provided on demand
   - This packaging style can sometimes not be present in a release
3. **Native Bundle**
   - An archive file that contains a native executable that points to a compiled JRE
   - Similar to a Native Binary
   - Usually bigger in size
   - Static Libraries & Dynamic Libraries linked on demand

<hr>

## <strong>Version Info</strong>
This project is the 3rd iteration of a much older project, which you can find [here](https://github.com/Halcyoninae/mp4j-old), so technical version number starts from **3** (rolling iteration). This number will increase by 1 (+1) per every 1 whole iteration in the rolling release number.

<u>Rolling Release:</u> 3.4.1<br>
<u>Rolling Iteration:</u> 3.0<br>
<u>Version Meta:</u> Beta<br>

View Version History [here](VERSION.txt)

<hr>

## <strong>System Requirements</strong>

This is a lightweight program, but there are some things that you need in order to have this program running smoothly. Usual memory usage hovers around 20-30 mB on any load.

**Runtime Requirements**

Entirely based on your provider.

A Native Bundle provides a runtime image based on your specified architecture

- Minimum version: 11
- Minimum Heap: 25 mB

**Client Requirements**

- Proper Sound Card
- CPU: Any that can run Java
- GPU: Any with OpenGL support
- Minimum Memory: 20 mB
- Maximum Memory: 300 mB
- Minimum Screen Resolution: None

<hr>

## <strong>Related Resources</strong>

**JavaDocs Documentation** | [Click Here](https://halcyoninae.github.io/halcyon-docs/)

**Project Website** | [Click Here](https://halcyoninae.github.io/.github/)

![](https://img.shields.io/github/repo-size/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/languages/code-size/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/stars/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/forks/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/search/Halcyoninae/Halcyon/goto?style=flat-square)
![](https://img.shields.io/github/languages/top/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/discord/851999446057222144?color=%20%09%237289da&label=discord&style=flat-square)
![](https://img.shields.io/github/languages/count/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/downloads/Halcyoninae/Halcyon/total?label=All%20Release%20Downloads&style=flat-square)
![](https://img.shields.io/github/v/tag/Halcyoninae/Halcyon?label=Latest%20Tag&style=flat-square)
![](https://img.shields.io/github/v/release/Halcyoninae/Halcyon?include_prereleases&label=Latest%20Release&style=flat-square)
![](https://img.shields.io/github/commit-activity/w/Halcyoninae/Halcyon?label=weekly%20activity&style=flat-square)
![](https://img.shields.io/github/last-commit/Halcyoninae/Halcyon?style=flat-square)
![](https://img.shields.io/github/commits-since/Halcyoninae/Halcyon/latest?label=last%20release&style=flat-square)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/1673939abd204e86a0629c73b2e917ff)](https://www.codacy.com/gh/exoad/Halcyon/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=exoad/Halcyon&amp;utm_campaign=Badge_Grade)
</center>

<hr>

> Everything is a work in progress :)

<hr>
