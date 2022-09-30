package cloudladder.core.knowledge.nodes;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.object.CLBoolean;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoolNode extends EntityNode {
    @Override
    public Class getEntityType() {
        return CLBoolean.class;
    }
}
