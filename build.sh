#!/bin/sh
echo "Cleaning existing classes..."
rm -f *.class
# This command looks for matching files and runs the rm command for each file
# Note that {} are replaced with each file name
find . -name \build/*.class -exec rm {} \;

echo "Compiling source code and unit tests..."
javac -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build src/main/java/*.java src/test/java/*.java -d build
if [ $? -ne 0 ] ; then echo BUILD FAILED!; exit 1; fi

echo "Running unit tests..."
java -cp .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build org.junit.runner.JUnitCore EdgeConnectorTest
if [ $? -ne 0 ] ; then echo TESTS FAILED!; exit 1; fi

echo "Running application..."
java -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build RunEdgeConvert
