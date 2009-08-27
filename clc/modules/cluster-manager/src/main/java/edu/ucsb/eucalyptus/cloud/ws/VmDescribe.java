/*******************************************************************************
*Copyright (c) 2009  Eucalyptus Systems, Inc.
* 
*  This program is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, only version 3 of the License.
* 
* 
*  This file is distributed in the hope that it will be useful, but WITHOUT
*  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
*  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
*  for more details.
* 
*  You should have received a copy of the GNU General Public License along
*  with this program.  If not, see <http://www.gnu.org/licenses/>.
* 
*  Please contact Eucalyptus Systems, Inc., 130 Castilian
*  Dr., Goleta, CA 93101 USA or visit <http://www.eucalyptus.com/licenses/>
*  if you need additional information or have any questions.
* 
*  This file may incorporate work covered under the following copyright and
*  permission notice:
* 
*    SOFTWARE, AND IF ANY SUCH MATERIAL IS DISCOVERED THE PARTY DISCOVERING
*    IT MAY INFORM DR. RICH WOLSKI AT THE UNIVERSITY OF CALIFORNIA, SANTA
*    BARBARA WHO WILL THEN ASCERTAIN THE MOST APPROPRIATE REMEDY, WHICH IN
*    THE REGENTS’ DISCRETION MAY INCLUDE, WITHOUT LIMITATION, REPLACEMENT
*    OF THE CODE SO IDENTIFIED, LICENSING OF THE CODE SO IDENTIFIED, OR
*    WITHDRAWAL OF THE CODE CAPABILITY TO THE EXTENT NEEDED TO COMPLY WITH
*    ANY SUCH LICENSES OR RIGHTS.
 ******************************************************************************/
/*
 *
 * Author: Chris Grzegorczyk grze@cs.ucsb.edu
 */

package edu.ucsb.eucalyptus.cloud.ws;

import edu.ucsb.eucalyptus.cloud.*;
import edu.ucsb.eucalyptus.cloud.cluster.*;
import edu.ucsb.eucalyptus.cloud.entities.*;
import edu.ucsb.eucalyptus.msgs.*;

import java.util.*;

import com.eucalyptus.util.EntityWrapper;
import com.eucalyptus.util.EucalyptusCloudException;

public class VmDescribe extends DescribeInstancesType implements Cloneable {

  private boolean isAdmin = false;
  private List<ReservationInfoType> reservations;

  public VmDescribe( DescribeInstancesType msg )
  {
    this.setCorrelationId( msg.getCorrelationId() );
    this.setEffectiveUserId( msg.getEffectiveUserId() );
    this.setUserId( msg.getUserId() );
    this.setInstancesSet( msg.getInstancesSet() );
    this.reservations = new ArrayList<ReservationInfoType>();
  }

  public void transform() throws EucalyptusInvalidRequestException
  {
    EntityWrapper<UserInfo> db = new EntityWrapper<UserInfo>();
    UserInfo user = null;
    try
    {
      user = db.getUnique( new UserInfo( this.getUserId() ) );
      this.isAdmin = user.isAdministrator();
    }
    catch ( EucalyptusCloudException e )
    {
      db.rollback();
      throw new EucalyptusInvalidRequestException( e );
    }
    db.commit();
    if ( this.getInstancesSet() == null )
      this.setInstancesSet( new ArrayList<String>() );
  }

  public void apply() throws EucalyptusCloudException
  {
    for ( Reservation r : Reservations.getInstance().listValues() )
    {
      if( r.isEmpty() ) continue;
      if ( this.isAdmin || r.getOwnerId().equals( this.getUserId() ) )
        if ( this.getInstancesSet().isEmpty() )
          this.reservations.add( r.getAsReservationInfoType() );
        else if ( !this.getInstancesSet().isEmpty() )
        {
          ReservationInfoType rsvInfo = r.getAsReservationInfoType();
          ArrayList<RunningInstancesItemType> instList = new ArrayList<RunningInstancesItemType>();
          for( RunningInstancesItemType runInst : rsvInfo.getInstancesSet() )
          {
            if( this.getInstancesSet().contains( runInst.getInstanceId() ) )
              instList.add( runInst );
          }
          if( instList.isEmpty() ) continue;
          rsvInfo.setInstancesSet( instList );
          this.reservations.add( rsvInfo );
        }
    }
  }

  public void rollback()
  {

  }

  public DescribeInstancesResponseType getResult()
  {
    DescribeInstancesResponseType reply = (DescribeInstancesResponseType)this.getReply();
    reply.getReservationSet().addAll( this.reservations );
    return reply;
  }
}
