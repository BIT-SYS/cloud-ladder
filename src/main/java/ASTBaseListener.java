public class ASTBaseListener {

	public void enterScopePointer(ScopePointer node) {}
	public void exitScopePointer(ScopePointer node) {}

	public void enterProgram(Program node) {}
	public void exitProgram(Program node) {}

	public void enterRangeListInitializer(RangeListInitializer node) {}
	public void exitRangeListInitializer(RangeListInitializer node) {}

	public void enterAssign(Assign node) {}
	public void exitAssign(Assign node) {}

	public void enterValuesListInitializer(ValuesListInitializer node) {}
	public void exitValuesListInitializer(ValuesListInitializer node) {}

	public void enterLambdaExpression(LambdaExpression node) {}
	public void exitLambdaExpression(LambdaExpression node) {}

	public void enterBreak(Break node) {}
	public void exitBreak(Break node) {}

	public void enterContinue(Continue node) {}
	public void exitContinue(Continue node) {}

	public void enterBlock(Block node) {}
	public void exitBlock(Block node) {}

	public void enterIfElseBlock(IfElseBlock node) {}
	public void exitIfElseBlock(IfElseBlock node) {}

	public void enterIfBlock(IfBlock node) {}
	public void exitIfBlock(IfBlock node) {}

	public void enterElifBlock(ElifBlock node) {}
	public void exitElifBlock(ElifBlock node) {}

	public void enterElseBlock(ElseBlock node) {}
	public void exitElseBlock(ElseBlock node) {}

	public void enterWhileBlock(WhileBlock node) {}
	public void exitWhileBlock(WhileBlock node) {}

	public void enterForBlock(ForBlock node) {}
	public void exitForBlock(ForBlock node) {}

	public void enterParameter(Parameter node) {}
	public void exitParameter(Parameter node) {}

	public void enterParameterList(ParameterList node) {}
	public void exitParameterList(ParameterList node) {}

	public void enterIndexExpression(IndexExpression node) {}
	public void exitIndexExpression(IndexExpression node) {}

	public void enterProcedureDefinition(ProcedureDefinition node) {}
	public void exitProcedureDefinition(ProcedureDefinition node) {}

	public void enterVariableDeclaration(VariableDeclaration node) {}
	public void exitVariableDeclaration(VariableDeclaration node) {}

	public void enterCallExpression(CallExpression node) {}
	public void exitCallExpression(CallExpression node) {}

	public void enterMemberExpression(MemberExpression node) {}
	public void exitMemberExpression(MemberExpression node) {}

	public void enterIdentifier(Identifier node) {}
	public void exitIdentifier(Identifier node) {}

	public void enterFunctionIdentifier(FunctionIdentifier node) {}
	public void exitFunctionIdentifier(FunctionIdentifier node) {}

	public void enterLiteral(Literal node) {}
	public void exitLiteral(Literal node) {}

	public void enterBinaryExpression(BinaryExpression node) {}
	public void exitBinaryExpression(BinaryExpression node) {}

}

