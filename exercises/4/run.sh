mkdir bin

javac -d bin -cp "CommandLineJunit/*:quickcheck-jars\*" src/*.java

java -cp "bin:CommandLineJunit\*:quickcheck-jars\*" MonkeySim $1
