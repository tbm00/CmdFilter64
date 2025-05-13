# CmdFilter64
A spigot plugin that filters Java command suggestions with a blacklist.

Created by tbm00 for play.mc64.wtf.

## Features
- Filter specific commands.
- Filter all commands prefixed with a plugin name, i.e., it contains ":".

## Dependencies
- **Java 17+**: REQUIRED
- **Spigot 1.18.1+**: UNTESTED ON OLDER VERSIONS

## Commands
#### Player Commands
- none

#### Admin Commands
- `/cmdfilter add <cmd>`
- `/cmdfilter remove <cmd>`
- `/cmdfilter check <cmd>`
- `/cmdfilter list`

## Permissions
#### Player Permissions
- none *(default: everyone)*

#### Admin Permissions
- `cmdfilter64.bypass` Ability to bypass the suggestion blacklist *(default: op)*
- `cmdfilter64.admin` Ability to use the /cmdfilter admin command *(default: op)*


## Config
```
# CmdFilter64 v0.0.0-beta by @tbm00
# https://github.com/tbm00/CmdFilter64

enabled: true

lang:
  prefix: "&8[&fCmdFilter&8] &7"

features:
  blockPluginPrefixedCommands: true
```