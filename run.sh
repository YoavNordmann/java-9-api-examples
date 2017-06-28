#!/bin/bash

#Run command line in order to rerun every time a file changes:
#find . -name '*.java' | entr ./run.sh

JAVA9_BIN=/usr/lib/jvm/java-9-ea-oracle/bin/
#set -x #echo on

/bin/rm -rf target/mlib
mkdir -p target/mlib
mkdir -p target/classes

$JAVA9_BIN/javac -d target/classes `find -name *.java`
$JAVA9_BIN/jar -c -f target/mlib/api-examples.jar -C target/classes .
/bin/rm -rf target/classes

$JAVA9_BIN/java -p target/mlib -m api.examples/org.nordmann.java9.ReactiveStreams
