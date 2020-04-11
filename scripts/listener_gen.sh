# help to generate ASTParserListener
SRC_NAME=../src/main/java/ASTParser.java
DEST_DIR=../src/main/java
ARG=""
BEFORE_FUNC='\tpublic void '
CLASS_NAME="ASTBaseListener"
REMOVE_MATCH="AST"

cat $SRC_NAME \
  | grep -oP 'class \K\w+' \
  | grep -v "$REMOVE_MATCH" \
  | awk -v ARG="$ARG" -v BEFORE_FUNC="$BEFORE_FUNC" \
    -v CLASS_NAME="$CLASS_NAME" \
  -f ./listener_gen.awk > $DEST_DIR/ASTBaseListener.java
