/*************************************************************************
 * Copyright 2009-2012 Eucalyptus Systems, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Please contact Eucalyptus Systems, Inc., 6755 Hollister Ave., Goleta
 * CA 93117, USA or visit http://www.eucalyptus.com/licenses/ if you need
 * additional information or have any questions.
 ************************************************************************/
package com.eucalyptus.reporting.event_store;

import org.apache.log4j.Logger;

import com.eucalyptus.entities.EntityWrapper;

import com.eucalyptus.entities.AbstractPersistent;

public class ReportingS3ObjectEventStore
	extends AbstractPersistent
{
	private static Logger LOG = Logger.getLogger( ReportingS3ObjectEventStore.class );

	private static ReportingS3ObjectEventStore instance = null;
	
	public static synchronized ReportingS3ObjectEventStore getInstance()
	{
		if (instance == null) {
			instance = new ReportingS3ObjectEventStore();
		}
		return instance;
	}
	
	private ReportingS3ObjectEventStore()
	{
		
	}

	public void insertS3ObjectCreateEvent(String s3BucketName, String s3ObjectName, long timestampMs,
			String userId)
	{
		EntityWrapper<ReportingS3ObjectCreateEvent> entityWrapper =
			EntityWrapper.get(ReportingS3ObjectCreateEvent.class);

		try {
			ReportingS3ObjectCreateEvent s3Object =
				new ReportingS3ObjectCreateEvent(s3BucketName, s3ObjectName, timestampMs, userId);
			entityWrapper.add(s3Object);
			entityWrapper.commit();
			LOG.debug("Added event to db:" + s3Object);
		} catch (Exception ex) {
			LOG.error(ex);
			entityWrapper.rollback();
			throw new RuntimeException(ex);
		}					
	}

	public void insertS3ObjectDeleteEvent(String s3BucketName, String s3ObjectName, long timestampMs)
	{
		EntityWrapper<ReportingS3ObjectDeleteEvent> entityWrapper =
			EntityWrapper.get(ReportingS3ObjectDeleteEvent.class);

		try {
			ReportingS3ObjectDeleteEvent s3Object =
				new ReportingS3ObjectDeleteEvent(s3BucketName, s3ObjectName, timestampMs);
			entityWrapper.add(s3Object);
			entityWrapper.commit();
			LOG.debug("Added event to db:" + s3Object);
		} catch (Exception ex) {
			LOG.error(ex);
			entityWrapper.rollback();
			throw new RuntimeException(ex);
		}							
	}

	public void insertS3ObjectUsageEvent(String s3BucketName, String s3ObjectName, long timestampMs,
			Long cumulativeMegsRead, Long cumulativeMegsWritten, Long cumulativeGetRequests,
			Long cumulativePutRequests)
	{
		EntityWrapper<ReportingS3ObjectUsageEvent> entityWrapper =
			EntityWrapper.get(ReportingS3ObjectUsageEvent.class);

		try {
			ReportingS3ObjectUsageEvent s3Object = new ReportingS3ObjectUsageEvent(s3BucketName,
					s3ObjectName, timestampMs, cumulativeMegsRead, cumulativeMegsWritten,
					cumulativeGetRequests, cumulativePutRequests);
			entityWrapper.add(s3Object);
			entityWrapper.commit();
			LOG.debug("Added event to db:" + s3Object);
		} catch (Exception ex) {
			LOG.error(ex);
			entityWrapper.rollback();
			throw new RuntimeException(ex);
		}							
	}

}