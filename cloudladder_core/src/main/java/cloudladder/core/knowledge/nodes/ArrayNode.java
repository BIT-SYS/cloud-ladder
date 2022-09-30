package cloudladder.core.knowledge.nodes;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.object.CLArray;

public class ArrayNode extends EntityNode {
    @Override
    public Class getEntityType() {
        return CLArray.class;
    }
}
