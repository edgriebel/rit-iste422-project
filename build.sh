#!/bin/sh
mainDir='src/main/java/'
testDir='src/test/java/'

echo "Cleaning existing classes..."
rm -f *.class
# This command looks for matching files and runs the rm command for each file
# Note that {} are replaced with each file name
find . -name \*.class -exec rm {} \;

echo "Compiling source code and unit tests..."
javac -d build -classpath .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar ${mainDir}*.java ${testDir}*.java 
if [ $? -ne 0 ] ; then echo BUILD FAILED!; exit 1; fi



echo "Running unit tests..."
java -cp ./build:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore EdgeConnectorTest
if [ $? -ne 0 ] ; then echo TESTS FAILED!; exit 1; fi

echo "Running application..."
java -classpath ./build RunEdgeConvert
