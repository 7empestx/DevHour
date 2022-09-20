/**
 * Project Roles
 * @version 0.9.0
 */
import { Construct } from 'constructs';
import { Role } from 'aws-cdk-lib/aws-iam';
export declare module Roles {
    module Cognito {
        class UnauthenticatedRole extends Role {
            constructor(scope: Construct, id: string, arns: string[], identityPoolRef: any);
        }
        class AuthenticatedRole extends Role {
            constructor(scope: Construct, id: string, arns: string[], identityPoolRef: any);
        }
    }
}
