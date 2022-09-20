import { PolicyStatement } from 'aws-cdk-lib/aws-iam';
export declare module PolicyStatements {
    module DynamoDB {
        class DynamoDBPolicyStatement extends PolicyStatement {
            constructor(resourceArns: string[]);
        }
    }
}
