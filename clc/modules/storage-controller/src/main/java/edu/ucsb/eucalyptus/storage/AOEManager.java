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
 * Author: Sunil Soman sunils@cs.ucsb.edu
 */

package edu.ucsb.eucalyptus.storage;

import edu.ucsb.eucalyptus.cloud.ws.StreamConsumer;
import org.apache.log4j.Logger;

public class AOEManager implements StorageExportManager {
    private static Logger LOG = Logger.getLogger(AOEManager.class);
    public native int exportVolume(String iface, String lvName, int major, int minor);

    public void unexportVolume(int vbladePid) {
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(new String[]{LVM2Manager.eucaHome + LVM2Manager.EUCA_ROOT_WRAPPER, "kill", "-9", String.valueOf(vbladePid)});
            StreamConsumer error = new StreamConsumer(proc.getErrorStream());
            StreamConsumer output = new StreamConsumer(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            output.join();
        } catch (Throwable t) {
            LOG.error(t);
        }
    }

    public void loadModule() {
        try
        {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(new String[]{LVM2Manager.eucaHome + LVM2Manager.EUCA_ROOT_WRAPPER, "modprobe", "aoe"});
            StreamConsumer error = new StreamConsumer(proc.getErrorStream());
            StreamConsumer output = new StreamConsumer(proc.getInputStream());
            error.start();
            output.start();
            proc.waitFor();
            output.join();
        } catch (Throwable t) {
            LOG.error(t);
        }
    }


    public AOEManager()  {
        loadModule();
    }
}
