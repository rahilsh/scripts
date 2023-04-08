tell application "Notes"
	repeat with myFolder in folders
		set myNotes to notes of myFolder
		set Foldername to name of myFolder
		repeat with thisMessage in myNotes
			set myTitle to the name of thisMessage
			set myText to the body of thisMessage
			set myCreateDate to the creation date of thisMessage
			set myModDate to the modification date of thisMessage
			tell application "Evernote"
				set myNote to create note with text myTitle title myTitle notebook Foldername
				set the HTML content of myNote to myText
				set the creation date of myNote to myCreateDate
				set the modification date of myNote to myModDate
			end tell
		end repeat
	end repeat
end tell

with timeout of (30 * 60) seconds
	tell application "Finder"
		set setBackupfolder to "/Users/rahil-3559/Documents/Docs/EverNote/" as POSIX file -- set backup folder path
		-- confirm valid path exists
		if setBackupfolder exists then
		else
			display dialog "The backup path you set does not exist. Please go to line 3, set a valid path and re-run this script." giving up after 99999999
			error number -128
		end if
		set backupFolder to setBackupfolder as text
	end tell
	-- activate evernote
	tell application "Evernote"
		activate
		-- wait for evernote to come to the front
		tell application "Finder"
			set frontAppname to "whatever"
			repeat while frontAppname is not "Evernote"
				tell application "System Events" to set frontAppname to name of first process where frontmost is true
			end repeat
			delay 1 -- wait a little more
		end tell
		-- do the backup
		set allNotebooks to every notebook
		repeat with currentNotebook in allNotebooks
			set notebookName to (the name of currentNotebook)
			set allNotes to every note in notebook notebookName
			if every note in notebook notebookName exists then -- proceed if notebook is not empty
				set dateString to (current date) as text -- get the date
				set newDatestring to {}
				repeat with i in dateString -- change colon in time stamp (changed to slash in Finder) to underscore
					if (i as string) is ":" then
						set end of newDatestring to "_"
					else
						set end of newDatestring to (i as string)
					end if
				end repeat
				set exportFilename to (backupFolder & notebookName & ".enex")
				export allNotes to exportFilename
			end if
			delete currentNotebook
		end repeat
		-- confirmation dialog
		display dialog "The backup of your Evernote notebooks is complete." buttons {"View Backup Folder", "OK"} default button 2 giving up after 99999999
		if the button returned of the result is "View Backup Folder" then
			tell application "Finder"
				activate
				open backupFolder
			end tell
		end if
	end tell
end timeout