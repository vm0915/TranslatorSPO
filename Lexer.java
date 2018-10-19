import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by Raymond on 09.10.2018.
 */
public class Lexer {
    private String code = "for  !";
    private StringBuilder tokenBuild = new StringBuilder();
    private boolean previousSuccess = false;
    private ArrayList<Token> listOfTokens = new ArrayList<>();
    private static final char END_SYMBOL = '!';



    private Map<String, Pattern> tokens = new HashMap<>();

    public Lexer(){
        tokens.put("FOR_KW",Pattern.compile("^\\bfor\\b$"));
        tokens.put("VAR",Pattern.compile("^[a-z]+$"));
        tokens.put("DIGIT",Pattern.compile("^[0]|[1-9][0-9]*$"));
        tokens.put("OP",Pattern.compile("^[-+/*]$"));
        tokens.put("ASSIGN_OP",Pattern.compile("^[=]$"));
        tokens.put("CON",Pattern.compile("^[<>]|(<=)|(>=)|(==)|(!=)$"));
        tokens.put("INC",Pattern.compile("^[+]{2}$"));
        tokens.put("L_B",Pattern.compile("^[(]$"));
        tokens.put("R_B",Pattern.compile("^[)]$"));
        tokens.put("DEL",Pattern.compile("^[;]$"));
        tokens.put("L_C",Pattern.compile("^[{]$"));
        tokens.put("R_C",Pattern.compile("^[}]$"));
        extractTokens();
    }


    private void extractTokens(){
        int k = 0;
        for(int i = 0; i < code.length(); i++){
            if(i < code.length()){tokenBuild.append(code.charAt(i));}
            if((!checkBuildForToken())||(code.charAt(i) == END_SYMBOL)){
                if(previousSuccess){
                    tokenBuild.deleteCharAt(k);
                    Token token = new Token();
                    token.setValue(tokenBuild.toString());
                    token.setType(getTokenType(tokenBuild.toString()));
                    listOfTokens.add(token);
                    previousSuccess = false;
                    if(code.charAt(i) == END_SYMBOL){break;}
                    i--;
                }
                tokenBuild.setLength(0);
                k = 0;

            }
            else {previousSuccess = true;}
            if(tokenBuild.length() != 0){k++;}
        }
        for(Token token: listOfTokens){
            System.out.print(token.getValue());
            System.out.print(" ");
            System.out.println(token.getType());
        }
    }

    private boolean checkBuildForToken(){
        for(Map.Entry<String, Pattern> entry : tokens.entrySet()) {
            if(entry.getValue().matcher(tokenBuild.toString()).matches()) {return true;}
        }
        return false;
    }
    private String getTokenType(String token){
        for(Map.Entry<String, Pattern> entry : tokens.entrySet()) {
            if(entry.getValue().matcher(token).matches()) {return entry.getKey();}
        }
        return null;
    }
}
