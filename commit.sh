#!/bin/bash

if [ "$#" -eq 0 ]; then
    echo "Error: No commit message provided"
    echo "Usage: ./commit.sh <commit message>"
    exit 1
fi

commit_message="$*"

git add -A

git commit -m "$commit_message"

if [ $? -eq 0 ]; then
    git push origin main
    
    if [ $? -eq 0 ]; then
        echo "Changes committed and pushed successfully!"
    else
        echo "Error: Failed to push changes. Please check your internet connection and try again."
    fi
else
    echo "Error: Commit failed. Please check your changes and try again."
fi