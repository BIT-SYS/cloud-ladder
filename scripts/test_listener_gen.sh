FILE=../src/main/java/ast/ASTBaseListener.java
MYTEMP=$(cat $FILE)
bash listener_gen.sh
diff -B $FILE <(echo "$MYTEMP")
exit $?

