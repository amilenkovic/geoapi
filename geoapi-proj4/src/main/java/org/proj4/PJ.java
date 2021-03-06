/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    This file is hereby placed into the Public Domain.
 *    This means anyone is free to do whatever they wish with this file.
 *
 *    The Proj.4 wrappers are provided as code examples, in the hope to facilitate
 *    GeoAPI implementations backed by other libraries. Implementors can take this
 *    source code and use it for any purpose, commercial or non-commercial, copyrighted
 *    or open-source, with no legal obligation to acknowledge the borrowing/copying
 *    in any way.
 *
 *    A copy of this file is given to the Proj.4 project under their own Open Source license.
 *    Because the "org.proj4" namespace is the property of the Proj.4 project, change to this
 *    file shall be made in collaboration with the Proj.4 project.
 */
package org.proj4;

import java.util.Objects;


/**
 * Wraps the <a href="http://proj.osgeo.org/">{@literal Proj.4}</a> {@code PJ} native data structure.
 * Almost every methods defined in this class are native methods delegating the work to the Proj.4 library.
 * This class is the only place where such native methods are defined.
 *
 * <p>In the Proj.4 library, the {@code PJ} structure aggregates in a single place information usually
 * splitted in many different ISO 19111 interfaces: {@link org.opengis.referencing.datum.Ellipsoid},
 * {@link org.opengis.referencing.datum.Datum}, {@link org.opengis.referencing.datum.PrimeMeridian},
 * {@link org.opengis.referencing.cs.CoordinateSystem}, {@link org.opengis.referencing.crs.CoordinateReferenceSystem}
 * and their sub-interfaces. The relationship with the GeoAPI methods is indicated in the
 * "See" tags when appropriate.</p>
 *
 * <p>This class requires PROJ to be compiled with JNI enabled.
 * See <a href="https://github.com/OSGeo/proj.4/tree/master/jniwrap#proj-bridge-to-java">PROJ bridge to Java</a> for details.</p>
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
public class PJ {
    /**
     * The maximal number of dimension accepted by the {@link #transform(PJ, int, double[], int, int)} method.
     * This upper limit is actually somewhat arbitrary. This limit exists mostly as a safety against potential misuse.
     */
    public static final int DIMENSION_MAX = 100;

    /**
     * Loads the {@literal Proj.4} library.
     */
    static {
        System.loadLibrary("proj");
    }

    /**
     * The pointer to {@code PJ} structure allocated in the C/C++ heap. This value has no meaning in Java code.
     * <strong>Do not modify</strong>, since this value is used by Proj.4. Do not rename neither, unless you update
     * accordingly the C code in JNI wrappers.
     */
    private final long ptr;

    /**
     * Creates a new {@code PJ} structure from the given {@literal Proj.4} definition string.
     *
     * @param  definition  the Proj.4 definition string.
     * @throws IllegalArgumentException if the PJ structure can not be created from the given string.
     */
    public PJ(final String definition) throws IllegalArgumentException {
        Objects.requireNonNull(definition);
        ptr = allocatePJ(definition);
        if (ptr == 0) {
            throw new IllegalArgumentException(definition);
        }
    }

    /**
     * Creates a new {@code PJ} structure derived from an existing {@code PJ} object.
     * This constructor is usually for getting the
     * {@linkplain org.opengis.referencing.crs.ProjectedCRS#getBaseCRS() base geographic CRS}
     * from a {@linkplain org.opengis.referencing.crs.ProjectedCRS projected CRS}.
     *
     * @param  crs   the CRS (usually projected) from which to derive a new CRS.
     * @param  type  the type of the new CRS. Currently, only {@link Type#GEOGRAPHIC} is supported.
     * @throws IllegalArgumentException if the PJ structure can not be created.
     */
    public PJ(final PJ crs, final Type type) throws IllegalArgumentException {
        Objects.requireNonNull(crs, "The CRS must be non-null.");
        if (type != Type.GEOGRAPHIC) {
            throw new IllegalArgumentException("Can not derive the " + type + " type.");
        }
        ptr = allocateGeoPJ(crs);
        if (ptr == 0) {
            throw new IllegalArgumentException(crs.getLastError());
        }
    }

    /**
     * Allocates a PJ native data structure and returns the pointer to it. This method should be
     * invoked by the constructor only, and the return value <strong>must</strong> be assigned
     * to the {@link #ptr} field. The allocated structure is released by the {@link #finalize()}
     * method.
     *
     * @param  definition  the Proj.4 definition string.
     * @return a pointer to the PJ native data structure, or 0 if the operation failed.
     */
    private static native long allocatePJ(String definition);

    /**
     * Allocates a PJ native data structure for the base geographic CRS of the given CRS, and
     * returns the pointer to it. This method should be invoked by the constructor only, and
     * the return value <strong>must</strong> be assigned to the {@link #ptr} field.
     * The allocated structure is released by the {@link #finalize()} method.
     *
     * @param  projected  the CRS from which to derive the base geographic CRS.
     * @return a pointer to the PJ native data structure, or 0 if the operation failed.
     */
    private static native long allocateGeoPJ(PJ projected);

    /**
     * Returns the version number of the {@literal Proj.4} library.
     *
     * @return the Proj.4 release string.
     */
    public static native String getVersion();

    /**
     * Returns the {@literal Proj.4} definition string. This is the string given to the constructor,
     * expanded with as much information as possible.
     *
     * @return the Proj.4 definition string.
     */
    public native String getDefinition();

    /**
     * Returns the Coordinate Reference System type.
     *
     * @return the CRS type.
     */
    public native Type getType();

    /**
     * The coordinate reference system (CRS) type returned by {@link PJ#getType()}.
     * In the Proj.4 library, a CRS can only be geographic, geocentric or projected,
     * without distinction between 2D and 3D CRS.
     *
     * @author  Martin Desruisseaux (Geomatys)
     * @version 3.1
     * @since   3.1
     */
    public static enum Type {
        /*
         * IMPLEMENTATION NOTE: Do not rename those fields, unless you update the
         * native C code accordingly.
         */

        /**
         * The CRS is of type {@link org.opengis.referencing.crs.GeographicCRS}.
         * The CRS can be two-dimensional or three-dimensional.
         */
        GEOGRAPHIC,

        /**
         * The CRS is of type {@link org.opengis.referencing.crs.GeocentricCRS}.
         * The CRS can only be three-dimensional.
         */
        GEOCENTRIC,

        /**
         * The CRS is of type {@link org.opengis.referencing.crs.ProjectedCRS}.
         * The CRS can be two-dimensional or three-dimensional.
         */
        PROJECTED
    }

    /**
     * Returns the value stored in the {@code a_orig} PJ field.
     *
     * @return the axis length stored in {@code a_orig}.
     *
     * @see org.opengis.referencing.datum.Ellipsoid#getSemiMajorAxis()
     */
    public native double getSemiMajorAxis();

    /**
     * Returns the value computed from PJ fields by {@code √((a_orig)² × (1 - es_orig))}.
     *
     * @return the axis length computed by {@code √((a_orig)² × (1 - es_orig))}.
     *
     * @see org.opengis.referencing.datum.Ellipsoid#getSemiMinorAxis()
     */
    public native double getSemiMinorAxis();

    /**
     * Returns the square of the ellipsoid eccentricity (ε²). The eccentricity
     * is related to axis length by ε=√(1-(<var>b</var>/<var>a</var>)²). The
     * eccentricity of a sphere is zero.
     *
     * @return the eccentricity.
     *
     * @see org.opengis.referencing.datum.Ellipsoid#isSphere()
     * @see org.opengis.referencing.datum.Ellipsoid#getInverseFlattening()
     */
    public native double getEccentricitySquared();

    /**
     * Returns an array of character indicating the direction of each axis. Directions are
     * characters like {@code 'e'} for East, {@code 'n'} for North and {@code 'u'} for Up.
     *
     * @return the axis directions.
     *
     * @see org.opengis.referencing.cs.CoordinateSystemAxis#getDirection()
     */
    public native char[] getAxisDirections();

    /**
     * Longitude of the prime meridian measured from the Greenwich meridian, positive eastward.
     *
     * @return the prime meridian longitude, in degrees.
     *
     * @see org.opengis.referencing.datum.PrimeMeridian#getGreenwichLongitude()
     */
    public native double getGreenwichLongitude();

    /**
     * Returns the conversion factor from the linear units to metres.
     *
     * @param  vertical {@code false} for the conversion factor of horizontal axes,
     *         or {@code true} for the conversion factor of the vertical axis.
     * @return the conversion factor to metres for the given axis.
     */
    public native double getLinearUnitToMetre(boolean vertical);

    /**
     * Transforms in-place the coordinates in the given array.
     * The coordinates array shall contain (<var>x</var>,<var>y</var>,<var>z</var>,…) tuples,
     * where the <var>z</var> and any additional dimensions are optional.
     * Note that any dimension after the <var>z</var> value are ignored.
     *
     * <p>Input and output units:</p>
     * <ul>
     *   <li>Angular units (as in longitude and latitudes) are decimal degrees.</li>
     *   <li>Linear units are usually metres, but this is actually projection-dependent.</li>
     * </ul>
     *
     * @param  target       the target CRS.
     * @param  dimension    the dimension of each coordinate value. Must be in the [2-{@value #DIMENSION_MAX}] range.
     * @param  coordinates  the coordinates to transform, as a sequence of (<var>x</var>,<var>y</var>,&lt;<var>z</var>&gt;,…) tuples.
     * @param  offset       offset of the first coordinate in the given array.
     * @param  numPts       number of points to transform.
     * @throws NullPointerException if the {@code target} or {@code coordinates} argument is null.
     * @throws IndexOutOfBoundsException if the {@code offset} or {@code numPts} arguments are invalid.
     * @throws PJException if the operation failed for another reason (provided by Proj.4).
     *
     * @see org.opengis.referencing.operation.MathTransform#transform(double[], int, double[], int, int)
     */
    public native void transform(PJ target, int dimension, double[] coordinates, int offset, int numPts)
            throws PJException;

    /**
     * Returns a description of the last error that occurred, or {@code null} if none.
     *
     * @return the last error that occurred, or {@code null}.
     */
    public native String getLastError();

    /**
     * Returns the string representation of the PJ structure.
     *
     * @return the string representation.
     */
    @Override
    public native String toString();

    /**
     * Deallocates the native PJ data structure.
     * This method should be invoked exactly once, only by the garbage collector,
     */
    @Override
    @SuppressWarnings("FinalizeDeclaration")
    protected final native void finalize();
}
