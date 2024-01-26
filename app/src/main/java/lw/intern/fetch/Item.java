package lw.intern.fetch;

public class Item implements ItemInterface {
    private int id;
    private int listId;
    private String name;

    public Item(int id, int listId, String name) {
        this.id = id;
        this.listId = listId;
        this.name = name;
    }


    public int getListId() {
        return listId;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", listId=" + listId +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(ItemInterface o) {
        int listIdCompare = this.getListId() - o.getListId();
        int nameCompare = Integer.parseInt(this.getName().split(" ")[1]) - Integer.parseInt(o.getName().split(" ")[1]);
        return listIdCompare == 0 ? nameCompare : listIdCompare;
    }
}
