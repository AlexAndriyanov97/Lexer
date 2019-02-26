import Lexems.Lexeme;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;

public class Parser {

    private Lexer lexer;

    private Lexeme currentLexeme;

    public Parser(Lexer lexer) throws IOException, InvalidAttributeValueException {
        this.lexer = lexer;
        currentLexeme = lexer.GetNextLexem();
    }


    public int Exec() throws IOException, InvalidAttributeValueException {
        int result = ExecExpr();
        if(currentLexeme.typeOfSymbol!= Lexeme.TypeOfSymbol.EOF){
            throw new InvalidAttributeValueException("Not Found EOF");
        }
        return result;
    }

    private int ExecTerm() throws IOException, InvalidAttributeValueException {
        int result=ExecFract();
        while (currentLexeme.typeOfSymbol== Lexeme.TypeOfSymbol.PLUS || currentLexeme.typeOfSymbol== Lexeme.TypeOfSymbol.DIV){
            currentLexeme=lexer.GetNextLexem();
            if(currentLexeme.typeOfSymbol== Lexeme.TypeOfSymbol.MULT){
                result*=ExecFract();
            }
            else {
                result/=ExecFract();
            }
        }
        return result;
    }

    private int ExecExpr() throws IOException, InvalidAttributeValueException {
        int result = ExecTerm();
        while (currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.PLUS || currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.MINUS) {
            currentLexeme =lexer.GetNextLexem();
            result += (currentLexeme.typeOfSymbol== Lexeme.TypeOfSymbol.PLUS)?ExecTerm():(ExecTerm()*(-1));
        }
        return result;
    }


    private int ExecPow() throws IOException, InvalidAttributeValueException {
        if (currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.MINUS) {
            currentLexeme = lexer.GetNextLexem();
            return ExecAtom() * (-1);
        }
        return ExecAtom();
    }

    private int ExecFract() throws IOException, InvalidAttributeValueException {
        int result = ExecPow();
        if (currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.POW) {
            currentLexeme = lexer.GetNextLexem();
            return (int) Math.pow(result, ExecFract());
        }
        return result;
    }


    private int ExecAtom() throws IOException, InvalidAttributeValueException {
        int result;

        if (currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.NUMBER) {
            result = Integer.parseInt(currentLexeme.value);
            currentLexeme = lexer.GetNextLexem();
            return result;
        }

        if (currentLexeme.typeOfSymbol == Lexeme.TypeOfSymbol.L_BRACKET) {
            currentLexeme = lexer.GetNextLexem();
            result = ExecExpr();
            if (currentLexeme.typeOfSymbol != Lexeme.TypeOfSymbol.R_BRACKET) {
                throw new InvalidAttributeValueException("Not found close Bracket");
            }
            currentLexeme = lexer.GetNextLexem();
            return result;
        }
        throw new InvalidAttributeValueException("Unknown lexeme type");
    }
}
