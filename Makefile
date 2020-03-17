# test bench

TARGET = build/src/CLParser.tokens

# run_grun(name, root, file_name, parameter)
define run_grun
  grun $(1) $(2) $(4) $(3)
endef


$(TARGET): src/CLParser.g4 src/CLLexer.g4
	mkdir -p build
	antlr4 -o build -lib ./src $<
	javac build/src/*.java

test: $(TARGET)
	cd build/src && for file in `ls ../../examples/*.cl`;  \
	  do  echo test $$file && $(call run_grun, CLParser, program, $$file); \
	  done \

test-gui: $(TARGET)
	cd build/src && for file in `ls ../../examples/*.cl`;  \
	  do echo test $$file && $(call run_grun, CLParser, program, $$file, -gui); \
	  done \

clean:
	rm build -r

