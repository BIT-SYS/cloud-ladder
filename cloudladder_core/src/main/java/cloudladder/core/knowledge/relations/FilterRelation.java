package cloudladder.core.knowledge.relations;

import cloudladder.core.knowledge.RelationShip;
import cloudladder.core.knowledge.nodes.ArrayNode;

public class FilterRelation extends RelationShip {
    public FilterRelation() {
        super("filter relation: filter one array to another");

        this.addFrom(new ArrayNode());
        this.addTo(new ArrayNode());
    }
}
