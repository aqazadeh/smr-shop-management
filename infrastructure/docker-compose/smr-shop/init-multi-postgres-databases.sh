function create_databases() {
    database=$1
    password=$2
    echo "Creating user and database '$database' with password '$password'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
      CREATE USER $database with encrypted password '$password';
      CREATE DATABASE $database;
      GRANT ALL PRIVILEGES ON DATABASE $database TO $database;
EOSQL
}


if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
  echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
  for db in $(echo $POSTGRES_MULTIPLE_DATABASES | tr ',' ' '); do
    echo "user is $user and pass is $POSTGRES_PASSWORD"
    create_databases $db $POSTGRES_PASSWORD
  done
  echo "Multiple databases created!"
fi