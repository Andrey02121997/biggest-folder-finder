import java.io.File;
import java.util.ArrayList;

public class Node
{
    private File folder;
    private ArrayList<Node> children;
    private long size;
    private int level;
    private long sizeLimite;

    public Node(File folder, long sizeLimite)
    {
        this.folder = folder;
        this.sizeLimite = sizeLimite;
        children = new ArrayList<>();
    }

    public File getFolder()
    {
        return folder;
    }

    public void addChild(Node node)
    {
        node.setLevel(level + 1);
        children.add(node);
    }

    public ArrayList<Node> getChildren()
    {
        return children;
    }

    public long getSize()
    {
        return size;
    }

    public void setSize(long size)
    {
        this.size = size;
    }

    public long getSizeLimite() {
        return sizeLimite;
    }

    public void setSizeLimite(long sizeLimite) {
        this.sizeLimite = sizeLimite;
    }

    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        String size = SizeCalculator.getHumanReadableSize(getSize());
        builder.append(folder.getName() + " — " + size + "\n");
        if (getSize() > sizeLimite)
        {
            for(Node child : children) {
                builder.append("  ".repeat(this.level) + child.toString());
            }
        }
        return builder.toString();
    }

    private void setLevel(int level)
    {
        this.level = level;
    }
}
