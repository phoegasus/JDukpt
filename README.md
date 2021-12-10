
# JDukpt
A Java implementation of [Dukpt.NET](https://github.com/sgbj/Dukpt.NET).

## Table of contents
* [Introduction](#introduction)
* [Java version support](#java-version-support)
* [How to use](#how-to-use)

## Introduction
This is a Java implementation of [Dukpt.NET](https://github.com/sgbj/Dukpt.NET),
> a C# implementation of the Derived Unique Key Per Transaction (DUKPT) process that's described in Annex A of ANS X9.24-2004.

For people who can't or won't pay 140$ for the ANS X9.24-2004 (and for those who do).

If you implement this in a different programming language, please consider sharing it with the community.

## Java version support
|Version| Status |
|--|--|
| Java 7 and older| ⚠️ Not tested ⚠️ |
| Java 8 | ✅ Functional ✅ |
| Java 9 and newer | ⚠️ Not tested ⚠️ |

## How to use

Instanciate the JDukpt class and call the appropriate method. 

``JDukpt jdukpt = new JDukpt();

jdukpt.encryptPIN(bdk, ksn, data);
jdukpt.decryptPIN(bdk, ksn, encryptedData);
jdukpt.encryptData(bdk, ksn, data);
jdukpt.decryptData(bdk, ksn, encryptedData);``

The parameters and return value are either String, byte[], or BigInteger.

You can provide your own DES and 3DES implementation by implementing the Crypto interface, and providing it as an argument to the JDukpt constructor.
``Crypto crypto = new MyCryptoImpl();

JDukpt jdukpt = new JDukpt(crypto);``
