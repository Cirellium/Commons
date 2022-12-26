/*
 * Copyright (C) 2022 Cirellium Network - All Rights Reserved
 *
 * Created by FearMyShotz on Mon Dec 26 2022 15:34:09
 *
 * Version.java is part of Cirellium Commons
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 */

package net.cirellium.commons.core.version;


/**
 * A class that represents a version.
 * Provides several methods to compare versions.
 * 
 * @author FearMyShotz
 * @version 1.0
 */
public class Version implements Comparable<Version> {
    
    private static final String SEPARATOR = "[_.-]";

    private String version;

    /*
     * Creates a new Version object.
     * @param version The version string
     * @throws NumberFormatException If the version does not match the format
     * @throws NullPointerException If the version is null
     */
    public Version(String version) {
        if (version == null) {
            throw new NullPointerException("Version can not be null");
        }
        if (!version.matches("[0-9]+(\\.[0-9]+)*")) {
            throw new NumberFormatException("Invalid version format");
        }
        this.version = version;
    }

    /*
     * Returns whether or not this version is greater than the one passed.
     * @return true if this version is greater than the one passed, false otherwise
     * @param v The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#compareTo(String v)
     */
    public boolean isGreaterThan(Version v) {
        return compareTo(v) > 0;
    }

    /*
     * Returns whether or not this version is less than the one passed.
     * @return true if this version is less than the one passed, false otherwise
     * @param v The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#compareTo(String v)
     */
    public boolean isLessThan(Version v) {
        return compareTo(v) < 0;
    }

    /*
     * Returns whether or not this version is greater than or equal to the one passed.
     * @return true if this version is greater than or equal to the one passed, false otherwise
     * @param v The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#compareTo(String v)
     */
    public boolean isGreaterThanOrEqualTo(Version v) {
        return compareTo(v) >= 0;
    }

    /*
     * Returns whether or not this version is less than or equal to the one passed.
     * @return true if this version is less than or equal to the one passed, false otherwise
     * @param v The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#compareTo(String v)
     */
    public boolean isLessThanOrEqualTo(Version v) {
        return compareTo(v) <= 0;
    }

    public boolean isEqualTo(Version v) {
        return compareTo(v) == 0;
    }

    /*
     * This method overrides the default implementation of the compareTo method.
     * @return 0, 1 or -1
     * @param o The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#compareTo(String v)
     */
    @Override
    public int compareTo(Version o) {
        return compareTo(o.toString());
    }

    /*
     * This method is used to compare versions.
     * If the version is not valid, it will return 0.
     * If the version is greater than the one passed, it will return 1.
     * If the version is less than the one passed, it will return -1.
     * @return 0, 1 or -1
     * @param v The version to compare to
     * @throws NumberFormatException If one of the versions is not valid
     * @throws NullPointerException If the version passed is null
     * @see Version#toString()
     * 
     */
    public int compareTo(String v) {
        String[] thisParts = version.split(SEPARATOR);
        String[] thatParts = v.split(SEPARATOR);
        int length = Math.max(thisParts.length, thatParts.length);
        for (int i = 0; i < length; i++) {
            int thisPart = i < thisParts.length ? Integer.parseInt(thisParts[i]) : 0;
            int thatPart = i < thatParts.length ? Integer.parseInt(thatParts[i]) : 0;
            if (thisPart < thatPart) {
                return -1;
            }
            if (thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }

    /*
     * Returns whether the given version is equal to the current version.
     * This method overrides the default implementation of the equals method.
     * @return true if the versions are equal, false otherwise
     * @param o The version to compare to
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Version version1 = (Version) o;

        return version != null ? version.equals(version1.version) : version1.version == null;
    }

    /*
     * Returns the String representation of the version.
     * @return The version String, or null if the version String is null.
     */
    @Override
    public String toString() {
        return version != null ? version : "";
    }
}