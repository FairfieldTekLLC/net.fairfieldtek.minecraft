# net.fairfieldtek.minecraft
Minecraft tools
main: net.fairfieldtek.minecraft.Plugin

commands:
  fft.we.set:
    description: Sets the blocks inside  your selection.
    Usage: /fft.we.set <To Material Name>
           /fft.we.set <To Material Name> <Magic#>
           /fft.we.set <To Material Name> <From Material>
           /fft.we.set <To Material Name> <Magic#> <From Material>
           /fft.we.set <To Material Name> <Magic#> <From Material> <Magic#>
  fft.we.matlist:
    description: Prints a list of the available materials available
                 in the game.  It does not show magic numbers, the best way to 
                 determine a magic number is to inspect (/fft.we.in) a block
                 that you want a magic number for.
    usage: /fft.we.matlist <Name or partial name>
  fft.we.select:
    description: Sets a selection point
    usage: /fft.we.select
           /fft.we.select X Y Z
  fft.we.print:
    description: Prints your selection start and selection end.
    usage: /fft.we.print
  fft.we.size:
    description: Prints the dimensions of your selection
    usage: /fft.we.size    
  fft.we.clear:
    description: Clears the Selections and the Clipboard
    usage: /fft.we.clear
  fft.we.clearHistory:
    description: Clears the undo history of you pastes.  use undo (/fft.we.undo)
                 to undo a paste, delete of set.
    usage: /fft.we.clearhistory
  fft.we.paste:
    description: Pastes your clipboard.
    usage: /fft.we.paste
           /fft.we.paste <Rotational Axis (X or Y or Z)> <Degrees (90 or 180 or 270)>
           /fft.we.paste x y z
           /fft.we.paste x y z <Rotational Axis (X or Y or Z)> <Degrees (90 or 180 or 270)>
  fft.we.copy:
    description: Copies your selection to the clipboard.
    usage: /fft.we.copy    
  fft.we.undo:
    description: Will undo your last World Edit Command.
    usage: /fft.we.undo        
  fft.we.in:
    description: Inspects the block.
    usage: /fft.we.in
  fft.we.rot:
    description: Rotates a single block, use /fft.we.paste with a rotation to
                 rotate a large area.
    usage: /fft.we.rot {Axis} {Degrees}
           /fft.we.rot x y z {Axis} {degrees}
  fft.we.rotate:
    description: Rotates a single block, use /fft.we.paste with a rotation to
                 rotate a large area.
    usage: /fft.we.rotate
           /fft.we.rotate {Axis} {Degrees}
           /fft.we.rotate x y z {Axis} {degrees}
  fft.we.del:
    description: Sets all blocks in your selection to Air.  Also removes all
                 water sources.
    usage: /fft.we.del    
  fft.we.delete:
    description: Sets all blocks in your selection to Air.  Also removes all
                 water sources.
    usage: /fft.we.delete
  fft.we.chunkregen:
    description: Regenerate Chunk using the server seed.  Does not restore 
                 Trees or buildings.
    usage: /fft.we.chunk.regen
  fft.we.eraseLiquid:
    description: Removes Water and Lava from x y z location or where player is 
                 looking.  Blocks out is how many blocks from the point you 
                 specify will be evaluated.
    usage: /fft.we.eraseLiquid X Y Z (Blocks Out)
  fft.reg:
    description: Register Email to Minecraft Account.  You will recieve an
                 email with a five alpha-numeric authentication code
    usage: /fft.reg <Email Address>
  fft.auth:
    description: Send Authentication Code to Email or authenticates you against
                 the system if you provide the five alpha-numeric authentication
                 code
    usage: /fft.auth 
           /fft.auth <5 alpha-numeric Authentication code>
  fft.ls:
    description: Lists all Blueprints and Directories in the current Directory.
    usage: /fft.ls
  fft.cd:
    description: Changes your current directory to the directory specified
    usage: /fft.cd <Directory Name>
  fft.rm:
    description: Deletes the Directory or Blueprint
    usage: /fft.rm <Folder or Blueprint Name>
  fft.mk: 
    description: Creates a folder in the folder you are currently in
    usage: /fft.mk <Folder Name>
  fft.save: 
    description: Saves the current clipboard as a BluePrint
    usage: /fft.save <Blueprint Name>
  fft.load:
    description: Loads the Blueprint into your clipboard
    usage: /fft.load <Blueprint Name>
  fft.we.distr:
    description: Gets the block distribution of the current clipboard
    usage: /fft.we.distr
