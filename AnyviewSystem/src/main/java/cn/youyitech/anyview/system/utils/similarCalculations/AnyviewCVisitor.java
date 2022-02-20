package cn.youyitech.anyview.system.utils.similarCalculations;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
* This interface defines a complete generic visitor for a parse tree produced
* by {@link AnyviewCParser}.
*
* @param <T> The return type of the visit operation. Use {@link Void} for
* operations with no return type.
*/
public interface AnyviewCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#capturedefault}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapturedefault(@NotNull AnyviewCParser.CapturedefaultContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarator(@NotNull AnyviewCParser.DeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#postfixexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixexpression(@NotNull AnyviewCParser.PostfixexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributenamespace}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributenamespace(@NotNull AnyviewCParser.AttributenamespaceContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#handlerseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandlerseq(@NotNull AnyviewCParser.HandlerseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#ctorinitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCtorinitializer(@NotNull AnyviewCParser.CtorinitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#primaryexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryexpression(@NotNull AnyviewCParser.PrimaryexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#assignmentexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentexpression(@NotNull AnyviewCParser.AssignmentexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#capturelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapturelist(@NotNull AnyviewCParser.CapturelistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#relationalexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalexpression(@NotNull AnyviewCParser.RelationalexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#unqualifiedid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnqualifiedid(@NotNull AnyviewCParser.UnqualifiedidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#parameterdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterdeclaration(@NotNull AnyviewCParser.ParameterdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namespacebody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacebody(@NotNull AnyviewCParser.NamespacebodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributedeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributedeclaration(@NotNull AnyviewCParser.AttributedeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#selectionstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelectionstatement(@NotNull AnyviewCParser.SelectionstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#pseudodestructorname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPseudodestructorname(@NotNull AnyviewCParser.PseudodestructornameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templateargument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateargument(@NotNull AnyviewCParser.TemplateargumentContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#baseclause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBaseclause(@NotNull AnyviewCParser.BaseclauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#functiontryblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiontryblock(@NotNull AnyviewCParser.FunctiontryblockContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#deleteexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeleteexpression(@NotNull AnyviewCParser.DeleteexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#literaloperatorid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteraloperatorid(@NotNull AnyviewCParser.LiteraloperatoridContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#linkagespecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLinkagespecification(@NotNull AnyviewCParser.LinkagespecificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#simplecapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimplecapture(@NotNull AnyviewCParser.SimplecaptureContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#meminitializerid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeminitializerid(@NotNull AnyviewCParser.MeminitializeridContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noexceptexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoexceptexpression(@NotNull AnyviewCParser.NoexceptexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typeid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeid(@NotNull AnyviewCParser.TypeidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#pointerliteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointerliteral(@NotNull AnyviewCParser.PointerliteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#virtspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVirtspecifier(@NotNull AnyviewCParser.VirtspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#shiftexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftexpression(@NotNull AnyviewCParser.ShiftexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#functiondefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctiondefinition(@NotNull AnyviewCParser.FunctiondefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumspecifier(@NotNull AnyviewCParser.EnumspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#balancedtokenseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBalancedtokenseq(@NotNull AnyviewCParser.BalancedtokenseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#iterationstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIterationstatement(@NotNull AnyviewCParser.IterationstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#unaryexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryexpression(@NotNull AnyviewCParser.UnaryexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(@NotNull AnyviewCParser.OperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typedefname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedefname(@NotNull AnyviewCParser.TypedefnameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitdeclarator(@NotNull AnyviewCParser.InitdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributespecifier(@NotNull AnyviewCParser.AttributespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classheadname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassheadname(@NotNull AnyviewCParser.ClassheadnameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#balancedtoken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBalancedtoken(@NotNull AnyviewCParser.BalancedtokenContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(@NotNull AnyviewCParser.ExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#operatorfunctionid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperatorfunctionid(@NotNull AnyviewCParser.OperatorfunctionidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#lambdaintroducer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaintroducer(@NotNull AnyviewCParser.LambdaintroducerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#compoundstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundstatement(@NotNull AnyviewCParser.CompoundstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#newinitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewinitializer(@NotNull AnyviewCParser.NewinitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#memberdeclaratorlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberdeclaratorlist(@NotNull AnyviewCParser.MemberdeclaratorlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#newplacement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewplacement(@NotNull AnyviewCParser.NewplacementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#rightShiftAssign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightShiftAssign(@NotNull AnyviewCParser.RightShiftAssignContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#elaboratedtypespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElaboratedtypespecifier(@NotNull AnyviewCParser.ElaboratedtypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#static_assertdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatic_assertdeclaration(@NotNull AnyviewCParser.Static_assertdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#idexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdexpression(@NotNull AnyviewCParser.IdexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#qualifiednamespacespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiednamespacespecifier(@NotNull AnyviewCParser.QualifiednamespacespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#purespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPurespecifier(@NotNull AnyviewCParser.PurespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#forinitstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForinitstatement(@NotNull AnyviewCParser.ForinitstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumerator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumerator(@NotNull AnyviewCParser.EnumeratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#usingdirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsingdirective(@NotNull AnyviewCParser.UsingdirectiveContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#capture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCapture(@NotNull AnyviewCParser.CaptureContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#blockdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockdeclaration(@NotNull AnyviewCParser.BlockdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#accessspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAccessspecifier(@NotNull AnyviewCParser.AccessspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(@NotNull AnyviewCParser.DeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#conversionfunctionid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversionfunctionid(@NotNull AnyviewCParser.ConversionfunctionidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#unaryoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryoperator(@NotNull AnyviewCParser.UnaryoperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(@NotNull AnyviewCParser.ConditionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#functionbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionbody(@NotNull AnyviewCParser.FunctionbodyContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namespacealias}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacealias(@NotNull AnyviewCParser.NamespacealiasContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#ptrdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPtrdeclarator(@NotNull AnyviewCParser.PtrdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributelist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributelist(@NotNull AnyviewCParser.AttributelistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classkey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasskey(@NotNull AnyviewCParser.ClasskeyContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namespacealiasdefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacealiasdefinition(@NotNull AnyviewCParser.NamespacealiasdefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#forrangeinitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForrangeinitializer(@NotNull AnyviewCParser.ForrangeinitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#parametersandqualifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametersandqualifiers(@NotNull AnyviewCParser.ParametersandqualifiersContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initializerclause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerclause(@NotNull AnyviewCParser.InitializerclauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributeargumentclause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeargumentclause(@NotNull AnyviewCParser.AttributeargumentclauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclspecifier(@NotNull AnyviewCParser.DeclspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#braceorequalinitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBraceorequalinitializer(@NotNull AnyviewCParser.BraceorequalinitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#trailingreturntype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingreturntype(@NotNull AnyviewCParser.TrailingreturntypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#abstractpackdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractpackdeclarator(@NotNull AnyviewCParser.AbstractpackdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#exclusiveorexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveorexpression(@NotNull AnyviewCParser.ExclusiveorexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initializerlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializerlist(@NotNull AnyviewCParser.InitializerlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#alignmentspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlignmentspecifier(@NotNull AnyviewCParser.AlignmentspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#ptrabstractdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPtrabstractdeclarator(@NotNull AnyviewCParser.PtrabstractdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#forrangedeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForrangedeclaration(@NotNull AnyviewCParser.ForrangedeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#logicalandexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalandexpression(@NotNull AnyviewCParser.LogicalandexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#abstractdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAbstractdeclarator(@NotNull AnyviewCParser.AbstractdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noptrabstractpackdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoptrabstractpackdeclarator(@NotNull AnyviewCParser.NoptrabstractpackdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#basetypespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasetypespecifier(@NotNull AnyviewCParser.BasetypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#booleanliteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanliteral(@NotNull AnyviewCParser.BooleanliteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namespacedefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacedefinition(@NotNull AnyviewCParser.NamespacedefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#rightShift}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightShift(@NotNull AnyviewCParser.RightShiftContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noptrabstractdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoptrabstractdeclarator(@NotNull AnyviewCParser.NoptrabstractdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namespacename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamespacename(@NotNull AnyviewCParser.NamespacenameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#nestednamespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNestednamespecifier(@NotNull AnyviewCParser.NestednamespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classordecltype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassordecltype(@NotNull AnyviewCParser.ClassordecltypeContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#tryblock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryblock(@NotNull AnyviewCParser.TryblockContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#handler}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHandler(@NotNull AnyviewCParser.HandlerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#explicitinstantiation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitinstantiation(@NotNull AnyviewCParser.ExplicitinstantiationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noptrdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoptrdeclarator(@NotNull AnyviewCParser.NoptrdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumname(@NotNull AnyviewCParser.EnumnameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#simpletemplateid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpletemplateid(@NotNull AnyviewCParser.SimpletemplateidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#asmdefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsmdefinition(@NotNull AnyviewCParser.AsmdefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#originalnamespacename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOriginalnamespacename(@NotNull AnyviewCParser.OriginalnamespacenameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#labeledstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledstatement(@NotNull AnyviewCParser.LabeledstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#conversiondeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversiondeclarator(@NotNull AnyviewCParser.ConversiondeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#lambdaexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaexpression(@NotNull AnyviewCParser.LambdaexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typeparameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeparameter(@NotNull AnyviewCParser.TypeparameterContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#trailingtypespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingtypespecifier(@NotNull AnyviewCParser.TrailingtypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declarationseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationseq(@NotNull AnyviewCParser.DeclarationseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#translationunit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTranslationunit(@NotNull AnyviewCParser.TranslationunitContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#andexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndexpression(@NotNull AnyviewCParser.AndexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templatedeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplatedeclaration(@NotNull AnyviewCParser.TemplatedeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#newexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewexpression(@NotNull AnyviewCParser.NewexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#lambdacapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdacapture(@NotNull AnyviewCParser.LambdacaptureContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#memberdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberdeclaration(@NotNull AnyviewCParser.MemberdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassname(@NotNull AnyviewCParser.ClassnameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#emptydeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptydeclaration(@NotNull AnyviewCParser.EmptydeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#aliasdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAliasdeclaration(@NotNull AnyviewCParser.AliasdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#refqualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRefqualifier(@NotNull AnyviewCParser.RefqualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#namednamespacedefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNamednamespacedefinition(@NotNull AnyviewCParser.NamednamespacedefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#conditionalexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalexpression(@NotNull AnyviewCParser.ConditionalexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#meminitializerlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeminitializerlist(@NotNull AnyviewCParser.MeminitializerlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#decltypespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecltypespecifier(@NotNull AnyviewCParser.DecltypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#originalnamespacedefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOriginalnamespacedefinition(@NotNull AnyviewCParser.OriginalnamespacedefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#castexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastexpression(@NotNull AnyviewCParser.CastexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#exceptiondeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptiondeclaration(@NotNull AnyviewCParser.ExceptiondeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#memberspecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberspecification(@NotNull AnyviewCParser.MemberspecificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributetoken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributetoken(@NotNull AnyviewCParser.AttributetokenContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templatename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplatename(@NotNull AnyviewCParser.TemplatenameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#pmexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPmexpression(@NotNull AnyviewCParser.PmexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumbase}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumbase(@NotNull AnyviewCParser.EnumbaseContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templateargumentlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateargumentlist(@NotNull AnyviewCParser.TemplateargumentlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#constantexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantexpression(@NotNull AnyviewCParser.ConstantexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declspecifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclspecifierseq(@NotNull AnyviewCParser.DeclspecifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumhead}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumhead(@NotNull AnyviewCParser.EnumheadContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#explicitspecialization}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitspecialization(@NotNull AnyviewCParser.ExplicitspecializationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templateparameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateparameter(@NotNull AnyviewCParser.TemplateparameterContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#multiplicativeexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeexpression(@NotNull AnyviewCParser.MultiplicativeexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#virtspecifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVirtspecifierseq(@NotNull AnyviewCParser.VirtspecifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#cvqualifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCvqualifier(@NotNull AnyviewCParser.CvqualifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typenamespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypenamespecifier(@NotNull AnyviewCParser.TypenamespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templateparameterlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateparameterlist(@NotNull AnyviewCParser.TemplateparameterlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#templateid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplateid(@NotNull AnyviewCParser.TemplateidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#storageclassspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStorageclassspecifier(@NotNull AnyviewCParser.StorageclassspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initcapture}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitcapture(@NotNull AnyviewCParser.InitcaptureContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#additiveexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveexpression(@NotNull AnyviewCParser.AdditiveexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#cvqualifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCvqualifierseq(@NotNull AnyviewCParser.CvqualifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#unnamednamespacedefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnnamednamespacedefinition(@NotNull AnyviewCParser.UnnamednamespacedefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#usingdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUsingdeclaration(@NotNull AnyviewCParser.UsingdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitializer(@NotNull AnyviewCParser.InitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#newtypeid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewtypeid(@NotNull AnyviewCParser.NewtypeidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#parameterdeclarationclause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterdeclarationclause(@NotNull AnyviewCParser.ParameterdeclarationclauseContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#basespecifierlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasespecifierlist(@NotNull AnyviewCParser.BasespecifierlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#newdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewdeclarator(@NotNull AnyviewCParser.NewdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributespecifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributespecifierseq(@NotNull AnyviewCParser.AttributespecifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#jumpstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpstatement(@NotNull AnyviewCParser.JumpstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noptrnewdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoptrnewdeclarator(@NotNull AnyviewCParser.NoptrnewdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#throwexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowexpression(@NotNull AnyviewCParser.ThrowexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#conversiontypeid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConversiontypeid(@NotNull AnyviewCParser.ConversiontypeidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typespecifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypespecifierseq(@NotNull AnyviewCParser.TypespecifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumkey}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumkey(@NotNull AnyviewCParser.EnumkeyContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#inclusiveorexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveorexpression(@NotNull AnyviewCParser.InclusiveorexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#userdefinedliteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUserdefinedliteral(@NotNull AnyviewCParser.UserdefinedliteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#expressionstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionstatement(@NotNull AnyviewCParser.ExpressionstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypespecifier(@NotNull AnyviewCParser.TypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attributescopedtoken}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributescopedtoken(@NotNull AnyviewCParser.AttributescopedtokenContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#simpletypespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpletypespecifier(@NotNull AnyviewCParser.SimpletypespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumeratordefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeratordefinition(@NotNull AnyviewCParser.EnumeratordefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#bracedinitlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracedinitlist(@NotNull AnyviewCParser.BracedinitlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classvirtspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassvirtspecifier(@NotNull AnyviewCParser.ClassvirtspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#enumeratorlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumeratorlist(@NotNull AnyviewCParser.EnumeratorlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#lambdadeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdadeclarator(@NotNull AnyviewCParser.LambdadeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull AnyviewCParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#noexceptspecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoexceptspecification(@NotNull AnyviewCParser.NoexceptspecificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#simpledeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpledeclaration(@NotNull AnyviewCParser.SimpledeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typeidlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeidlist(@NotNull AnyviewCParser.TypeidlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#memberdeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberdeclarator(@NotNull AnyviewCParser.MemberdeclaratorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassspecifier(@NotNull AnyviewCParser.ClassspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(@NotNull AnyviewCParser.AttributeContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#opaqueenumdeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpaqueenumdeclaration(@NotNull AnyviewCParser.OpaqueenumdeclarationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#statementseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementseq(@NotNull AnyviewCParser.StatementseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#qualifiedid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQualifiedid(@NotNull AnyviewCParser.QualifiedidContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#trailingtypespecifierseq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailingtypespecifierseq(@NotNull AnyviewCParser.TrailingtypespecifierseqContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declaratorid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaratorid(@NotNull AnyviewCParser.DeclaratoridContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#exceptionspecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionspecification(@NotNull AnyviewCParser.ExceptionspecificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#classhead}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasshead(@NotNull AnyviewCParser.ClassheadContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#functionspecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionspecifier(@NotNull AnyviewCParser.FunctionspecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#basespecifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasespecifier(@NotNull AnyviewCParser.BasespecifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#initdeclaratorlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInitdeclaratorlist(@NotNull AnyviewCParser.InitdeclaratorlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#dynamicexceptionspecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDynamicexceptionspecification(@NotNull AnyviewCParser.DynamicexceptionspecificationContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#equalityexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityexpression(@NotNull AnyviewCParser.EqualityexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#assignmentoperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentoperator(@NotNull AnyviewCParser.AssignmentoperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#extensionnamespacedefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExtensionnamespacedefinition(@NotNull AnyviewCParser.ExtensionnamespacedefinitionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#ptroperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPtroperator(@NotNull AnyviewCParser.PtroperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#expressionlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionlist(@NotNull AnyviewCParser.ExpressionlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#meminitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeminitializer(@NotNull AnyviewCParser.MeminitializerContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#typename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypename(@NotNull AnyviewCParser.TypenameContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#logicalorexpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalorexpression(@NotNull AnyviewCParser.LogicalorexpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#parameterdeclarationlist}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterdeclarationlist(@NotNull AnyviewCParser.ParameterdeclarationlistContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#declarationstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclarationstatement(@NotNull AnyviewCParser.DeclarationstatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link AnyviewCParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull AnyviewCParser.LiteralContext ctx);
}
