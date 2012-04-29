package com.debugi.app.anyvblog.location;

import android.location.Address;

public class AddressConver {
	public static String conver(Address address) {
		StringBuilder addr = new StringBuilder();
		if (address.getCountryName() != null) {
			addr.append(address.getCountryName());
		}
		if (address.getAdminArea() != null) {
			addr.append(address.getAdminArea());
		}
		if (address.getSubAdminArea() != null) {
			addr.append(address.getSubAdminArea());
		}
		if (address.getLocality() != null) {
			addr.append(address.getLocality());
		}
		if (address.getFeatureName() != null) {
			addr.append(address.getFeatureName());
		}
		if (address.getThoroughfare() != null) {
			addr.append(address.getThoroughfare());
		}
		return addr.toString();
	}
}
