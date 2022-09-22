import { Stack } from 'aws-cdk-lib'
import { dynamodb } from 'aws-cdk-lib/aws-dynamodb'

export class DynamoDBStack extends Stack {
    const table = new dynamodb.Table(this, 'Table', {
        partitionKey: { name: 'id', type: dynamodb.AttributeType.STRING },
    });
}