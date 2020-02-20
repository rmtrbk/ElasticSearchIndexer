# ElasticSerchIndexer
`Lambda function` that updates `indexes` in `ElasticSearch` triggered by `SQS` event

## Design
* `MetadataExtractorServiceImpl` extracts metadata from an event payload.

* `ESTransactionManagerSeviceImpl` manages all `Elasticsearch` transactions.

* `ClientBuilderManager` utility class build an `Elasticsearch` client to access `Elasticsearch` APIs.

* `PropertyManager` reads required properties from the environment and makes them available across the application.

## Configuring AWS Infrastructure
* Create an `Elasticsearch` domain(e.g. `bookinventory`).

* Create an `AWS Lambda` with your preferred function name(e.g. `bookInventoryESIndexer`) with runtime `Java 8`. In `Permissions` section select a role by creating a new role or use an existing role(e.g. `LambdaFullAccess`).

* In created `Lambda Function`'s `Configuration` tab's `Designer` section, add an `SQS` trigger. Note that trigger can be configured at `SQS` end too. Which ever mechanism you prefer would do the job.

* In `Environment variables` section add below 4 environment variables.
`es_aws_secret`, `es_aws_accessKey`, `es_service_name` and `es_endpoint`.
Save the relevant values you want to use(ones you used while creating the table).

* In `Function code` section, update `Handler` to denote event listener of the trigger to `updater.elasticsearchindexer.event.SQSRequestEventHandler::handleRequest`

* Upload the `Lambda` `jar` file and click on `Save`

* Note that if you have a higher load you may need to configure `Basic settings` section with appropriate values for memory and execution time thresholds.

## How to test
This is a simple `Java` `Maven` project.

Build `Lambda` `jar` with `mvn clean install package` and upload it to the created `Lambda` function.

Send a message with correct `metadata` to the `SQS` queue. You may need to use a simple code snippet to get the relevant `metadata` attached to the message.

Check `Elasticsearch` domain(using `Kibana` if you prefer). you should see the record.

Below are some `Kibana` start up help queries just in case if they are required :)

```sql
GET _search
{
  "query": {
    "match_all": {}
  }
}
```

```sql
DELETE book-metadata-index
```

You can check `Lambda` logs with using `CloudWatch` too.



