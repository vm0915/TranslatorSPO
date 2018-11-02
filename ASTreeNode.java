import java.util.ArrayList;
import java.util.List;
/**
 * Created by Raymond on 02.11.2018.
 */
public class ASTreeNode extends Node {
    private List<ASTreeNode> children;
    private Token token;

    public ASTreeNode(final String value) {
        super(value);
        this.children = new ArrayList<>();
    }

    public ASTreeNode(final String value, final ASTreeNode parent) {
        super(value, parent);
        this.children = new ArrayList<>();
    }

    public void addChild(final ASTreeNode child) {
        this.children.add(child);
    }

    public Token getToken() {
        return this.token;
    }

    public void setToken(final Token token) {
        this.token = token;
    }

    public List<ASTreeNode> getChildren() {
        return this.children;
    }
    public void reverseChildren(){
        List<ASTreeNode> temp = this.children;
        List<ASTreeNode> newChildren = new ArrayList<>();
        for(int i = temp.size() - 1; i != -1; i--){
            newChildren.add(temp.get(i));
        }
        this.children = newChildren;
    }
}
