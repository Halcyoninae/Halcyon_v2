# <strong>Halcyon</strong> : <br/> <em>Simple & Robust Audio Player</em>
> <em>Copyright (C) Jack Meng 2021</em>
<hr>

![](https://img.shields.io/static/v1?label=Halcyon&message=b3.4.1&color=89EDA4&labelColor=21252B&logo=data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+CjxzdmcKICAgaWQ9InN2ZyIKICAgdmVyc2lvbj0iMS4xIgogICB3aWR0aD0iMzIiCiAgIGhlaWdodD0iMzIiCiAgIHZpZXdCb3g9IjAgMCAzMiAzMiIKICAgc29kaXBvZGk6ZG9jbmFtZT0iaW1hZ2UydmVjdG9yLnN2ZyIKICAgaW5rc2NhcGU6dmVyc2lvbj0iMS4yIChkYzJhZWRhLCAyMDIyLTA1LTE1KSIKICAgeG1sbnM6aW5rc2NhcGU9Imh0dHA6Ly93d3cuaW5rc2NhcGUub3JnL25hbWVzcGFjZXMvaW5rc2NhcGUiCiAgIHhtbG5zOnNvZGlwb2RpPSJodHRwOi8vc29kaXBvZGkuc291cmNlZm9yZ2UubmV0L0RURC9zb2RpcG9kaS0wLmR0ZCIKICAgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIgogICB4bWxuczpzdmc9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KICA8ZGVmcwogICAgIGlkPSJkZWZzOCIgLz4KICA8c29kaXBvZGk6bmFtZWR2aWV3CiAgICAgaWQ9Im5hbWVkdmlldzYiCiAgICAgcGFnZWNvbG9yPSIjNTA1MDUwIgogICAgIGJvcmRlcmNvbG9yPSIjZmZmZmZmIgogICAgIGJvcmRlcm9wYWNpdHk9IjEiCiAgICAgaW5rc2NhcGU6c2hvd3BhZ2VzaGFkb3c9IjAiCiAgICAgaW5rc2NhcGU6cGFnZW9wYWNpdHk9IjAiCiAgICAgaW5rc2NhcGU6cGFnZWNoZWNrZXJib2FyZD0iMSIKICAgICBpbmtzY2FwZTpkZXNrY29sb3I9IiM1MDUwNTAiCiAgICAgc2hvd2dyaWQ9ImZhbHNlIgogICAgIGlua3NjYXBlOnpvb209IjEyLjcyNzkyMiIKICAgICBpbmtzY2FwZTpjeD0iMTkuMDEzMzE2IgogICAgIGlua3NjYXBlOmN5PSIxOS4wMTMzMTYiCiAgICAgaW5rc2NhcGU6Y3VycmVudC1sYXllcj0ic3ZnIiAvPgogIDxnCiAgICAgaWQ9InN2Z2ciCiAgICAgdHJhbnNmb3JtPSJzY2FsZSgwLjA4MDA1NjQxKSI+CiAgICA8cGF0aAogICAgICAgaWQ9InBhdGgwIgogICAgICAgZD0ibSAzOS44LDY2Ljg2MiBjIC00LjE5NCwyLjI5NCAtMS44MjksNy4yNzMgNi40LDEzLjQ3MiAxMC42MTMsNy45OTQgMTIuMDQyLDEwLjIyNSAxMC44MTYsMTYuODk1IC0xLjE2Niw2LjM0NCAtMC4wNzEsOC4wOTcgMTAuMzg4LDE2LjYzMiA4LjQ2Myw2LjkwNiAxMC42MTksOS45OTggMTEuMzk2LDE2LjMzOSAwLjgwOCw2LjU5NiAxLjU5NSw4LjE4OCA3LjU0LDE1LjI2MyA2LjkwNCw4LjIxNSAxOC4zNDksMjMuMjg4IDE5LjY2NywyNS45MDEgMS4zODcsMi43NDkgMS40NzksNC4yODYgMC41MjQsOC43MzMgLTEuNTAzLDYuOTk0IDUuMDIzLDIxLjU2OSAxNS40MDYsMzQuNDA2IDQuOTExLDYuMDczIDUuMDI5LDYuMzY0IDUuMjc3LDEzLjA5NyAwLjMxMyw4LjQ5NiAzLjUxNywxMy4zNzEgMTMuMzQ0LDIwLjMgMTUuODg3LDExLjIwMyAyMC41Niw3LjI5NSAyOC4zNzQsLTIzLjcyNyA1Ljk4MiwtMjMuNzQ5IDUuOTQ0LC0yOC4zOTYgLTAuMzg1LC00Ny4zNzMgLTQuOTY2LC0xNC44OSAtMTQuMTc4LC0yOS4yMjkgLTI1LjY0NywtMzkuOTIzIC0yLjU4NSwtMi40MSAtNy4yNzUsLTYuNzg4IC0xMC40MjIsLTkuNzI5IEMgMTIwLjM1OCwxMTUuODE5IDEwOC43NzksMTA2LjcyMiA5Ny45MzEsMTAwLjAwMyA5NC41NTksOTcuOTE0IDg2Ljk0LDkyLjk4MiA4MSw4OS4wNDMgNTAuODMsNjkuMDM1IDQzLjI2OSw2NC45NjQgMzkuOCw2Ni44NjIgbSAzMDcuMTQ2LDU0LjMyOSBjIC0yLjc3OCwwLjY0IC00LjQ4OCwyLjY0MiAtOC4zMDYsOS43MjEgLTQuMTMsNy42NTUgLTE2LjA0MywyMC41MDMgLTI2LjU0NCwyOC42MjcgLTI0LjEwOCwxOC42NSAtMzguMTA0LDM1LjczMyAtNDYuNDIzLDU2LjY2MSAtNC4wMzIsMTAuMTQyIC00LjYyNCwxMi4zMyAtNi42NjEsMjQuNiAtMy4zMzUsMjAuMDk4IC01Ljc2MSwyMS42ODkgLTI2LjgxMiwxNy41ODUgLTMuMywtMC42NDQgLTguNywtMS40NDYgLTEyLC0xLjc4NCAtMTYuMjExLC0xLjY1OCAtMTYuOTA1LC0zLjU0OCAtNy4xMjQsLTE5LjQwMSAxNS43NzIsLTI1LjU2MyAxOC4zOTksLTQxLjc0OCAxMC4wNzksLTYyLjA5NSAtOC4yMDgsLTIwLjA3NCAtMTQuMjMyLC0yMy44NTggLTE2LjYxNywtMTAuNDM5IC0xLjM3Niw3Ljc0NCAtMi44NzYsMTIuOTA2IC01LjQ2NCwxOC44MDkgLTMuMjY4LDcuNDUyIC0zLjIyOSw3LjI1NyAtMy4yNTQsMTYuMTI1IC0wLjAyNCw5LjAwOSAtMC4xOCw5LjQ5MiAtNS40MTgsMTYuOCAtOC4xMTgsMTEuMzI4IC0xMi4yMzcsMjEuNTg4IC0xNS4yNTUsMzggLTIuMDE1LDEwLjk1OSAwLjM0NywyMS43MDggNy4xMTgsMzIuNCA3LjM0OSwxMS42MDQgMTMuMDc2LDIyLjcyNyAxNS41NTIsMzAuMiAzLjg1NiwxMS42NCA5LjAxMiwxNy4xNzYgMjAuMjg2LDIxLjc4MSAxMi42NzYsNS4xNzcgMjQuNDg0LDUuMjI3IDUwLjM2OCwwLjIxMiAxOC4yNTYsLTMuNTM3IDIzLjgxOCwtMy44ODUgMzEuNTI5LC0xLjk3MyA0LjM4NiwxLjA4NyA1LjExMywxLjE0NyAxNS42MzcsMS4yOTMgMTYuMDAyLDAuMjIgMTYuMzUyLDAuMDUzIDE5LjU1MywtOS4zMzEgMS4wNDcsLTMuMDcgMi40NjYsLTcuMTEyIDMuMTUyLC04Ljk4MiAzLjI1MywtOC44NjcgLTAuMDYsLTEyLjgzNiAtOS4wMzgsLTEwLjgyNyAtNi4xNjcsMS4zOCAtOS4wNTMsMC45IC0xNy45MDQsLTIuOTc5IC01LjU2MSwtMi40MzcgLTguOTY3LC0zLjc1NiAtMTYuMDc2LC02LjIyNSAtMTMuODU5LC00LjgxNSAtMjEuMzIxLC04LjUwNyAtMjIuMzk3LC0xMS4wODMgLTEuOTU1LC00LjY3OSA3LjksLTEyLjQ3NiAyNS4yOTksLTIwLjAxNSAxMy43MDYsLTUuOTQgMjAuNjY2LC0xMS43NjIgMjIuNzg5LC0xOS4wNjIgMC43MjYsLTIuNSAwLjY1OSwtNC4xODYgLTAuMzQ2LC04LjcxNSAtMC44MzcsLTMuNzY5IC0wLjQ1MiwtNC44MzMgMy4xODcsLTguODA2IDUuMjAzLC01LjY3OSA1Ljk5LC04Ljg3NiAzLjU5MSwtMTQuNTc2IC0xLjkwNywtNC41MzIgLTEuNDMyLC02LjYwNSAyLjc3OCwtMTIuMTEyIDQuNTgyLC01Ljk5NCA1LjM0NSwtOS44MDkgMy4yMzksLTE2LjIgLTIuMDM3LC02LjE4NSAtMS40MiwtOC4wNDggNS41NjcsLTE2LjggOS40NzUsLTExLjg3IDEzLjkwMiwtMjIuNzkgMTQuNDU5LC0zNS42NjcgMC40OCwtMTEuMTEzIC0yLjc1NywtMTcuMDc3IC04LjU0NCwtMTUuNzQyIgogICAgICAgc3Ryb2tlPSJub25lIgogICAgICAgZmlsbD0iIzdmZDk5NyIKICAgICAgIGZpbGwtcnVsZT0iZXZlbm9kZCIgLz4KICAgIDxwYXRoCiAgICAgICBpZD0icGF0aDEiCiAgICAgICBkPSJNIDEuNDgxLDEuNTE3IDAsMy4wMzQgViAyMDAuNDQyIDM5Ny44NSBsIDEuMTIyLDEuMDc1IDEuMTIyLDEuMDc1IGggMTk3LjU5OSAxOTcuNiBsIDEuMjc5LC0xLjMxOSAxLjI3OCwtMS4zMTkgViAxOTkuODAzIDIuMjQ0IEwgMzk4LjkyNSwxLjEyMiAzOTcuODUsMCBIIDIwMC40MDYgMi45NjIgTCAxLjQ4MSwxLjUxNyBtIDQzLjQzMiw2NS43MiBjIDQuOTYsMS42NjggMTMuNTI4LDYuODQ1IDM2LjA4NywyMS44MDYgNS45NCwzLjkzOSAxMy41NTksOC44NzEgMTYuOTMxLDEwLjk2IDEwLjg0OCw2LjcxOSAyMi40MjcsMTUuODE2IDM0LjU0NywyNy4xNDUgMy4xNDcsMi45NDEgNy44MzcsNy4zMTkgMTAuNDIyLDkuNzI5IDExLjQ2OSwxMC42OTQgMjAuNjgxLDI1LjAzMyAyNS42NDcsMzkuOTIzIDYuMzI5LDE4Ljk3NyA2LjM2NywyMy42MjQgMC4zODUsNDcuMzczIC03LjgxNCwzMS4wMjIgLTEyLjQ4NywzNC45MyAtMjguMzc0LDIzLjcyNyAtOS44MjcsLTYuOTI5IC0xMy4wMzEsLTExLjgwNCAtMTMuMzQ0LC0yMC4zIC0wLjI0OCwtNi43MzMgLTAuMzY2LC03LjAyNCAtNS4yNzcsLTEzLjA5NyAtMTAuMzgzLC0xMi44MzcgLTE2LjkwOSwtMjcuNDEyIC0xNS40MDYsLTM0LjQwNiAwLjk1NSwtNC40NDcgMC44NjMsLTUuOTg0IC0wLjUyNCwtOC43MzMgQyAxMDQuNjg5LDE2OC43NTEgOTMuMjQ0LDE1My42NzggODYuMzQsMTQ1LjQ2MyA4MC4zOTUsMTM4LjM4OCA3OS42MDgsMTM2Ljc5NiA3OC44LDEzMC4yIDc4LjAyMywxMjMuODU5IDc1Ljg2NywxMjAuNzY3IDY3LjQwNCwxMTMuODYxIDU2Ljk0NSwxMDUuMzI2IDU1Ljg1LDEwMy41NzMgNTcuMDE2LDk3LjIyOSA1OC4yNDIsOTAuNTU5IDU2LjgxMyw4OC4zMjggNDYuMiw4MC4zMzQgNDAuMTI0LDc1Ljc1NyAzOCw3My4xMDUgMzgsNzAuMDk2IGMgMCwtMy40MjQgMi4zNDcsLTQuMzk1IDYuOTEzLC0yLjg1OSBtIDMwNi41MTgsNTQuODA0IGMgMy4wMTUsMS44MDkgNC40MDQsNi45MDQgNC4wNTksMTQuODkyIC0wLjU1NywxMi44NzcgLTQuOTg0LDIzLjc5NyAtMTQuNDU5LDM1LjY2NyAtNi45ODcsOC43NTIgLTcuNjA0LDEwLjYxNSAtNS41NjcsMTYuOCAyLjEwNiw2LjM5MSAxLjM0MywxMC4yMDYgLTMuMjM5LDE2LjIgLTQuMjEsNS41MDcgLTQuNjg1LDcuNTggLTIuNzc4LDEyLjExMiAyLjM5OSw1LjcgMS42MTIsOC44OTcgLTMuNTkxLDE0LjU3NiAtMy42MzksMy45NzMgLTQuMDI0LDUuMDM3IC0zLjE4Nyw4LjgwNiAxLjAwNSw0LjUyOSAxLjA3Miw2LjIxNSAwLjM0Niw4LjcxNSAtMi4xMjMsNy4zIC05LjA4MywxMy4xMjIgLTIyLjc4OSwxOS4wNjIgLTE3LjM5OSw3LjUzOSAtMjcuMjU0LDE1LjMzNiAtMjUuMjk5LDIwLjAxNSAxLjA3NiwyLjU3NiA4LjUzOCw2LjI2OCAyMi4zOTcsMTEuMDgzIDcuMTA5LDIuNDY5IDEwLjUxNSwzLjc4OCAxNi4wNzYsNi4yMjUgOC44NTEsMy44NzkgMTEuNzM3LDQuMzU5IDE3LjkwNCwyLjk3OSA4Ljk3OCwtMi4wMDkgMTIuMjkxLDEuOTYgOS4wMzgsMTAuODI3IC0wLjY4NiwxLjg3IC0yLjEwNSw1LjkxMiAtMy4xNTIsOC45ODIgLTMuMjAxLDkuMzg0IC0zLjU1MSw5LjU1MSAtMTkuNTUzLDkuMzMxIC0xMC41MjQsLTAuMTQ2IC0xMS4yNTEsLTAuMjA2IC0xNS42MzcsLTEuMjkzIC03LjcxMSwtMS45MTIgLTEzLjI3MywtMS41NjQgLTMxLjUyOSwxLjk3MyAtMjUuODg0LDUuMDE1IC0zNy42OTIsNC45NjUgLTUwLjM2OCwtMC4yMTIgLTExLjI3NCwtNC42MDUgLTE2LjQzLC0xMC4xNDEgLTIwLjI4NiwtMjEuNzgxIC0yLjQ3NiwtNy40NzMgLTguMjAzLC0xOC41OTYgLTE1LjU1MiwtMzAuMiAtNi43NzEsLTEwLjY5MiAtOS4xMzMsLTIxLjQ0MSAtNy4xMTgsLTMyLjQgMy4wMTgsLTE2LjQxMiA3LjEzNywtMjYuNjcyIDE1LjI1NSwtMzggNS4yMzgsLTcuMzA4IDUuMzk0LC03Ljc5MSA1LjQxOCwtMTYuOCAwLjAyNSwtOC44NjggLTAuMDE0LC04LjY3MyAzLjI1NCwtMTYuMTI1IDIuNTg4LC01LjkwMyA0LjA4OCwtMTEuMDY1IDUuNDY0LC0xOC44MDkgMi4zODUsLTEzLjQxOSA4LjQwOSwtOS42MzUgMTYuNjE3LDEwLjQzOSA4LjMyLDIwLjM0NyA1LjY5MywzNi41MzIgLTEwLjA3OSw2Mi4wOTUgLTkuNzgxLDE1Ljg1MyAtOS4wODcsMTcuNzQzIDcuMTI0LDE5LjQwMSAzLjMsMC4zMzggOC43LDEuMTQgMTIsMS43ODQgMjEuMDUxLDQuMTA0IDIzLjQ3NywyLjUxMyAyNi44MTIsLTE3LjU4NSAyLjAzNywtMTIuMjcgMi42MjksLTE0LjQ1OCA2LjY2MSwtMjQuNiA4LjMxOSwtMjAuOTI4IDIyLjMxNSwtMzguMDExIDQ2LjQyMywtNTYuNjYxIDEwLjUwMSwtOC4xMjQgMjIuNDE0LC0yMC45NzIgMjYuNTQ0LC0yOC42MjcgNS4xNjksLTkuNTgyIDguMTU0LC0xMS42NTMgMTIuNzkxLC04Ljg3MSIKICAgICAgIHN0cm9rZT0ibm9uZSIKICAgICAgIGZpbGw9IiMwZDE0MWMiCiAgICAgICBmaWxsLXJ1bGU9ImV2ZW5vZGQiIC8+CiAgPC9nPgo8L3N2Zz4K)

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
