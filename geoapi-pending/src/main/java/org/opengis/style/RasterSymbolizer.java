/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2008-2018 Open Geospatial Consortium, Inc.
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
package org.opengis.style;

import org.opengis.annotation.XmlElement;
import org.opengis.filter.expression.Expression;


/**
 * The RasterSymbolizer describes how to render raster/matrix-coverage data
 * (e.g., satellite photos, DEMs).
 *
 * @version <A HREF="http://www.opengeospatial.org/standards/symbol">Symbology Encoding Implementation Specification 1.1.0</A>
 * @author Open Geospatial Consortium
 * @author Ian Turton, CCG
 * @author Johann Sorel (Geomatys)
 * @since GeoAPI 2.2
 */
@XmlElement("RasterSymbolizer")
public interface RasterSymbolizer extends Symbolizer {
    /**
     * Indicates the level of translucency as a floating point number whose value is between 0.0
     * and 1.0 (inclusive).  A value of zero means completely transparent.  A value of 1.0 means
     * completely opaque.  If null, the default value is 1.0, totally opaque.
     */
    @XmlElement("Opacity")
    Expression getOpacity();

    /**
     * The ChannelSelection element specifies the false-color channel selection
     * for a multi-spectral raster source (such as a multi-band
     * satellite-imagery source). Either a channel may be selected to display
     * in each of red, green, and blue, or a single channel may be selected to
     * display in grayscale.  (The spelling ?gray? is used since it seems to
     * be more common on the Web than ?grey? by a ratio of about 3:1.)
     * Contrast enhancement may be applied to each channel in isolation.
     * Channels are identified by a system and data-dependent character
     * identifier.  Commonly, channels will be labelled as ?1?, ?2?, etc.
     *
     * @return the ChannelSelection object set or null if none is available.
     */
    @XmlElement("ChannelSelection")
    ChannelSelection getChannelSelection();

    /**
     * The OverlapBehavior element tells a system how to behave when multiple
     * raster images in a layer  overlap each other, for example with
     * satellite-image scenes. LATEST_ON_TOP and EARLIEST_ON_TOP refer to the
     * time the scene was captured.   AVERAGE means to average multiple scenes
     * together.   This can produce blurry results if the source images are
     * not perfectly aligned in their geo-referencing. RANDOM means to select
     * an image (or piece thereof) randomly and place it on top.  This can
     * produce crisper  results than AVERAGE potentially more efficiently than
     * LATEST_ON_TOP or EARLIEST_ON_TOP.   The default behaviour is
     * system-dependent.
     *
     * @return LATEST_ON_TOP,EARLIEST_ON_TOP, AVERAGE or RANDOM
     */
    @XmlElement("OverlapBehavior")
    OverlapBehavior getOverlapBehavior();

    /**
     * The ColorMap element defines either the colors of a palette-type raster
     * source or the mapping of  fixed-numeric pixel values to colors.  For
     * example, a DEM raster giving elevations in meters above sea level can
     * be translated to a colored  image with a ColorMap.  The quantity
     * attributes of a color-map are used for translating between numeric
     * matrixes and color rasters and the ColorMap entries should be in order
     * of increasing numeric quantity so  that intermediate numeric values can
     * be matched to a color (or be interpolated between two colors).   Labels
     * may be used for legends or may be used in the future to match character
     * values.   Not all systems can support opacity in colormaps.  The
     * default opacity is 1.0 (fully opaque).   Defaults for quantity and
     * label are system-dependent.
     *
     * @return the ColorMap for the raster
     */
    @XmlElement("ColorMap")
    ColorMap getColorMap();

    /**
     * The ContrastEnhancement element defines contrast enhancement for a
     * channel of a false-color image or  for a color image. In the case of a
     * color image, the relative grayscale brightness of a pixel color is
     * used.  ?Normalize? means to stretch the contrast so that the dimmest
     * color is stretched to black and  the brightest color is stretched to
     * white, with all colors in between stretched out linearly.   ?Histogram?
     * means to stretch the contrast based on a histogram of how many colors
     * are at  each brightness level on input, with the goal of producing
     * equal number of pixels in the image at each brightness level on output.
     * This has the effect of revealing many subtle ground features.   A
     * ?GammaValue? tells how much to brighten (value greater than 1.0) or dim
     * (value less than 1.0) an image. The default GammaValue is 1.0 (no
     * change). If none of Normalize, Histogram, or GammaValue are selected in
     * a ContrastEnhancement, then no enhancement is performed.
     *
     * @return the contrast enhancement
     */
    @XmlElement("ContrastEnhancement")
    ContrastEnhancement getContrastEnhancement();

    /**
     * The ShadedRelief element selects the application of relief shading (or
     * ?hill shading?) to an image for  a three-dimensional visual effect.  It
     * is defined as: Exact parameters of the shading are system-dependent
     * (for now).  If the BrightnessOnly flag is ?0?  (false, default), the
     * shading is applied to the layer being rendered as the current
     * RasterSymbol. If BrightnessOnly is ?1? (true), the shading is applied
     * to the brightness of the colors in the rendering canvas generated so
     * far by other layers, with the effect of relief-shading these other
     * layers. The default for BrightnessOnly is ?0? (false).  The
     * ReliefFactor gives the amount of exaggeration to  use for the height of
     * the ?hills.?  A value of around 55 (times) gives reasonable results for
     * Earth-based DEMs. The default value is system-dependent.
     *
     * @return the shaded relief object
     */
    @XmlElement("ShadedRelief")
    ShadedRelief getShadedRelief();

    /**
     * The ImageOutline element specifies that individual source rasters in a
     * multi-raster set (such as a  set of satellite-image scenes) should be
     * outlined with either a LineStringSymbol or PolygonSymbol.
     *
     * An Opacity of 0.0 can be selected for the main raster to avoid rendering
     * the main-raster pixels, or  an opacity can be used for a
     * PolygonSymbolizer Fill to allow the main-raster data be visible through
     * the fill.
     *
     * @return the relevant symbolizer
     */
    @XmlElement("ImageOutline")
    Symbolizer getImageOutline();

    /**
     * calls the visit method of a StyleVisitor
     *
     * @param visitor the style visitor
     */
    Object accept(StyleVisitor visitor, Object extraData);
}
