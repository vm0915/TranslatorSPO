import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Raymond on 23.10.2018.
 */
public class Parser {
    private Stack<String> currentParseStack = new Stack<>();
    private Stack<Integer> stackForCNReturns = new Stack<>();
    private ASTreeNode currentTreeNode;

    public Parser(ArrayList<Token> listOfTokens){
        int i = 0;
        currentParseStack.push("lang");
        currentTreeNode = new ASTreeNode("lang");
        ASTreeNode nodeToRPN = currentTreeNode;
        LangRule lg = new LangRule();
        while(true) {
            //System.out.print(listOfTokens.get(i).getValue());
            //System.out.print(" TYPE: ");
            //System.out.println(listOfTokens.get(i).getType());
            //System.out.println(currentParseStack.toString());


            if (!checkIsTerminal(currentParseStack.peek())) {
                ArrayList<String> rules = lg.getRuleRevresed(currentParseStack.peek(), listOfTokens.get(i).getType());

                if(!rules.isEmpty()){
                    if (!currentParseStack.peek().equals("lang")) {
                        for (final ASTreeNode child : currentTreeNode.getChildren()) {
                            if (child.getName().equals(currentParseStack.peek()) && child.getChildren().size() == 0) {
                                currentTreeNode = child;
                                break;
                            }
                        }
                    }
                    currentParseStack.pop();

                    stackForCNReturns.push(rules.size());
                    for(String production: rules){
                        currentTreeNode.addChild(new ASTreeNode(production, currentTreeNode));
                        currentParseStack.push(production);
                    }
                    //prinint node
                    //System.out.println("PARENT: " + currentTreeNode.getName());
                    for(ASTreeNode child: currentTreeNode.getChildren()){
                        //System.out.println("NODEs children: " + child.getName());
                    }
                }
            }
            else if (currentParseStack.peek().equals(listOfTokens.get(i).getType())) {
                if (currentParseStack.peek().equals("$")) {
                    for (final ASTreeNode child : currentTreeNode.getChildren()) {
                        if (child.getName().equals(currentParseStack.peek()) && child.getChildren().size() == 0) {
                            if (child.getName().equals(listOfTokens.get(i).getType()) && child.getToken() == null)
                                child.setToken(listOfTokens.get(i));
                            //System.out.println("****************************NODE:" + child.getName() + " " + listOfTokens.get(i).getType());
                            break;
                        }
                    }
                    //currentTreeNode.setToken(listOfTokens.get(i));
                    System.out.println("Parsed successfully");
                    currentParseStack.pop();
                    break;
                }
                //

                for (final ASTreeNode child : currentTreeNode.getChildren()) {
                    if (child.getName().equals(currentParseStack.peek()) && child.getChildren().size() == 0) {
                        if (child.getName().equals(listOfTokens.get(i).getType()) && child.getToken() == null)
                            child.setToken(listOfTokens.get(i));
                        //System.out.println("****************************NODE:" + child.getName() + " " + listOfTokens.get(i).getType());
                        break;
                    }
                }
                //currentTreeNode.setToken(listOfTokens.get(i));
                //переставить currentNode на уровень выше
                moveCN();

                currentParseStack.pop();
                //System.out.println("POP");
                i++;
            }
            else {
                if(currentParseStack.peek().equals("EMPTY")){
                    //переставиь currentNode на уровень выше
                    moveCN();
                    currentParseStack.pop();
                }
                else{
                    System.out.println("Grammar Error");
                    break;
                }
            }
        }
        List<String> strings = RPNConverter.convertToRPN(reverse(nodeToRPN));
        System.out.println(strings);
        StackMachine stackMachine = new StackMachine(strings);

    }

    private void moveCN(){
        int eleCount = stackForCNReturns.pop() - 1;
        if (eleCount != 0) {
            stackForCNReturns.push(eleCount);
        } else if (stackForCNReturns.size() != 0) {
            currentTreeNode = (ASTreeNode) currentTreeNode.getParent();
            moveCN();
        }
    }


    private boolean checkIsTerminal(String str){
        String[] arrayOfTerminals = {"FOR_KW", "L_B", "R_B", "L_C", "R_C", "VAR", "ASSIGN_OP", "DIGIT", "OP", "CON", "DEL", "$", "EMPTY"};
        for(String terminal: arrayOfTerminals){
            if(terminal.equals(str)){return true;}
        }
        return false;
    }

    private ASTreeNode reverse(ASTreeNode node){
        if((node.getName().equals("assign_expr"))||(node.getName().equals("lang")))
        node.reverseChildren();
        for(ASTreeNode child: node.getChildren()){
            //System.out.println(child.getName());
            reverse(child);
        }
        return node;
    }
}
