using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Collections;
//---- Program to demonstrate
//---- just a symbolic place holder to satisfy the compiler
public class RUNTIME_CONTEXT {
}
public enum TYPE_INFO{
        TYPE_ILLEGAL = -1, // NOT A TYPE
        TYPE_NUMERIC,      // IEEE Double precision floating point 
        TYPE_BOOL,         // Boolean Data type
        TYPE_STRING ,      // String data type 
        TYPE_ARRAY ,
        TYPE_MAP 
        
    }
//---------------- Symbol Info
  public class SYMBOL_INFO
    {
        public String SymbolName;   // Symbol Name
        public TYPE_INFO Type;      // Data type
        public String str_val;      // memory to hold string 
        public double dbl_val;      // memory to hold double
        public bool bol_val;      // memory to hold boolean
 
    }
//----- enumerator for the operator 
public enum OPERATOR{
       ILLEGAL = -1, PLUS, MINUS, DIV, MUL
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
        TOK_DOUBLE, // 'number'
        TOK_NULL, // End of string
        //------ Added in the Step 3
        TOK_PRINT, // Print Statement
        TOK_PRINTLN, // PrintLine
        TOK_UNQUOTED_STRING,
        TOK_SEMI // ; 
    }


 /// <summary>
    ///     Keyword Table Entry
    /// </summary>
    /// 
    public struct ValueTable
    {
        public TOKEN tok;          // Token id
        public String Value;       // Token string  
        public ValueTable(TOKEN tok, String Value)
        {
            this.tok = tok;
            this.Value = Value;

        }
    }


//---- Lexer breaks the input into tokens
//
 public class Lexer
    {

        private string _exp;
        private int _index;
        private int _length_string;
        private double _curr_num;
        private ValueTable[] _val = null;
        private string last_str;

        public Lexer(string exp)
        {
            _exp = exp;
            _length_string = exp.Length;
            _index = 0;

            _val = new ValueTable[2];
            _val[0] = new ValueTable(TOKEN.TOK_PRINT, "PRINT");
            _val[1] = new ValueTable(TOKEN.TOK_PRINTLN, "PRINTLINE");
        }


        public double Number
        {
            get { return _curr_num; }
        }

        public double GetNumber()
        {
            return _curr_num;
        }

        public TOKEN GetToken()
        {
        re_start: /// Label
            TOKEN tok = TOKEN.ILLEGAL_TOKEN;

            //// Skipping white spaces
            while ((_index < _length_string)
                && (_exp[_index] == ' ' || _exp[_index] == '\t'))
            {
                _index++;
            }

            /// Enf Of Expression
            if (_index == _length_string)
            {
                return TOKEN.TOK_NULL;
            }



            switch (_exp[_index])
            {
                case '\r':
                case '\n':
                    _index++;
                    goto re_start;
                case '+':
                    tok = TOKEN.TOK_PLUS;
                    _index++;
                    break;
                case '-':
                    tok = TOKEN.TOK_SUB;
                    _index++;
                    break;
                case '/':
                    tok = TOKEN.TOK_DIV;
                    _index++;
                    break;
                case '*':
                    tok = TOKEN.TOK_MUL;
                    _index++;
                    break;
                case '(':
                    tok = TOKEN.TOK_OPAREN;
                    _index++;
                    break;
                case ')':
                    tok = TOKEN.TOK_CPAREN;
                    _index++;
                    break;
                case ';':
                    tok = TOKEN.TOK_SEMI;
                    _index++;
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
                        string str = "";
                        while ((_index < _length_string)
                            && (_exp[_index] == '0' ||
                            _exp[_index] == '1' ||
                            _exp[_index] == '2' ||
                            _exp[_index] == '3' ||
                            _exp[_index] == '4' ||
                            _exp[_index] == '5' ||
                            _exp[_index] == '6' ||
                            _exp[_index] == '7' ||
                            _exp[_index] == '8' ||
                            _exp[_index] == '9'))
                        {
                            str += Convert.ToString(_exp[_index]);
                            _index++;
                        }
                        _curr_num = Convert.ToDouble(str);
                        tok = TOKEN.TOK_DOUBLE;

                    }
                    break;
                default:
                    {
                        if (char.IsLetter(_exp[_index]))
                        {

                            String tem = Convert.ToString(_exp[_index]);
                            _index++;
                            while (_index < _length_string && (char.IsLetterOrDigit(_exp[_index]) ||
                            _exp[_index] == '_'))
                            {
                                tem += _exp[_index];
                                _index++;
                            }

                            tem = tem.ToUpper();

                            for (int i = 0; i < this._val.Length; ++i)
                            {
                                if (_val[i].Value.CompareTo(tem) == 0)
                                    return _val[i].tok;

                            }


                            this.last_str = tem;



                            return TOKEN.TOK_UNQUOTED_STRING;



                        }
                        else
                        {
                            Console.WriteLine("Error");
                            throw new Exception();
                        }

                    }
            }
            return tok;
        }
    }



//---- Expression is what you evaluates for value
public abstract class Exp{
        public abstract double Evaluate(RUNTIME_CONTEXT cont);
        // -------- Generate Java
        public abstract SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont);
}

public class NumericConstant : Exp {
        //---- value holder
        private double _value;
        //---- ctor
        public NumericConstant(double value){  _value = value; }
        //--- Evaluate 
        public override double Evaluate(RUNTIME_CONTEXT cont){ return _value; }
        public override SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont) {
                    Console.Write(_value);
                    return null;
         }
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
        //---- Evaluate is a recursive routine 
        public override double Evaluate(RUNTIME_CONTEXT cont)
        {
	switch (_op)
            	{
              	  case OPERATOR.PLUS:
                    	return _ex1.Evaluate(cont) + _ex2.Evaluate(cont);
                	  case OPERATOR.MINUS:
                    	return _ex1.Evaluate(cont) - _ex2.Evaluate(cont);
               	 case OPERATOR.DIV:
                   	 return _ex1.Evaluate(cont) / _ex2.Evaluate(cont);
               	 case OPERATOR.MUL:
                    	return _ex1.Evaluate(cont) * _ex2.Evaluate(cont);

           	 }
	return Double.NaN;
           }

           public override SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont) {
                     Console.Write("(");
                     _ex1.GenerateJS(cont);
                     switch (_op)
            	{
              	  case OPERATOR.PLUS:
                    	Console.Write("+");
                                    break;
                	  case OPERATOR.MINUS:
                    	Console.Write("-");;
                                   break;
               	 case OPERATOR.DIV:
                   	 Console.Write("/");
                                    break;
               	 case OPERATOR.MUL:
                    	Console.Write("*");
                                    break;
           	 }
                     _ex2.GenerateJS(cont);
                     Console.Write(");");
                     return null;
                   
         }

}

///------Modeling Unary Expression ( + | - )

public class UnaryExp : Exp {
        private Exp _ex1;
        private OPERATOR _op;

        public UnaryExp(Exp a, OPERATOR op){
            _ex1 = a;
            _op = op;
        }
        public override double Evaluate(RUNTIME_CONTEXT cont)
        {

            switch (_op)
            {
                case OPERATOR.PLUS:
                    return _ex1.Evaluate(cont);
                case OPERATOR.MINUS:
                    return -_ex1.Evaluate(cont);
            }

            return Double.NaN;

        }

         public override SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont) {
                  Console.Write(  (_op == OPERATOR.PLUS ) ?  "+" : "-"  );

                     _ex1.GenerateJS(cont);
                     return null;
                   
         }

}
///----------------------------------------- Added in the Step 2
//--------- Parser need not inherit from the Lexer...
//---- used for Convenience
//
 public class RDParser : Lexer {
        TOKEN Current_Token;
        TOKEN Last_Token;
        public RDParser(String str) : base(str){}

        public Exp CallExpr(){
            Current_Token = GetToken();
            return Expr();
        }

         
        protected TOKEN GetNext(){
            Last_Token = Current_Token;
            Current_Token = GetToken();
            return Current_Token;
        }

          public Exp Expr(){
            TOKEN l_token;
            Exp RetValue = Term();
            while (Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB)
            {
                l_token = Current_Token;
                Current_Token = GetToken();
                Exp e1 = Expr();
                RetValue = new BinaryExp(RetValue, e1,
                    l_token == TOKEN.TOK_PLUS ? OPERATOR.PLUS : OPERATOR.MINUS);

            }

            return RetValue;

        }
      
        public Exp Term()
        {
            TOKEN l_token;
            Exp RetValue = Factor();

            while (Current_Token == TOKEN.TOK_MUL || Current_Token == TOKEN.TOK_DIV)
            {
                l_token = Current_Token;
                Current_Token = GetToken();


                Exp e1 = Term();
                RetValue = new BinaryExp(RetValue, e1,
                    l_token == TOKEN.TOK_MUL ? OPERATOR.MUL : OPERATOR.DIV);

            }

            return RetValue;
        }

      
        public Exp Factor()
        {
            TOKEN l_token;
            Exp RetValue = null;
            if (Current_Token == TOKEN.TOK_DOUBLE)
            {

                RetValue = new NumericConstant(GetNumber());
                Current_Token = GetToken();

            }
            else if (Current_Token == TOKEN.TOK_OPAREN)
            {

                Current_Token = GetToken();

                RetValue = Expr();  // Recurse

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

        //---- Step 3 
         
        public ArrayList Parse() {
            GetNext();  // Get the Next Token
            // Parse all the statements
            return StatementList();
        }

         private ArrayList StatementList()
        {
            ArrayList arr = new ArrayList();
            while (Current_Token != TOKEN.TOK_NULL)
            {
                Stmt temp = Statement();
                if (temp != null)
                {
                    arr.Add(temp);
                }
            }
            return arr;
        }

         private Stmt Statement()
        {
            Stmt retval = null;
            switch (Current_Token)
            {
                case TOKEN.TOK_PRINT:
                    retval = ParsePrintStatement();
                    GetNext();
                    break;
                case TOKEN.TOK_PRINTLN:
                    retval = ParsePrintLNStatement();
                    GetNext();
                    break;
                default:
                    throw new Exception("Invalid statement");
                   
            }
            return retval;
        }

         private Stmt ParsePrintStatement()
        {
            GetNext();
            Exp a = Expr();

            if (Current_Token != TOKEN.TOK_SEMI)
            {
                throw new Exception("; is expected");
            }
            return new PrintStatement(a);
        }

         private Stmt ParsePrintLNStatement()
        {
            GetNext();
            Exp a = Expr();

            if (Current_Token != TOKEN.TOK_SEMI)
            {
                throw new Exception("; is expected");
            }
            return new PrintLineStatement(a);
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
//--- Added in the step 3
 //----- Statement is what you execute for it's effect 
 public abstract class Stmt {
        public abstract bool Execute(RUNTIME_CONTEXT con);
        public abstract SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont);
 }

 public class PrintStatement : Stmt{
        private Exp _ex;
        public PrintStatement(Exp ex){_ex = ex;}
        public override bool Execute(RUNTIME_CONTEXT con){
            double a = _ex.Evaluate(con);
            Console.Write(a.ToString());
            return true;
        }
         public override SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont) {
	 Console.Write("printf(");
                   _ex.GenerateJS(cont);
                   Console.Write(");\r\n");
                   return null;
         }
}
///  Implementation of  PrintLine Statement
/// </summary>
public class PrintLineStatement : Stmt{
        private Exp _ex;
        public PrintLineStatement(Exp ex){_ex = ex;}
        public override bool Execute(RUNTIME_CONTEXT con)
        {
            double a = _ex.Evaluate(con);
            Console.WriteLine(a.ToString());
            return true;
        }

        public override SYMBOL_INFO GenerateJS(RUNTIME_CONTEXT cont) {
	 Console.Write("printf(");
                   _ex.GenerateJS(cont);
                   Console.Write(");"+"\r\n");
                   return null;
         }
}

 class Program
    {
        static void TestFirstScript()
        {
            string a = "PRINTLINE 2*10;" + "\r\n" + "PRINTLINE 10;\r\n PRINT 2*10;\r\n";
            RDParser p = new RDParser(a);
            ArrayList arr = p.Parse();
            foreach (object obj in arr)
            {
                Stmt s = obj as Stmt;
               // s.Execute(null);
               s.GenerateJS(null);
            }
        }
        /// <summary>
        /// 
        /// </summary>
        static void TestSecondScript()
        {
            string a = "PRINTLINE -2*10;" + "\r\n" + "PRINTLINE -10*-1;\r\n PRINT 2*10;\r\n";
            RDParser p = new RDParser(a);
            ArrayList arr = p.Parse();
            foreach (object obj in arr)
            {
                Stmt s = obj as Stmt;
                //s.Execute(null);
                s.GenerateJS(null);
            }
        }
        /// <summary>
        /// 
        /// </summary>
        /// <param name="args"></param>
        static void Main(string[] args)
        {
           TestFirstScript();//TestSecondScript();
            Console.Read();
        }
    }







