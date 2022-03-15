# 代码结构

## `core/compiler`
此处包含编译器的代码，即从源代码到IR的过程在此处实现  
`CLCompilerCodeToAST`是你需要实现的内容，即从AST到IR的转换。
其中，所有AST类型在`core/compiler/ast`中
`CLCompiler`和`CLCompileContext`是遗留的代码，可以参考

## `core/ir`
此处包含所有的中间代码，你需要将AST编译到中间代码，必要时可以自己增加中间代码

## `core/runtime`
此处包含运行时的环境

### `core/runtime/env/CLRtEnvironment`
运行时环境，在普通物理机器上，可以理解为内存以及寄存器的状态，相当于一个巨大的状态机，在此之上执行中间代码

这部分也是需要实现的内容

# 你需要实现的内容
- `core/compiler/CLCompilerASTToIR`完成编译
- `core/runtime/env/CLRtEnvironment`完成运行时环境
- 任意一个标准库，例如Image，包括loadImage和Image下的任意一个AI相关的接口。这些库可以直接就存在于全局作用域中（类似Python的全局函数），不必导入
- 执行`src/cloudladder/tests`下的测试用例

