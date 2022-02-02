#!/bin/sh
echo "Cleaning existing classes..."
rm -f ./build/*.class
# This command looks for matching files and runs the rm command for each file
# Note that {} are replaced with each file name
find . -name \*.class -exec rm {} \;

echo "Compiling source code and unit tests..."
javac -d ./build -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar ./src/main/java/*.java  ./src/test/java/*.java 

if [ $? -ne 0 ] ; then echo BUILD FAILED!; exit 1; fi
q2
echo "Running unit tests..."
java -cp .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build org.junit.runner.JUnitCore EdgeConnectorTest
if [ $? -ne 0 ] ; then echo TESTS FAILED!; exit 1; fi

echo "Running application..."
java -cp .:build RunEdgeConvert
