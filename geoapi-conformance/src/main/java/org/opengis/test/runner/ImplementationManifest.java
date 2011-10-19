/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2011 Open Geospatial Consortium, Inc.
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
package org.opengis.test.runner;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static org.opengis.test.runner.Runner.LINE_SEPARATOR;


/**
 * Information about a GeoAPI implementation, as found in the JAR manifest.
 * This class accepts only JAR files having at least one {@code "org.opengis.*"}
 * entry in their {@code META-INF/services/} directory. We use this criterion in
 * order to distinguish between the GeoAPI implementation and its dependencies.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 3.1
 * @since   3.1
 */
final class ImplementationManifest {
    /**
     * The prefix of GeoAPI services to search for in the JAR files, in preference order.
     */
    private static final String[] SERVICES = {
        "META-INF/services/org.opengis.referencing",
        "META-INF/services/org.opengis.util"
    };

    /**
     * The preference order of this manifest. Used only if more than one suitable
     * manifest is found.
     */
    private final int priority;

    /**
     * The implementation title, version, vendor or URL.
     * Any of those attributes accept the title may be null.
     */
    final String title, version, vendor, vendorID, url, specification, specVersion, specVendor;

    /**
     * Creates a new manifest for the given attributes.
     */
    private ImplementationManifest(final int priority, final String title, final Attributes attributes) {
        this.priority = priority;
        this.title    = title;
        version       = (String) attributes.get(Attributes.Name.IMPLEMENTATION_VERSION);
        vendor        = (String) attributes.get(Attributes.Name.IMPLEMENTATION_VENDOR);
        vendorID      = (String) attributes.get(Attributes.Name.IMPLEMENTATION_VENDOR_ID);
        url           = (String) attributes.get(Attributes.Name.IMPLEMENTATION_URL);
        specification = (String) attributes.get(Attributes.Name.SPECIFICATION_TITLE);
        specVersion   = (String) attributes.get(Attributes.Name.SPECIFICATION_VERSION);
        specVendor    = (String) attributes.get(Attributes.Name.SPECIFICATION_VENDOR);
    }

    /**
     * Parses the manifest entry of the given JAR files for information about GeoAPI
     * implementation.
     *
     * @param  files The JAR files to parse.
     * @return Information about the implementation, or {@code null} if none.
     */
    static ImplementationManifest parse(final File[] files) throws IOException {
        ImplementationManifest manifest = null;
        for (final File file : files) {
            final ImplementationManifest candidate = ImplementationManifest.parse(file);
            if (candidate != null && (manifest == null || candidate.priority < manifest.priority)) {
                manifest = candidate;
            }
        }
        return manifest;
    }

    /**
     * Parses the manifest entry of the given JAR file for information about GeoAPI
     * implementation. If such information is found, then this method returns the
     * information in an {@link ImplementationManifest} object. Otherwise this method
     * returns {@code null}.
     *
     * @param  file The JAR file to parse.
     * @return Information about the implementation, or {@code null} if none.
     * @throws IOException If an error occurred while reading the JAR file.
     */
    private static ImplementationManifest parse(final File file) throws IOException {
        int priority = 0;
        Manifest manifest = null;
        final JarFile jar = new JarFile(file, false);
        final Enumeration<JarEntry> entries = jar.entries();
scan:   while (entries.hasMoreElements()) {
            final String name = entries.nextElement().getName();
            for (int i=0; i<SERVICES.length; i++) {
                if (name.startsWith(SERVICES[i])) {
                    manifest = jar.getManifest();
                    priority = i;
                    break scan;
                }
            }
        }
        jar.close();
        if (manifest != null) {
            final Attributes attributes = manifest.getMainAttributes();
            if (attributes != null) {
                final String title = (String) attributes.get(Attributes.Name.IMPLEMENTATION_TITLE);
                if (title != null) {
                    return new ImplementationManifest(priority, title, attributes);
                }
            }
        }
        return null;
    }

    /**
     * Returns a string representation of the information provided in the manifest.
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        if (title    != null) buffer.append("Title:     ").append(title   ).append(LINE_SEPARATOR);
        if (version  != null) buffer.append("Version:   ").append(version ).append(LINE_SEPARATOR);
        if (vendor   != null) buffer.append("Vendor:    ").append(vendor  ).append(LINE_SEPARATOR);
        if (vendorID != null) buffer.append("Vendor ID: ").append(vendorID).append(LINE_SEPARATOR);
        if (url      != null) buffer.append("URL:       ").append(url     ).append(LINE_SEPARATOR);
        return buffer.toString();
    }
}
