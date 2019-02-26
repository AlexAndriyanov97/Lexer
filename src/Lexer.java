import Lexems.Lexeme;

import javax.management.InvalidAttributeValueException;
import java.io.IOException;
import java.io.Reader;

public class Lexer {

    private Reader reader;

    private int currentSymbol;

    private int currentPosition;

    public Lexer(Reader reader) throws IOException {
        if (reader == null) {
            throw new NullPointerException("Reader is null");
        }
        reader = reader;
        PutSymbolToBuffer();
    }


    private void PutSymbolToBuffer() throws IOException {
        currentSymbol = reader.read();
        currentPosition++;
    }

    public Lexeme GetNextLexem() throws IOException, InvalidAttributeValueException {
        SkipWhiteSpace();

        switch (currentSymbol) {
            case -1:
                return new Lexeme(Lexeme.TypeOfSymbol.EOF);
            case '(':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.L_BRACKET);
            case ')':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.R_BRACKET);
            case '+':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.PLUS);
            case '-':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.MINUS);
            case '*':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.MULT);
            case '/':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.DIV);
            case '^':
                PutSymbolToBuffer();
                return new Lexeme(Lexeme.TypeOfSymbol.POW);
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
                return ReadNumber();
            default:
                throw new InvalidAttributeValueException("unknown char = " + currentSymbol + " on position: " + currentPosition);
        }
    }

    private Lexeme ReadNumber() throws IOException {
        StringBuilder strNumber = new StringBuilder();
        strNumber.append(currentSymbol);
        PutSymbolToBuffer();
        while (Character.isDigit(currentSymbol)) {
            strNumber.append(currentSymbol);
            PutSymbolToBuffer();
        }
        return new Lexeme(Lexeme.TypeOfSymbol.NUMBER, strNumber.toString());
    }

    private void SkipWhiteSpace() throws IOException {
        while (Character.isWhitespace(currentSymbol)) {
            PutSymbolToBuffer();
        }
    }
}
