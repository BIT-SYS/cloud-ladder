# help to generate ASTParserListener
SRC_NAME=ASTParser.java
ARG=""
BEFORE_FUNC='\tpublic void '
CLASS_NAME="ASTBaseListener"
REMOVE_MATCH="AST"

cat $SRC_NAME \
  | grep -oP 'class \K\w+' \
  | grep -v "$REMOVE_MATCH" \
  | awk -v ARG="$ARG" -v BEFORE_FUNC="$BEFORE_FUNC" \
    -v CLASS_NAME="$CLASS_NAME" \
  -f ./listener_gen.awk > ASTBaseListener.java
