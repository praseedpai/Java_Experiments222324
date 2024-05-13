using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


public class RUNTIME_CONTEXT{
               public RUNTIME_CONTEXT(){}
}
 public enum OPERATOR {
       	ILLEGAL = -1,
        	PLUS,
        	MINUS,
        	DIV,
        	MUL
         }
          //------------------- Expression is what you evaluate it's value
          //------------------- Statement is what you execute for it's effect ( on Variables)
          //------------------- Collection of Statements form a Procedure
          //------------------  Collection of Procedures form a Module
          //------------------- Module is  a Program
          public abstract class Exp{
      	  public abstract double Evaluate(RUNTIME_CONTEXT cont);
          }
          //---- A Node to store IEEE 754 floating point number
          public class NumericConstant : Exp {
       	 private double _value;
                  public NumericConstant(double value){ _value = value;}
                  public override double Evaluate(RUNTIME_CONTEXT cont)
                    { return _value;}
          }


          //------------ A Node to Represent Binary Expression
          /// <summary>
    ///     This class supports Binary Operators like + , - , / , *
    /// </summary>
    public class BinaryExp : Exp{
        private Exp _ex1, _ex2;
        private OPERATOR _op;
        public BinaryExp(Exp a, Exp b, OPERATOR op)
        { _ex1 = a; _ex2 = b; _op = op;}
        public override double Evaluate(RUNTIME_CONTEXT cont){


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

    }

    //- ---- A class to represent Unary Operation
    public class UnaryExp : Exp{
        private Exp _ex1;
        private OPERATOR _op;
        public UnaryExp(Exp a, OPERATOR op)
        {
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

        } // EOF UnaryEx
}
//------------------------ Main 
class EntryPoint
{
	 public static void Main(String [] args ){
                           //--- 2 + 3
                           Exp e = new BinaryExp(new NumericConstant (2),
                                                                   new NumericConstant(3) , OPERATOR.PLUS);
                           Console.WriteLine(e.Evaluate(null));

                            // AST for  -(10 + (30 + 50 ) )

            e = new UnaryExp(
                         new BinaryExp(new NumericConstant(10),
                             new BinaryExp(new NumericConstant(30),
                                           new NumericConstant(50),
                                  OPERATOR.PLUS),
                         OPERATOR.PLUS),
                     OPERATOR.MINUS);

            //
            // Evaluate the Expression
            //
            Console.WriteLine(e.Evaluate(null));
                
     	}

}