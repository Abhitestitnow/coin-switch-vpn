#!/usr/bin/env sh

##############################################################################
# Gradle start up script for UN*X
##############################################################################

# Resolve script location
PRG="$0"
while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$' || :`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`"/$link"
    fi
done

SAVED="`pwd`"
cd "`dirname \"$PRG\"`/" >/dev/null
APP_HOME="`pwd -P`"
cd "$SAVED" >/dev/null

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Use proper JVM args format
exec java -Xmx1024m -Xms256m -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
