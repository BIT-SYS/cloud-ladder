package cloudladder.core.object;

import ij.ImagePlus;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@CLObjectAnnotation(typeIdentifier = "image")
public class CLImage extends CLObject {
    public final ImagePlus image;
}
