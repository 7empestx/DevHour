
import { Effect, PolicyStatement } from 'aws-cdk-lib/aws-iam'
import { Constants } from './constants'


export module PolicyStatements {

    export module DynamoDB {

        export class DynamoDBPolicyStatement extends PolicyStatement {

            constructor(resourceArns: string[]) {
                super({
                    effect: Effect.ALLOW,
  		    actions: Constants.DynamoDB.User.AllowActions,
                    resources: resourceArns
                });
            }

        }

    }

}
