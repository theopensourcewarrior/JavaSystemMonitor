#!/usr/bin/env bash
set -e

# Build the fat JAR
if command -v gradle >/dev/null 2>&1; then
  GRADLE_CMD=gradle
else
  echo "Gradle is required to build the project" >&2
  exit 1
fi

if ! command -v rpmbuild >/dev/null 2>&1; then
  echo "rpmbuild is required to build the RPM package" >&2
  exit 1
fi

# Extract version from build.gradle
VERSION=$(grep "^version" build.gradle | sed -E "s/.*'([^']+)'.*/\1/")
APP_NAME="JavaSystemMonitor"
JAR_NAME="${APP_NAME}-${VERSION}-all.jar"
JAR_PATH="build/libs/${JAR_NAME}"
JAR_ABS_PATH="$(pwd)/${JAR_PATH}"
ICON_ABS_PATH="$(pwd)/readme/JavaSystemMonitor.png"

$GRADLE_CMD shadowJar

RPMBUILD_DIR="rpmbuild"
SPEC_FILE="$RPMBUILD_DIR/${APP_NAME}.spec"

rm -rf "$RPMBUILD_DIR"
mkdir -p "$RPMBUILD_DIR/BUILD" "$RPMBUILD_DIR/RPMS" "$RPMBUILD_DIR/SOURCES" "$RPMBUILD_DIR/SPECS" "$RPMBUILD_DIR/SRPMS"

cat > "$SPEC_FILE" <<'SPEC'
Name: javasystemmonitor
Version: __VERSION__
Release: 1%{?dist}
Summary: Java System Monitor
License: MIT
Group: Applications/System
BuildArch: noarch
Requires: java

%description
A cross platform utility to monitor system performance.

%install
mkdir -p %{buildroot}/usr/lib/__APP_NAME__
mkdir -p %{buildroot}/usr/bin
mkdir -p %{buildroot}/usr/share/applications
mkdir -p %{buildroot}/usr/share/pixmaps
cp __JAR_PATH__ %{buildroot}/usr/lib/__APP_NAME__/__APP_NAME__.jar
cat > %{buildroot}/usr/bin/jsm <<'EOF'
#!/usr/bin/env bash
exec java -jar /usr/lib/__APP_NAME__/__APP_NAME__.jar "$@"
EOF
chmod 755 %{buildroot}/usr/bin/jsm
cat > %{buildroot}/usr/share/applications/javasystemmonitor.desktop <<'EOF'
[Desktop Entry]
Type=Application
Name=Java System Monitor
Exec=jsm
Icon=javasystemmonitor
Categories=Utility;
Terminal=false
EOF
cp __ICON_PATH__ %{buildroot}/usr/share/pixmaps/javasystemmonitor.png
chmod 644 %{buildroot}/usr/share/pixmaps/javasystemmonitor.png

%files
/usr/lib/__APP_NAME__/__APP_NAME__.jar
/usr/bin/jsm
/usr/share/applications/javasystemmonitor.desktop
/usr/share/pixmaps/javasystemmonitor.png

%changelog
* Thu Jan 01 1970 Unknown <unknown@example.com> - __VERSION__-1
- Initial package
SPEC

sed -i \
  -e "s|__VERSION__|$VERSION|g" \
  -e "s|__APP_NAME__|$APP_NAME|g" \
  -e "s|__JAR_PATH__|$JAR_ABS_PATH|g" \
  -e "s|__ICON_PATH__|$ICON_ABS_PATH|g" \
  "$SPEC_FILE"

rpmbuild -bb "$SPEC_FILE" --define "_topdir $(pwd)/$RPMBUILD_DIR" --buildroot "$(pwd)/$RPMBUILD_DIR/BUILDROOT"

RPM_OUTPUT=$(find "$RPMBUILD_DIR/RPMS" -name '*.rpm')
echo "Package created: $RPM_OUTPUT"
