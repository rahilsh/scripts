#!/usr/bin/python

files = ['/path/to/.git/config']

#Code to replace remote url in git config
for file in files:
	with open(file) as f:
    		newText=f.read().replace('git@github.com:rahils/', 'git@github.com:rahilsh/')
    		print newText
	with open(file, "w") as f:
    		f.write(newText)

import os
for r, d, f in os.walk('/Users/rahil.shaikh/code/bitbucket/'):
	for file in f:
		if file == 'config':
			if os.path.basename(r) == '.git':
				print os.path.join(r,file)