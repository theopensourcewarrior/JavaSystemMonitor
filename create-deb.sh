#!/usr/bin/env bash
set -e

# Build the fat JAR
if command -v gradle >/dev/null 2>&1; then
  GRADLE_CMD=gradle
else
  echo "Gradle is required to build the project" >&2
  exit 1
fi

# Extract version from build.gradle
VERSION=$(grep "^version" build.gradle | sed -E "s/.*'([^']+)'.*/\1/")
APP_NAME="JavaSystemMonitor"
JAR_NAME="${APP_NAME}-${VERSION}-all.jar"
JAR_PATH="build/libs/${JAR_NAME}"

$GRADLE_CMD shadowJar

# Prepare package layout
PKG_DIR="pkg"
DEBIAN_DIR="$PKG_DIR/DEBIAN"
INSTALL_DIR="$PKG_DIR/usr/lib/${APP_NAME}"
BIN_DIR="$PKG_DIR/usr/bin"
DESKTOP_DIR="$PKG_DIR/usr/share/applications"
ICON_DIR="$PKG_DIR/usr/share/pixmaps"

rm -rf "$PKG_DIR"
mkdir -p "$DEBIAN_DIR" "$INSTALL_DIR" "$BIN_DIR" "$DESKTOP_DIR" "$ICON_DIR"

# Copy application JAR
cp "$JAR_PATH" "$INSTALL_DIR/${APP_NAME}.jar"

# Launcher script
cat > "$BIN_DIR/jsm" <<'LAUNCHER'
#!/usr/bin/env bash
exec java -jar /usr/lib/JavaSystemMonitor/JavaSystemMonitor.jar "$@"
LAUNCHER
chmod +x "$BIN_DIR/jsm"

# Desktop entry
cat > "$DESKTOP_DIR/javasystemmonitor.desktop" <<'DESKTOP'
[Desktop Entry]
Type=Application
Name=Java System Monitor
Exec=jsm
Icon=javasystemmonitor
Categories=Utility;
Terminal=false
DESKTOP
chmod 644 "$DESKTOP_DIR/javasystemmonitor.desktop"

# Icon
cp readme/JavaSystemMonitor.png "$ICON_DIR/javasystemmonitor.png"
chmod 644 "$ICON_DIR/javasystemmonitor.png"

# Control file
cat > "$DEBIAN_DIR/control" <<CONTROL
Package: javasystemmonitor
Version: $VERSION
Section: utils
Priority: optional
Architecture: all
Maintainer: Unknown <unknown@example.com>
Description: Java System Monitor
 A cross platform utility to monitor system performance.
CONTROL

# Build the Debian package
dpkg-deb --build "$PKG_DIR" "${APP_NAME}_${VERSION}.deb"
echo "Package created: ${APP_NAME}_${VERSION}.deb"
