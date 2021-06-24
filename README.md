# minio-aws3-storage-rest-api
 Spring Boot 2 minio/aws3 storage rest API.
 Setup of Minio Cluster can be found in docker-compose file.
 
 ## REST API:
 
- /buckets: Lists all existing buckets.
- /{path}/objects: Lists all existing objects from the {path} of the default bucket.
- /{path}/upload: Upload object/s to {path} in default bucket.
- /{path}/download/{object}: Download {object} from {path} from default bucket.
- /{path}/delete/{object}: Delete {object} from {path} from default bucket.
