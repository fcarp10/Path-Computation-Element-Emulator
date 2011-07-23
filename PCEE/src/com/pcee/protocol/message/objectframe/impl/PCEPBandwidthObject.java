/**
 *  This file is part of Path Computation Element Emulator (PCEE).
 *
 *  PCEE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PCEE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PCEE.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.pcee.protocol.message.objectframe.impl;

import com.pcee.protocol.message.PCEPComputationFactory;
import com.pcee.protocol.message.PCEPConstantValues;
import com.pcee.protocol.message.objectframe.PCEPCommonObjectHeader;
import com.pcee.protocol.message.objectframe.PCEPObjectFrame;

/**
 * <pre>
 *  0                   1                   2                   3
 *  0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                        Bandwidth                              |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * </pre>
 */

public class PCEPBandwidthObject implements PCEPObjectFrame {

	private final String NAME = "Bandwidth";

	private String bandwidth;

	private PCEPCommonObjectHeader objectHeader;
	// private LinkedList<PCEPTLVObject> tlvList;

	private int bandwidthStartBit = PCEPConstantValues.BANDWIDTH_OBJECT_BANDWIDTH_START_BIT;
	private int bandwidthEndBit = PCEPConstantValues.BANDWIDTH_OBJECT_BANDWIDTH_END_BIT;
	private int bandwidthLength = PCEPConstantValues.BANDWIDTH_OBJECT_BANDWIDTH_LENGTH;

	public PCEPBandwidthObject(PCEPCommonObjectHeader objectHeader, String bandwidth) {
		this.setObjectHeader(objectHeader);
		this.setBandwidthBinaryString(bandwidth);
		this.updateHeaderLength();
	}

	private void updateHeaderLength() {
		int objectFrameByteLength = this.getObjectFrameByteLength();
		this.getObjectHeader().setLengthDecimalValue(objectFrameByteLength);
	}

	/**
	 * Object
	 */
	public PCEPCommonObjectHeader getObjectHeader() {
		return objectHeader;
	}

	public void setObjectHeader(PCEPCommonObjectHeader objectHeader) {
		this.objectHeader = objectHeader;
	}

	public String getObjectBinaryString() {
		String binaryString = bandwidth;
		return binaryString;
	}

	public void setObjectBinaryString(String binaryString) {
		String bandwidthBinaryString = binaryString.substring(bandwidthStartBit, bandwidthEndBit + 1);

		this.setBandwidthBinaryString(bandwidthBinaryString);
	}

	public int getObjectFrameByteLength() {
		int objectLength = bandwidth.length();
		int headerLength = PCEPConstantValues.COMMON_OBJECT_HEADER_LENGTH;
		int objectFrameByteLength = (objectLength + headerLength) / 8;
		return objectFrameByteLength;
	}

	public String getObjectFrameBinaryString() {
		String headerBinaryString = this.getObjectHeader().getHeaderBinaryString();
		String objectBinaryString = this.getObjectBinaryString();

		return headerBinaryString + objectBinaryString;
	}

	/**
	 * bandwidth
	 */
	public int getBandwidthDecimalValue() {
		int decimalValue = (int) PCEPComputationFactory.getDecimalValue(bandwidth);
		return decimalValue;
	}

	public String getBandwidthBinaryString() {
		return this.bandwidth;
	}

	public void setBandwidthDecimalValue(int decimalValue) {
		int binaryLength = bandwidthLength;
		int maxValue = (int) PCEPComputationFactory.MaxValueFabrication(binaryLength);

		this.bandwidth = PCEPComputationFactory.setDecimalValue(decimalValue, maxValue, binaryLength);
	}

	public void setBandwidthBinaryString(String binaryString) {
		String checkedBinaryString = PCEPComputationFactory.setBinaryString(binaryString, bandwidthLength);
		this.bandwidth = checkedBinaryString;
	}

	public void setBandwidthBinaryString(int startingBit, String binaryString) {
		String checkedBinaryString = PCEPComputationFactory.setBinaryString(bandwidth, startingBit, binaryString, bandwidthLength);
		this.bandwidth = checkedBinaryString;
	}

	public String toString() {
		String bandwidthInfo = "Bandwidth=" + this.getBandwidthDecimalValue();

		String headerInfo = this.getObjectHeader().toString();
		String objectInfo = "<Bandwidth:" + bandwidthInfo + ">";

		return headerInfo + objectInfo;
	}

	public String binaryInformation() {
		String bandwidthBinaryInfo = getBandwidthBinaryString();

		String headerInfo = this.getObjectHeader().binaryInformation();
		String objectInfo = "[" + bandwidthBinaryInfo + "]";

		return headerInfo + objectInfo;
	}

	public String contentInformation() {
		return "[" + NAME + "]";
	}

}
