/*
 * Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 */
package org.opengis.cs;

// J2SE direct dependencies and extensions
import javax.units.Unit;  // For Javadoc

// OpenGIS direct dependencies
import org.opengis.rs.Info;


/**
 * The set of coordinate system axes that spans a given coordinate space. A coordinate system (CS)
 * is derived from a set of (mathematical) rules for specifying how coordinates in a given space
 * are to be assigned to points. The coordinate values in a coordinate tuple shall be recorded in
 * the order in which the coordinate system axes associations are recorded, whenever those
 * coordinates use a coordinate reference system that uses this coordinate system.
 *
 * @UML abstract CS_CoordinateSystem
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 *
 * @see org.opengis.cs.CoordinateSystemAxis
 * @see javax.units.Unit
 * @see org.opengis.cd.Datum
 * @see org.opengis.sc.CoordinateReferenceSystem
 */
public interface CoordinateSystem extends Info {
    /**
     * Returns the ordered set of axis for this coordinate system. Each coordinate system
     * must have at least one axis.
     *
     * @return The ordered set of axis.
     * @UML association usesAxis
     */
    public CoordinateSystemAxis[] getAxis();
}
