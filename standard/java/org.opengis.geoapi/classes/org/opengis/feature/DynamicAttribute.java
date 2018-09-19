/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2006-2018 Open Geospatial Consortium, Inc.
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
package org.opengis.feature;

import java.util.Collection;
import org.opengis.annotation.UML;
import org.opengis.temporal.TemporalPosition;

import static org.opengis.annotation.Specification.OGC_14083;


/**
 * An instance of a {@link DynamicAttributeType} containing time-dependent values of an attribute.
 * A dynamic attribute represents the result of ascertaining the value of an attribute of a moving
 * feature that changes over time and/or location. Dynamic attributes contain non-spatial values;
 * time-varying geometric values should be represented by trajectories instead.
 * {@code DynamicAttribute} holds four main information:
 *
 * <ul>
 *   <li>A {@linkplain #getType() reference to a dynamic attribute type}
 *       which defines the base Java type and domain of valid values.</li>
 *   <li>One or more {@linkplain #valuesAt values at given times},
 *       which may be singletons ([0 … 1] multiplicity) or multi-valued ([0 … ∞] multiplicity).</li>
 *   <li>One or more {@linkplain #getValues() values at no particular time} (optional),
 *       inherited from the parent {@link Attribute} interface.</li>
 *   <li>Optional {@linkplain #characteristics() characteristics} about the attribute.</li>
 * </ul>
 *
 * Values are evaluated at given {@link TemporalPosition}s, which may be calendar dates or Julian days for instances.
 * Even though the value of dynamic attribute depends on the spatiotemporal location,
 * this interface only considers the temporal dependencies of their changes of value.
 * Evaluating at the given temporal position may require interpolation.
 * Interpolation algorithm, if any, is implementation-dependent.
 *
 * <p>{@code DynamicAttribute} can be instantiated by calls to {@link DynamicAttributeType#newInstance()}.</p>
 *
 * @param <V> the type of dynamic attribute values.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 *
 * @see DynamicAttributeType
 * @see Attribute
 */
@UML(identifier="AttributeType", specification=OGC_14083)
public interface DynamicAttribute<V> extends Attribute<V> {
    /**
     * Returns information about the dynamic attribute (base Java class, domain of values, <i>etc.</i>).
     *
     * @return information about the dynamic attribute.
     */
    @Override
    DynamicAttributeType<V> getType();

    /**
     * Returns the attribute value at the given time, or {@code null} if none.
     * This convenience method can be invoked in the common case where the
     * {@linkplain DynamicAttributeType#getMaximumOccurs() maximum number}
     * of dynamic attribute values is restricted to 1 or 0.
     *
     * @param  time  the date, Julian day or other means to represent a position in time.
     * @return the attribute value at the given time (may be {@code null}).
     * @throws OutOfTemporalDomainException if the given temporal time is outside the period of validity.
     * @throws MultiValuedPropertyException if this attribute contains more than one value at the given time.
     */
    V valueAt(TemporalPosition time) throws OutOfTemporalDomainException, MultiValuedPropertyException;

    /**
     * Returns all attribute values at the given time, or an empty collection if none.
     * Evaluating at the given temporal position may require interpolation.
     * Interpolation algorithm, if any, is implementation-dependent.
     *
     * <div class="note"><b>Implementation note</b><br>
     * there is different approaches in the way that collection elements are related to those property values:
     * <ul>
     *   <li>The collection may be a snapshot of property values at the method invocation time.</li>
     *   <li>The collection may be an unmodifiable view of properties values.</li>
     *   <li>The collection may be <cite>live</cite> (changes in the collection are reflected in this attribute, and vis-versa).</li>
     * </ul>
     * This method does not mandate a particular approach.
     * However implementations should document which policy they choose.
     * </div>
     *
     * @param  time  the date, Julian day or other means to represent a position in time.
     * @return the attribute values.
     * @throws OutOfTemporalDomainException if the given temporal time is outside the period of validity.
     */
    @UML(identifier="valueAt", specification=OGC_14083)
    Collection<V> valuesAt(TemporalPosition time) throws OutOfTemporalDomainException;
}
