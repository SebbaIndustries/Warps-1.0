# Warps
Server and player warps module with advanced capabilities and lightweight game configuration. <br>
<br>
Version: `v0.0.1-Alpha` <br>
Native API: `Paper 1.15.2-R0.1-SNAPSHOT` <br>
Website: <a href="www.sebbaindustries.com">SebbaIndustries.com</a><br>
Source code: <a href="https://github.com/SebbaIndustries/Warps">github.com/SebbaIndustries/Warps</a><br>
Java Docs: <a href="https://github.com/SebbaIndustries/JavaDocs">github.com/SebbaIndustries/JavaDocs</a><br>
Developers: `SebbaIndustries`, `Frcsty` <br>

![Java CI with Maven](https://github.com/SebbaIndustries/Warps/workflows/Java%20CI%20with%20Maven/badge.svg)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](code_of_conduct.md)
![CodeQL](https://github.com/SebbaIndustries/Warps/workflows/CodeQL/badge.svg)

## Licence
<b>MIT License</b><br>
<br>
<b>Copyright (c) 2020 SebbaIndustries</b><br>
<br>
Permission is hereby granted, free of charge, to any person obtaining a copy <br>
of this software and associated documentation files (the "Software"), to deal <br>
in the Software without restriction, including without limitation the rights <br>
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br>
copies of the Software, and to permit persons to whom the Software is <br>
furnished to do so, subject to the following conditions: <br>
<br>
The above copyright notice and this permission notice shall be included in all <br>
copies or substantial portions of the Software. <br>
<br>
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br>
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br>
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br>
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br>
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br>
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br>
SOFTWARE. <br>
<br>

## Commands and permissions
<b>Flags:</b><br>
`-a` : Flag for admin commands, create, modify, delete warps for other players<br>
Permission: `warps.command.{command_name}.admin` <br>
Use: `/delwarp -a warpName`
<br>
<br>
`-o` : Flag for creating/modifying official/server warps<br>
Permission: `warps.command.{command_name}.official` <br>
Use `/setwarp -o warpName` <br><br>

|Command|Permission|Flags|Description|
|-------|----------|-----|-----------|
|/warps|warps.command.warps|`N/A`|Opens warps GUI|
|/warp {warp}|warps.command.warp|`N/A`|Teleports to selected warp|
|/delwarp {warp}|warps.command.delwarp|`-o` `-a`|Deletes warp|
|/setwarp {warp}|warps.command.setwarp|`-o` `-a`|Creates warp|
|/movewarp {warp}|warps.command.movewarp|`-o` `-a`|Moves warp|
|/modifywarp {warp} description {new warp description}|warps.command.modifywarp.description|`-o` `-a`|Modify description  of the warp|
|/modifywarp {warp} category {category}|warps.command.modifywarp.category|`-o` `-a`|Modify category of the warp|
|/listwarps {player}|warps.command.listwarps|`-a`|Lists all warps|