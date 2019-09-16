# FileIO

This [Warp 10](https://www.warp10.io/) extension allow access to the filesystem from WarpScript.

# Installation

# Configuration


```
// activate the FileIO extension
warpscript.extension.fr.couincouin.fileIOextension = fr.couincouin.fileIO.FileIOExtension

// extension fileIO root path
// make sure it exists and that warp10 user could access it.
ext.fileIO.rootPath = ${standalone.home}/filetmp
```

The extension will not create the rootPath. The rootPath is the root of the filesystem WarpScript can access. You cannot use parent reference (../) while reading or writing your files. 

# Usage
