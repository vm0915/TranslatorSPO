import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created by Raymond on 02.11.2018.
 */
public class StackMachine {
    private HashMap<String, Integer> variables = new HashMap<>();
    private Stack<String> varStack = new Stack<>();
    private Stack<String> opStack = new Stack<>();
    private boolean iReplaced = false;

    public StackMachine(List<String> inputs){

        for(int i = 0; i < inputs.size(); i++) {
            iReplaced = false;
            System.out.println("varstack: " + varStack);
            System.out.println("opstack: " + opStack);
            System.out.println("varmap: " + variables);
            System.out.println("input:  " + inputs.get(i));

            switch (inputs.get(i).charAt(0)) {
                //********проверить на изменение i перед каждым условием
                case ('$'): {
                    //end it all
                    System.out.println(variables);
                    break;
                }
                case ('#'): {
                    //создать переменную
                    boolean alreadyExist = false;
                    for(HashMap.Entry<String, Integer> entry : variables.entrySet()){
                        if(entry.getKey().equals(inputs.get(i).substring(1))){
                            alreadyExist = true;
                        }
                    }
                    if(alreadyExist) {
                        varStack.push(inputs.get(i).substring(1));
                    }
                    else
                    {
                        variables.put(inputs.get(i).substring(1), 0);
                        varStack.push(inputs.get(i).substring(1));
                    }
                    break;
                }
                case ('!'): {
                    if (inputs.get(i).charAt(1) == 'F') {
                        //перейти по false
                        if (varStack.peek().equals("true")) {
                            varStack.pop();
                            //ничего не делать
                        }
                        if ((!varStack.isEmpty()))
                        if ((varStack.peek().equals("false"))&&(!varStack.isEmpty())) {
                            varStack.pop();
                            //переходим к поинту
                            int point = Integer.parseInt(inputs.get(i).substring(3));
                            i = point - 1;
                            iReplaced = true;
                        }
                    } else {
                        //перейти по true
                        //переходим к поинту
                            int point = Integer.parseInt(inputs.get(i).substring(2));
                            i = point - 1;
                            iReplaced = true;
                    }
                    break;
                }
                case ('%'): {
                    //берем существующую переменную
                    variables.get(inputs.get(i).substring(1));
                    varStack.push(inputs.get(i).substring(1));
                    break;
                }
            }
            if (!iReplaced) {
                //перебрать все знаки сравнения
                String[] cops = {"<", ">"};
                for(String string : cops) {
                    if (string.contains(Character.toString(inputs.get(i).charAt(0)))) {
                        //для операции сравнения
                        opStack.push(inputs.get(i));
                        compare(inputs.get(i));
                    }
                }

                if (inputs.get(i).charAt(0) == '=') {
                    //для операции присваивания
                    opStack.push(inputs.get(i));
                    initialization();

                }
                String numbers = "1234567890";
                if (numbers.contains(Character.toString(inputs.get(i).charAt(0)))) {
                    //для цифры
                    varStack.push(inputs.get(i));

                }
                String ops = "+-*/";
                if (ops.contains(Character.toString(inputs.get(i).charAt(0)))) {
                    //для цифры
                    opStack.push(inputs.get(i));
                    makeArithmetic(inputs.get(i));
                }

            }
        }
        System.out.println(varStack);
        System.out.println(opStack);
    }

    private void compare(String operation){
        int secondParameter = getPopParameter();
        int firstParameter = getPopParameter();
        opStack.pop();

        switch (operation){
            case "<" : {
                if(firstParameter < secondParameter){
                    varStack.push("true");
                }else {varStack.push("false");}
                break;
            }
            case ">" : {
                if(firstParameter > secondParameter){
                    varStack.push("true");
                }else {varStack.push("false");}
                break;
            }
            case "<=" : {
                if(firstParameter <= secondParameter){
                    varStack.push("true");
                }else {varStack.push("false");}
                break;
            }
            case ">=" : {
                if(firstParameter >= secondParameter){
                    varStack.push("true");
                }else {varStack.push("false");}
                break;
            }
            case "==" : {
                if(firstParameter == secondParameter){
                    varStack.push("true");
                }else {varStack.push("false");}
                break;
            }

        }
    }

    private void initialization(){
        opStack.pop();
        int znachenie = getPopParameter();
        String variable = varStack.pop();
        variables.put(variable, znachenie);
        System.out.println("varmapUpdated: " + variables);
    }

    private void makeArithmetic(String operation){
        int secondParameter = getPopParameter();
        int firstParameter = getPopParameter();

        switch (operation){
            case "+" : {
                varStack.push(Integer.toString(firstParameter + secondParameter));
                break;
            }
            case "-" : {
                varStack.push(Integer.toString(firstParameter - secondParameter));
                break;
            }
            case "/" : {
                varStack.push(Integer.toString(firstParameter / secondParameter));
                break;
            }
            case "*" : {
                varStack.push(Integer.toString(firstParameter * secondParameter));
                break;
            }
        }
        opStack.pop();

    }



    private int getPopParameter(){
        int parameter;
        String peek = varStack.peek();
        try{
        parameter = Integer.parseInt(peek);
        }catch (Exception e){
            parameter = variables.get(peek);
        }
        varStack.pop();
        return parameter;
    }

}
