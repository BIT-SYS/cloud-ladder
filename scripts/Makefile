# test bench

TARGET = build/src/CLParser.tokens
FILE = expr.cl

# run_grun(name, root, file_name, flags)
define run_grun
  grun $(1) $(2) $(4) $(3)
endef


$(TARGET): src/CLParser.g4 src/CLLexer.g4
	mkdir -p build build/classes
	antlr4 -o build -visitor -lib ./src $<
	javac build/src/*.java -d build/classes

test: $(TARGET)
	cd build/classes && for file in `ls ../../examples/*.cl`;  \
	  do  echo test $$file && $(call run_grun, CLParser, program, $$file); \
	  done \

test-gui: $(TARGET)
	cd build/classes && for file in `ls ../../examples/*.cl`;  \
	  do echo test $$file && $(call run_grun, CLParser, program, $$file, -gui); \
	  done \

build: $(TARGET)
	javac src/*.java build/src/*.java -cp $$CLASSPATH:/home/fky/playground/gson-2.8.6.jar -d build/classes 

run: build
	java -cp build/classes:$$CLASSPATH:/home/fky/playground/gson-2.8.6.jar Test examples/$(FILE)

clean:
	rm build -r

