/**
 * Created by Raymond on 02.11.2018.
 */

import java.util.*;

public final class RPNConverter {
    private static final Map<String, Integer> priority;
    private static Stack<String> stack;
    private static List<String> out;


    static {
        priority = new HashMap<String, Integer>() {{
            put("/", 5);
            put("*", 5);
            put("+", 4);
            put("-", 4);
            put("<", 3);
            put(">", 3);
            put("<=", 3);
            put(">=", 3);
            put("==", 3);
            put("(", 2);
            put(")", 2);
            put("=", 1);

        }};
    }

    public static synchronized List<String> convertToRPN(final ASTreeNode root) {
        stack = new Stack<>();
        out = new ArrayList<>();

        lang(root);
        return out;
    }

    private static void lang(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "expr": {
                    expr(child);
                    break;
                }
                case "expr_continue": {
                    expr_continue(child);
                    break;
                }
                case "$": {
                    out.add("$");
                    break;
                }
            }
        }
    }

    private static void expr(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "for_loop": {
                    for_loop(child);
                    break;
                }
                case "assign_expr": {
                    assign_expr(child);
                    break;
                }
                case "DEL": {
                    while (stack.size() != 0) {
                        out.add(stack.pop());
                    }
                }
            }
        }
    }

    private static void expr_continue(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "expr": {
                    expr(child);
                    break;
                }
                case "expr_continue": {
                    expr_continue(child);
                    break;
                }
            }
        }
    }

    private static void assign_expr(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "VAR": {
                    out.add("#" + child.getToken().getValue());
                    break;
                }
                case "ASSIGN_OP": {
                    stack.push(child.getToken().getValue());
                    break;
                }
                case "value_expr": {
                    value_expr(child);
                    break;
                }
            }
        }
    }

    private static void for_loop(final ASTreeNode root) {
        //for(i = 1; i < 1; i = i + 1) {a = 0;} -> i1= (index1) i1<!F@pa0=ii1+=!@p (index2) ; !@Fp - to index2, !@p - to index1
        int iteration = Thread.getAllStackTraces().hashCode();
        int index1; //at i
        int index2; //after !@p

        ASTreeNode condition = new ASTreeNode("");
        ASTreeNode assign = new ASTreeNode("");
        ASTreeNode increment = new ASTreeNode("");
        ASTreeNode expr_continue = new ASTreeNode("");

        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "FOR":
                    break;
                case "L_B":
                    break;
                case "assign_expr": {
                    if (assign.getName().equals(""))
                        assign = child;
                    else
                        increment = child;
                    break;
                }
                case "DEL":
                    break;
                case "condition": {
                    condition = child;
                    break;
                }
                case "L_C":
                    break;
                case "expr_continue": {
                    expr_continue = child;
                    break;
                }
                case "R_C":
                    break;
            }
        }

        assign_expr(assign);
        while (stack.size() != 0) {
            out.add(stack.pop());
        }

        index1 = out.size();
        condition(condition);
        while (stack.size() != 0) {
            out.add(stack.pop());
        }
        out.add("!F@p" + iteration);

        expr_continue(expr_continue);
        while (stack.size() != 0) {
            out.add(stack.pop());
        }

        assign_expr(increment);
        while (stack.size() != 0) {
            out.add(stack.pop());
        }
        out.add("!@p" + iteration);
        index2 = out.size();

        out.set(out.indexOf("!F@p" + iteration), "!F@" + index2);
        out.set(out.indexOf("!@p" + iteration), "!@" + index1);
    }


    private static void condition(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "value_expr": {
                    value_expr(child);
                    break;
                }
                case "CON": {
                    pushOP(child.getToken());
                    break;
                }
            }
        }
    }


    private static void value_expr(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "value_expr_with_br": {
                    value_expr_with_br(child);
                    break;
                }
                case "value_expr_without_br": {
                    value_without_br(child);
                    break;
                }
                case "value_expr_continue": {
                    value_expr_continue(child);
                    break;
                }
            }
        }
    }

    private static void value_expr_with_br(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "L_B": {
                    pushOP(child.getToken());
                    break;
                }
                case "value_expr": {
                    value_expr(child);
                    break;
                }
                case "R_B": {
                    pushOP(child.getToken());
                    break;
                }
            }
        }
    }

    private static void value_without_br(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "value": {
                    value(child);
                    break;
                }
                case "value_expr_continue": {
                    value_expr_continue(child);
                    break;
                }
            }
        }
    }

    private static void value(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "VAR": {
                    out.add(checkTrace() + child.getToken().getValue());
                    //out.add(child.getToken().getValue());
                    break;
                }
                case "DIGIT": {
                    out.add(child.getToken().getValue());
                    break;
                }
                case "DOUBLE": {
                    out.add(child.getToken().getValue());
                    break;
                }
                case "STRING": {
                    out.add(child.getToken().getValue());
                }
            }
        }
    }

    private static String checkTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String methodToCheck = trace[3].getMethodName();
        return methodToCheck.equals("none")
                ? "#"
                : "%";
    }

    private static void value_expr_continue(final ASTreeNode root) {
        for (ASTreeNode child : root.getChildren()) {
            switch (child.getName()) {
                case "OP": {
                    pushOP(child.getToken());
                    break;
                }
                case "value_expr": {
                    value_expr(child);
                    break;
                }
            }
        }
    }

    private static void pushOP(final Token op) {
        switch (op.getValue()) {
            case "(": {
                stack.push(op.getValue());
                break;
            }
            case ")": {
                while (!stack.peek().equals("(")) {
                    out.add(stack.pop());
                }
                stack.pop();
                break;
            }
            default: {
                while (!stack.isEmpty() && priority.get(op.getValue()) <= priority.get(stack.peek())) {
                    out.add(stack.pop());
                }
                stack.push(op.getValue());
                break;
            }
        }
    }
}