## Run API
```cmd
cd api
./gradlew
./gradlew bootRun
```

## Run Client
```cmd
cd BeanHub.Client
./gradlew
./gradlew app:run   
```

## auth.tfvars
```hcl
access_key = "YOUR_ACCESS_KEY_HERE"
secret_key = "YOUR_SECRET_KEY_HERE"
db_password = "YOUR_DB_PASSWORD"
db_username = "YOUR_DB_USERNAME"
```

## Apply terraform
```cli
terraform apply -var-file="auth.tfvars"
```

## Destroy terraform
```cli
terraform destroy -var-file="auth.tfvars"
```