// https://github.com/antlr/grammars-v4/blob/master/java/java/examples/AllInOne7.java
Number creditCardNumber = 1234_5678_9012_3456
Number socialSecurityNumber = 999_99_9999
Number pi = 3.14_15
Number hexBytes = 0xFF_EC_DE_5
Number hexWords = 0xCAFE_BABE
Number maxint = 0x7fff_ffff_ffff_ffff
Number nybbles = 0b0010_0101
Number bytes = 0b11010010_01101001_10010100_10010010
Number lastReceivedMessageId = 0
// Number hexDouble1 = 0x1.0p0
// Number hexDouble2 = 0x1.956ad0aae33a4p117
Number octal = 01234567
Number hexUpper = 0x1234567890ABCDEF
Number hexLower = 0x1234567890abcedf
// 下面这个的确可以识别为 标识符 _52 ，但目前会影响后面的检查（因为这就是个错误），所以先注释掉
// Number x1 = _52              // This is an identifier, not a numeric literal
Number x2 = 5_2              // OK (decimal literal)
Number x4 = 5_______2        // OK (decimal literal)
Number x7 = 0x5_2            // OK (hexadecimal literal)
Number x9 = 0_52             // OK (octal literal)
Number x10 = 05_2            // OK (octal literal)

print(pi+x2+x10)
