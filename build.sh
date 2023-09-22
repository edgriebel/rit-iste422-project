#!/bin/sh

echo "Cleaning existing classes..."
rm -f *.class
# rm -f build/test/java/*.class
# This command looks for matching files and runs the rm command for each file
# Note that {} are replaced with each file name
find . -name \*.class -exec rm {} \;

echo "Compiling source code and unit tests..."
# javac -classpath .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar src/main/java/*.java src/test/java/*.java -d build
javac -classpath .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar -sourcepath src/main/java -d build src/main/java/*.java
javac -classpath .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar -sourcepath src/test/java -d build src/test/java/*.java

if [ $? -ne 0 ] ; then echo BUILD FAILED!; exit 1; fi

echo "Running unit tests..."
java -cp ./build/src/main/java:./build/src/main/test/java:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore EdgeConnectorTest
if [ $? -ne 0 ] ; then echo TESTS FAILED!; exit 1; fi

echo "Running application..."
java -classpath ./build/src/test/java RunEdgeConvert
