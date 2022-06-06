javac -source 1.7 -target 1.7 -bootclasspath "C:\Program Files (x86)\Java\jdk1.7.0_80\jre\lib\rt.jar" *.java
jar -cvfm %1.jar manifest.txt *.class