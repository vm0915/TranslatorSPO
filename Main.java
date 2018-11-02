/**
 * Created by Raymond on 09.10.2018.
 */
public class Main {
    public static void main(String []args){
        Lexer lx = new Lexer();
        Parser p = new Parser(lx.extractTokens("for(i=1;i<=5;i=i+1){a=a+2; for(k=1;k<=5;k=k+1){b=b+2;}} $"));
    }
}
