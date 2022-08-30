# Standard Linux Usage
# Usage:
# RUN.sh <1> <2> <3>
# PARAM 1: The user location (for example "jackm" -> /home/jackm/ (~))
# PARAM 2: The location of the downloaded source code, aka the path to the root folder of this project
# PARAM 3: The location of the JavaFX SDK, aka the path to the root folder of the javafx folder

/usr/bin/env java --module-path /home/$1/programs/javafx-sdk-18.0.2/lib --add-modules javafx.base,javafx.controls -XX:+ShowCodeDetailsInExceptionMessages-cp "$2/.vscode/build:$3/javafx-sdk-18.0.2/lib/javafx.base.jar:$3/javafx-sdk-18.0.2/lib/javafx.fxml.jar:$2/lib/tritonus-all.jar:$2/lib/de.jarnbjo.jar:$3/javafx-sdk-18.0.2/lib/javafx.swing.jar:$2/lib/discord-rpc.jar:$2/lib/javazoom-helper.jar:$3/javafx-sdk-18.0.2/lib/javafx.web.jar:$2/lib/Graphics2DGL.jar:$3/javafx-sdk-18.0.2/lib/javafx.controls.jar:$2/lib/gluegen.jar:$2/lib/imgscaler.jar:$2/lib/jaffree.jar:$2/lib/javazoom.jar:$2/nativelib/linux/x64/linux_x64_gluegen.jar:$3/javafx-sdk-18.0.2/lib/javafx.graphics.jar:$2/lib/jaudiotagger.jar:$2/lib/jogl.jar:$2/lib/flatlaf.jar:$2/nativelib/linux/x64/linux_x64_jogl.jar:$2/lib/nanojson.jar:$3/javafx-sdk-18.0.2/lib/javafx-swt.jar:$3/javafx-sdk-18.0.2/lib/javafx.media.jar:$2/lib/jorbis.jar:$2/lib/flatlaf-intellij.jar" com.jackmeng.halcyoninae.halcyon.Halcyon