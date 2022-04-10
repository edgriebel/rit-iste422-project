# Release Notes

* Removed some depricated "new Integer(...)" casting in EdgeTable.java
* Fixed bug where arrays wouldn't get created if trying to open a file that wasn't the correct type in "EdgeConvertFileParser.java"
* Squashed bug in "EdgeConvertGUI.java" where related tables is null, and creates a NullPointerException when attempting to retrieve the array's length. Specifically occurs when opening an .edg file when opening after trying to open a save file.
* 