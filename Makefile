all: EdgeConnectorTest.class RunEdgeConvert

*.class: src/main/java/*.java src/test/java/*.java
	javac -cp lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar src/main/java/*.java \
	src/test/java/*.java -d build 

EdgeConnectorTest.class: *.class 
	java -cp .:lib/junit-4.12.jar:lib/hamcrest-core-1.3.jar:build \
	org.junit.runner.JUnitCore EdgeConnectorTest

RunEdgeConvert: *.class
	java -cp build RunEdgeConvert

clean:
	rm -f build/*.class