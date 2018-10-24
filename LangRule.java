import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Raymond on 24.10.2018.
 */
public class LangRule {
    private HashMap<String, HashMap> langRule = new HashMap<>();
    private HashMap<String, Integer> terminals;
    private HashMap<Integer, ArrayList<String>> langRules = new HashMap<>();
    private ArrayList<String> openRuleInto;

    public LangRule() {
        terminals = new HashMap<>();
        terminals.put("FOR_KW", 1);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 1);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", 2);
        langRule.put("lang", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", 3);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 4);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("expr", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", 5);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", 6);
        terminals.put("VAR", 5);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", 6);
        langRule.put("expr_continue", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", 7);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", null);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("for_loop", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 8);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("assign_expr", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", 9);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 10);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", 10);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("value_expr", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", 11);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", null);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("value_expr_with_br", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 12);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", 12);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("value_expr_without_br", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 14);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", 13);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("value", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", null);
        terminals.put("R_B", 16);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", null);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", null);
        terminals.put("OP", 15);
        terminals.put("CON", 16);
        terminals.put("DEL", 16);
        terminals.put("$", null);
        langRule.put("value_expr_continue", terminals);

        terminals = new HashMap<>();
        terminals.put("FOR_KW", null);
        terminals.put("L_B", null);
        terminals.put("R_B", null);
        terminals.put("L_C", null);
        terminals.put("R_C", null);
        terminals.put("VAR", 17);
        terminals.put("ASSIGN_OP", null);
        terminals.put("DIGIT", 17);
        terminals.put("OP", null);
        terminals.put("CON", null);
        terminals.put("DEL", null);
        terminals.put("$", null);
        langRule.put("condition", terminals);


        openRuleInto = new ArrayList<>();
        openRuleInto.add("expr");
        openRuleInto.add("expr_continue");
        openRuleInto.add("$");
        langRules.put(1, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("$");
        langRules.put(2, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("for_loop");
        langRules.put(3, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("assign_expr");
        openRuleInto.add("DEL");
        langRules.put(4, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("expr");
        openRuleInto.add("expr_continue");
        langRules.put(5, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("EMPTY");
        langRules.put(6, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("FOR_KW");
        openRuleInto.add("L_B");
        openRuleInto.add("assign_expr");
        openRuleInto.add("DEL");
        openRuleInto.add("condition");
        openRuleInto.add("DEL");
        openRuleInto.add("assign_expr");
        openRuleInto.add("R_B");
        openRuleInto.add("L_C");
        openRuleInto.add("expr_continue");
        openRuleInto.add("R_C");
        langRules.put(7, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("VAR");
        openRuleInto.add("ASSIGN_OP");
        openRuleInto.add("value_expr");
        langRules.put(8, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("value_expr_with_br");
        openRuleInto.add("value_expr_continue");
        langRules.put(9, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("value_expr_without_br");
        openRuleInto.add("value_expr_continue");
        langRules.put(10, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("L_B");
        openRuleInto.add("value_expr");
        openRuleInto.add("R_B");
        langRules.put(11, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("value");
        openRuleInto.add("value_expr_continue");
        langRules.put(12, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("DIGIT");
        langRules.put(13, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("VAR");
        langRules.put(14, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("OP");
        openRuleInto.add("value_expr");
        langRules.put(15, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("EMPTY");
        langRules.put(16, openRuleInto);

        openRuleInto = new ArrayList<>();
        openRuleInto.add("value_expr");
        openRuleInto.add("CON");
        openRuleInto.add("value_expr");
        langRules.put(17, openRuleInto);
    }


    public ArrayList<String> getRuleRevresed(String currentParseStackTop, String currentItemOnTape) {
        ArrayList<String> reverseList = new ArrayList<>();
        if((currentItemOnTape != null)&&(currentItemOnTape != null)) {
            Integer digitOnCross = (Integer) langRule.get(currentParseStackTop).get(currentItemOnTape);
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!CHECK CYCLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (digitOnCross != null) {
                for (int i = langRules.get((int) digitOnCross).size() - 1; i >= 0; i--) {
                    reverseList.add(langRules.get((int) digitOnCross).get(i));
                }
                return reverseList;
            }
            else {
                System.out.println("digitOnCross = NULL");
            }
        }
        return null;
    }
}
