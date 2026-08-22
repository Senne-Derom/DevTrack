#!/bin/bash
set -e

PROJECT="/home/senned/Documents/programming/DevTrack/DevTrack"
BACKEND="$PROJECT/Backend"
FRONTEND="$PROJECT/Frontend/devtrack"

echo "Checking for updates..."

cd "$PROJECT"

git fetch origin

LOCAL=$(git rev-parse HEAD)
REMOTE=$(git rev-parse origin/main)

if [ "$LOCAL" != "$REMOTE" ]; then
    echo "Updating repository..."

    git pull origin main

    echo "Building backend..."
    cd "$BACKEND"
    mvn clean package

    echo "Building frontend..."
    cd "$FRONTEND"
    npm install
    npm run test
    npm run build
#    npm run start

    echo "Restarting services..."
    sudo systemctl restart backend
    sudo systemctl restart frontend    

    echo "Deployment complete."
else
    echo "Already up to date."
fi
