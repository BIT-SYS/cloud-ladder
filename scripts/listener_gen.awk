BEGIN {
  OFS="\n"
  ORS="\n\n"

  print "package AST;"
  print "public class "CLASS_NAME" {"
}

{
print BEFORE_FUNC "enter" $0 "(" ARG $0  " node) {}"  ,
BEFORE_FUNC "exit" $0 "(" ARG $0" node) {}" 
}

END {
  print "}"

}
