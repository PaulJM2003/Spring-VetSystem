# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
  echo "Docker is not running, please start it and try again."
  exit 1
fi

# Clean up existing files
echo "---------------------------------- Cleaning up residual database files ----------------------------------"

# Remove database container and volume
docker container rm team-project-group-p03-02-database-1
docker volume rm team-project-group-p03-02_db

echo "----------------------------------- Building and running containers -----------------------------------"

# Rebuild docker containers
docker-compose build

# Run docker containers
docker-compose up