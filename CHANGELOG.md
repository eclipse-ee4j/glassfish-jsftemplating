# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Improvements

### Fixes

### Notes

## 2.1.4

### Notes

Project donated to Eclipse, first release under Eclipse for the EE4J 8 release.

## 2.1.3

### Notes

Few Bug Fixes and File headers updated.

## 2.1.2

### Notes

Relaxed OSGi import for javax.servlet.* to "[3.0,5)"

## 2.1.1

### Notes

- Move the build system to Maven 3.x
- Updated the Annotation Processor to JSR 169 in order to support JSK8 (APT has
 been removed effectively in JDK8).
- refactored events and task to match the state of com.sun.jsft in version 2.1.0.

## 2.1.0

## Notes

- Created new "jsft.jar" which focuses on supporting true Facelets while
  providing features, such as JSFT's event model.
- Added new EL-based event handling <jsft:event> to jsft.jar.
- Added initial implementation of "deferredFragments" -- allowing slow data
  to be processed in parallel so rendering can be sped up.  WIP
- Minor NPE bug fix re: (null) viewId being passed in -- why do that?

## 2.0.4

- Fixed NPE in ResourceContentSource when empty file ("") was requested.
- Added ability to define suffix resource exclusions (i.e. *.jsf,
  *.xml, etc.) for FileStreamer
- Changed build to no longer generate jsftemplating-base.jar
- Added flag for components to suppress duplicate id checking in debug mode.
  Flag is "skipIdCheck" and should be set as an attribute in the .jsf file
  of the component which should avoid checking for duplicate ids.
- Added ability to define "instance" handlers.  These are essentially macro
  handlers that can be defined on a page (LayoutDefinition actually).
- Updated the faces-config.xml file to use 1.2 (previously referring to 1.1)

## 2.0.3

- Made duplicate component ids in debug mode non-fatal, but
  still logs a warning message.
- Now building for Java 6

## 2.0.2

- Changed urlencode handler to use UTF-8 if no encoding is specified
  (vs. system default), also changed behavior to return null if null
  is given to encode (vs. throwing a NPE).
- Ensured JSFT "template" LayoutDefinitionManager is used first (unless
  overridden) and that no LDM's are added more than once.
- Removed DynaFaces references (DF is no longer supported w/ this version)
- Added change to no process JSF2 Ajax requests targeted to @all via JSF2
  but instead handle them as a Full Page request.