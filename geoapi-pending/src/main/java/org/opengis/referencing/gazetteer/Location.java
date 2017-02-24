/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2016 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.referencing.gazetteer;

import java.util.Collection;
import org.opengis.util.InternationalString;
import org.opengis.geometry.coordinate.Position;
import org.opengis.metadata.extent.TemporalExtent;
import org.opengis.metadata.extent.GeographicExtent;
import org.opengis.metadata.citation.Party;
import org.opengis.annotation.UML;

import static org.opengis.annotation.Obligation.*;
import static org.opengis.annotation.Specification.*;


/**
 * Location instance.
 * The minimum set of attributes of each location instance is:
 * <ul>
 *   <li>geographic identifier;</li>
 *   <li>geographic extent;</li>
 *   <li>administrator.</li>
 * </ul>
 *
 * The following may also be recorded:
 * <ul>
 *   <li>temporal extent;</li>
 *   <li>alternative geographic identifier;</li>
 *   <li>position;</li>
 *   <li>parent location instance;</li>
 *   <li>child location instance.</li>
 * </ul>
 *
 * Position must be recorded if the geographic identifier contains insufficient information to identify location.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
@UML(identifier="SI_LocationInstance", specification=ISO_19112)
public interface Location {
    /**
     * Unique identifier for the location instance.
     * In order to ensure that a geographic identifier is unique within a wider geographic domain,
     * the geographic identifier may need to include an identifier of an instance of a parent location type,
     * for example “Paris, Texas”.
     *
     * @return unique identifier for the location instance.
     */
    @UML(identifier="geographicIdentifier", obligation=MANDATORY, specification=ISO_19112)
    InternationalString getGeographicIdentifier();

    /**
     * Date of creation of this version of the location instance.
     *
     * @return date of creation of this version of the location instance.
     *
     * @see ReferenceSystemUsingIdentifiers#getTheme()
     */
    @UML(identifier="temporalExtent", obligation=OPTIONAL, specification=ISO_19112)
    TemporalExtent getTemporalExtent();

    /**
     * Other identifier(s) for the location instance.
     *
     * @return other identifier(s) for the location instance, or an empty collection if none.
     */
    @UML(identifier="alternativeGeographicIdentifier", obligation=OPTIONAL, specification=ISO_19112)
    Collection<InternationalString> getAlternativeGeographicIdentifiers();

    /**
     * Description of the location instance.
     * The geographic extent shall be defined in one of the following ways:
     * <ul>
     *   <li>As a collection of smaller geographic features.
     *       Example: the European Union, defined by its constituent countries;</li>
     *   <li>By a bounding polygon, described by either of the following:
     *     <ul>
     *       <li>As a closed set of boundary segments (each defined by one or more geographic features).
     *           Example: a block defined by the bounding streets.</li>
     *       <li>By a set of coordinates. Example: a land parcel defined by the coordinates of its boundary.</li>
     *     </ul>
     *   </li>
     * </ul>
     *
     * @return description of the location instance.
     */
    @UML(identifier="geographicExtent", obligation=MANDATORY, specification=ISO_19112)
    GeographicExtent getGeographicExtent();

    /**
     * Coordinates of a representative point for the location instance.
     * An example of the position is the coordinates of the centroid of the location instance.
     * This provides a linking mechanism to spatial referencing by coordinates.
     *
     * <p>Position must be recorded if the geographic identifier contains insufficient information
     * to identify location.</p>
     *
     * @departure generalization
     *   ISO 19112 declares the <code>GM_Point</code> type. GeoAPI uses the <code>Position</code> union
     *   for allowing the use of either <code>GM_Point</code> or <code>DirectPosition</code>.
     *
     * @return coordinates of a representative point for the location instance.
     */
    @UML(identifier="position", obligation=CONDITIONAL, specification=ISO_19112)
    Position getPosition();

    /**
     * Name of organization responsible for defining the characteristics of the location instance.
     *
     * @return organization responsible for defining the characteristics of the location instance.
     *
     * @see Gazetteer#getCustodian()
     * @see LocationType#getOwner()
     * @see ReferenceSystemUsingIdentifiers#getOverallOwner()
     */
    @UML(identifier="administrator", obligation=MANDATORY, specification=ISO_19112)
    Party getAdministrator();

    /**
     * Location instances of a different location type, for which this location instance is a sub-division.
     *
     * @return parent locations, or an empty collection if none.
     *
     * @see LocationType#getParents()
     */
    @UML(identifier="parent", obligation=OPTIONAL, specification=ISO_19112)
    Collection<Location> getParents();

    /**
     * Location instances of a different location type which subdivides this location instance.
     *
     * @return child locations, or an empty collection if none.
     *
     * @see LocationType#getChildren()
     */
    @UML(identifier="child", obligation=OPTIONAL, specification=ISO_19112)
    Collection<Location> getChildren();
}
