# test bench

TARGET = build/src/CLParser.tokens
FILE = expr.cl
GrammarSourceDir = ../src/main/antlr

# run_grun(name, root, flags)
define run_grun
  unset _JAVA_OPTIONS && \
  cd ../build/classes/java/main/ && for file in `ls ../../../../examples/*.cl`;  \
  do echo test $$file >&2 && grun $(1) $(2) $$file $(3); \
  done
endef

unset_java_options:

$(TARGET): $(GrammarSourceDir)/CLParser.g4 $(GrammarSourceDir)/CLLexer.g4
	echo "Find grammar files."

test: $(TARGET) unset_java_options
	$(call run_grun, CLParser, program)

test-gui: $(TARGET)
	$(call run_grun, CLParser, program, -gui)

