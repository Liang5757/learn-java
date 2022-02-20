package cn.youyitech.anyview.system.utils.similarCalculations;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
* This interface defines a complete listener for a parse tree produced by
* {@link AnyviewCParser}.
*/
public interface AnyviewCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#capturedefault}.
	 * @param ctx the parse tree
	 */
	void enterCapturedefault(@NotNull AnyviewCParser.CapturedefaultContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#capturedefault}.
	 * @param ctx the parse tree
	 */
	void exitCapturedefault(@NotNull AnyviewCParser.CapturedefaultContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declarator}.
	 * @param ctx the parse tree
	 */
	void enterDeclarator(@NotNull AnyviewCParser.DeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declarator}.
	 * @param ctx the parse tree
	 */
	void exitDeclarator(@NotNull AnyviewCParser.DeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#postfixexpression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixexpression(@NotNull AnyviewCParser.PostfixexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#postfixexpression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixexpression(@NotNull AnyviewCParser.PostfixexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributenamespace}.
	 * @param ctx the parse tree
	 */
	void enterAttributenamespace(@NotNull AnyviewCParser.AttributenamespaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributenamespace}.
	 * @param ctx the parse tree
	 */
	void exitAttributenamespace(@NotNull AnyviewCParser.AttributenamespaceContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#handlerseq}.
	 * @param ctx the parse tree
	 */
	void enterHandlerseq(@NotNull AnyviewCParser.HandlerseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#handlerseq}.
	 * @param ctx the parse tree
	 */
	void exitHandlerseq(@NotNull AnyviewCParser.HandlerseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#ctorinitializer}.
	 * @param ctx the parse tree
	 */
	void enterCtorinitializer(@NotNull AnyviewCParser.CtorinitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#ctorinitializer}.
	 * @param ctx the parse tree
	 */
	void exitCtorinitializer(@NotNull AnyviewCParser.CtorinitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#primaryexpression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryexpression(@NotNull AnyviewCParser.PrimaryexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#primaryexpression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryexpression(@NotNull AnyviewCParser.PrimaryexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#assignmentexpression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentexpression(@NotNull AnyviewCParser.AssignmentexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#assignmentexpression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentexpression(@NotNull AnyviewCParser.AssignmentexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#capturelist}.
	 * @param ctx the parse tree
	 */
	void enterCapturelist(@NotNull AnyviewCParser.CapturelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#capturelist}.
	 * @param ctx the parse tree
	 */
	void exitCapturelist(@NotNull AnyviewCParser.CapturelistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#relationalexpression}.
	 * @param ctx the parse tree
	 */
	void enterRelationalexpression(@NotNull AnyviewCParser.RelationalexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#relationalexpression}.
	 * @param ctx the parse tree
	 */
	void exitRelationalexpression(@NotNull AnyviewCParser.RelationalexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#unqualifiedid}.
	 * @param ctx the parse tree
	 */
	void enterUnqualifiedid(@NotNull AnyviewCParser.UnqualifiedidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#unqualifiedid}.
	 * @param ctx the parse tree
	 */
	void exitUnqualifiedid(@NotNull AnyviewCParser.UnqualifiedidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#parameterdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterdeclaration(@NotNull AnyviewCParser.ParameterdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#parameterdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterdeclaration(@NotNull AnyviewCParser.ParameterdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namespacebody}.
	 * @param ctx the parse tree
	 */
	void enterNamespacebody(@NotNull AnyviewCParser.NamespacebodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namespacebody}.
	 * @param ctx the parse tree
	 */
	void exitNamespacebody(@NotNull AnyviewCParser.NamespacebodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributedeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAttributedeclaration(@NotNull AnyviewCParser.AttributedeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributedeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAttributedeclaration(@NotNull AnyviewCParser.AttributedeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#selectionstatement}.
	 * @param ctx the parse tree
	 */
	void enterSelectionstatement(@NotNull AnyviewCParser.SelectionstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#selectionstatement}.
	 * @param ctx the parse tree
	 */
	void exitSelectionstatement(@NotNull AnyviewCParser.SelectionstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#pseudodestructorname}.
	 * @param ctx the parse tree
	 */
	void enterPseudodestructorname(@NotNull AnyviewCParser.PseudodestructornameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#pseudodestructorname}.
	 * @param ctx the parse tree
	 */
	void exitPseudodestructorname(@NotNull AnyviewCParser.PseudodestructornameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templateargument}.
	 * @param ctx the parse tree
	 */
	void enterTemplateargument(@NotNull AnyviewCParser.TemplateargumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templateargument}.
	 * @param ctx the parse tree
	 */
	void exitTemplateargument(@NotNull AnyviewCParser.TemplateargumentContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#baseclause}.
	 * @param ctx the parse tree
	 */
	void enterBaseclause(@NotNull AnyviewCParser.BaseclauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#baseclause}.
	 * @param ctx the parse tree
	 */
	void exitBaseclause(@NotNull AnyviewCParser.BaseclauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#functiontryblock}.
	 * @param ctx the parse tree
	 */
	void enterFunctiontryblock(@NotNull AnyviewCParser.FunctiontryblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#functiontryblock}.
	 * @param ctx the parse tree
	 */
	void exitFunctiontryblock(@NotNull AnyviewCParser.FunctiontryblockContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#deleteexpression}.
	 * @param ctx the parse tree
	 */
	void enterDeleteexpression(@NotNull AnyviewCParser.DeleteexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#deleteexpression}.
	 * @param ctx the parse tree
	 */
	void exitDeleteexpression(@NotNull AnyviewCParser.DeleteexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#literaloperatorid}.
	 * @param ctx the parse tree
	 */
	void enterLiteraloperatorid(@NotNull AnyviewCParser.LiteraloperatoridContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#literaloperatorid}.
	 * @param ctx the parse tree
	 */
	void exitLiteraloperatorid(@NotNull AnyviewCParser.LiteraloperatoridContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#linkagespecification}.
	 * @param ctx the parse tree
	 */
	void enterLinkagespecification(@NotNull AnyviewCParser.LinkagespecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#linkagespecification}.
	 * @param ctx the parse tree
	 */
	void exitLinkagespecification(@NotNull AnyviewCParser.LinkagespecificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#simplecapture}.
	 * @param ctx the parse tree
	 */
	void enterSimplecapture(@NotNull AnyviewCParser.SimplecaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#simplecapture}.
	 * @param ctx the parse tree
	 */
	void exitSimplecapture(@NotNull AnyviewCParser.SimplecaptureContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#meminitializerid}.
	 * @param ctx the parse tree
	 */
	void enterMeminitializerid(@NotNull AnyviewCParser.MeminitializeridContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#meminitializerid}.
	 * @param ctx the parse tree
	 */
	void exitMeminitializerid(@NotNull AnyviewCParser.MeminitializeridContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noexceptexpression}.
	 * @param ctx the parse tree
	 */
	void enterNoexceptexpression(@NotNull AnyviewCParser.NoexceptexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noexceptexpression}.
	 * @param ctx the parse tree
	 */
	void exitNoexceptexpression(@NotNull AnyviewCParser.NoexceptexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typeid}.
	 * @param ctx the parse tree
	 */
	void enterTypeid(@NotNull AnyviewCParser.TypeidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typeid}.
	 * @param ctx the parse tree
	 */
	void exitTypeid(@NotNull AnyviewCParser.TypeidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#pointerliteral}.
	 * @param ctx the parse tree
	 */
	void enterPointerliteral(@NotNull AnyviewCParser.PointerliteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#pointerliteral}.
	 * @param ctx the parse tree
	 */
	void exitPointerliteral(@NotNull AnyviewCParser.PointerliteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#virtspecifier}.
	 * @param ctx the parse tree
	 */
	void enterVirtspecifier(@NotNull AnyviewCParser.VirtspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#virtspecifier}.
	 * @param ctx the parse tree
	 */
	void exitVirtspecifier(@NotNull AnyviewCParser.VirtspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#shiftexpression}.
	 * @param ctx the parse tree
	 */
	void enterShiftexpression(@NotNull AnyviewCParser.ShiftexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#shiftexpression}.
	 * @param ctx the parse tree
	 */
	void exitShiftexpression(@NotNull AnyviewCParser.ShiftexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#functiondefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctiondefinition(@NotNull AnyviewCParser.FunctiondefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#functiondefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctiondefinition(@NotNull AnyviewCParser.FunctiondefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumspecifier}.
	 * @param ctx the parse tree
	 */
	void enterEnumspecifier(@NotNull AnyviewCParser.EnumspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumspecifier}.
	 * @param ctx the parse tree
	 */
	void exitEnumspecifier(@NotNull AnyviewCParser.EnumspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#balancedtokenseq}.
	 * @param ctx the parse tree
	 */
	void enterBalancedtokenseq(@NotNull AnyviewCParser.BalancedtokenseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#balancedtokenseq}.
	 * @param ctx the parse tree
	 */
	void exitBalancedtokenseq(@NotNull AnyviewCParser.BalancedtokenseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#iterationstatement}.
	 * @param ctx the parse tree
	 */
	void enterIterationstatement(@NotNull AnyviewCParser.IterationstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#iterationstatement}.
	 * @param ctx the parse tree
	 */
	void exitIterationstatement(@NotNull AnyviewCParser.IterationstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#unaryexpression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryexpression(@NotNull AnyviewCParser.UnaryexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#unaryexpression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryexpression(@NotNull AnyviewCParser.UnaryexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(@NotNull AnyviewCParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(@NotNull AnyviewCParser.OperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typedefname}.
	 * @param ctx the parse tree
	 */
	void enterTypedefname(@NotNull AnyviewCParser.TypedefnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typedefname}.
	 * @param ctx the parse tree
	 */
	void exitTypedefname(@NotNull AnyviewCParser.TypedefnameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterInitdeclarator(@NotNull AnyviewCParser.InitdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitInitdeclarator(@NotNull AnyviewCParser.InitdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributespecifier}.
	 * @param ctx the parse tree
	 */
	void enterAttributespecifier(@NotNull AnyviewCParser.AttributespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributespecifier}.
	 * @param ctx the parse tree
	 */
	void exitAttributespecifier(@NotNull AnyviewCParser.AttributespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classheadname}.
	 * @param ctx the parse tree
	 */
	void enterClassheadname(@NotNull AnyviewCParser.ClassheadnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classheadname}.
	 * @param ctx the parse tree
	 */
	void exitClassheadname(@NotNull AnyviewCParser.ClassheadnameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#balancedtoken}.
	 * @param ctx the parse tree
	 */
	void enterBalancedtoken(@NotNull AnyviewCParser.BalancedtokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#balancedtoken}.
	 * @param ctx the parse tree
	 */
	void exitBalancedtoken(@NotNull AnyviewCParser.BalancedtokenContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(@NotNull AnyviewCParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(@NotNull AnyviewCParser.ExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#operatorfunctionid}.
	 * @param ctx the parse tree
	 */
	void enterOperatorfunctionid(@NotNull AnyviewCParser.OperatorfunctionidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#operatorfunctionid}.
	 * @param ctx the parse tree
	 */
	void exitOperatorfunctionid(@NotNull AnyviewCParser.OperatorfunctionidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#lambdaintroducer}.
	 * @param ctx the parse tree
	 */
	void enterLambdaintroducer(@NotNull AnyviewCParser.LambdaintroducerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#lambdaintroducer}.
	 * @param ctx the parse tree
	 */
	void exitLambdaintroducer(@NotNull AnyviewCParser.LambdaintroducerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#compoundstatement}.
	 * @param ctx the parse tree
	 */
	void enterCompoundstatement(@NotNull AnyviewCParser.CompoundstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#compoundstatement}.
	 * @param ctx the parse tree
	 */
	void exitCompoundstatement(@NotNull AnyviewCParser.CompoundstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#newinitializer}.
	 * @param ctx the parse tree
	 */
	void enterNewinitializer(@NotNull AnyviewCParser.NewinitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#newinitializer}.
	 * @param ctx the parse tree
	 */
	void exitNewinitializer(@NotNull AnyviewCParser.NewinitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#memberdeclaratorlist}.
	 * @param ctx the parse tree
	 */
	void enterMemberdeclaratorlist(@NotNull AnyviewCParser.MemberdeclaratorlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#memberdeclaratorlist}.
	 * @param ctx the parse tree
	 */
	void exitMemberdeclaratorlist(@NotNull AnyviewCParser.MemberdeclaratorlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#newplacement}.
	 * @param ctx the parse tree
	 */
	void enterNewplacement(@NotNull AnyviewCParser.NewplacementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#newplacement}.
	 * @param ctx the parse tree
	 */
	void exitNewplacement(@NotNull AnyviewCParser.NewplacementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#rightShiftAssign}.
	 * @param ctx the parse tree
	 */
	void enterRightShiftAssign(@NotNull AnyviewCParser.RightShiftAssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#rightShiftAssign}.
	 * @param ctx the parse tree
	 */
	void exitRightShiftAssign(@NotNull AnyviewCParser.RightShiftAssignContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#elaboratedtypespecifier}.
	 * @param ctx the parse tree
	 */
	void enterElaboratedtypespecifier(@NotNull AnyviewCParser.ElaboratedtypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#elaboratedtypespecifier}.
	 * @param ctx the parse tree
	 */
	void exitElaboratedtypespecifier(@NotNull AnyviewCParser.ElaboratedtypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#static_assertdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStatic_assertdeclaration(@NotNull AnyviewCParser.Static_assertdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#static_assertdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStatic_assertdeclaration(@NotNull AnyviewCParser.Static_assertdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#idexpression}.
	 * @param ctx the parse tree
	 */
	void enterIdexpression(@NotNull AnyviewCParser.IdexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#idexpression}.
	 * @param ctx the parse tree
	 */
	void exitIdexpression(@NotNull AnyviewCParser.IdexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 */
	void enterQualifiednamespacespecifier(@NotNull AnyviewCParser.QualifiednamespacespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 */
	void exitQualifiednamespacespecifier(@NotNull AnyviewCParser.QualifiednamespacespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#purespecifier}.
	 * @param ctx the parse tree
	 */
	void enterPurespecifier(@NotNull AnyviewCParser.PurespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#purespecifier}.
	 * @param ctx the parse tree
	 */
	void exitPurespecifier(@NotNull AnyviewCParser.PurespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#forinitstatement}.
	 * @param ctx the parse tree
	 */
	void enterForinitstatement(@NotNull AnyviewCParser.ForinitstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#forinitstatement}.
	 * @param ctx the parse tree
	 */
	void exitForinitstatement(@NotNull AnyviewCParser.ForinitstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void enterEnumerator(@NotNull AnyviewCParser.EnumeratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumerator}.
	 * @param ctx the parse tree
	 */
	void exitEnumerator(@NotNull AnyviewCParser.EnumeratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#usingdirective}.
	 * @param ctx the parse tree
	 */
	void enterUsingdirective(@NotNull AnyviewCParser.UsingdirectiveContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#usingdirective}.
	 * @param ctx the parse tree
	 */
	void exitUsingdirective(@NotNull AnyviewCParser.UsingdirectiveContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#capture}.
	 * @param ctx the parse tree
	 */
	void enterCapture(@NotNull AnyviewCParser.CaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#capture}.
	 * @param ctx the parse tree
	 */
	void exitCapture(@NotNull AnyviewCParser.CaptureContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#blockdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterBlockdeclaration(@NotNull AnyviewCParser.BlockdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#blockdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitBlockdeclaration(@NotNull AnyviewCParser.BlockdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#accessspecifier}.
	 * @param ctx the parse tree
	 */
	void enterAccessspecifier(@NotNull AnyviewCParser.AccessspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#accessspecifier}.
	 * @param ctx the parse tree
	 */
	void exitAccessspecifier(@NotNull AnyviewCParser.AccessspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(@NotNull AnyviewCParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(@NotNull AnyviewCParser.DeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#conversionfunctionid}.
	 * @param ctx the parse tree
	 */
	void enterConversionfunctionid(@NotNull AnyviewCParser.ConversionfunctionidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#conversionfunctionid}.
	 * @param ctx the parse tree
	 */
	void exitConversionfunctionid(@NotNull AnyviewCParser.ConversionfunctionidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#unaryoperator}.
	 * @param ctx the parse tree
	 */
	void enterUnaryoperator(@NotNull AnyviewCParser.UnaryoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#unaryoperator}.
	 * @param ctx the parse tree
	 */
	void exitUnaryoperator(@NotNull AnyviewCParser.UnaryoperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(@NotNull AnyviewCParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(@NotNull AnyviewCParser.ConditionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#functionbody}.
	 * @param ctx the parse tree
	 */
	void enterFunctionbody(@NotNull AnyviewCParser.FunctionbodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#functionbody}.
	 * @param ctx the parse tree
	 */
	void exitFunctionbody(@NotNull AnyviewCParser.FunctionbodyContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namespacealias}.
	 * @param ctx the parse tree
	 */
	void enterNamespacealias(@NotNull AnyviewCParser.NamespacealiasContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namespacealias}.
	 * @param ctx the parse tree
	 */
	void exitNamespacealias(@NotNull AnyviewCParser.NamespacealiasContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#ptrdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterPtrdeclarator(@NotNull AnyviewCParser.PtrdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#ptrdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitPtrdeclarator(@NotNull AnyviewCParser.PtrdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributelist}.
	 * @param ctx the parse tree
	 */
	void enterAttributelist(@NotNull AnyviewCParser.AttributelistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributelist}.
	 * @param ctx the parse tree
	 */
	void exitAttributelist(@NotNull AnyviewCParser.AttributelistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classkey}.
	 * @param ctx the parse tree
	 */
	void enterClasskey(@NotNull AnyviewCParser.ClasskeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classkey}.
	 * @param ctx the parse tree
	 */
	void exitClasskey(@NotNull AnyviewCParser.ClasskeyContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namespacealiasdefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespacealiasdefinition(@NotNull AnyviewCParser.NamespacealiasdefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namespacealiasdefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespacealiasdefinition(@NotNull AnyviewCParser.NamespacealiasdefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#forrangeinitializer}.
	 * @param ctx the parse tree
	 */
	void enterForrangeinitializer(@NotNull AnyviewCParser.ForrangeinitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#forrangeinitializer}.
	 * @param ctx the parse tree
	 */
	void exitForrangeinitializer(@NotNull AnyviewCParser.ForrangeinitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#parametersandqualifiers}.
	 * @param ctx the parse tree
	 */
	void enterParametersandqualifiers(@NotNull AnyviewCParser.ParametersandqualifiersContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#parametersandqualifiers}.
	 * @param ctx the parse tree
	 */
	void exitParametersandqualifiers(@NotNull AnyviewCParser.ParametersandqualifiersContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initializerclause}.
	 * @param ctx the parse tree
	 */
	void enterInitializerclause(@NotNull AnyviewCParser.InitializerclauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initializerclause}.
	 * @param ctx the parse tree
	 */
	void exitInitializerclause(@NotNull AnyviewCParser.InitializerclauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributeargumentclause}.
	 * @param ctx the parse tree
	 */
	void enterAttributeargumentclause(@NotNull AnyviewCParser.AttributeargumentclauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributeargumentclause}.
	 * @param ctx the parse tree
	 */
	void exitAttributeargumentclause(@NotNull AnyviewCParser.AttributeargumentclauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declspecifier}.
	 * @param ctx the parse tree
	 */
	void enterDeclspecifier(@NotNull AnyviewCParser.DeclspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declspecifier}.
	 * @param ctx the parse tree
	 */
	void exitDeclspecifier(@NotNull AnyviewCParser.DeclspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#braceorequalinitializer}.
	 * @param ctx the parse tree
	 */
	void enterBraceorequalinitializer(@NotNull AnyviewCParser.BraceorequalinitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#braceorequalinitializer}.
	 * @param ctx the parse tree
	 */
	void exitBraceorequalinitializer(@NotNull AnyviewCParser.BraceorequalinitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#trailingreturntype}.
	 * @param ctx the parse tree
	 */
	void enterTrailingreturntype(@NotNull AnyviewCParser.TrailingreturntypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#trailingreturntype}.
	 * @param ctx the parse tree
	 */
	void exitTrailingreturntype(@NotNull AnyviewCParser.TrailingreturntypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#abstractpackdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterAbstractpackdeclarator(@NotNull AnyviewCParser.AbstractpackdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#abstractpackdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitAbstractpackdeclarator(@NotNull AnyviewCParser.AbstractpackdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#exclusiveorexpression}.
	 * @param ctx the parse tree
	 */
	void enterExclusiveorexpression(@NotNull AnyviewCParser.ExclusiveorexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#exclusiveorexpression}.
	 * @param ctx the parse tree
	 */
	void exitExclusiveorexpression(@NotNull AnyviewCParser.ExclusiveorexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initializerlist}.
	 * @param ctx the parse tree
	 */
	void enterInitializerlist(@NotNull AnyviewCParser.InitializerlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initializerlist}.
	 * @param ctx the parse tree
	 */
	void exitInitializerlist(@NotNull AnyviewCParser.InitializerlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 */
	void enterAlignmentspecifier(@NotNull AnyviewCParser.AlignmentspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 */
	void exitAlignmentspecifier(@NotNull AnyviewCParser.AlignmentspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#ptrabstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterPtrabstractdeclarator(@NotNull AnyviewCParser.PtrabstractdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#ptrabstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitPtrabstractdeclarator(@NotNull AnyviewCParser.PtrabstractdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#forrangedeclaration}.
	 * @param ctx the parse tree
	 */
	void enterForrangedeclaration(@NotNull AnyviewCParser.ForrangedeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#forrangedeclaration}.
	 * @param ctx the parse tree
	 */
	void exitForrangedeclaration(@NotNull AnyviewCParser.ForrangedeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#logicalandexpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalandexpression(@NotNull AnyviewCParser.LogicalandexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#logicalandexpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalandexpression(@NotNull AnyviewCParser.LogicalandexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#abstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterAbstractdeclarator(@NotNull AnyviewCParser.AbstractdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#abstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitAbstractdeclarator(@NotNull AnyviewCParser.AbstractdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noptrabstractpackdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoptrabstractpackdeclarator(@NotNull AnyviewCParser.NoptrabstractpackdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noptrabstractpackdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoptrabstractpackdeclarator(@NotNull AnyviewCParser.NoptrabstractpackdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#basetypespecifier}.
	 * @param ctx the parse tree
	 */
	void enterBasetypespecifier(@NotNull AnyviewCParser.BasetypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#basetypespecifier}.
	 * @param ctx the parse tree
	 */
	void exitBasetypespecifier(@NotNull AnyviewCParser.BasetypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#booleanliteral}.
	 * @param ctx the parse tree
	 */
	void enterBooleanliteral(@NotNull AnyviewCParser.BooleanliteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#booleanliteral}.
	 * @param ctx the parse tree
	 */
	void exitBooleanliteral(@NotNull AnyviewCParser.BooleanliteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namespacedefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamespacedefinition(@NotNull AnyviewCParser.NamespacedefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namespacedefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamespacedefinition(@NotNull AnyviewCParser.NamespacedefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#rightShift}.
	 * @param ctx the parse tree
	 */
	void enterRightShift(@NotNull AnyviewCParser.RightShiftContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#rightShift}.
	 * @param ctx the parse tree
	 */
	void exitRightShift(@NotNull AnyviewCParser.RightShiftContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noptrabstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoptrabstractdeclarator(@NotNull AnyviewCParser.NoptrabstractdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noptrabstractdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoptrabstractdeclarator(@NotNull AnyviewCParser.NoptrabstractdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namespacename}.
	 * @param ctx the parse tree
	 */
	void enterNamespacename(@NotNull AnyviewCParser.NamespacenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namespacename}.
	 * @param ctx the parse tree
	 */
	void exitNamespacename(@NotNull AnyviewCParser.NamespacenameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#nestednamespecifier}.
	 * @param ctx the parse tree
	 */
	void enterNestednamespecifier(@NotNull AnyviewCParser.NestednamespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#nestednamespecifier}.
	 * @param ctx the parse tree
	 */
	void exitNestednamespecifier(@NotNull AnyviewCParser.NestednamespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classordecltype}.
	 * @param ctx the parse tree
	 */
	void enterClassordecltype(@NotNull AnyviewCParser.ClassordecltypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classordecltype}.
	 * @param ctx the parse tree
	 */
	void exitClassordecltype(@NotNull AnyviewCParser.ClassordecltypeContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#tryblock}.
	 * @param ctx the parse tree
	 */
	void enterTryblock(@NotNull AnyviewCParser.TryblockContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#tryblock}.
	 * @param ctx the parse tree
	 */
	void exitTryblock(@NotNull AnyviewCParser.TryblockContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#handler}.
	 * @param ctx the parse tree
	 */
	void enterHandler(@NotNull AnyviewCParser.HandlerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#handler}.
	 * @param ctx the parse tree
	 */
	void exitHandler(@NotNull AnyviewCParser.HandlerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#explicitinstantiation}.
	 * @param ctx the parse tree
	 */
	void enterExplicitinstantiation(@NotNull AnyviewCParser.ExplicitinstantiationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#explicitinstantiation}.
	 * @param ctx the parse tree
	 */
	void exitExplicitinstantiation(@NotNull AnyviewCParser.ExplicitinstantiationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noptrdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoptrdeclarator(@NotNull AnyviewCParser.NoptrdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noptrdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoptrdeclarator(@NotNull AnyviewCParser.NoptrdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumname}.
	 * @param ctx the parse tree
	 */
	void enterEnumname(@NotNull AnyviewCParser.EnumnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumname}.
	 * @param ctx the parse tree
	 */
	void exitEnumname(@NotNull AnyviewCParser.EnumnameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#simpletemplateid}.
	 * @param ctx the parse tree
	 */
	void enterSimpletemplateid(@NotNull AnyviewCParser.SimpletemplateidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#simpletemplateid}.
	 * @param ctx the parse tree
	 */
	void exitSimpletemplateid(@NotNull AnyviewCParser.SimpletemplateidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#asmdefinition}.
	 * @param ctx the parse tree
	 */
	void enterAsmdefinition(@NotNull AnyviewCParser.AsmdefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#asmdefinition}.
	 * @param ctx the parse tree
	 */
	void exitAsmdefinition(@NotNull AnyviewCParser.AsmdefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#originalnamespacename}.
	 * @param ctx the parse tree
	 */
	void enterOriginalnamespacename(@NotNull AnyviewCParser.OriginalnamespacenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#originalnamespacename}.
	 * @param ctx the parse tree
	 */
	void exitOriginalnamespacename(@NotNull AnyviewCParser.OriginalnamespacenameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#labeledstatement}.
	 * @param ctx the parse tree
	 */
	void enterLabeledstatement(@NotNull AnyviewCParser.LabeledstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#labeledstatement}.
	 * @param ctx the parse tree
	 */
	void exitLabeledstatement(@NotNull AnyviewCParser.LabeledstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#conversiondeclarator}.
	 * @param ctx the parse tree
	 */
	void enterConversiondeclarator(@NotNull AnyviewCParser.ConversiondeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#conversiondeclarator}.
	 * @param ctx the parse tree
	 */
	void exitConversiondeclarator(@NotNull AnyviewCParser.ConversiondeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#lambdaexpression}.
	 * @param ctx the parse tree
	 */
	void enterLambdaexpression(@NotNull AnyviewCParser.LambdaexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#lambdaexpression}.
	 * @param ctx the parse tree
	 */
	void exitLambdaexpression(@NotNull AnyviewCParser.LambdaexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typeparameter}.
	 * @param ctx the parse tree
	 */
	void enterTypeparameter(@NotNull AnyviewCParser.TypeparameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typeparameter}.
	 * @param ctx the parse tree
	 */
	void exitTypeparameter(@NotNull AnyviewCParser.TypeparameterContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#trailingtypespecifier}.
	 * @param ctx the parse tree
	 */
	void enterTrailingtypespecifier(@NotNull AnyviewCParser.TrailingtypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#trailingtypespecifier}.
	 * @param ctx the parse tree
	 */
	void exitTrailingtypespecifier(@NotNull AnyviewCParser.TrailingtypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declarationseq}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationseq(@NotNull AnyviewCParser.DeclarationseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declarationseq}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationseq(@NotNull AnyviewCParser.DeclarationseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#translationunit}.
	 * @param ctx the parse tree
	 */
	void enterTranslationunit(@NotNull AnyviewCParser.TranslationunitContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#translationunit}.
	 * @param ctx the parse tree
	 */
	void exitTranslationunit(@NotNull AnyviewCParser.TranslationunitContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#andexpression}.
	 * @param ctx the parse tree
	 */
	void enterAndexpression(@NotNull AnyviewCParser.AndexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#andexpression}.
	 * @param ctx the parse tree
	 */
	void exitAndexpression(@NotNull AnyviewCParser.AndexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templatedeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTemplatedeclaration(@NotNull AnyviewCParser.TemplatedeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templatedeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTemplatedeclaration(@NotNull AnyviewCParser.TemplatedeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#newexpression}.
	 * @param ctx the parse tree
	 */
	void enterNewexpression(@NotNull AnyviewCParser.NewexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#newexpression}.
	 * @param ctx the parse tree
	 */
	void exitNewexpression(@NotNull AnyviewCParser.NewexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#lambdacapture}.
	 * @param ctx the parse tree
	 */
	void enterLambdacapture(@NotNull AnyviewCParser.LambdacaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#lambdacapture}.
	 * @param ctx the parse tree
	 */
	void exitLambdacapture(@NotNull AnyviewCParser.LambdacaptureContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#memberdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberdeclaration(@NotNull AnyviewCParser.MemberdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#memberdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberdeclaration(@NotNull AnyviewCParser.MemberdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classname}.
	 * @param ctx the parse tree
	 */
	void enterClassname(@NotNull AnyviewCParser.ClassnameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classname}.
	 * @param ctx the parse tree
	 */
	void exitClassname(@NotNull AnyviewCParser.ClassnameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#emptydeclaration}.
	 * @param ctx the parse tree
	 */
	void enterEmptydeclaration(@NotNull AnyviewCParser.EmptydeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#emptydeclaration}.
	 * @param ctx the parse tree
	 */
	void exitEmptydeclaration(@NotNull AnyviewCParser.EmptydeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#aliasdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAliasdeclaration(@NotNull AnyviewCParser.AliasdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#aliasdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAliasdeclaration(@NotNull AnyviewCParser.AliasdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#refqualifier}.
	 * @param ctx the parse tree
	 */
	void enterRefqualifier(@NotNull AnyviewCParser.RefqualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#refqualifier}.
	 * @param ctx the parse tree
	 */
	void exitRefqualifier(@NotNull AnyviewCParser.RefqualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#namednamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void enterNamednamespacedefinition(@NotNull AnyviewCParser.NamednamespacedefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#namednamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void exitNamednamespacedefinition(@NotNull AnyviewCParser.NamednamespacedefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#conditionalexpression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalexpression(@NotNull AnyviewCParser.ConditionalexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#conditionalexpression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalexpression(@NotNull AnyviewCParser.ConditionalexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#meminitializerlist}.
	 * @param ctx the parse tree
	 */
	void enterMeminitializerlist(@NotNull AnyviewCParser.MeminitializerlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#meminitializerlist}.
	 * @param ctx the parse tree
	 */
	void exitMeminitializerlist(@NotNull AnyviewCParser.MeminitializerlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#decltypespecifier}.
	 * @param ctx the parse tree
	 */
	void enterDecltypespecifier(@NotNull AnyviewCParser.DecltypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#decltypespecifier}.
	 * @param ctx the parse tree
	 */
	void exitDecltypespecifier(@NotNull AnyviewCParser.DecltypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#originalnamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void enterOriginalnamespacedefinition(@NotNull AnyviewCParser.OriginalnamespacedefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#originalnamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void exitOriginalnamespacedefinition(@NotNull AnyviewCParser.OriginalnamespacedefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#castexpression}.
	 * @param ctx the parse tree
	 */
	void enterCastexpression(@NotNull AnyviewCParser.CastexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#castexpression}.
	 * @param ctx the parse tree
	 */
	void exitCastexpression(@NotNull AnyviewCParser.CastexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#exceptiondeclaration}.
	 * @param ctx the parse tree
	 */
	void enterExceptiondeclaration(@NotNull AnyviewCParser.ExceptiondeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#exceptiondeclaration}.
	 * @param ctx the parse tree
	 */
	void exitExceptiondeclaration(@NotNull AnyviewCParser.ExceptiondeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#memberspecification}.
	 * @param ctx the parse tree
	 */
	void enterMemberspecification(@NotNull AnyviewCParser.MemberspecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#memberspecification}.
	 * @param ctx the parse tree
	 */
	void exitMemberspecification(@NotNull AnyviewCParser.MemberspecificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributetoken}.
	 * @param ctx the parse tree
	 */
	void enterAttributetoken(@NotNull AnyviewCParser.AttributetokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributetoken}.
	 * @param ctx the parse tree
	 */
	void exitAttributetoken(@NotNull AnyviewCParser.AttributetokenContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templatename}.
	 * @param ctx the parse tree
	 */
	void enterTemplatename(@NotNull AnyviewCParser.TemplatenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templatename}.
	 * @param ctx the parse tree
	 */
	void exitTemplatename(@NotNull AnyviewCParser.TemplatenameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#pmexpression}.
	 * @param ctx the parse tree
	 */
	void enterPmexpression(@NotNull AnyviewCParser.PmexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#pmexpression}.
	 * @param ctx the parse tree
	 */
	void exitPmexpression(@NotNull AnyviewCParser.PmexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumbase}.
	 * @param ctx the parse tree
	 */
	void enterEnumbase(@NotNull AnyviewCParser.EnumbaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumbase}.
	 * @param ctx the parse tree
	 */
	void exitEnumbase(@NotNull AnyviewCParser.EnumbaseContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templateargumentlist}.
	 * @param ctx the parse tree
	 */
	void enterTemplateargumentlist(@NotNull AnyviewCParser.TemplateargumentlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templateargumentlist}.
	 * @param ctx the parse tree
	 */
	void exitTemplateargumentlist(@NotNull AnyviewCParser.TemplateargumentlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#constantexpression}.
	 * @param ctx the parse tree
	 */
	void enterConstantexpression(@NotNull AnyviewCParser.ConstantexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#constantexpression}.
	 * @param ctx the parse tree
	 */
	void exitConstantexpression(@NotNull AnyviewCParser.ConstantexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declspecifierseq}.
	 * @param ctx the parse tree
	 */
	void enterDeclspecifierseq(@NotNull AnyviewCParser.DeclspecifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declspecifierseq}.
	 * @param ctx the parse tree
	 */
	void exitDeclspecifierseq(@NotNull AnyviewCParser.DeclspecifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumhead}.
	 * @param ctx the parse tree
	 */
	void enterEnumhead(@NotNull AnyviewCParser.EnumheadContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumhead}.
	 * @param ctx the parse tree
	 */
	void exitEnumhead(@NotNull AnyviewCParser.EnumheadContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#explicitspecialization}.
	 * @param ctx the parse tree
	 */
	void enterExplicitspecialization(@NotNull AnyviewCParser.ExplicitspecializationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#explicitspecialization}.
	 * @param ctx the parse tree
	 */
	void exitExplicitspecialization(@NotNull AnyviewCParser.ExplicitspecializationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templateparameter}.
	 * @param ctx the parse tree
	 */
	void enterTemplateparameter(@NotNull AnyviewCParser.TemplateparameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templateparameter}.
	 * @param ctx the parse tree
	 */
	void exitTemplateparameter(@NotNull AnyviewCParser.TemplateparameterContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#multiplicativeexpression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeexpression(@NotNull AnyviewCParser.MultiplicativeexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#multiplicativeexpression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeexpression(@NotNull AnyviewCParser.MultiplicativeexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#virtspecifierseq}.
	 * @param ctx the parse tree
	 */
	void enterVirtspecifierseq(@NotNull AnyviewCParser.VirtspecifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#virtspecifierseq}.
	 * @param ctx the parse tree
	 */
	void exitVirtspecifierseq(@NotNull AnyviewCParser.VirtspecifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#cvqualifier}.
	 * @param ctx the parse tree
	 */
	void enterCvqualifier(@NotNull AnyviewCParser.CvqualifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#cvqualifier}.
	 * @param ctx the parse tree
	 */
	void exitCvqualifier(@NotNull AnyviewCParser.CvqualifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typenamespecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypenamespecifier(@NotNull AnyviewCParser.TypenamespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typenamespecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypenamespecifier(@NotNull AnyviewCParser.TypenamespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templateparameterlist}.
	 * @param ctx the parse tree
	 */
	void enterTemplateparameterlist(@NotNull AnyviewCParser.TemplateparameterlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templateparameterlist}.
	 * @param ctx the parse tree
	 */
	void exitTemplateparameterlist(@NotNull AnyviewCParser.TemplateparameterlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#templateid}.
	 * @param ctx the parse tree
	 */
	void enterTemplateid(@NotNull AnyviewCParser.TemplateidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#templateid}.
	 * @param ctx the parse tree
	 */
	void exitTemplateid(@NotNull AnyviewCParser.TemplateidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#storageclassspecifier}.
	 * @param ctx the parse tree
	 */
	void enterStorageclassspecifier(@NotNull AnyviewCParser.StorageclassspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#storageclassspecifier}.
	 * @param ctx the parse tree
	 */
	void exitStorageclassspecifier(@NotNull AnyviewCParser.StorageclassspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initcapture}.
	 * @param ctx the parse tree
	 */
	void enterInitcapture(@NotNull AnyviewCParser.InitcaptureContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initcapture}.
	 * @param ctx the parse tree
	 */
	void exitInitcapture(@NotNull AnyviewCParser.InitcaptureContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#additiveexpression}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveexpression(@NotNull AnyviewCParser.AdditiveexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#additiveexpression}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveexpression(@NotNull AnyviewCParser.AdditiveexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 */
	void enterCvqualifierseq(@NotNull AnyviewCParser.CvqualifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 */
	void exitCvqualifierseq(@NotNull AnyviewCParser.CvqualifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#unnamednamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void enterUnnamednamespacedefinition(@NotNull AnyviewCParser.UnnamednamespacedefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#unnamednamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void exitUnnamednamespacedefinition(@NotNull AnyviewCParser.UnnamednamespacedefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#usingdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterUsingdeclaration(@NotNull AnyviewCParser.UsingdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#usingdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitUsingdeclaration(@NotNull AnyviewCParser.UsingdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initializer}.
	 * @param ctx the parse tree
	 */
	void enterInitializer(@NotNull AnyviewCParser.InitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initializer}.
	 * @param ctx the parse tree
	 */
	void exitInitializer(@NotNull AnyviewCParser.InitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#newtypeid}.
	 * @param ctx the parse tree
	 */
	void enterNewtypeid(@NotNull AnyviewCParser.NewtypeidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#newtypeid}.
	 * @param ctx the parse tree
	 */
	void exitNewtypeid(@NotNull AnyviewCParser.NewtypeidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#parameterdeclarationclause}.
	 * @param ctx the parse tree
	 */
	void enterParameterdeclarationclause(@NotNull AnyviewCParser.ParameterdeclarationclauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#parameterdeclarationclause}.
	 * @param ctx the parse tree
	 */
	void exitParameterdeclarationclause(@NotNull AnyviewCParser.ParameterdeclarationclauseContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#basespecifierlist}.
	 * @param ctx the parse tree
	 */
	void enterBasespecifierlist(@NotNull AnyviewCParser.BasespecifierlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#basespecifierlist}.
	 * @param ctx the parse tree
	 */
	void exitBasespecifierlist(@NotNull AnyviewCParser.BasespecifierlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#newdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNewdeclarator(@NotNull AnyviewCParser.NewdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#newdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNewdeclarator(@NotNull AnyviewCParser.NewdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributespecifierseq}.
	 * @param ctx the parse tree
	 */
	void enterAttributespecifierseq(@NotNull AnyviewCParser.AttributespecifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributespecifierseq}.
	 * @param ctx the parse tree
	 */
	void exitAttributespecifierseq(@NotNull AnyviewCParser.AttributespecifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#jumpstatement}.
	 * @param ctx the parse tree
	 */
	void enterJumpstatement(@NotNull AnyviewCParser.JumpstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#jumpstatement}.
	 * @param ctx the parse tree
	 */
	void exitJumpstatement(@NotNull AnyviewCParser.JumpstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noptrnewdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNoptrnewdeclarator(@NotNull AnyviewCParser.NoptrnewdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noptrnewdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNoptrnewdeclarator(@NotNull AnyviewCParser.NoptrnewdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#throwexpression}.
	 * @param ctx the parse tree
	 */
	void enterThrowexpression(@NotNull AnyviewCParser.ThrowexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#throwexpression}.
	 * @param ctx the parse tree
	 */
	void exitThrowexpression(@NotNull AnyviewCParser.ThrowexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#conversiontypeid}.
	 * @param ctx the parse tree
	 */
	void enterConversiontypeid(@NotNull AnyviewCParser.ConversiontypeidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#conversiontypeid}.
	 * @param ctx the parse tree
	 */
	void exitConversiontypeid(@NotNull AnyviewCParser.ConversiontypeidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typespecifierseq}.
	 * @param ctx the parse tree
	 */
	void enterTypespecifierseq(@NotNull AnyviewCParser.TypespecifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typespecifierseq}.
	 * @param ctx the parse tree
	 */
	void exitTypespecifierseq(@NotNull AnyviewCParser.TypespecifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumkey}.
	 * @param ctx the parse tree
	 */
	void enterEnumkey(@NotNull AnyviewCParser.EnumkeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumkey}.
	 * @param ctx the parse tree
	 */
	void exitEnumkey(@NotNull AnyviewCParser.EnumkeyContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#inclusiveorexpression}.
	 * @param ctx the parse tree
	 */
	void enterInclusiveorexpression(@NotNull AnyviewCParser.InclusiveorexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#inclusiveorexpression}.
	 * @param ctx the parse tree
	 */
	void exitInclusiveorexpression(@NotNull AnyviewCParser.InclusiveorexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#userdefinedliteral}.
	 * @param ctx the parse tree
	 */
	void enterUserdefinedliteral(@NotNull AnyviewCParser.UserdefinedliteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#userdefinedliteral}.
	 * @param ctx the parse tree
	 */
	void exitUserdefinedliteral(@NotNull AnyviewCParser.UserdefinedliteralContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#expressionstatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionstatement(@NotNull AnyviewCParser.ExpressionstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#expressionstatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionstatement(@NotNull AnyviewCParser.ExpressionstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typespecifier}.
	 * @param ctx the parse tree
	 */
	void enterTypespecifier(@NotNull AnyviewCParser.TypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typespecifier}.
	 * @param ctx the parse tree
	 */
	void exitTypespecifier(@NotNull AnyviewCParser.TypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attributescopedtoken}.
	 * @param ctx the parse tree
	 */
	void enterAttributescopedtoken(@NotNull AnyviewCParser.AttributescopedtokenContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attributescopedtoken}.
	 * @param ctx the parse tree
	 */
	void exitAttributescopedtoken(@NotNull AnyviewCParser.AttributescopedtokenContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#simpletypespecifier}.
	 * @param ctx the parse tree
	 */
	void enterSimpletypespecifier(@NotNull AnyviewCParser.SimpletypespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#simpletypespecifier}.
	 * @param ctx the parse tree
	 */
	void exitSimpletypespecifier(@NotNull AnyviewCParser.SimpletypespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumeratordefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratordefinition(@NotNull AnyviewCParser.EnumeratordefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumeratordefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratordefinition(@NotNull AnyviewCParser.EnumeratordefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#bracedinitlist}.
	 * @param ctx the parse tree
	 */
	void enterBracedinitlist(@NotNull AnyviewCParser.BracedinitlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#bracedinitlist}.
	 * @param ctx the parse tree
	 */
	void exitBracedinitlist(@NotNull AnyviewCParser.BracedinitlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classvirtspecifier}.
	 * @param ctx the parse tree
	 */
	void enterClassvirtspecifier(@NotNull AnyviewCParser.ClassvirtspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classvirtspecifier}.
	 * @param ctx the parse tree
	 */
	void exitClassvirtspecifier(@NotNull AnyviewCParser.ClassvirtspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#enumeratorlist}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorlist(@NotNull AnyviewCParser.EnumeratorlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#enumeratorlist}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorlist(@NotNull AnyviewCParser.EnumeratorlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#lambdadeclarator}.
	 * @param ctx the parse tree
	 */
	void enterLambdadeclarator(@NotNull AnyviewCParser.LambdadeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#lambdadeclarator}.
	 * @param ctx the parse tree
	 */
	void exitLambdadeclarator(@NotNull AnyviewCParser.LambdadeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull AnyviewCParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull AnyviewCParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#noexceptspecification}.
	 * @param ctx the parse tree
	 */
	void enterNoexceptspecification(@NotNull AnyviewCParser.NoexceptspecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#noexceptspecification}.
	 * @param ctx the parse tree
	 */
	void exitNoexceptspecification(@NotNull AnyviewCParser.NoexceptspecificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#simpledeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSimpledeclaration(@NotNull AnyviewCParser.SimpledeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#simpledeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSimpledeclaration(@NotNull AnyviewCParser.SimpledeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typeidlist}.
	 * @param ctx the parse tree
	 */
	void enterTypeidlist(@NotNull AnyviewCParser.TypeidlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typeidlist}.
	 * @param ctx the parse tree
	 */
	void exitTypeidlist(@NotNull AnyviewCParser.TypeidlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#memberdeclarator}.
	 * @param ctx the parse tree
	 */
	void enterMemberdeclarator(@NotNull AnyviewCParser.MemberdeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#memberdeclarator}.
	 * @param ctx the parse tree
	 */
	void exitMemberdeclarator(@NotNull AnyviewCParser.MemberdeclaratorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classspecifier}.
	 * @param ctx the parse tree
	 */
	void enterClassspecifier(@NotNull AnyviewCParser.ClassspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classspecifier}.
	 * @param ctx the parse tree
	 */
	void exitClassspecifier(@NotNull AnyviewCParser.ClassspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(@NotNull AnyviewCParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(@NotNull AnyviewCParser.AttributeContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#opaqueenumdeclaration}.
	 * @param ctx the parse tree
	 */
	void enterOpaqueenumdeclaration(@NotNull AnyviewCParser.OpaqueenumdeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#opaqueenumdeclaration}.
	 * @param ctx the parse tree
	 */
	void exitOpaqueenumdeclaration(@NotNull AnyviewCParser.OpaqueenumdeclarationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#statementseq}.
	 * @param ctx the parse tree
	 */
	void enterStatementseq(@NotNull AnyviewCParser.StatementseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#statementseq}.
	 * @param ctx the parse tree
	 */
	void exitStatementseq(@NotNull AnyviewCParser.StatementseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#qualifiedid}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedid(@NotNull AnyviewCParser.QualifiedidContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#qualifiedid}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedid(@NotNull AnyviewCParser.QualifiedidContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#trailingtypespecifierseq}.
	 * @param ctx the parse tree
	 */
	void enterTrailingtypespecifierseq(@NotNull AnyviewCParser.TrailingtypespecifierseqContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#trailingtypespecifierseq}.
	 * @param ctx the parse tree
	 */
	void exitTrailingtypespecifierseq(@NotNull AnyviewCParser.TrailingtypespecifierseqContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declaratorid}.
	 * @param ctx the parse tree
	 */
	void enterDeclaratorid(@NotNull AnyviewCParser.DeclaratoridContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declaratorid}.
	 * @param ctx the parse tree
	 */
	void exitDeclaratorid(@NotNull AnyviewCParser.DeclaratoridContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#exceptionspecification}.
	 * @param ctx the parse tree
	 */
	void enterExceptionspecification(@NotNull AnyviewCParser.ExceptionspecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#exceptionspecification}.
	 * @param ctx the parse tree
	 */
	void exitExceptionspecification(@NotNull AnyviewCParser.ExceptionspecificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#classhead}.
	 * @param ctx the parse tree
	 */
	void enterClasshead(@NotNull AnyviewCParser.ClassheadContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#classhead}.
	 * @param ctx the parse tree
	 */
	void exitClasshead(@NotNull AnyviewCParser.ClassheadContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#functionspecifier}.
	 * @param ctx the parse tree
	 */
	void enterFunctionspecifier(@NotNull AnyviewCParser.FunctionspecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#functionspecifier}.
	 * @param ctx the parse tree
	 */
	void exitFunctionspecifier(@NotNull AnyviewCParser.FunctionspecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#basespecifier}.
	 * @param ctx the parse tree
	 */
	void enterBasespecifier(@NotNull AnyviewCParser.BasespecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#basespecifier}.
	 * @param ctx the parse tree
	 */
	void exitBasespecifier(@NotNull AnyviewCParser.BasespecifierContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#initdeclaratorlist}.
	 * @param ctx the parse tree
	 */
	void enterInitdeclaratorlist(@NotNull AnyviewCParser.InitdeclaratorlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#initdeclaratorlist}.
	 * @param ctx the parse tree
	 */
	void exitInitdeclaratorlist(@NotNull AnyviewCParser.InitdeclaratorlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#dynamicexceptionspecification}.
	 * @param ctx the parse tree
	 */
	void enterDynamicexceptionspecification(@NotNull AnyviewCParser.DynamicexceptionspecificationContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#dynamicexceptionspecification}.
	 * @param ctx the parse tree
	 */
	void exitDynamicexceptionspecification(@NotNull AnyviewCParser.DynamicexceptionspecificationContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#equalityexpression}.
	 * @param ctx the parse tree
	 */
	void enterEqualityexpression(@NotNull AnyviewCParser.EqualityexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#equalityexpression}.
	 * @param ctx the parse tree
	 */
	void exitEqualityexpression(@NotNull AnyviewCParser.EqualityexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#assignmentoperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentoperator(@NotNull AnyviewCParser.AssignmentoperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#assignmentoperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentoperator(@NotNull AnyviewCParser.AssignmentoperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#extensionnamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void enterExtensionnamespacedefinition(@NotNull AnyviewCParser.ExtensionnamespacedefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#extensionnamespacedefinition}.
	 * @param ctx the parse tree
	 */
	void exitExtensionnamespacedefinition(@NotNull AnyviewCParser.ExtensionnamespacedefinitionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#ptroperator}.
	 * @param ctx the parse tree
	 */
	void enterPtroperator(@NotNull AnyviewCParser.PtroperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#ptroperator}.
	 * @param ctx the parse tree
	 */
	void exitPtroperator(@NotNull AnyviewCParser.PtroperatorContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#expressionlist}.
	 * @param ctx the parse tree
	 */
	void enterExpressionlist(@NotNull AnyviewCParser.ExpressionlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#expressionlist}.
	 * @param ctx the parse tree
	 */
	void exitExpressionlist(@NotNull AnyviewCParser.ExpressionlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#meminitializer}.
	 * @param ctx the parse tree
	 */
	void enterMeminitializer(@NotNull AnyviewCParser.MeminitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#meminitializer}.
	 * @param ctx the parse tree
	 */
	void exitMeminitializer(@NotNull AnyviewCParser.MeminitializerContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#typename}.
	 * @param ctx the parse tree
	 */
	void enterTypename(@NotNull AnyviewCParser.TypenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#typename}.
	 * @param ctx the parse tree
	 */
	void exitTypename(@NotNull AnyviewCParser.TypenameContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#logicalorexpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalorexpression(@NotNull AnyviewCParser.LogicalorexpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#logicalorexpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalorexpression(@NotNull AnyviewCParser.LogicalorexpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#parameterdeclarationlist}.
	 * @param ctx the parse tree
	 */
	void enterParameterdeclarationlist(@NotNull AnyviewCParser.ParameterdeclarationlistContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#parameterdeclarationlist}.
	 * @param ctx the parse tree
	 */
	void exitParameterdeclarationlist(@NotNull AnyviewCParser.ParameterdeclarationlistContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#declarationstatement}.
	 * @param ctx the parse tree
	 */
	void enterDeclarationstatement(@NotNull AnyviewCParser.DeclarationstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#declarationstatement}.
	 * @param ctx the parse tree
	 */
	void exitDeclarationstatement(@NotNull AnyviewCParser.DeclarationstatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link AnyviewCParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull AnyviewCParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link AnyviewCParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(@NotNull AnyviewCParser.LiteralContext ctx);
}
