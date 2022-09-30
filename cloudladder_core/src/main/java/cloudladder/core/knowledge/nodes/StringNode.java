package cloudladder.core.knowledge.nodes;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.object.CLString;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringNode extends EntityNode {
    @Override
    public Class getEntityType() {
        return CLString.class;
    }
}
