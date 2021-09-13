#!/bin/sh
echo "Cleaning existing classes..."
rm -f *.class
# This command looks for matching files and runs the rm command for each file
# Note that {} are replaced with each file name
find . -name \*.class -exec rm {} \;

# echo "Writing the generated class files to a directory called 'build'"
# javac -d build src/java/*.java

# echo "Writing the generated test class files to a directory called 'build'"
# javac -d build -cp build test/java/*.java

echo "Compiling source code and unit tests..."
javac -d build -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar src/java/*.java test/java/*.java
if [ $? -ne 0 ] ; then echo BUILD FAILED!; exit 1; fi

echo "Running unit tests..."
java -cp .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build/EdgeConnectorTest.class org.junit.runner.JUnitCore test/java/EdgeConnectorTest
if [ $? -ne 0 ] ; then echo TESTS FAILED!; exit 1; fi

echo "Running application..."
java -cp build src/java/RunEdgeConvert