/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.catalog;
import java.util.Iterator;
/**
 * A Catalog is simply a collection of Catalog Entries that is organized to assist in the discovery,
 * access, and retrieval of geospatial resources that are of interest to the user, especially when the
 * existence or whereabouts of the resource are not know to the user.
 * 
 * <p>
 * Being a collection of catalog entries, a catalog should be able to enumerate each of its entries, and
 * should allow entries to be added or removed. It should also support queries that will enable the
 * user to obtain entries of interest based on specified criteria.
 * </p>
 * 
 * <p>
 * NOTE:  The specification <A HREF="http://www.opengis.org/docs/02-087r3.pdf">Catalog Services 1.1.1</A>
 * does not specify the methods of the Catalog interface.  The methods in this interface
 * were inferred from reading the abstract catalog specification: 
 * <A HREF="http://www.opengis.org/docs/99-113.pdf">Catalog Services</A> section 3.1.1
 * 
 * @UML abstract CG_Catalog
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A> 
 * @version <A HREF="http://www.opengis.org/docs/02-087r3.pdf">Catalog Services 1.1.1</A> 
 */
public interface Catalog {
	/**
	 * Searches through the catalog and finds the CatalogEntries that match the
	 * query
	 * <p>
	 * See Section 3.1.1.1.1 <i>Query Functions </i> in the <A
	 * HREF="http://www.opengis.org/docs/99-113.pdf">OGC Abstract Catalog
	 * Services </A> Specification
	 * </p>
	 * 
	 * @param query
	 *            A QueryDefinition used to select CatalogEntries
	 * @return QueryResult containing all matching CatalogEntries
	 */
	public QueryResult query(QueryDefinition query);
	/**
	 * Adds a CatalogEntry to the Catalog
	 * <p>
	 * See Section 3.1.1.1.2 <i>Other Functions on Catalog </i> in the <A
	 * HREF="http://www.opengis.org/docs/99-113.pdf">OGC Abstract Catalog
	 * Services </A> Specification
	 * </p>
	 * <p>
	 * Not all catalogs can provide this functionality so a
	 * NotImplementedException is provided
	 * </p>
	 * <p>
	 * For example A GridCoverageExchange implementation maybe implement catalog
	 * as well, however it may not bepossible for for GridCoverageExchange to
	 * remove or add to its catalog because it maybe dependent on another
	 * resource such as a database.
	 * </p>
	 * 
	 * @param entry
	 *            CatalogEntry to add
	 * @throws NotImplementedException
	 *             if Catalog does not provide this functionality then
	 *             NotImplementedException will be thrown
	 */
	public void add(CatalogEntry entry) throws NotImplementedException;
	/**
	 * Removes a CatalogEntry from the Catalog
	 * 
	 * <p>
	 * See Section 3.1.1.1.2 <i>Other Functions on Catalog </i> in the <A
	 * HREF="http://www.opengis.org/docs/99-113.pdf">OGC Abstract Catalog
	 * Services </A> Specification
	 * </p>
	 * <p>
	 * Not all catalogs can provide this functionality so a
	 * NotImplementedException is provided.
	 * </p>
	 * <p>
	 * For example A GridCoverageExchange implementation maybe implement catalog
	 * as well, however it may not bepossible for for GridCoverageExchange to
	 * remove or add to its catalog because it maybe dependent on another
	 * resource such as a database.
	 * </p>
	 * 
	 * @param entry
	 *            CatalogEntry to remove
	 * @throws NotImplementedException
	 *             if Catalog does not provide this functionality then
	 *             NotImplementedException will be thrown
	 */
	public void remove(CatalogEntry entry) throws NotImplementedException;
	/**
	 * creates an iterator that traverses through catalog
	 * <p>
	 * See Section 3.1.1.1.2 <i>Other Functions on Catalog </i> in the <A
	 * HREF="http://www.opengis.org/docs/99-113.pdf">OGC Abstract Catalog
	 * Services </A> Specification
	 * </p>
	 * 
	 * @return An iterator which can be used to traverse the catalog
	 */
	public Iterator iterator();
}