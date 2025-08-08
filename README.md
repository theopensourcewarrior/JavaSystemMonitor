# JavaSystemMonitor

A cross platform utility to monitor system performance.

![](https://raw.githubusercontent.com/theopensourcewarrior/JavaSystemMonitor/master/readme/JavaSystemMonitor.png)
![](https://raw.githubusercontent.com/theopensourcewarrior/JavaSystemMonitor/master/readme/JavaSystemMonitor2.png)

## How to Build
This project now uses Gradle. To build an executable JAR, run (requires Gradle installed):

```
gradle shadowJar
```

## How to Run
```
java -jar build/libs/JavaSystemMonitor-1.0-SNAPSHOT-all.jar
```

## Create a Debian Package
To build a `.deb` package and install it on Debian/Ubuntu systems, run:

```
./create-deb.sh
sudo dpkg -i JavaSystemMonitor_1.0-SNAPSHOT.deb
```
This installs a `jsm` command that launches the monitor.
