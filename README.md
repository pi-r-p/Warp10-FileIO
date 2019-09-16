# FileIO

This [Warp 10](https://www.warp10.io/) extension allow access to the filesystem from WarpScript.
You can read/write byte arrays (BYTES), delete files, check the remaining free space, delete files.


# Installation 

## With [WarpFleet](https://warpfleet.senx.io/browse/fr.couincouin/FileIO)

Open a terminal in your Warp 10 directory, then
```
wf g fr.couincouin FileIO --confDir=etc/conf.d --libDir=lib --macroDir=macros
```

## Manual installation

Clone this repo and launch `./gradlew build`.
Copy the jar from `build/libs/` to your Warp 10 /lib/ directory or your Warp 10.

Update your Warp 10 configuration.


# Configuration


```
// activate FileIO extension
warpscript.extension.fr.couincouin.fileIOextension = fr.couincouin.fileIO.FileIOExtension

// FileIO extension root path
// make sure it exists and that warp10 user could access it.
ext.fileIO.rootPath = ${standalone.home}/filetmp
```

The extension will not create the rootPath. The rootPath is the root of the filesystem WarpScript can access. You cannot use parent reference (../) in filenames. If you plan to use subdirectories, you also need to create them manually.

# Usage

- `FILEDF` return the free space (in bytes)
- `'./' FILELIST` lists files in the required directory (but not subdirectories)
- `'filename' FILEREAD` returns a byte array with file content.
- `'file content' 'utf8' ->BYTES 'filename' FILEWRITE` writes a byte array in a file. File is created or replaced.
- `'file content' 'utf8' ->BYTES 'filename' FILEAPPEND` write at the end of the file, or create it if needed.
- `'filename' FILEDELETE` delete the file.


See [function doc](https://warpfleet.senx.io/browse/fr.couincouin/FileIO#functions) for more information