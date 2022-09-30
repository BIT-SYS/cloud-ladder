package cloudladder.core.knowledge;

import lombok.AllArgsConstructor;

import java.util.ArrayList;

public class RelationShip {
    public String description;
    public ArrayList<EntityNode> from;
    public ArrayList<EntityNode> to;

    public RelationShip(String desc) {
        this.description = desc;
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
    }

    public void addFrom(EntityNode node) {
        this.from.add(node);
    }

    public void addTo(EntityNode node) {
        this.to.add(node);
    }

    public boolean matchInterface(ArrayList<Class> args, ArrayList<Class> rets) {
        if (this.from.size() != args.size()) {
            return false;
        }
        if (this.to.size() != rets.size()) {
            return false;
        }

        for (int i = 0; i < this.from.size(); i++) {
            if (this.from.get(i).getEntityType() != args.get(i)) {
                return false;
            }
        }
        for (int i = 0; i < this.to.size(); i++) {
            if (this.to.get(i).getEntityType() != rets.get(i)) {
                return false;
            }
        }

        return true;
    }
}
