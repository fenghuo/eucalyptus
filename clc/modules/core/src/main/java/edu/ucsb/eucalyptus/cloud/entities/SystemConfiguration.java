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
 * Author: Sunil Soman sunils@cs.ucsb.edu
 * Author: Dmitrii Zagorodnov dmitrii@cs.ucsb.edu
 */

package edu.ucsb.eucalyptus.cloud.entities;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

@Entity
@Table( name = "system_info" )
@Cache( usage = CacheConcurrencyStrategy.READ_WRITE )
public class SystemConfiguration {
    @Id
    @GeneratedValue
    @Column( name = "system_info_id" )
    private Long id = -1l;
    @Column( name = "system_info_storage_url" )
    private String storageUrl;
    @Column( name = "system_info_default_kernel" )
    private String defaultKernel;
    @Column( name = "system_info_default_ramdisk" )
    private String defaultRamdisk;
    @Column( name = "system_registration_id" )
    private String registrationId;
    @Column( name = "system_max_user_public_addresses" )
    private Integer maxUserPublicAddresses;
    @Column( name = "system_do_dynamic_public_addresses" )
    private Boolean doDynamicPublicAddresses;
    @Column( name = "system_reserved_public_addresses" )
    private Integer systemReservedPublicAddresses;
    @Column( name = "zero_fill_volumes" )
    private Boolean zeroFillVolumes;
    @Column( name = "dns_domain" )
    private String dnsDomain;
    @Column( name = "nameserver" )
    private String nameserver;
    @Column( name = "ns_address" )
    private String nameserverAddress;

    public SystemConfiguration() {}

    public SystemConfiguration(	final String storageUrl,
                                   final String defaultKernel,
                                   final String defaultRamdisk,
                                   final Integer maxUserPublicAddresses,
                                   final Boolean doDynamicPublicAddresses,
                                   final Integer systemReservedPublicAddresses,
                                   final Boolean zeroFillVolumes,
                                   final String dnsDomain,
                                   final String nameserver,
                                   final String nameserverAddress)
    {
        this.storageUrl = storageUrl;
        this.defaultKernel = defaultKernel;
        this.defaultRamdisk = defaultRamdisk;
        this.maxUserPublicAddresses = maxUserPublicAddresses;
        this.doDynamicPublicAddresses = doDynamicPublicAddresses;
        this.systemReservedPublicAddresses = systemReservedPublicAddresses;
        this.dnsDomain = dnsDomain;
        this.zeroFillVolumes = zeroFillVolumes;
        this.nameserver = nameserver;
        this.nameserverAddress = nameserverAddress;
    }

    public Long getId() {
        return id;
    }

    public String getStorageUrl() {
        return storageUrl;
    }

    public String getDefaultKernel() {
        return defaultKernel;
    }

    public String getDefaultRamdisk() {
        return defaultRamdisk;
    }

    public void setStorageUrl( final String storageUrl ) {
        this.storageUrl = storageUrl;
    }

    public void setDefaultKernel( final String defaultKernel ) {
        this.defaultKernel = defaultKernel;
    }

    public void setDefaultRamdisk( final String defaultRamdisk ) {
        this.defaultRamdisk = defaultRamdisk;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId( final String registrationId ) {
        this.registrationId = registrationId;
    }

    public Integer getMaxUserPublicAddresses() {
        return maxUserPublicAddresses;
    }

    public void setMaxUserPublicAddresses( final Integer maxUserPublicAddresses ) {
        this.maxUserPublicAddresses = maxUserPublicAddresses;
    }

    public Integer getSystemReservedPublicAddresses() {
        return systemReservedPublicAddresses;
    }

    public void setSystemReservedPublicAddresses( final Integer systemReservedPublicAddresses ) {
        this.systemReservedPublicAddresses = systemReservedPublicAddresses;
    }

    public Boolean isDoDynamicPublicAddresses() {
        return doDynamicPublicAddresses;
    }

    public void setDoDynamicPublicAddresses( final Boolean doDynamicPublicAddresses ) {
        this.doDynamicPublicAddresses = doDynamicPublicAddresses;
    }

    public String getDnsDomain() {
        return dnsDomain;
    }

    public void setDnsDomain(String dnsDomain) {
        this.dnsDomain = dnsDomain;
    }

    public String getNameserver() {
        return nameserver;
    }

    public void setNameserver(String nameserver) {
        this.nameserver = nameserver;
    }

    public String getNameserverAddress() {
        return nameserverAddress;
    }

    public void setNameserverAddress(String nameserverAddress) {
        this.nameserverAddress = nameserverAddress;
    }

	public Boolean getZeroFillVolumes() {
		return zeroFillVolumes;
	}

	public void setZeroFillVolumes(Boolean zeroFillVolumes) {
		this.zeroFillVolumes = zeroFillVolumes;
	}
}
