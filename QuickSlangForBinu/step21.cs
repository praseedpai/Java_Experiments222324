using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
//---- Program to demonstrate
//---- just a symbolic place holder to satisfy the compiler
public class RUNTIME_CONTEXT {
}

//----- enumerator for the operator 
public enum OPERATOR{
       ILLEGAL = -1, PLUS, MINUS, DIV, MUL, UNARY_PLUS,UNARY_MINUS
}
 //----- Added in Step 2 ....for Lexical Analysis and Parsing 
 public enum TOKEN
    {
        ILLEGAL_TOKEN = -1, // Not a Token
        TOK_PLUS = 1, // '+'
        TOK_MUL, // '*'
        TOK_DIV, // '/'
        TOK_SUB, // '-'
        TOK_OPAREN, // '('
        TOK_CPAREN, // ')'
        TOK_DOUBLE, // '('
        TOK_NULL // End of string
}
//---- Lexer breaks the input into tokens
//
public interface IExprVisitor{
   double Visit(NumericConstant  num);
   double Visit(BinaryExp  bin) ;
   double Visit(UnaryExp un) ;
}

//----- Visitor to evaluate the code...
public class TreeEvaluatorVisitor : IExprVisitor{
 
   public double Visit(NumericConstant num){ return num.GetNumber();}
   public double Visit(BinaryExp bin) {
     OPERATOR temp = bin.GetOP(); 
     double lval = bin.getLeft().accept(this);
     double rval = bin.getRight().accept(this);
     return (temp == OPERATOR.PLUS) ? lval + rval: (temp == OPERATOR.MUL) ? 
         lval*rval : (temp == OPERATOR.DIV)? lval/rval : lval-rval;  
   }
   public double Visit(UnaryExp un) {
     OPERATOR temp = un.GetOP(); 
     double rval = un.getRight().accept(this);
     return (temp == OPERATOR.PLUS)  ? +rval : -rval;
   }
}

//-----------------------------------------
class ReversePolishEvaluator :  IExprVisitor {
  // 2 + 3*4 => 2 3 4 * +
   public double Visit(NumericConstant num){Console.Write( num.GetNumber() +" "); return 42;}
   public double Visit(BinaryExp bin){
     bin.getLeft().accept(this); bin.getRight().accept(this);
     OPERATOR temp = bin.GetOP();
     Console.Write((  (temp==OPERATOR.PLUS) ? " + " :(temp==OPERATOR.MUL) ?
              " * " : (temp == OPERATOR.DIV) ? " / ": "  - " )) ; return 42;
   }
   public double Visit(UnaryExp un){
     OPERATOR temp = un.GetOP();
      un.getRight().accept(this);
     Console.Write( (temp == OPERATOR.PLUS) ?" (+) " : " (-) "); 
      return 42;
   }
};

public class TStack{
   double[] stk;
   int top_stack = 0; 
   public TStack(){
	stk = new double[256];
	top_stack = 0;
   }

   public void Clear() { top_stack = 0;}
   public void push(double dbl ) {
        if ( top_stack == 255 ){
	Console.WriteLine("Stack OverFlow"); 
	throw new Exception();
        }
        stk[top_stack++] = dbl;
   }
    public double  pop(){
	if ( top_stack == 0 ){
	      Console.WriteLine("Stack UnderFlow"); 
	      throw new Exception();
	}
	return stk[--top_stack];
  }

}

//----- Visitor to evaluate the code...
public class StackEvaluatorVisitor : IExprVisitor{
   private TStack _stk = new TStack();
   public double GetNumber() { return _stk.pop(); }
   public double Visit(NumericConstant num){  _stk.push(num.GetNumber()); return -1; }
   public double Visit(BinaryExp bin) {
     OPERATOR temp = bin.GetOP(); 
     double lval = bin.getLeft().accept(this);
     double rval = bin.getRight().accept(this);

     lval = _stk.pop();
     rval = _stk.pop();
     
    if  (temp == OPERATOR.PLUS) 
                    _stk.push(lval + rval);
    else if  (temp == OPERATOR.MUL) 
         _stk.push(lval*rval);
     else if  (temp == OPERATOR.DIV)
                       _stk.push(lval/rval) ;
     else
	_stk.push( lval-rval);  
     return -1;
   }
   public double Visit(UnaryExp un) {
     OPERATOR temp = un.GetOP(); 
     double rval = un.getRight().accept(this);
     rval = _stk.pop();
    if  (temp == OPERATOR.PLUS)  
                  _stk.push(+rval );
    else 
	 _stk.push(-rval);
     return -1;
   }
}



public class Lexer
    {
        String IExpr; // Expression string
        int index; // index into a character
        int length; // Length of the string
        double number; // Last grabbed number from the stream
        /////////////////////////////////////////////
        //
        // Ctor
        //
        //
        public Lexer(String Expr)
        {
            IExpr = Expr;
            length = IExpr.Length;
            index = 0;
        }
        /////////////////////////////////////////////////////
        // Grab the next token from the stream
        //
        //
        //
        //
        public TOKEN GetToken()
        {
            TOKEN tok = TOKEN.ILLEGAL_TOKEN;
            ////////////////////////////////////////////////////////////
            //
            // Skip the white space
            //
            while (index < length &&
            (IExpr[index] == ' ' || IExpr[index] == '\t'))
                index++;
            //////////////////////////////////////////////
            //
            // End of string ? return NULL;
            //
            if (index == length)
                return TOKEN.TOK_NULL;
            /////////////////////////////////////////////////
            //
            //
            //
            switch (IExpr[index])
            {
                case '+':
                    tok = TOKEN.TOK_PLUS;
                    index++;
                    break;
                case '-':
                    tok = TOKEN.TOK_SUB;
                    index++;
                    break;
                case '/':
                    tok = TOKEN.TOK_DIV;
                    index++;
                    break;
                case '*':
                    tok = TOKEN.TOK_MUL;
                    index++;
                    break;
                case '(':
                    tok = TOKEN.TOK_OPAREN;
                    index++;
                    break;
                case ')':
                    tok = TOKEN.TOK_CPAREN;
                    index++;
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    {
                        String str = "";
                        while (index < length &&
                        (IExpr[index] == '0' ||
                        IExpr[index] == '1' ||
                        IExpr[index] == '2' ||
                        IExpr[index] == '3' ||
                        IExpr[index] == '4' ||
                        IExpr[index] == '5' ||
                        IExpr[index] == '6' ||
                        IExpr[index] == '7' ||
                        IExpr[index] == '8' ||
                        IExpr[index] == '9'))
                        {
                            str += Convert.ToString(IExpr[index]);
                            index++;
                        }
                        number = Convert.ToDouble(str);
                        tok = TOKEN.TOK_DOUBLE;
                    }
                    break;
                default:
                    Console.WriteLine("Error While Analyzing Tokens");
                    throw new Exception();
            }
            return tok;
        }
        public double GetNumber() { return number; }
    }



//---- Expression is what you evaluates for value
public abstract class Exp{
  
      public abstract double accept(IExprVisitor  expr_vis) ;
}

public class NumericConstant : Exp {
        //---- value holder
        private double _value;
        //---- ctor
        public NumericConstant(double value){  _value = value; }
        //--- Evaluate 
       
        public override double accept(IExprVisitor  expr_vis)  { return expr_vis.Visit(this); }
        public double GetNumber() { return _value; }
}

//----- Binary Expression 
   public class BinaryExp : Exp
    {
        //---- Left Expression ( _ex1 ) , Right Expression ( _ex2)
        private Exp _ex1, _ex2;
        //------- Operator Enum
        private OPERATOR _op;
        //---- ctor 
        public BinaryExp(Exp a, Exp b, OPERATOR op) {
            _ex1 = a;  _ex2 = b;  _op = op;
        }
        public OPERATOR GetOP() { return _op;}
        public Exp getLeft() { return _ex1; }
        public Exp getRight() { return _ex2; }
        //---- Evaluate is a recursive routine 
    
            public override double accept(IExprVisitor  expr_vis)  { return expr_vis.Visit(this); }

}

///------Modeling Unary Expression ( + | - )

public class UnaryExp : Exp {
        private Exp _ex1;
        private OPERATOR _op;

        public UnaryExp(Exp a, OPERATOR op){
            _ex1 = a;
            _op = op;
        }
     
          public OPERATOR GetOP() { return _op;}
           public Exp getLeft() { return null; }
           public Exp getRight() { return _ex1; }
          public override double accept(IExprVisitor  expr_vis)  { return expr_vis.Visit(this); }

}
///----------------------------------------- Added in the Step 2
//--------- Parser need not inherit from the Lexer...
//---- used for Convenience
//

public class RDParser : Lexer{
        TOKEN Current_Token;
        //---- Public Part
         public RDParser(String str) : base(str){}
         public Exp CallExpr(){
            Current_Token = GetToken();
            return Expr();
        }

         // implementation details
         //  <Expr> :=  <Term> | <Term> { + | - } <Expr>
         public Exp Expr() {
            TOKEN l_token;
            Exp RetValue = Term();
            while (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB){
                l_token = Current_Token;
                Current_Token = GetToken();    Exp e1 = Expr();
                RetValue = new BinaryExp(RetValue, e1,
                    l_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
            }
            return RetValue;
        }
        // ---- <Term> := <Factor> | <Factor> { / | * } <Term>
        public Exp Term(){
            TOKEN l_token;
            Exp RetValue = Factor();
            while (Current_Token == TOKEN.TOK_MUL || Current_Token == TOKEN.TOK_DIV) {
                l_token = Current_Token;
                Current_Token = GetToken(); Exp e1 = Term();
                RetValue = new BinaryExp(RetValue, e1,
                    l_token == TOKEN.TOK_MUL ? OPERATOR.MUL : OPERATOR.DIV);
             }
             return RetValue;
        }
        //------ <Factor> := <Number> |  '(' <Expr> ')' |  {+ | - } <Factor>
         public Exp Factor()
        {
            TOKEN l_token;
            Exp RetValue = null;
            if (Current_Token == TOKEN.TOK_DOUBLE){
                RetValue = new NumericConstant(GetNumber());
                Current_Token = GetToken();
            }
            else if (Current_Token == TOKEN.TOK_OPAREN){

                Current_Token = GetToken();  RetValue = Expr();  // Recurse

                if (Current_Token != TOKEN.TOK_CPAREN)
                {
                    Console.WriteLine("Missing Closing Parenthesis\n");
                    throw new Exception();

                }
                Current_Token = GetToken();
            }

            else if (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB)
            {
                l_token = Current_Token;
                Current_Token = GetToken();
                RetValue = Factor();

                RetValue = new UnaryExp(RetValue,
                     l_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);
            }
            else
            {

                Console.WriteLine("Illegal Token");
                throw new Exception();
            }


            return RetValue;

        }
}




public class AbstractBuilder{}
public class ExpressionBuilder : AbstractBuilder {
        public string _expr_string;
        public ExpressionBuilder(string expr){
            _expr_string = expr;
        }
        public Exp GetExpression()
        {
            try
            {
                RDParser p = new RDParser(_expr_string);
                return p.CallExpr();
            }
            catch (Exception)
            { return null;}
        }
}
//
//
//
//




class Program
    {
        static void Main(string[] args) {

            if ( args.Length != 1 )
                        return ;

            ExpressionBuilder b = new ExpressionBuilder(args[0]);
            Exp e = b.GetExpression();
            double x =  e.accept(new TreeEvaluatorVisitor());
            Console.WriteLine(x);
            e.accept(new ReversePolishEvaluator());
            Console.WriteLine("   ");
            StackEvaluatorVisitor m = new StackEvaluatorVisitor();
            e.accept(m);
            Console.WriteLine(m.GetNumber());
            Console.Read();
        }
    }








