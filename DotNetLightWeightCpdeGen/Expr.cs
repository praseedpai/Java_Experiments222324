//////////////////////////////////////////////////////////////////////
//
// A Four function calculator which generates .NET IL code on the fly .
//
// using Light Weight Code generation under .NET
//
//
//
// BNF Grammar for Evaluator
//
//
// <expr> ::= <term> | <term> { '+' | '-' } <expr>
//
// <term> ::= <factor> | <factor> { '*' | '/' } <term>
//
// <factor> ::= <TOK_DOUBLE> | ( <expr> ) | { + | - } <factor>
//
// F:\arun>csc Expr.cs
// Microsoft (R) Visual C# Compiler version 4.7.0-3.23469.2 (a3b9f0c2)
// Copyright (C) Microsoft Corporation. All rights reserved.
//
//
//
// F:\arun>Expr "2+4"
// The Evaluated Value is 6
//
//
// F:\arun>Expr "2+4*2"
// The Evaluated Value is 10
//
//
// F:\arun>Expr "2+3*4"
// The Evaluated Value is 14
//
//
//
// Written by
//     Praseed Pai K.T.
//      http://praseedp.blogspot.com 
//
//

using System;
using System.Reflection;
using System.Reflection.Emit;
using System.Threading;
///////////////////////////////////////////////////////////////////////////////
//
//
// enum TOKEN - Symbolic constants for Lexical Tokens
//
// class Lexer - Lexical Analyzer module. ( proof of concept endeavour )
//
// class CLRParser - A recursive descent parser created to generate IL
// code on the fly.

namespace ExpressionEvaluator
{
  ////////////////////////////////////////////////
  //
  // Lexical Tokens
  //
  //
  //
  public enum TOKEN
  {  
    ILLEGAL_TOKEN=-1, // Not a Token
    TOK_PLUS=1, // '+'
    TOK_MUL, // '*'
    TOK_DIV, // '/'
    TOK_SUB, // '-'
    TOK_OPAREN, // '('
    TOK_CPAREN, // ')'
    TOK_DOUBLE, // '('
    TOK_NULL // End of string
  }
  //////////////////////////////////////////////////////////
  //
  // A naive Lexical analyzer which looks for operators , Parenthesis
  // and number. All numbers are treated as IEEE doubles. Only numbers
  // without decimals can be entered. Feel free to modify the code
  // to accomodate LONG and Double values
  public class Lexer
  {
    String IExpr; // Expression string
    int index ; // index into a character
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
      (IExpr[index] == ' ' || IExpr[index]== '\t') )
        index++;
      //////////////////////////////////////////////
      //
      // End of string ? return NULL;
      //
      if ( index == length)
        return TOKEN.TOK_NULL;
      /////////////////////////////////////////////////
      //
      //
      //
      switch(IExpr[index])
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
          String str="";
          while ( index < length &&
          ( IExpr[index]== '0' ||
          IExpr[index]== '1' ||
          IExpr[index]== '2' ||
          IExpr[index]== '3' ||
          IExpr[index]== '4' ||
          IExpr[index]== '5' ||
          IExpr[index]== '6' ||
          IExpr[index]== '7' ||
          IExpr[index] == '8'||
          IExpr[index]== '9' ))
          {
            str += Convert.ToString(IExpr[index]);
            index ++;
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
///////////////////////////////////////////////////////////
//
//
//
//
//
class CLRParser : Lexer
{
  TOKEN Current_Token; // Current input token
  /////////////////////////////////////////////
  // CLR/IL related classes from Reflection.Emit
  //
  //
  ILGenerator ILout;
  
  DynamicMethod myMthdBld;
  
  //////////////////////////////////////////////////
  //
  // Ctor
  //  
  public CLRParser(String str):base(str)
  {
    CreateAssemblyOntheFly();
  }
  /////////////////////////////////////////////////////////////
  //
  //
  //
  //
  private void CreateAssemblyOntheFly()
  {
    
    

                myMthdBld = new DynamicMethod("Eval", 
                           typeof(double), new Type[] {},typeof(CLRParser) , false);
                ILout  = myMthdBld.GetILGenerator();

    return;
  }
  //////////////////////////////////////////////
  //
  //
  // Parser Driver
  //
  //
  public double CallExpr()
  {
    Current_Token= GetToken(); // Grab the First token
    ///////////////////////////////////////////
    // Call the Top level evaluation method Expr
    //
    Expr();
    /////////////////////////////
    // Emit Return instruction in the method code stream
    //
    ILout.Emit(OpCodes.Ret);
    
    
    //////////////////////////////////////////////////
    // Call the method
    //
    return (double)myMthdBld.Invoke(null,
      null);
  }
  //////////////////////////////////////////////////////////////////////
  //
  // <expr> ::= <term> | <term> { '+' | '-' } <expr>
  //
  //
  //
  public void Expr()
  {
    TOKEN l_token;
    Term();
    if ( Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB )
    {
      l_token = Current_Token;
      Current_Token = GetToken();
      Expr();
      //////////////////////////////////////////////////
      // Emit Add or Sub instruction to the stream
      //
      ILout.Emit(l_token == TOKEN.TOK_PLUS ? OpCodes.Add : OpCodes.Sub );
    }
  }
  ///////////////////////////////////////
  //
  // <term> ::= <factor> | <factor> { '*' | '/' } <term>
  //
  //
  public void Term()
  {
    TOKEN l_token;
    Factor();
    if ( Current_Token == TOKEN.TOK_MUL || Current_Token == TOKEN.TOK_DIV )
    {
      l_token = Current_Token;
      Current_Token = GetToken();
      Term();
      //////////////////////////////////////////////////
      // Emit Mul or Div instruction to the stream
      //
      ILout.Emit( l_token == TOKEN.TOK_MUL ? OpCodes.Mul:OpCodes.Div);
    }
  }
  //////////////////////////////////////////////////////
  //
  // <factor> ::= <TOK_DOUBLE> | ( <expr> ) | {+ | - } <factor>
  //
  //
  public void Factor()
  {
    TOKEN l_token;
    if ( Current_Token == TOKEN.TOK_DOUBLE )
    {
    ////////////////////////////////////////////
    //
    // Load the Data into the CLR stack
    //
    ILout.Emit(OpCodes.Ldc_R8, GetNumber());
    Current_Token = GetToken();
  }
  else if ( Current_Token == TOKEN.TOK_OPAREN )
  {
    Current_Token = GetToken();
    Expr(); // Recurse
    if ( Current_Token != TOKEN.TOK_CPAREN )
    {
      Console.WriteLine("Missing Closing Parenthesis\n");
      throw new Exception();
    }
    Current_Token = GetToken();
  }
  else if ( Current_Token == TOKEN.TOK_PLUS || Current_Token == TOKEN.TOK_SUB )
  {
    l_token = Current_Token;
    Current_Token = GetToken();
    Factor();
    if ( l_token == TOKEN.TOK_SUB )
    {
      ///////////////////////////////
      //
      // TOP_OF_STACK = -TOP_OF_STACK
      ILout.Emit(OpCodes.Neg );
    }
        }
  else
  {
    Console.WriteLine("Illegal Token");
    throw new Exception();
  }
}
//////////////////////////////////////////////////////////////////
//
// Entry point for the Test Driver
//
//
//
public static void Main(string[] args)
{
  if ( args.Length == 0 || args.Length > 1 )
  {  
    Console.WriteLine("Usage : Expr \"<expr>\" \n");
    Console.WriteLine(" eg:- Expr \"2*3+4\" \n");
    Console.WriteLine(" Expr \"-2-3\" \n");
    return;
  }
  try {
    CLRParser parser = new CLRParser(args[0]);
    double nd = parser.CallExpr();
    Console.WriteLine("The Evaluated Value is {0} \n",nd );
  }
  catch(Exception )
  {
    Console.WriteLine("Error Evaluating Expression\n");
    return;
  }
}
}

}