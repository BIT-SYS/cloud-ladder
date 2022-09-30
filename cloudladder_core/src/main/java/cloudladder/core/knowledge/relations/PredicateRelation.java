package cloudladder.core.knowledge.relations;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.knowledge.RelationShip;
import cloudladder.core.knowledge.nodes.BoolNode;
import cloudladder.core.knowledge.nodes.ObjectNode;

import java.util.ArrayList;

public class PredicateRelation extends RelationShip {
    public PredicateRelation() {
        super("predicate relation (map anything to a boolean)");

        ArrayList<EntityNode> args = new ArrayList<>();
        ArrayList<EntityNode> to = new ArrayList<>();
        args.add(new ObjectNode());
        to.add(new BoolNode());

        this.from = args;
        this.to = to;
    }
}
