# test bench


# run_grun(name, root, file_name, parameter)
define run_grun
  grun $(1) $(2) $(4) $(3)
endef


build-test:
	mkdir -p build
	antlr4 -o build -lib ./src ./src/CLParser.g4
	javac build/src/*.java

test: build-test
	cd build/src && for file in `ls ../../examples/*.cl`;  \
	  do $(call run_grun, CLParser, program, $$file); \
	  done \

test-gui: build-test
	cd build/src && for file in `ls ../../examples/*.cl`;  \
	  do $(call run_grun, CLParser, program, $$file, -gui); \
	  done \

clean:
	rm build -r

