package cloudladder.core.knowledge.nodes;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.object.CLObject;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ObjectNode extends EntityNode {
    @Override
    public Class getEntityType() {
        return CLObject.class;
    }
}
