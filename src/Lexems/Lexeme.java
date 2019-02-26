package Lexems;

public class Lexeme {
    public final TypeOfSymbol typeOfSymbol;
    public final String value;

    public Lexeme(TypeOfSymbol type) {
        typeOfSymbol = type;
        value = typeOfSymbol.basicValue;
    }

    public Lexeme(TypeOfSymbol type, String value) {
        typeOfSymbol = type;
        this.value = value;
    }

    public enum TypeOfSymbol {
        NUMBER,
        PLUS("+"),
        MINUS("-"),
        MULT("*"),
        DIV("/"),
        POW("^"),
        L_BRACKET("("),
        R_BRACKET(")"),
        EOF(null);


        private final String basicValue;

        TypeOfSymbol() {
            this(null);
        }

        TypeOfSymbol(String value) {
            basicValue = value;
        }


    }
}
