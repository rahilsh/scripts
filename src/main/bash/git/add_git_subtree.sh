#!/bin/bash

# Check if a Git repository URL is provided
if [ -z "$1" ]; then
    echo "Usage: $0 <git_repository_url> [optional_repository_name]"
    exit 1
fi

# Extract repository name from URL or use the optional repository name if provided
repo_url=$1
if [ -n "$2" ]; then
    repo_name=$2
else
    repo_name=$(basename -s .git "$repo_url")
fi

if [ -n "$3" ]; then
    branch=$3
else
    branch=master
fi

# Add the remote repository
git remote add $repo_name $repo_url

# Add the repository as a subtree
git subtree add --prefix $repo_name $repo_url $branch --squash
cp .git/config .gitsubtree
echo "Remote '$repo_name' added successfully."
echo "Git subtree '$repo_name' added successfully."

