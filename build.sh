#!/bin/sh
rm *.class
javac -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar *.java || (echo BUILD FAILED!; exit 1)
java -cp .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore EdgeConnectorTest || (echo TESTS FAILED!; exit 1)
java RunEdgeConvert
