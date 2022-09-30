package cloudladder.core.knowledge.relations;

import cloudladder.core.knowledge.RelationShip;
import cloudladder.core.knowledge.nodes.ArrayNode;

public class MapperRelation extends RelationShip {
    public MapperRelation() {
        super("mapper relation, map an array to another");

        this.addFrom(new ArrayNode());
        this.addTo(new ArrayNode());
    }
}
