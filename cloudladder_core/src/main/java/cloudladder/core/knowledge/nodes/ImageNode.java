package cloudladder.core.knowledge.nodes;

import cloudladder.core.knowledge.EntityNode;
import cloudladder.core.object.CLImage;
import cloudladder.core.object.CLObject;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ImageNode extends EntityNode {
    @Override
    public Class getEntityType() {
        return CLImage.class;
    }
}
