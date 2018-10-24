import sun.security.ssl.Debug;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Raymond on 23.10.2018.
 */
public class Parser {
    private Stack<String> currentParseStack = new Stack<>();

    public Parser(ArrayList<Token> listOfTokens){
        int i = 0;
        currentParseStack.push("lang");
        LangRule lg = new LangRule();
        while(true) {
            System.out.print(listOfTokens.get(i).getValue());
            System.out.print(" TYPE: ");
            System.out.println(listOfTokens.get(i).getType());
            System.out.println(currentParseStack.toString());


            if (!checkIsTerminal(currentParseStack.peek())) {
                ArrayList<String> rules = lg.getRuleRevresed(currentParseStack.peek(), listOfTokens.get(i).getType());

                if(!rules.isEmpty()){
                currentParseStack.pop();}
                for(String production: rules){
                    currentParseStack.push(production);
                }
            }
            else if (currentParseStack.peek().equals(listOfTokens.get(i).getType())) {
                if (currentParseStack.peek().equals("$")) {
                    System.out.print("Parsed successfully");
                    currentParseStack.pop();
                    break;
                }
                currentParseStack.pop();
                System.out.println("POP");
                i++;
            }
            else {
                if(currentParseStack.peek().equals("EMPTY")){
                    currentParseStack.pop();
                }
            }
        }

    }

    private boolean checkIsTerminal(String str){
        String[] arrayOfTerminals = {"FOR_KW", "L_B", "R_B", "L_C", "R_C", "VAR", "ASSIGN_OP", "DIGIT", "OP", "CON", "DEL", "$", "EMPTY"};
        for(String terminal: arrayOfTerminals){
            if(terminal.equals(str)){return true;}
        }
        return false;
    }
}
