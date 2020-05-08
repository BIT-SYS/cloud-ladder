# help to generate ASTParserListener
#SRC_NAME=../src/main/java/ast.ASTParser.java
SRC_NAME=../src/main/java/ast
DEST_DIR=../src/main/java/ast
ARG=""
BEFORE_FUNC='\tpublic void '
CLASS_NAME="ASTBaseListener"
REMOVE_MATCH="AST\|Temp"

#cat $SRC_NAME \
  #| grep -oP 'class \K\w+' \
  #| grep -v "$REMOVE_MATCH" \
  #| awk -v ARG="$ARG" -v BEFORE_FUNC="$BEFORE_FUNC" \
    #-v CLASS_NAME="$CLASS_NAME" \
  #-f ./listener_gen.awk > $DEST_DIR/ast.ASTBaseListener.java

ls $SRC_NAME -1\
  | tr '.' ' ' \
  | cut -f 1 -d ' '\
  | grep -v "$REMOVE_MATCH" \
  | awk -v ARG="$ARG" -v BEFORE_FUNC="$BEFORE_FUNC" \
    -v CLASS_NAME="$CLASS_NAME" \
  -f ./listener_gen.awk \
  > $DEST_DIR/ASTBaseListener.java
