/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.cs;

// Annotations
import org.opengis.annotation.UML;
import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * A two- or three-dimensional coordinate system that consists of any combination of coordinate
 * axes not covered by any other Coordinate System type. An example is a multilinear coordinate
 * system which contains one coordinate axis that may have any 1-D shape which has no intersections
 * with itself. This non-straight axis is supplemented by one or two straight axes to complete a 2
 * or 3 dimensional coordinate system. The non-straight axis is typically incrementally straight or
 * curved. A {@code UserDefinedCS} shall have two or three
 * {@linkplain #getAxis axis associations}.
 *
 * @version <A HREF="http://portal.opengeospatial.org/files/?artifact_id=6716">Abstract specification 2.0</A>
 * @author ISO/DIS 19111
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 1.0
 */
@UML(identifier="CS_UserDefinedCS", specification=ISO_19111)
public interface UserDefinedCS extends CoordinateSystem {
}
