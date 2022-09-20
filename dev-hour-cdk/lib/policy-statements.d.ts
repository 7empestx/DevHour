/**
 * Policy Statements
 * @version 0.9.0
 */
import { PolicyStatement } from 'aws-cdk-lib/aws-iam';
export declare module PolicyStatements {
    module DynamoDB {
        class BasicReadPolicyStatement extends PolicyStatement {
            constructor(resourceArns: string[]);
        }
    }
}
