all : clean classes test run

clean: 
	rm -f build/*.class

classes: clean
	javac -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar src/*/java/*.java -d build

test: classes 
	java -cp ./build:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore EdgeConnectorTest

run: classes
	java -cp ./build RunEdgeConvert
