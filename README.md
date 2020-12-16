Project Blockelot.Com

This program is a SpigotMc.org minecraft plug-in.

For more information, go to http://www.blockelot.com

Native Minecraft Version:
1.16
Tested Minecraft Versions:
1.16
Source Code:
https://github.com/FairfieldTekLLC/net.fairfieldtek.minecraft
Contributors:
ChapleKeep, GrumpyPasta
Donation Link:
https://www.patreon.com/Blockelot
Blockelot

Tested on 1.16.4

*Looking for people to help with Website, Plugin and MySql database, email me @ vince at FairfieldTek.com


Ok, so Blockelot allows you to make schematics of things in minecraft and save them in the cloud. Then if you go to another server, you can just go into your cloud storage, download them and paste it in the new world.

Watch my release video!



About me and the Plugin,

My name is Vincent Gee, I am the Vice President of Software Development at B. F. Saul Company, a large company in Bethesda Maryland. I developed this plugin in my free time and boredom due to Covid 19 under my own consulting company FairfieldTek L.L.C..

Feel free to email me at Vince at Fairfieldtek.com if you have any questions or problems.

Also, your more than welcome to send me a friend request on Facebook, https://www.facebook.com/VincentGeeFairfield

Or hop on my discord server at https://discord.gg/sKdSfjV

Also, the plugin is big probably because of my ignorance of the details of java. I included the Google Json library and Apache http code to the jar cause it was the only way I could get it to run. Anyone that wants to help reduce the size is welcome.



Features
A online Block Bank where you can deposit blocks into and withdrawal blocks from.
The Block Bank Storage is unique to each world.
A online Storage System where you can save and load Schematics from.
Schematics stored in your online account follow you server to server as long as the server operator has the plugin installed.
Block Copy - Copy anything in the world, default includes container items
Block Paste - Paste your designs on any world that has the plugin
Block Rotation - Rotate schematics on any axis at 90 180 or 270 degrees.
And much more!
What are the commands?
Help
/b.help Shows the help screen.
/b.about Provides Author information.

Banking
/b.bbinv Request a inventory report from bank.
/b.bbdep [Material] [Amount] Deposit material in bank.
/b.bbdep true Deposit all blocks in inventory in bank.
/b.bbwd [Material] [Amount] Withdrawal material from bank.

World Editing
/b.we.clear Clears the Selections and the Clipboard.
/b.we.clearHistory Clears the undo history of you pastes.
/b.we.size Prints the dimensions of your selection
/b.we.print Prints your selection start and selection end.
/b.we.select Sets a selection point
/b.we.copy Copies your selection into your clipboard.
/b.we.del Set Selection into MINECRAFT:AIR
/b.we.delete Set Selection into MINECRAFT:AIR
/b.we.distr Gets the block distribution of the current clipboard.
/b.we.paste Pastes your clipboard to the world.
/b.we.paste X Y Z
/b.we.paste [Rotational Axis X or Y or Z] [Degrees 90 180 270)]
/b.we.paste x y z
/b.we.paste x y z [Rotational Axis X or Y or Z] [Degrees 90 180 270)]
/b.we.stripmine Clears the Chunk and puts all blocks in chests
/b.we.stripmine true Clears the chunk and deposits blocks in bank, leaves rest in chests
/b.we.undo Undoes your last action

Registration
Registers your player to your email address.Forces a reconnection to the cloud storage.
/b.reg [EmailAddress]
/b.auth


File System
/b.ls - Directory Listing
/b.cd [Directory] -Change Directory
/b.rm [File] - Remove File
/b.rm [Folder] - Remove Folder
/b.mk [Folder] - Make Folder
/b.save [File] Saves the current clipboard to the file.
/b.load [File] Loads the file into the clipboard.

Permissions:

user: Blockelot.WorldEditor.User
clear: Blockelot.WorldEditor.User.Clear
clearhistory: Blockelot.WorldEditor.User.ClearHistory
size: Blockelot.WorldEditor.User.Size
print: Blockelot.WorldEditor.User.Print
select: Blockelot.WorldEditor.User.Select
editor: Blockelot.WorldEditor.Editor
copy: Blockelot.WorldEditor.Editor.Copy
delete: Blockelot.WorldEditor.Editor.Delete
distr: Blockelot.WorldEditor.Editor.Distr
paste: Blockelot.WorldEditor.Editor.Paste
stripmine: Blockelot.WorldEditor.Editor.StripMine
undo: Blockelot.WorldEditor.Editor.Undo
filesystem: Blockelot.FileSystem.User
bank: Blockelot.Bank



Where I want to go with it....

I want to add more world Building functions for spheres etc. in the plugin.

And, of course I want to build out the website so people can log into it and view their schematics in 3d on the web. Maybe even come up with a way for people to sell their schematics, post reviews, etc.

No more messing with command blocks, no more messing with nbt's. Just copy Blockelot.com.jar to your plugs folder, start the server, configure the options and reboot!

Now, those of you who are wise are thinking, well how long is this guy going to pay for the cloud storage... Well the server costs me about 30 dollars a month, I budgeted to operate the plugin for a year, if I cannot get 30 people who use the plugin to donate 1 dollar a month, well then my plugin sucks and I deserve to go out of business.

Beyond that, please do not decompile, reverse engineer, yada, yada, this and that.

This plugin took me 6 months to build and test.

Of course, catch me on discord if you need any help.



License.

//End-User License Agreement (EULA) of Blockelot
//
//This End-User License Agreement ("EULA") is a legal agreement between you and Fairfield Tek L.L.C.. Our EULA was created by EULA Template for Blockelot.
//
//This EULA agreement governs your acquisition and use of our Blockelot software ("Software") directly from Fairfield Tek L.L.C. or indirectly through a Fairfield Tek L.L.C. authorized reseller or distributor (a "Reseller"). Our Privacy Policy was created by the Privacy Policy Generator.
//
//Please read this EULA agreement carefully before completing the installation process and using the Blockelot software. It provides a license to use the Blockelot software and contains warranty information and liability disclaimers.
//
//If you register for a free trial of the Blockelot software, this EULA agreement will also govern that trial. By clicking "accept" or installing and/or using the Blockelot software, you are confirming your acceptance of the Software and agreeing to become bound by the terms of this EULA agreement.
//
//If you are entering into this EULA agreement on behalf of a company or other legal entity, you represent that you have the authority to bind such entity and its affiliates to these terms and conditions. If you do not have such authority or if you do not agree with the terms and conditions of this EULA agreement, do not install or use the Software, and you must not accept this EULA agreement.
//
//This EULA agreement shall apply only to the Software supplied by Fairfield Tek L.L.C. herewith regardless of whether other software is referred to or described herein. The terms also apply to any Fairfield Tek L.L.C. updates, supplements, Internet-based services, and support services for the Software, unless other terms accompany those items on delivery. If so, those terms apply.
//
//License Grant
//Fairfield Tek L.L.C. hereby grants you a personal, non-transferable, non-exclusive licence to use the Blockelot software on your devices 
//in accordance with the terms of this EULA agreement.
//
//You are permitted to load the Blockelot software (for example a PC, laptop, mobile or tablet) under your control. You are responsible
//for ensuring your device meets the minimum requirements of the Blockelot software.
//
//You are not permitted to:
//
//Edit, alter, modify, adapt, translate or otherwise change the whole or any part of the Software nor permit the whole or any part of
//the Software to be combined with or become incorporated in any other software, nor decompile, disassemble or reverse engineer the 
//Software or attempt to do any such things
//
//Reproduce, copy, distribute, resell or otherwise use the Software for any commercial purpose
//Allow any third party to use the Software on behalf of or for the benefit of any third party
//Use the Software in any way which breaches any applicable local, national or international law
//use the Software for any purpose that Fairfield Tek L.L.C. considers is a breach of this EULA agreement
//Intellectual Property and Ownership

//Fairfield Tek L.L.C. shall at all times retain ownership of the Software as originally downloaded by you and all subsequent downloads
// of the Software by you. The Software (and the copyright, and other intellectual property rights of whatever nature in the Software,
// including any modifications made thereto) are and shall remain the property of Fairfield Tek L.L.C..

//Fairfield Tek L.L.C. reserves the right to grant licences to use the Software to third parties.

//Termination
//This EULA agreement is effective from the date you first use the Software and shall continue until terminated. 
//You may terminate it at any time upon written notice to Fairfield Tek L.L.C..

//It will also terminate immediately if you fail to comply with any term of this EULA agreement. Upon such termination,
// the licenses granted by this EULA agreement will immediately terminate and you agree to stop all access and use of the Software.
//The provisions that by their nature continue and survive will survive any termination of this EULA agreement.
//
//Governing Law
//This EULA agreement, and any dispute arising out of or in connection with this EULA agreement, 
//shall be governed by and construed in accordance with the laws of us.
//
//By accepting this EULA, you agree to hold harmless (Blockelot) FairfieldTek in the event that the cloud storage service is discontinued.
//
//Blockelot and it's Cloud Storage is provided "as is", without warranties of any kind.
